package com.balako.onlinebookstore.service;

import com.balako.onlinebookstore.dto.user.UserRegistrationRequestDto;
import com.balako.onlinebookstore.dto.user.UserRegistrationResponseDto;
import com.balako.onlinebookstore.exception.RegistrationException;

public interface UserService {
    UserRegistrationResponseDto register(UserRegistrationRequestDto request)
            throws RegistrationException;
}
