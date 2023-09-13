package com.balako.onlinebookstore.dto.cart.request;

import jakarta.validation.constraints.Min;

public record UpdateCartItemDto(@Min(1) int quantity) {
}