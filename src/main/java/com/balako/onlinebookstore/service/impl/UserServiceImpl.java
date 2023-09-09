package com.balako.onlinebookstore.service.impl;

import com.balako.onlinebookstore.dto.user.UserRegistrationRequestDto;
import com.balako.onlinebookstore.dto.user.UserRegistrationResponseDto;
import com.balako.onlinebookstore.enums.RoleName;
import com.balako.onlinebookstore.exception.RegistrationException;
import com.balako.onlinebookstore.mapper.UserMapper;
import com.balako.onlinebookstore.model.Role;
import com.balako.onlinebookstore.model.User;
import com.balako.onlinebookstore.repository.role.RoleRepository;
import com.balako.onlinebookstore.repository.user.UserRepository;
import com.balako.onlinebookstore.service.UserService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserRegistrationResponseDto register(UserRegistrationRequestDto request)
            throws RegistrationException {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RegistrationException("Unable to complete registration");
        }
        User user = userMapper.toModel(request);
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setShippingAddress(request.getShippingAddress());
        Role role = roleRepository.getByName(RoleName.ROLE_USER);
        user.setRoles(Set.of(role));
        User savedUser = userRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }
}