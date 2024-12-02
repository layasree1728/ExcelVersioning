package com.laya.ExcelVersion.controller;

import com.laya.ExcelVersion.constants.RoleConstants;
import com.laya.ExcelVersion.dto.EmployeeDTO;
import com.laya.ExcelVersion.service.MasterExcelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/step")
public class MasterExcelController {

    private final MasterExcelService masterExcelService;

    @PreAuthorize("hasRole('ROLE_SA')")
    @RequestMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, method = RequestMethod.POST)
    @Operation(summary = "Upload Excel", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully uploaded Excel",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = "Access Denied",
                    content = {@Content(mediaType = "application/json")})
    })
    public ResponseEntity<List<EmployeeDTO>> upload(@RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok(masterExcelService.parseAndSaveExcelFile(file));
    }

    @GetMapping("/employees")
    @PreAuthorize("hasAnyRole('ROLE_SA', 'ROLE_SU', 'ROLE_P')")
    @Operation(summary = "get employee data", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved employee data",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", description = "Access Denied",
                    content = {@Content(mediaType = "application/json")})
    })
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employeeData = masterExcelService.getAllEmployeeData();
        return ResponseEntity.ok(employeeData);
    }

}