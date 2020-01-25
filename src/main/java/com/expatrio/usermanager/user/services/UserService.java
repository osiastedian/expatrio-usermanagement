package com.expatrio.usermanager.user.services;

import com.expatrio.usermanager.user.domain.User;
import com.expatrio.usermanager.user.dto.UserDto;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.UUID;

public interface UserService {

    UserDto getUser(UUID userId);
    UserDto getUserByUserNameAndPassword(String username, String password);
    UserDto createUser(User user);
    User loadUserByUsername(String userName) throws UsernameNotFoundException;
}
