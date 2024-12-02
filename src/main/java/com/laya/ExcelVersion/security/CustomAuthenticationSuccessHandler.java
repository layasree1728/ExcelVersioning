package com.laya.ExcelVersion.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtilService jwtUtilService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String token = jwtUtilService.generateToken(authentication);
        log.info("User {} logged in successfully", authentication.getPrincipal());
        response.sendRedirect("http://localhost:3000" + "?token=" + token);
    }
}