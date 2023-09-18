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
                .mapToInt(cartItem -> cartItem.getBook().getPrice().intValue())
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
        return null;
    }

    @Override
    public List<OrderItemDto> findAllOrderItems(Pageable pageable) {
        return null;
    }

    @Override
    public OrderItemDto getOrderItem(Long orderId, Long itemId) {
        return null;
    }
}
