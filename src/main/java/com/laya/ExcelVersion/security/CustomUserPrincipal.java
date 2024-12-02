package com.laya.ExcelVersion.security;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.security.Principal;

@Data
@AllArgsConstructor
public class CustomUserPrincipal implements Principal {
    private String firstName;
    private String lastName;
    private String email;
    private String role;

    @Override
    public String getName() {
        return email;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

}
