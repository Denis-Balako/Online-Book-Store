package com.balako.onlinebookstore.controller;

import com.balako.onlinebookstore.dto.order.request.CreateOrderRequestDto;
import com.balako.onlinebookstore.dto.order.request.UpdateOrderRequestDto;
import com.balako.onlinebookstore.dto.order.response.OrderDto;
import com.balako.onlinebookstore.dto.order.response.OrderItemDto;
import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @PostMapping
    public OrderDto createOrder(
            @RequestBody @Valid CreateOrderRequestDto requestDto) {
        return null;
    }

    @GetMapping
    public List<OrderDto> getAllOrders() {
        return Collections.emptyList();
    }

    @PatchMapping("/{id}")
    public OrderDto update(@PathVariable Long id,
                           @RequestBody UpdateOrderRequestDto requestDto) {
        return null;
    }

    @GetMapping("/{id}/items")
    public List<OrderItemDto> getAllOrderItems() {
        return Collections.emptyList();
    }

    @GetMapping("/{orderId}/items/{itemId}")
    public OrderItemDto getOrderItem(
            @PathVariable Long orderId,
            @PathVariable Long itemId) {
        return null;
    }
}
