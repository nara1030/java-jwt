package org.among.usermodule.user.security;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomAuthenticationToken extends AbstractAuthenticationToken {
    private String email;
    private String password;
    @Getter
    private String username;

    public CustomAuthenticationToken(String email, String password, String username, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        super.setAuthenticated(true);
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public CustomAuthenticationToken(String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this(email, password, null, authorities);
    }

    public CustomAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
        this(null, null, null, authorities);
    }

    @Override
    public Object getCredentials() {
        return this.password;
    }

    @Override
    public Object getPrincipal() {
        return this.email;
    }
}
