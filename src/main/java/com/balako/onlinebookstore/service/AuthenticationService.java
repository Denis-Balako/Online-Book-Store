package com.balako.onlinebookstore.service;

import com.balako.onlinebookstore.dto.user.request.UserLoginRequestDto;
import com.balako.onlinebookstore.dto.user.response.UserLoginResponseDto;

public interface AuthenticationService {
    UserLoginResponseDto authenticate(UserLoginRequestDto requestDto);
}
