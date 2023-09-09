package com.balako.onlinebookstore.service;

import com.balako.onlinebookstore.dto.user.UserLoginRequestDto;
import com.balako.onlinebookstore.dto.user.UserLoginResponseDto;

public interface AuthenticationService {
    UserLoginResponseDto authenticate(UserLoginRequestDto requestDto);
}
