package com.expatrio.usermanager.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChangePasswordDto {
    String oldPassword;
    String newPassword;
}
