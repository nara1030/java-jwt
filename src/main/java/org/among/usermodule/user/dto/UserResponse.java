package org.among.usermodule.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserResponse {
    private String email;
    private String username;
    private LocalDateTime lastLoginDateTime;
}
