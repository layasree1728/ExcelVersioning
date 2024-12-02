package com.laya.ExcelVersion.dto;

import com.laya.ExcelVersion.entity.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private String email;
    private String firstName;
    private String lastName;
    private String practice;
    private Role role;
}
