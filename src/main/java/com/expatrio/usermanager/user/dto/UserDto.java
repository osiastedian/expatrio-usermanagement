package com.expatrio.usermanager.user.dto;

import com.expatrio.usermanager.user.domain.User;
import com.expatrio.usermanager.user.domain.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.UUID;

@Data
@NoArgsConstructor
public class UserDto {

    @Nullable
    UUID id;
    String firstName;
    String lastName;
    String userName;
    UserRole role;

    public UserDto(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.userName = user.getUsername();
        this.role = user.getRole();
        this.id = user.getId();
    }
}
