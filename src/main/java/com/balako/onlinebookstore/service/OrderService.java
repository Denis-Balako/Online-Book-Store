package com.balako.onlinebookstore.service;

import com.balako.onlinebookstore.dto.order.request.CreateOrderRequestDto;
import com.balako.onlinebookstore.dto.order.request.UpdateOrderRequestDto;
import com.balako.onlinebookstore.dto.order.response.OrderDto;
import com.balako.onlinebookstore.dto.order.response.OrderItemDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderDto create(CreateOrderRequestDto requestDto);

    List<OrderDto> findAllOrders(Pageable pageable);

    OrderDto update(Long id, UpdateOrderRequestDto requestDto);

    List<OrderItemDto> findAllOrderItems(Long id, Pageable pageable);

    OrderItemDto getOrderItem(Long orderId, Long itemId);
}
