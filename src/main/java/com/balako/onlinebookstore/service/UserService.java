package com.balako.onlinebookstore.service;

import com.balako.onlinebookstore.dto.user.request.UserRegistrationRequestDto;
import com.balako.onlinebookstore.dto.user.response.UserRegistrationResponseDto;
import com.balako.onlinebookstore.exception.RegistrationException;

public interface UserService {
    UserRegistrationResponseDto register(UserRegistrationRequestDto request)
            throws RegistrationException;
}
