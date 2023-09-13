package com.balako.onlinebookstore.controller;

import com.balako.onlinebookstore.dto.cart.request.CreateCartItemDto;
import com.balako.onlinebookstore.dto.cart.request.UpdateCartItemDto;
import com.balako.onlinebookstore.dto.cart.response.CartDto;
import com.balako.onlinebookstore.dto.cart.response.CartItemDto;
import com.balako.onlinebookstore.service.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Cart management", description = "Endpoints for managing carts")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/cart")
public class CartController {
    private final CartService cartService;

    @PostMapping
    public CartItemDto create(@RequestBody CreateCartItemDto requestDto) {
        return cartService.save(requestDto);
    }

    @GetMapping
    public CartDto getAll() {
        return cartService.findAll();
    }

    @PutMapping("/cart-items/{id}")
    public CartItemDto update(@PathVariable Long id,
            @RequestBody UpdateCartItemDto requestDto) {
        return cartService.update(id, requestDto);
    }

    @DeleteMapping("/cart-items/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        cartService.delete(id);
    }

}
