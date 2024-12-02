package com.laya.ExcelVersion.config;

import com.laya.ExcelVersion.security.JwtUtilService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtilService jwtUtilService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        log.info("Request received with URI: {}", request.getRequestURI());
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            log.info("Token found in request header");
            String token = authHeader.substring(7);

            boolean isValidToken = jwtUtilService.validateToken(token);

            if (isValidToken && SecurityContextHolder.getContext().getAuthentication() == null) {
                String role = jwtUtilService.extractRole(token);

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(jwtUtilService.extractUser(token), null, List.of(new SimpleGrantedAuthority(role)));
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                log.info("User {} authenticated successfully", jwtUtilService.extractEmail(token));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else if (!isValidToken) {
                SecurityContextHolder.clearContext();
            }

        }
        filterChain.doFilter(request, response);
    }


}
