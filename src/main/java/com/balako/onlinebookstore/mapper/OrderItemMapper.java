package com.balako.onlinebookstore.mapper;

import com.balako.onlinebookstore.config.MapperConfig;
import com.balako.onlinebookstore.dto.order.response.OrderItemDto;
import com.balako.onlinebookstore.model.CartItem;
import com.balako.onlinebookstore.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    @Mapping(target = "bookId", source = "orderItem.book.id")
    OrderItemDto toDto(OrderItem orderItem);

    @Mapping(target = "price", source = "cartItem.book.price")
    OrderItem toModel(CartItem cartItem);
}
