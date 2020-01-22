package com.expatrio.usermanager.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateUserDto extends  UserDto {

    String password;
}
