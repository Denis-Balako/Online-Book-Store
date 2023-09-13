package com.balako.onlinebookstore.service.impl;

import com.balako.onlinebookstore.dto.user.request.UserRegistrationRequestDto;
import com.balako.onlinebookstore.dto.user.response.UserRegistrationResponseDto;
import com.balako.onlinebookstore.enums.RoleName;
import com.balako.onlinebookstore.exception.RegistrationException;
import com.balako.onlinebookstore.mapper.UserMapper;
import com.balako.onlinebookstore.model.Role;
import com.balako.onlinebookstore.model.ShoppingCart;
import com.balako.onlinebookstore.model.User;
import com.balako.onlinebookstore.repository.cart.ShoppingCartRepository;
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
    private final ShoppingCartRepository shoppingCartRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserRegistrationResponseDto register(UserRegistrationRequestDto request)
            throws RegistrationException {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RegistrationException("Unable to complete registration");
        }
        User user = userMapper.toModel(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Role role = roleRepository.getByName(RoleName.ROLE_USER);
        user.setRoles(Set.of(role));
        User savedUser = userRepository.save(user);
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(savedUser);
        shoppingCartRepository.save(shoppingCart);
        return userMapper.toUserResponse(savedUser);
    }
}
