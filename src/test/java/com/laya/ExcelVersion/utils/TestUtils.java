package com.laya.ExcelVersion.utils;

import com.laya.ExcelVersion.constants.RoleConstants;
import com.laya.ExcelVersion.dto.UserDTO;
import com.laya.ExcelVersion.entity.Role;
import com.laya.ExcelVersion.security.CustomUserPrincipal;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
 
import java.util.Collections;
 
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
 
public class TestUtils {
 
    public static Authentication getMockAuthenticationWithSecurity(String role) {
        CustomUserPrincipal principal = new CustomUserPrincipal("John", "Doe", "test_user@epam.com", role);
        Authentication authentication = new TestingAuthenticationToken(principal, null, Collections.singletonList(new SimpleGrantedAuthority(role)));
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        return authentication;
    }
 
    public static Authentication getMockAuthentication() {
        CustomUserPrincipal principal = new CustomUserPrincipal("John", "Doe", "test_user@epam.com", RoleConstants.ROLE_U);
        return new TestingAuthenticationToken(principal, null, Collections.singletonList(new SimpleGrantedAuthority(RoleConstants.ROLE_U)));
    }
 
    public static UserDTO getUserDTO() {
        return UserDTO.builder()
                .email("test_user@epam.com")
                .firstName("John")
                .lastName("Doe")
                .build();
    }
 
    public static UserDTO getUserDTOWithRole() {
        Role role = new Role();
        role.setId(1L);
        role.setName("P");
        return UserDTO.builder()
                .email("test_user@epam.com")
                .firstName("John")
                .lastName("Doe")
                .role(role)
                .build();
    }
 
 
}