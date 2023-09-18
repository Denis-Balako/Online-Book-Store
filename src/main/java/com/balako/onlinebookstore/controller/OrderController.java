package com.balako.onlinebookstore.controller;

import com.balako.onlinebookstore.dto.order.request.CreateOrderRequestDto;
import com.balako.onlinebookstore.dto.order.request.UpdateOrderRequestDto;
import com.balako.onlinebookstore.dto.order.response.OrderDto;
import com.balako.onlinebookstore.dto.order.response.OrderItemDto;
import com.balako.onlinebookstore.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    @Operation(summary = "Create a new order.",
            description = "Create a new order from cart. "
                    + "Validation included.")
    public OrderDto createOrder(
            @RequestBody @Valid CreateOrderRequestDto requestDto) {
        return orderService.create(requestDto);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    @Operation(summary = "Get all orders.", description = "Get list of all orders. "
            + "Pagination and sorting included.")
    public List<OrderDto> getAllOrders(Pageable pageable) {
        return orderService.findAllOrders(pageable);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}")
    @Operation(summary = "Update an order status by ID",
            description = "Update an order status by ID. "
                    + "Validation included.")
    public OrderDto update(@PathVariable Long id,
                           @RequestBody UpdateOrderRequestDto requestDto) {
        return orderService.update(id, requestDto);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{id}/items")
    @Operation(summary = "Get all order items by order ID.",
            description = "Get list of all order items. "
                    + "Pagination and sorting included.")
    public List<OrderItemDto> getAllOrderItems(@PathVariable Long id,
                                               Pageable pageable) {
        return orderService.findAllOrderItems(id, pageable);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{orderId}/items/{itemId}")
    @Operation(summary = "Get an order item by order ID and item ID")
    public OrderItemDto getOrderItem(@PathVariable Long orderId,
                                     @PathVariable Long itemId) {
        return orderService.getOrderItem(orderId, itemId);
    }
}
