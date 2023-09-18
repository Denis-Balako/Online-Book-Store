package com.balako.onlinebookstore.service.impl;

import com.balako.onlinebookstore.dto.order.request.CreateOrderRequestDto;
import com.balako.onlinebookstore.dto.order.request.UpdateOrderRequestDto;
import com.balako.onlinebookstore.dto.order.response.OrderDto;
import com.balako.onlinebookstore.dto.order.response.OrderItemDto;
import com.balako.onlinebookstore.service.OrderService;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public OrderDto create(CreateOrderRequestDto requestDto) {
        return null;
    }

    @Override
    public List<OrderDto> findAllOrders(Pageable pageable) {
        return null;
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
