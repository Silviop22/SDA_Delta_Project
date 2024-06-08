package com.ecommerce.ecommerce.services;

import com.ecommerce.ecommerce.dto.UserDTO;
import com.ecommerce.ecommerce.dto.UserResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    UserDTO registerUser(UserDTO userDTO);

    UserResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    UserDTO getUserById(Long userId);

    UserDTO updateUser(Long userId, UserDTO userDTO);

    String deleteUser(Long userId);
}
