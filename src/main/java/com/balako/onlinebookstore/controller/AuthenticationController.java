package com.balako.onlinebookstore.controller;

import com.balako.onlinebookstore.dto.user.UserLoginRequestDto;
import com.balako.onlinebookstore.dto.user.UserLoginResponseDto;
import com.balako.onlinebookstore.dto.user.UserRegistrationRequestDto;
import com.balako.onlinebookstore.dto.user.UserRegistrationResponseDto;
import com.balako.onlinebookstore.exception.RegistrationException;
import com.balako.onlinebookstore.service.AuthenticationService;
import com.balako.onlinebookstore.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public UserLoginResponseDto login(
            @RequestBody @Valid UserLoginRequestDto requestDto
    ) {
        return authenticationService.authenticate(requestDto);
    }

    @PostMapping("/register")
    @ResponseBody
    public UserRegistrationResponseDto register(
            @RequestBody @Valid UserRegistrationRequestDto requestDto
    )
            throws RegistrationException {
        return userService.register(requestDto);
    }
}
