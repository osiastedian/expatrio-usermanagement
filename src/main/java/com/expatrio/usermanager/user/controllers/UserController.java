package com.expatrio.usermanager.user.controllers;

import com.expatrio.usermanager.user.domain.User;
import com.expatrio.usermanager.user.dto.CreateUserDto;
import com.expatrio.usermanager.user.dto.UserDto;
import com.expatrio.usermanager.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
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
    UserDto createUser(@NotNull @RequestBody CreateUserDto userDto) throws Exception {
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

    @GetMapping()
    Page<UserDto> getAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return this.userService.findAll(PageRequest.of(page, size));
    }

    @DeleteMapping("/{id}")
    UserDto deleteUser(@PathVariable( name = "id") UUID userID) {
        return userService.deleteUser(userID);
    }

    @PutMapping("/{id}")
    UserDto updateUser(@PathVariable( name = "id") UUID userID, @RequestBody UserDto userDto) throws Exception {
        return userService.updateUser(userID, userDto);
    }

    @PostMapping("/{id}/change-password")
    boolean changeUserPassword(@PathVariable( name = "id") UUID userID, @RequestBody String newPassword) {
        return userService.changePassword(userID, newPassword);
    }

    @GetMapping("/me")
    UserDto getCurrentUser(Authentication authentication) {
        String userName = (String) authentication.getPrincipal();
        return new UserDto(this.userService.loadUserByUsername(userName));
    }
}
