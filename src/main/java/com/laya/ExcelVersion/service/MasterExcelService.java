package com.laya.ExcelVersion.service;

import com.laya.ExcelVersion.dto.EmployeeDTO;
import com.laya.ExcelVersion.entity.MasterExcelData;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface MasterExcelService {

    List<EmployeeDTO> parseAndSaveExcelFile(MultipartFile file);

    List<EmployeeDTO> getAllEmployeeData();

    MasterExcelData getEmployeeDataByUid(Long uid);
}
