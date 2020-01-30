package com.expatrio.usermanager.user.services;

import com.expatrio.usermanager.user.domain.User;
import com.expatrio.usermanager.user.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserDto getUser(UUID userId);
    UserDto getUserByUserNameAndPassword(String username, String password);
    UserDto createUser(User user) throws Exception;
    UserDto updateUser(UUID userId, UserDto user) throws Exception;
    User loadUserByUsername(String userName) throws UsernameNotFoundException;
    Page<UserDto> findAll(Pageable pageable);
    boolean changePassword(UUID userId, String password);
    UserDto deleteUser(UUID userId);
}
