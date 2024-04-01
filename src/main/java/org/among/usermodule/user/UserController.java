package org.among.usermodule.user;

import org.among.usermodule.common.ApiConstant;
import org.among.usermodule.response.Response;
import org.among.usermodule.user.dto.UserResponse;
import org.among.usermodule.user.security.CustomAuthenticationToken;
import org.among.usermodule.user.security.CustomUserDetailService;
import org.among.usermodule.user.security.User;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiConstant.API_PREFIX_MEMBER)
public class UserController {
    private final CustomUserDetailService userDetailService;

    public UserController(CustomUserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @GetMapping("/info")
    public Response<UserResponse> info(Authentication authentication) {
        CustomAuthenticationToken authenticationToken = (CustomAuthenticationToken) authentication;
        String username = authenticationToken.getUsername();
        String email = authenticationToken.getPrincipal().toString();
        User user = (User) userDetailService.loadUserByUsername(email);
        return Response.success(new UserResponse(email, username, user.getLastLoginDateTime()));
    }
}
