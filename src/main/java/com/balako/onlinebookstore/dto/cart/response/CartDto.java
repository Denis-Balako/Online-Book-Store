package com.balako.onlinebookstore.dto.cart.response;

import java.util.Set;

public class CartDto {
    private Long id;
    private Long userId;
    private Set<CartItemDto> cartItems;
}
