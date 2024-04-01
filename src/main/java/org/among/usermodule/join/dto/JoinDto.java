package org.among.usermodule.join.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JoinDto {
    private String email;
    private String password;
    private String username;
}
