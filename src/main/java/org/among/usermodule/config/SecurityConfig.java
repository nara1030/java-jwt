package org.among.usermodule.config;

import org.among.usermodule.common.ApiConstant;
import org.among.usermodule.jwt.JwtAuthenticationFilter;
import org.among.usermodule.jwt.TokenProvider;
import org.among.usermodule.jwt.exception.JwtAuthenticationEntryPoint;
import org.among.usermodule.user.security.CustomAuthenticationProvider;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
public class SecurityConfig {
    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    public SecurityConfig(CustomAuthenticationProvider customAuthenticationProvider, TokenProvider tokenProvider, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.customAuthenticationProvider = customAuthenticationProvider;
        this.tokenProvider = tokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable);
        http
                .csrf(AbstractHttpConfigurer::disable) // POST요청 permitAll 허용
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)) // H2-CONSOLE 허용
                .authorizeHttpRequests(req -> req
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        .requestMatchers(ApiConstant.API_PREFIX_MEMBER + "/join"
                                , ApiConstant.API_PREFIX_MEMBER + "/login").permitAll()
                        .requestMatchers(ApiConstant.API_PREFIX_MEMBER + "/info").authenticated()
                        .anyRequest().denyAll());
        http
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint));
        http.addFilterBefore(new JwtAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider);
        return authenticationManagerBuilder.build();
    }
}
