package org.among.usermodule.join;

import org.among.usermodule.common.ApiConstant;
import org.among.usermodule.common.Validation;
import org.among.usermodule.join.dto.JoinDto;
import org.among.usermodule.join.dto.JoinResponse;
import org.among.usermodule.response.ErrorCode;
import org.among.usermodule.response.Response;
import org.among.usermodule.response.RestApiException;
import org.among.usermodule.user.UserEntity;
import org.among.usermodule.user.security.CustomUserDetailService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiConstant.API_PREFIX_MEMBER)
public class JoinController {
    private PasswordEncoder passwordEncoder;
    private JoinService joinService;
    private CustomUserDetailService userService;

    public JoinController(PasswordEncoder passwordEncoder, JoinService joinService, CustomUserDetailService userService) {
        this.passwordEncoder = passwordEncoder;
        this.joinService = joinService;
        this.userService = userService;
    }

//    @GetMapping("/joinPage")
//    public String joinPage() {
//        return "success";
//    }

    @PostMapping("/join")
    public Response<JoinResponse> join(@RequestBody JoinDto joinDto) {
        // 유효성 체크
        if (!Validation.isValidEmail(joinDto.getEmail())) throw new RestApiException(ErrorCode.INVALID_EMAIL_FORMAT);
        if (!Validation.isValidPassword(joinDto.getPassword())) throw new RestApiException(ErrorCode.INVALID_PASSWORD_FORMAT);
        // 기사용자 여부 체크
        if (userService.existUserByUsername(joinDto.getEmail()))
            throw new RestApiException(ErrorCode.EXISTING_EMAIL_VALUE);
        // 서비스단 엔티티 전달
        UserEntity user = UserEntity.builder()
                .email(joinDto.getEmail())
                .password(passwordEncoder.encode(joinDto.getPassword()))
                .username(joinDto.getUsername())
                .build();
        // 서비스단 응답 반환
        JoinResponse response = joinService.join(user);
        return Response.success(response);
    }
}
