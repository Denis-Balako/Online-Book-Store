package com.balako.onlinebookstore.service.impl;

import com.balako.onlinebookstore.dto.order.request.CreateOrderRequestDto;
import com.balako.onlinebookstore.dto.order.request.UpdateOrderRequestDto;
import com.balako.onlinebookstore.dto.order.response.OrderDto;
import com.balako.onlinebookstore.dto.order.response.OrderItemDto;
import com.balako.onlinebookstore.enums.Status;
import com.balako.onlinebookstore.exception.EntityNotFoundException;
import com.balako.onlinebookstore.mapper.OrderItemMapper;
import com.balako.onlinebookstore.mapper.OrderMapper;
import com.balako.onlinebookstore.model.Order;
import com.balako.onlinebookstore.model.OrderItem;
import com.balako.onlinebookstore.model.ShoppingCart;
import com.balako.onlinebookstore.model.User;
import com.balako.onlinebookstore.repository.cart.ShoppingCartRepository;
import com.balako.onlinebookstore.repository.cartitem.CartItemRepository;
import com.balako.onlinebookstore.repository.order.OrderRepository;
import com.balako.onlinebookstore.repository.orderitem.OrderItemRepository;
import com.balako.onlinebookstore.service.OrderService;
import com.balako.onlinebookstore.service.UserService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final UserService userService;
    private final ShoppingCartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderItemMapper orderItemMapper;
    private final OrderMapper orderMapper;

    @Override
    public OrderDto create(CreateOrderRequestDto requestDto) {
        User user = userService.getCurrentAuthenticatedUser();
        ShoppingCart cart = cartRepository.findByUserWithCartItems(user.getId()).orElseThrow(
                () -> new EntityNotFoundException("Can't find cart by user email: "
                        + user.getEmail()));

        Order order = orderMapper.toModel(requestDto);
        order.setUser(user);
        order.setStatus(Status.getDefaultStatus());
        order.setOrderDate(LocalDateTime.now());
        order.setTotal(BigDecimal.valueOf(cart.getCartItems().stream()
                .mapToDouble(cartItem -> cartItem.getBook().getPrice().doubleValue()
                        * cartItem.getQuantity())
                .sum()));

        Order savedOrder = orderRepository.save(order);
        List<OrderItem> orderItems = cart.getCartItems().stream()
                .map(orderItemMapper::toModel)
                .peek(orderItem -> orderItem.setOrder(savedOrder))
                .toList();
        savedOrder.setOrderItems(Set.copyOf(orderItemRepository.saveAll(orderItems)));
        cartItemRepository.deleteAll(cart.getCartItems());
        return orderMapper.toDto(savedOrder);
    }

    @Override
    public List<OrderDto> findAllOrders(Pageable pageable) {
        User user = userService.getCurrentAuthenticatedUser();
        return orderRepository.findAllByUserId(user.getId(), pageable).stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public OrderDto update(Long id, UpdateOrderRequestDto requestDto) {
        Order order = orderRepository.findByIdWithItems(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find order by id: " + id)
        );
        try {
            order.setStatus(Status.valueOf(requestDto.status()));
        } catch (IllegalArgumentException e) {
            throw new EntityNotFoundException("Can't find status with name: "
                    + requestDto.status());
        }
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public List<OrderItemDto> findAllOrderItems(Long orderId, Pageable pageable) {
        User user = userService.getCurrentAuthenticatedUser();
        return orderItemRepository
                .findAllByOrderIdAndUserId(orderId, user.getId(), pageable).stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    @Override
    public OrderItemDto getOrderItem(Long orderId, Long itemId) {
        User user = userService.getCurrentAuthenticatedUser();
        return orderItemRepository
                .findByIdAndOrderIdAndUserId(itemId, orderId, user.getId())
                .map(orderItemMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find order item by id: " + itemId
                ));
    }
}
