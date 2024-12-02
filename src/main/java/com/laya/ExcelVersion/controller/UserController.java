package com.laya.ExcelVersion.controller;

import com.laya.ExcelVersion.constants.RoleConstants;
import com.laya.ExcelVersion.dto.UserDTO;
import com.laya.ExcelVersion.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/step/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('" + RoleConstants.ROLE_U + "')")
    @GetMapping
    @Operation(summary = "Get user", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = "Access Denied",
                    content = {@Content(mediaType = "application/json")})
    })
    public ResponseEntity<UserDTO> getUser(Authentication authentication) {
        log.info("Request to get user");
        return new ResponseEntity<>(userService.getUser(authentication), HttpStatus.OK);
    }

}

