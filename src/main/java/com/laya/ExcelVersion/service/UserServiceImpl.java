package com.laya.ExcelVersion.service;

import com.laya.ExcelVersion.constants.ErrorMessages;
import com.laya.ExcelVersion.dto.UserDTO;
import com.laya.ExcelVersion.entity.Role;
import com.laya.ExcelVersion.exception.UserNotFoundException;
import com.laya.ExcelVersion.mapper.UserMapper;
import com.laya.ExcelVersion.repository.UserRepository;
import com.laya.ExcelVersion.security.CustomUserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper = UserMapper.INSTANCE;

    @Override
    public UserDTO getUser(Authentication authentication) {
        String email = validateAuthenticationAndGetEmail(authentication);
        log.info("Getting user with email {}", email);
        return userMapper.toUserDTO(userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(ErrorMessages.USER_NOT_FOUND + email)));
    }

    @Override
    public Role getUserRoleFromEmail(String email) {
        log.info("Getting user roles with email {}", email);
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(ErrorMessages.USER_NOT_FOUND)).getRole();
    }

    @Override
    public List<UserDTO> getAllUsers() {
        log.info("Getting all users");
        return userRepository.findAll().stream().collect(ArrayList::new, (list, user) -> list.add(userMapper.toUserDTO(user)), ArrayList::addAll);
    }

    @Override
    public List<UserDTO> getUsersByPractice(String practice, Authentication authentication) {
        String email = validateAuthenticationAndGetEmail(authentication);
        log.info("Getting all users by practice {} with email {}", practice, email);

        if (StringUtils.isEmpty(practice)) {
            throw new IllegalArgumentException(ErrorMessages.PRACTICE_NOT_NULL);
        }
        String practiceName = userRepository.findByEmail(email).get().getPractice();
        log.info("Practice name of email {} is {}", email, practiceName);
        if (!practiceName.equals(practice)) {
            throw new AccessDeniedException(ErrorMessages.ACCESS_DENIED);
        }
        return userRepository.findByPractice(practice).stream().collect(ArrayList::new, (list, user) -> list.add(userMapper.toUserDTO(user)), ArrayList::addAll);
    }

    private String validateAuthenticationAndGetEmail(Authentication authentication) {
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new AccessDeniedException(ErrorMessages.ACCESS_DENIED);
        }
        String email;
        if (authentication.getPrincipal() instanceof CustomUserPrincipal) {
            email = ((CustomUserPrincipal) authentication.getPrincipal()).getEmail();
        } else if (authentication.getPrincipal() instanceof OidcUser oidcUser) {
            email = oidcUser.getEmail();
        } else {
            throw new AccessDeniedException(ErrorMessages.ACCESS_DENIED);
        }
        if (userRepository.findByEmail(email).isEmpty()) {
            throw new UserNotFoundException(ErrorMessages.USER_NOT_FOUND + email);
        }
        return email;
    }


}

