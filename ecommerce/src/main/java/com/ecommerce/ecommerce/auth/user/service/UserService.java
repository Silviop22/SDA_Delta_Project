package com.ecommerce.ecommerce.auth.user.service;

import com.ecommerce.ecommerce.auth.user.mapper.UserMapper;
import com.ecommerce.ecommerce.auth.user.model.User;
import com.ecommerce.ecommerce.auth.user.model.UserDto;
import com.ecommerce.ecommerce.auth.user.repository.UserRepository;
import com.ecommerce.ecommerce.commons.util.ObjectPatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService( UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public long count() {
        return userRepository.count();
    }

    public Long save(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user = userRepository.save(user);
        return user.getId();
    }

    public void update(UserDto userDto, Long id) {
        User existing = getExistingEntity(id);
        ObjectPatcher.patchObject(userDto, existing);
        userRepository.save(existing);
    }

    public void deleteById(Long id) {
        getExistingEntity(id);
        userRepository.deleteById(id);
    }

    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    public UserDto getById(Long id) {
        return userMapper.toDto(getExistingEntity(id));
    }

    private User getExistingEntity( Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
    }

    @Override
    public UserDetails loadUserByUsername( String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole())))
                .build();
    }
}
