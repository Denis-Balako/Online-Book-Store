package com.balako.onlinebookstore.dto.order.response;

import com.balako.onlinebookstore.enums.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;

@Data
public class OrderDto {
    private Long id;
    private Long userId;
    private Set<OrderItemDto> orderItems;
    private LocalDateTime orderDate;
    private BigDecimal total;
    private OrderStatus status;
}
