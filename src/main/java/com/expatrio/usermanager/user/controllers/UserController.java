package com.expatrio.usermanager.user.controllers;

import com.expatrio.usermanager.user.domain.User;
import com.expatrio.usermanager.user.dto.CreateUserDto;
import com.expatrio.usermanager.user.dto.UserDto;
import com.expatrio.usermanager.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    UserDto getUser(@PathVariable( name = "id") UUID userID) {
        return userService.getUser(userID);
    }

    @PostMapping()
    UserDto createUser(@NotNull @RequestBody CreateUserDto userDto) {
        User user = User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .username(userDto.getUserName())
                .password(userDto.getPassword())
                .role(userDto.getRole())
                .build();
        UserDto createdUser = userService.createUser(user);
        return createdUser;
    }

    @GetMapping("/me")
    UserDto getCurrentUser(Authentication authentication) {
        String userName = (String) authentication.getPrincipal();
        return new UserDto(this.userService.loadUserByUsername(userName));
    }
}
