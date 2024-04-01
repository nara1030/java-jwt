package org.among.usermodule.join.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.among.usermodule.user.UserEntity;

@Getter
@AllArgsConstructor
public class JoinResponse {
    private String email;
    private String username;

    public JoinResponse(UserEntity user) {
        this.email = user.getEmail();
        this.username = user.getUsername();
    }
}
