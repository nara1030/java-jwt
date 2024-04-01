package org.among.usermodule.join;

import org.among.usermodule.join.dto.JoinResponse;
import org.among.usermodule.user.UserEntity;
import org.among.usermodule.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JoinService {
    private UserRepository userRepository;

    public JoinService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public JoinResponse join(UserEntity user) {
        UserEntity savedEntity = userRepository.save(user);
        return new JoinResponse(savedEntity);
    }
}
