package com.balako.onlinebookstore.mapper;

import com.balako.onlinebookstore.config.MapperConfig;
import com.balako.onlinebookstore.dto.order.request.CreateOrderRequestDto;
import com.balako.onlinebookstore.dto.order.response.OrderDto;
import com.balako.onlinebookstore.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = OrderItemMapper.class)
public interface OrderMapper {
    @Mapping(target = "userId", source = "order.user.id")
    OrderDto toDto(Order order);

    Order toModel(CreateOrderRequestDto requestDto);
}
