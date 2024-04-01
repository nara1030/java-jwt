package org.among.usermodule.user.security;

import org.among.usermodule.response.ErrorCode;
import org.among.usermodule.response.RestApiException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailService userService;

    public CustomAuthenticationProvider(PasswordEncoder passwordEncoder, CustomUserDetailService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 1. 사용자 유무 체크
        String email = authentication.getName();
        User user = (User) userService.loadUserByUsername(email);
        // 2. 해당 사용자의 비밀번호 일치 여부 확인
        String password = (String) authentication.getCredentials();
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new RestApiException(ErrorCode.INVALID_PASSWORD_VALUE);
        // 3. 최종 로그인 일시 수정
        userService.updateLastLoginDateTime(email, LocalDateTime.now());

        return new CustomAuthenticationToken(email, password, user.getFullNameOfUser(), null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
