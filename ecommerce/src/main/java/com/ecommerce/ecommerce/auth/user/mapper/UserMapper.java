package com.ecommerce.ecommerce.auth.user.mapper;

import com.ecommerce.ecommerce.auth.user.model.User;
import com.ecommerce.ecommerce.auth.user.model.UserDto;
import org.apache.catalina.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements Mapper<User, UserDto> {
    @Override
    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .password(user.getPassword())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .active(user.isActive())
                .build();
    }

    @Override
    public User toEntity(UserDto userDto) {
        User result = new User();
        result.setId(userDto.getId());
        result.setFirstName(userDto.getFirstName());
        result.setLastName(userDto.getLastName());
        result.setEmail(userDto.getEmail());
        result.setPassword(userDto.getPassword());
        result.setActive(userDto.isActive());
        result.setRole(userDto.getRole());
        return result;
    }
}