package com.balako.onlinebookstore.service;

import com.balako.onlinebookstore.dto.cart.request.CreateCartItemDto;
import com.balako.onlinebookstore.dto.cart.request.UpdateCartItemDto;
import com.balako.onlinebookstore.dto.cart.response.CartDto;
import com.balako.onlinebookstore.dto.cart.response.CartItemDto;

public interface CartService {
    CartItemDto save(CreateCartItemDto requestDto);

    CartDto getCart();

    CartItemDto update(Long id, UpdateCartItemDto requestDto);

    void delete(Long id);
}
