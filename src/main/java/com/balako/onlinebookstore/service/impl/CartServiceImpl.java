package com.balako.onlinebookstore.service.impl;

import com.balako.onlinebookstore.dto.cart.request.CreateCartItemDto;
import com.balako.onlinebookstore.dto.cart.request.UpdateCartItemDto;
import com.balako.onlinebookstore.dto.cart.response.CartDto;
import com.balako.onlinebookstore.dto.cart.response.CartItemDto;
import com.balako.onlinebookstore.mapper.CartItemMapper;
import com.balako.onlinebookstore.model.CartItem;
import com.balako.onlinebookstore.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {
    private CartItemMapper cartItemMapper;

    @Override
    public CartItemDto save(CreateCartItemDto requestDto) {
        CartItem cartItem = cartItemMapper.toModel(requestDto);

        return null;
    }

    @Override
    public CartDto findAll() {
        return null;
    }

    @Override
    public CartItemDto update(Long id, UpdateCartItemDto requestDto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
