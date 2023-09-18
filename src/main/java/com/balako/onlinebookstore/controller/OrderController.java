package com.balako.onlinebookstore.controller;

import com.balako.onlinebookstore.dto.order.request.CreateOrderRequestDto;
import com.balako.onlinebookstore.dto.order.request.UpdateOrderRequestDto;
import com.balako.onlinebookstore.dto.order.response.OrderDto;
import com.balako.onlinebookstore.dto.order.response.OrderItemDto;
import com.balako.onlinebookstore.service.OrderService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
    private final OrderService orderService;

    @PostMapping
    public OrderDto createOrder(
            @RequestBody @Valid CreateOrderRequestDto requestDto) {
        return orderService.create(requestDto);
    }

    @GetMapping
    public List<OrderDto> getAllOrders(Pageable pageable) {
        return orderService.findAllOrders(pageable);
    }

    @PatchMapping("/{id}")
    public OrderDto update(@PathVariable Long id,
                           @RequestBody UpdateOrderRequestDto requestDto) {
        return orderService.update(id, requestDto);
    }

    @GetMapping("/{id}/items")
    public List<OrderItemDto> getAllOrderItems(@PathVariable Long id,
                                               Pageable pageable) {
        return orderService.findAllOrderItems(id, pageable);
    }

    @GetMapping("/{orderId}/items/{itemId}")
    public OrderItemDto getOrderItem(@PathVariable Long orderId,
                                     @PathVariable Long itemId) {
        return orderService.getOrderItem(orderId, itemId);
    }
}
