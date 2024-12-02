package com.laya.ExcelVersion.service;


import com.laya.ExcelVersion.dto.UserDTO;
import com.laya.ExcelVersion.entity.Role;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {
    UserDTO getUser(Authentication authentication);

    List<UserDTO> getAllUsers();

    List<UserDTO> getUsersByPractice(String practice, Authentication authentication);

    Role getUserRoleFromEmail(String email);
}
