package com.expatrio.usermanager.user.controllers;

import com.expatrio.usermanager.user.domain.User;
import com.expatrio.usermanager.user.dto.CreateUserDto;
import com.expatrio.usermanager.user.dto.UserDto;
import com.expatrio.usermanager.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping()
    UserDto getUser(UUID userID) {
        return userService.getUser(userID);
    }

    @GetMapping("/test")
    String test() {
        return "Test";
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
}
