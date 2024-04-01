package org.among.usermodule.user.security;

import org.among.usermodule.response.ErrorCode;
import org.among.usermodule.response.RestApiException;
import org.among.usermodule.user.UserEntity;
import org.among.usermodule.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static java.util.Objects.*;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(username);
        if (isNull(user))
            throw new RestApiException(ErrorCode.INVALID_EMAIL_VALUE);
        return new User(user);
    }

    public boolean existUserByUsername(String username) {
        UserEntity user = userRepository.findByEmail(username);
        return nonNull(user);
    }

    /**
     * 사용자 있는 경우, 최종 로그인 시각 업데이트
     * @param username {String}
     * @param dateTime {LocalDateTime}
     */
    @Transactional
    public void updateLastLoginDateTime(String username, LocalDateTime dateTime) {
        UserEntity user = userRepository.findByEmail(username);
        user.setLastLoginDateTime(dateTime);
    }
}
