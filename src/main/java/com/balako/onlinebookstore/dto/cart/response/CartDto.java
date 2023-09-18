package com.balako.onlinebookstore.dto.cart.response;

import java.util.Set;
import lombok.Data;

@Data
public class CartDto {
    private Long id;
    private Long userId;
    private Set<CartItemDto> cartItems;
}
