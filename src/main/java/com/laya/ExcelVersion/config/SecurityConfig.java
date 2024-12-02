package com.laya.ExcelVersion.config;

import com.laya.ExcelVersion.exception.CustomAccessDeniedHandler;
import com.laya.ExcelVersion.security.CustomAuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    SecurityFilterChain customSecurityChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/oauth2/**", "/login/oauth2/code/**", "/error").permitAll()
                        .anyRequest().authenticated()
                )
                //.oauth2Login(Customizer.withDefaults())
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(customAuthenticationSuccessHandler)
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )
                .exceptionHandling(exceptions -> exceptions
                        .accessDeniedHandler(customAccessDeniedHandler)
                ).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}
