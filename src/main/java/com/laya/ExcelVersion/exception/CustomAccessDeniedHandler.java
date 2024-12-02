package com.laya.ExcelVersion.exception;

import com.laya.ExcelVersion.constants.ErrorMessages;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.io.IOException;

@Slf4j
@ControllerAdvice
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        String requestURI = request.getRequestURI();
        String userPrincipal = request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : "anonymous";

        log.error("{} for user: {} on URI: {}", ErrorMessages.ACCESS_DENIED, userPrincipal, requestURI);

        response.sendError(HttpServletResponse.SC_FORBIDDEN, "You do not have permission to access this resource.");
    }
}