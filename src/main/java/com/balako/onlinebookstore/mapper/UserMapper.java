package com.balako.onlinebookstore.mapper;

import com.balako.onlinebookstore.config.MapperConfig;
import com.balako.onlinebookstore.dto.user.request.UserRegistrationRequestDto;
import com.balako.onlinebookstore.dto.user.response.UserLoginResponseDto;
import com.balako.onlinebookstore.dto.user.response.UserRegistrationResponseDto;
import com.balako.onlinebookstore.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserRegistrationResponseDto toUserResponse(User user);

    UserLoginResponseDto toDto(User user);

    User toModel(UserRegistrationRequestDto requestDto);
}
