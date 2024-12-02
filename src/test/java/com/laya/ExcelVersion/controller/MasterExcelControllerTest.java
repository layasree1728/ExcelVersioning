package com.laya.ExcelVersion.controller;

import com.laya.ExcelVersion.constants.RoleConstants;
import com.laya.ExcelVersion.dto.EmployeeDTO;
import com.laya.ExcelVersion.exception.InvalidFileFormatException;
import com.laya.ExcelVersion.security.JwtUtilService;
import com.laya.ExcelVersion.service.MasterExcelService;
import static com.laya.ExcelVersion.utils.TestUtils.getMockAuthenticationWithSecurity;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MasterExcelController.class)
public class MasterExcelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MasterExcelService masterExcelService;

    @MockBean
    private JwtUtilService jwtUtilService;

    @Test
    public void testUploadSuccess() throws Exception {
        Authentication authentication = getMockAuthenticationWithSecurity(RoleConstants.ROLE_SA);
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "STEP_2024_V1.xlsx",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                "sample content".getBytes()
        );

        List<EmployeeDTO> employeeList = List.of(new EmployeeDTO(12345L, "Hyderabad", "2021-05-10", "2 years", "Engineer", "Active",
                "Production", "Software Engineering", "John Manager", "Alice PM", "P123",
                "JF2", "Competency Practice", "Java", "Microservices", "Yes", "Profile2020",
                85L, 4.5, 4.2, 90L, 88L, 85L, 87L, 86L, 3.9, 89L, 87L, 85L, 90L, 4L, 4.0,
                86L, 4L, 87L, 89L, 85L, 88L, 90L, 3L, 4.5, 1L, "HR Mapping", "DH1"));

        when(masterExcelService.parseAndSaveExcelFile(any(MultipartFile.class))).thenReturn(employeeList);

        mockMvc.perform(multipart("/step/upload")
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .principal(authentication)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].UID").value(12345L))
                .andExpect(jsonPath("$[0].Location").value("Hyderabad"));
    }

    @Test
    public void testUploadFailure_WithInvalidFileName() throws Exception {
        Authentication authentication = getMockAuthenticationWithSecurity(RoleConstants.ROLE_SA);
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.xlsx",
                "text/plain",
                "invalid content".getBytes()
        );

        when(masterExcelService.parseAndSaveExcelFile(any(MultipartFile.class)))
                .thenThrow(new InvalidFileFormatException("Invalid file format"));

        mockMvc.perform(multipart("/step/upload")
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .principal(authentication)
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUploadFailure_WithInvalidFileFormat() throws Exception {
        Authentication authentication = getMockAuthenticationWithSecurity(RoleConstants.ROLE_SA);
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "STEP_2024_V3.txt",
                "text/plain",
                "invalid content".getBytes()
        );

        when(masterExcelService.parseAndSaveExcelFile(any(MultipartFile.class)))
                .thenThrow(new InvalidFileFormatException("Invalid file format"));

        mockMvc.perform(multipart("/step/upload")
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .principal(authentication)
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUploadFailure_WithNoAccess() throws Exception {
        Authentication authentication = getMockAuthenticationWithSecurity(RoleConstants.ROLE_U);
        MockMultipartFile file = new MockMultipartFile(
            "file",
            "STEP_2024_V1.xlsx",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
            "sample content".getBytes()
        );

        mockMvc.perform(multipart("/step/upload")
                    .file(file)
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .principal(authentication)
                    .with(csrf()))
            .andExpect(status().isForbidden())
            .andExpect(result -> assertEquals("You do not have permission to access this resource.", result.getResponse().getContentAsString()));
    }

    @Test
    public void testGetAllEmployeesSuccess_WithRoleSA() throws Exception {
        Authentication authentication = getMockAuthenticationWithSecurity(RoleConstants.ROLE_SA);

        List<EmployeeDTO> employeeList = List.of(new EmployeeDTO(12345L, "Hyderabad", "2021-05-10", "2 years", "Engineer", "Active",
                "Production", "Software Engineering", "John Manager", "Alice PM", "P123",
                "JF2", "Competency Practice", "Java", "Microservices", "Yes", "Profile2020",
                85L, 4.5, 4.2, 90L, 88L, 85L, 87L, 86L, 3.9, 89L, 87L, 85L, 90L, 4L, 4.0,
             86L, 4L, 87L, 89L, 85L, 88L, 90L, 3L, 4.5, 1L, "HR Mapping", "DH1"));

        when(masterExcelService.getAllEmployeeData()).thenReturn(employeeList);

        mockMvc.perform(get("/step/employees").principal(authentication))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].UID").value(12345L))
                .andExpect(jsonPath("$[0].Location").value("Hyderabad"));
    }

    @Test
    public void testGetAllEmployeesSuccess_WithRoleP() throws Exception {
        Authentication authentication = getMockAuthenticationWithSecurity(RoleConstants.ROLE_P);

        List<EmployeeDTO> employeeList = List.of(new EmployeeDTO(12345L, "Hyderabad", "2021-05-10", "2 years", "Engineer", "Active",
                "Production", "Software Engineering", "John Manager", "Alice PM", "P123",
                "JF2", "Competency Practice", "Java", "Microservices", "Yes", "Profile2020",
                85L, 4.5, 4.2, 90L, 88L, 85L, 87L, 86L, 3.9, 89L, 87L, 85L, 90L, 4L, 4.0,
                86L, 4L, 87L, 89L, 85L, 88L, 90L, 3L, 4.5, 1L, "HR Mapping", "DH1"));

        when(masterExcelService.getAllEmployeeData()).thenReturn(employeeList);

        mockMvc.perform(get("/step/employees").principal(authentication))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].UID").value(12345L))
                .andExpect(jsonPath("$[0].Location").value("Hyderabad"));
    }

    @Test
    public void testGetAllEmployeesSuccess_WithRoleSU() throws Exception {
        Authentication authentication = getMockAuthenticationWithSecurity(RoleConstants.ROLE_SU);

        List<EmployeeDTO> employeeList = List.of(new EmployeeDTO(12345L, "Hyderabad", "2021-05-10", "2 years", "Engineer", "Active",
                "Production", "Software Engineering", "John Manager", "Alice PM", "P123",
                "JF2", "Competency Practice", "Java", "Microservices", "Yes", "Profile2020",
                85L, 4.5, 4.2, 90L, 88L, 85L, 87L, 86L, 3.9, 89L, 87L, 85L, 90L, 4L, 4.0,
                86L, 4L, 87L, 89L, 85L, 88L, 90L, 3L, 4.5, 1L, "HR Mapping", "DH1"));

        when(masterExcelService.getAllEmployeeData()).thenReturn(employeeList);

        mockMvc.perform(get("/step/employees").principal(authentication))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].UID").value(12345L))
                .andExpect(jsonPath("$[0].Location").value("Hyderabad"));
    }

    @Test
    public void testGetAllEmployeesFailure_WithRoleU() throws Exception {
        Authentication authentication = getMockAuthenticationWithSecurity(RoleConstants.ROLE_U);

        mockMvc.perform(get("/step/employees").principal(authentication))
                .andExpect(status().isForbidden());
    }
}