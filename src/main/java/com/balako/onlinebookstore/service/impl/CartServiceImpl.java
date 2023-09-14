package com.balako.onlinebookstore.service.impl;

import com.balako.onlinebookstore.dto.cart.request.CreateCartItemDto;
import com.balako.onlinebookstore.dto.cart.request.UpdateCartItemDto;
import com.balako.onlinebookstore.dto.cart.response.CartDto;
import com.balako.onlinebookstore.dto.cart.response.CartItemDto;
import com.balako.onlinebookstore.exception.EntityNotFoundException;
import com.balako.onlinebookstore.mapper.CartItemMapper;
import com.balako.onlinebookstore.model.CartItem;
import com.balako.onlinebookstore.model.ShoppingCart;
import com.balako.onlinebookstore.model.User;
import com.balako.onlinebookstore.repository.book.BookRepository;
import com.balako.onlinebookstore.repository.cart.ShoppingCartRepository;
import com.balako.onlinebookstore.repository.cartitem.CartItemRepository;
import com.balako.onlinebookstore.service.CartService;
import com.balako.onlinebookstore.service.UserService;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {
    private final CartItemMapper cartItemMapper;
    private final BookRepository bookRepository;
    private final ShoppingCartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserService userService;

    @Override
    public CartItemDto save(CreateCartItemDto requestDto) {
        CartItem cartItem = new CartItem();
        cartItem.setBook(bookRepository.findById(requestDto.getBookId())
                .orElseThrow(
                        () -> new EntityNotFoundException("Can't find book by id: "
                        + requestDto.getBookId())));

        User user = userService.getCurrentAuthenticatedUser();
        ShoppingCart cart = cartRepository.findByUser(user).orElseThrow(
                () -> new EntityNotFoundException("Can't find cart by user email: "
                        + user.getEmail()));

        cartItem.setShoppingCart(cart);
        cartItem.setQuantity(requestDto.getQuantity());
        return cartItemMapper.toDto(cartItemRepository.save(cartItem));
    }

    @Override
    public CartDto findAll() {
        User user = userService.getCurrentAuthenticatedUser();
        ShoppingCart cart = cartRepository.findByUserWithCartItems(user.getId()).orElseThrow(
                () -> new EntityNotFoundException("Can't find cart by user email: "
                        + user.getEmail()));
        CartDto cartDto = new CartDto();
        cartDto.setId(cart.getId());
        cartDto.setUserId(user.getId());
        cartDto.setCartItems(cart.getCartItems().stream()
                .map(cartItemMapper::toDto)
                .collect(Collectors.toSet()));
        return cartDto;
    }

    @Override
    public CartItemDto update(Long id, UpdateCartItemDto requestDto) {
        CartItem cartItem = cartItemRepository.findByIdWithBook(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find cart item by id: "
                        + id));
        cartItem.setQuantity(requestDto.quantity());
        cartItemRepository.save(cartItem);
        return cartItemMapper.toDto(cartItem);
    }

    @Override
    public void delete(Long id) {
        cartItemRepository.deleteById(id);
    }
}
