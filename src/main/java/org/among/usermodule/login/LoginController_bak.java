package org.among.usermodule.login;

import org.among.usermodule.common.ApiConstant;
import org.among.usermodule.common.Validation;
import org.among.usermodule.login.dto.LoginDto;
import org.among.usermodule.login.dto.LoginResponse;
import org.among.usermodule.response.ErrorCode;
import org.among.usermodule.response.Response;
import org.among.usermodule.response.RestApiException;
import org.among.usermodule.user.security.CustomUserDetailService;
import org.among.usermodule.user.security.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiConstant.API_PREFIX_MEMBER)
public class LoginController_bak {
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailService userService;

    public LoginController_bak(PasswordEncoder passwordEncoder, CustomUserDetailService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

//    @PostMapping("/login")
    public Response<LoginResponse> login(@RequestBody LoginDto loginDto) {
        // 유효성 체크
        if (!Validation.isValidEmail(loginDto.getEmail())) throw new RestApiException(ErrorCode.INVALID_EMAIL_FORMAT);
        if (!Validation.isValidPassword(loginDto.getPassword()))
            throw new RestApiException(ErrorCode.INVALID_PASSWORD_FORMAT);

        // 사용자 유무 체크
        User user = (User) userService.loadUserByUsername(loginDto.getEmail());
        // 해당 사용자의 비밀번호 일치 여부 확인
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword()))
            throw new RestApiException(ErrorCode.INVALID_PASSWORD_VALUE);

        return Response.success(new LoginResponse(user.getUsername(), user.getFullNameOfUser(), "token"));
    }
}
