package com.expatrio.usermanager.user.controllers;

import com.expatrio.usermanager.user.domain.User;
import com.expatrio.usermanager.user.dto.ChangePasswordDto;
import com.expatrio.usermanager.user.dto.CreateUserDto;
import com.expatrio.usermanager.user.dto.UserDto;
import com.expatrio.usermanager.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    TokenStore tokenStore;

    @GetMapping("/{id}")
    UserDto getUser(@PathVariable(name = "id") UUID userID) {
        return userService.getUser(userID);
    }

    @PreAuthorize("hasAuthority('Admin')")
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

    @PreAuthorize("hasAuthority('Admin')")
    @DeleteMapping("/{id}")
    UserDto deleteUser(@PathVariable(name = "id") UUID userId,
                       OAuth2Authentication authentication) {
        return userService.deleteUser(userId);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('Admin','Customer')")
    UserDto updateUser(@PathVariable(name = "id") UUID userId,
                       @RequestBody UserDto userDto,
                       OAuth2Authentication authentication) throws Exception {
        boolean isCustomer = authentication.getAuthorities().stream().anyMatch((GrantedAuthority authority) -> authority.getAuthority().equals("Customer"));
        if (isCustomer && !checkIfSameWithCurrentUser(userId, authentication)) ;
        return userService.updateUser(userId, userDto);
    }

    private boolean checkIfSameWithCurrentUser(UUID userId, OAuth2Authentication authentication) {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        OAuth2AccessToken token = tokenStore.readAccessToken(details.getTokenValue());
        Map<String, Object> info = token.getAdditionalInformation();
        Object tokenId = info.getOrDefault("id", null);
        if (tokenId instanceof String) {
            if (!userId.equals(UUID.fromString((String) tokenId))) {
                throw new IllegalArgumentException("Invalid Operation");
            }
        }
        return true;
    }

    @PostMapping("/{id}/change-password")
    @PreAuthorize("hasAuthority('Admin')")
    boolean changeUserPassword(@PathVariable(name = "id") UUID userID, @RequestBody ChangePasswordDto changePasswordDto) {
        return userService.changePassword(userID, changePasswordDto.getOldPassword(), changePasswordDto.getOldPassword());
    }

    @GetMapping("/me")
    UserDto getCurrentUser(Authentication authentication) {
        String userName = (String) authentication.getPrincipal();
        return new UserDto(this.userService.loadUserByUsername(userName));
    }
}
