package com.balako.onlinebookstore.dto.order.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateOrderRequestDto(@NotBlank String status) {
}
