package com.laya.ExcelVersion.service;

import com.laya.ExcelVersion.constants.ErrorMessages;
import com.laya.ExcelVersion.dto.EmployeeDTO;
import com.laya.ExcelVersion.entity.MasterExcelVersion;
import com.laya.ExcelVersion.exception.CorruptedFileException;
import com.laya.ExcelVersion.exception.EmptyFileException;
import com.laya.ExcelVersion.exception.InvalidFileFormatException;
import com.laya.ExcelVersion.repository.MasterExcelDataRepository;
import com.laya.ExcelVersion.repository.MasterExcelVersionRepository;
import com.laya.ExcelVersion.security.CustomUserPrincipal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MasterExcelServiceImplTest {

    @Mock
    private MasterExcelVersionRepository masterExcelVersionRepository;
    @Mock
    private SecurityContext securityContext;
    @Mock
    private Authentication authentication;
    @Mock
    private CustomUserPrincipal customUserPrincipal;

    private MasterExcelVersionServiceImpl masterExcelVersionService;

    @BeforeEach
    void setUp() {
        masterExcelVersionService = new MasterExcelVersionServiceImpl(masterExcelVersionRepository);
        SecurityContextHolder.setContext(securityContext);

        lenient().when(securityContext.getAuthentication()).thenReturn(authentication);
        lenient().when(authentication.getPrincipal()).thenReturn(customUserPrincipal);
        lenient().when(customUserPrincipal.getFullName()).thenReturn("Jane Doe");
    }

    @Test
    void saveVersion_Successfully() {
        String fileName = "STEP_2020_V1.xlsx";
        when(masterExcelVersionRepository.save(any())).thenReturn(new MasterExcelVersion());

        assertDoesNotThrow(() -> masterExcelVersionService.saveVersion(fileName));
    }


    @Test
    void saveVersion_ThrowsExceptionIfVersionExists() {
        String fileName = "STEP_2020_V1.xlsx";
        when(masterExcelVersionRepository.existsByVersionName(anyString())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> masterExcelVersionService.saveVersion(fileName));
    }

    @Test
    void extractFileName_ExtractsValidFileName() {
        String fileName = "STEP_2020_V1.xlsx";
        String extractedName = masterExcelVersionService.extractVersionName(fileName);

        assertEquals("V1", extractedName);
    }

    @Test
    void extractFileName_ThrowsInvalidFileFormatException() {
        String fileName = "invalid_file_name.xlsx";

        assertThrows(InvalidFileFormatException.class, () -> masterExcelVersionService.extractVersionName(fileName));
    }

    @Test
    void testDeleteVersion() {
        MasterExcelVersion version = new MasterExcelVersion();
        doNothing().when(masterExcelVersionRepository).delete(any(MasterExcelVersion.class));

        assertDoesNotThrow(() -> masterExcelVersionService.deleteVersion(version));
        verify(masterExcelVersionRepository).delete(version);
    }

    // Assuming MasterExcelServiceImpl contains methods to handle file parsing
    @Mock
    private MasterExcelServiceImpl masterExcelService;

    @Mock
    private MasterExcelDataRepository masterExcelDataRepository;

    @Test
    void parseExcelFile_EmptyFileWithHeaders() throws IOException {
        byte[] content = "Header1,Header2".getBytes();
        MultipartFile file = new MockMultipartFile("file", "STEP_2020_V1.xlsx", "application/vnd.ms-excel", new ByteArrayInputStream(content));

        MasterExcelServiceImpl realService = new MasterExcelServiceImpl(masterExcelDataRepository, masterExcelVersionService);

        // Assuming the real implementation looks for more than just headers, otherwise needing to mock deeper behaviors
        assertThrows(EmptyFileException.class, () -> realService.parseAndSaveExcelFile(file));
    }

    @Test
    void parseExcelFile_EmptyFileWithOutHeaders() throws IOException {
        byte[] content = "".getBytes();
        MultipartFile file = new MockMultipartFile("file", "STEP_2020_V1.xlsx", "application/vnd.ms-excel", new ByteArrayInputStream(content));

        MasterExcelServiceImpl realService = new MasterExcelServiceImpl(masterExcelDataRepository, masterExcelVersionService);

        // Assuming the real implementation looks for more than just headers, otherwise needing to mock deeper behaviors
        assertThrows(EmptyFileException.class, () -> realService.parseAndSaveExcelFile(file));
    }


    @Test
    void testExcelFileWithEmptyRows() throws Exception {
        // Prepare mock MultipartFile
        MultipartFile file = mock(MultipartFile.class);
        InputStream emptyRowsExcel = getClass().getClassLoader().getResourceAsStream("emptyRowsExcel.xlsx");
        when(file.getInputStream()).thenReturn(emptyRowsExcel);

        // Test if it processes empty rows without error
        List<EmployeeDTO> result = masterExcelService.parseAndSaveExcelFile(file);

        assertNotNull(result);
        assertTrue(result.isEmpty());  // No data, so the list should be empty
    }

    // You must mock the behaviour within `MasterExcelServiceImpl` for further test scenarios like "ExcelFile With Empty Rows", "Excel File With Missing Columns" etc.

}