package com.laya.ExcelVersion.controller;

import com.laya.ExcelVersion.constants.RoleConstants;
import com.laya.ExcelVersion.dto.UserDTO;
import com.laya.ExcelVersion.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/step/practice")
public class PracticeController {
    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('" + RoleConstants.ROLE_P + "')")
    @Operation(summary = "Get Users by Practice", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully Retrieved users",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = "Access Denied",
                    content = {@Content(mediaType = "application/json")})
    })
    public ResponseEntity<List<UserDTO>> getUsersByPractice(@RequestParam String practice, Authentication authentication) {
        log.info("Request to get users by practice");
        return ResponseEntity.ok(userService.getUsersByPractice(practice, authentication));
    }
}
