package org.among.usermodule.login;

import org.among.usermodule.common.ApiConstant;
import org.among.usermodule.common.Validation;
import org.among.usermodule.jwt.TokenProvider;
import org.among.usermodule.login.dto.LoginDto;
import org.among.usermodule.login.dto.LoginResponse;
import org.among.usermodule.response.ErrorCode;
import org.among.usermodule.response.Response;
import org.among.usermodule.response.RestApiException;
import org.among.usermodule.user.security.CustomAuthenticationToken;
import org.among.usermodule.user.security.CustomUserDetailService;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiConstant.API_PREFIX_MEMBER)
public class LoginController {
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailService userService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    public LoginController(PasswordEncoder passwordEncoder, CustomUserDetailService userService,  AuthenticationManagerBuilder authenticationManagerBuilder, TokenProvider tokenProvider) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/login")
    public Response<LoginResponse> login(@RequestBody LoginDto loginDto) {
        // 유효성 체크
        if (!Validation.isValidEmail(loginDto.getEmail())) throw new RestApiException(ErrorCode.INVALID_EMAIL_FORMAT);
        if (!Validation.isValidPassword(loginDto.getPassword()))
            throw new RestApiException(ErrorCode.INVALID_PASSWORD_FORMAT);
        // 로그인(위임)
        CustomAuthenticationToken authenticationToken = new CustomAuthenticationToken(loginDto.getEmail(), loginDto.getPassword(), null);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        String email = (String) authentication.getPrincipal();
        String username = ((CustomAuthenticationToken) authentication).getUsername();
        // 토큰 생성
        String jwt = tokenProvider.createAccessToken(authentication);

        return Response.success(new LoginResponse(email, username, jwt));
    }
}
