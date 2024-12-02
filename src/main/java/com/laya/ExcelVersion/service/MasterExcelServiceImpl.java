package com.laya.ExcelVersion.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.laya.ExcelVersion.constants.ErrorMessages;
import com.laya.ExcelVersion.dto.EmployeeDTO;
import com.laya.ExcelVersion.entity.MasterExcelData;
import com.laya.ExcelVersion.exception.CorruptedFileException;
import com.laya.ExcelVersion.mapper.ExcelRowListener;
import com.laya.ExcelVersion.mapper.MasterExcelEmployeeDataMapper;
import com.laya.ExcelVersion.repository.MasterExcelDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MasterExcelServiceImpl implements MasterExcelService {

    private final MasterExcelDataRepository excelEmployeeRepository;
    private final MasterExcelVersionService masterExcelVersionService;
    private static final MasterExcelEmployeeDataMapper masterExcelEmployeeDataMapper = MasterExcelEmployeeDataMapper.INSTANCE;


    public List<EmployeeDTO> parseAndSaveExcelFile(MultipartFile file) {
        ExcelRowListener listener = new ExcelRowListener(masterExcelVersionService, file.getOriginalFilename());

        try {
            EasyExcel.read(file.getInputStream(), MasterExcelData.class, listener)
                    .ignoreEmptyRow(true)
                    .headRowNumber(2)
                    .sheet().doRead();
        }catch (ExcelAnalysisException | IOException ioException){
            log.error("Error reading file: {}", ioException.getMessage());
            throw new CorruptedFileException(ErrorMessages.CORRUPTED_EXCEL_FILE_ERROR);
        }


        return excelEmployeeRepository.saveAll(listener.getDataList())
                .stream().map(masterExcelEmployeeDataMapper::employeeDataToEmployeeDTO)
                .toList();
    }

    public List<EmployeeDTO> getAllEmployeeData() {
        return excelEmployeeRepository.findAll()
                .stream().map(masterExcelEmployeeDataMapper::employeeDataToEmployeeDTO)
                .toList();
    }

    public MasterExcelData getEmployeeDataByUid(Long uid) {
        return excelEmployeeRepository.findByUid(uid);
    }
}
