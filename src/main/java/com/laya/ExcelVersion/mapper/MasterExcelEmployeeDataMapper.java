package com.laya.ExcelVersion.mapper;

import com.laya.ExcelVersion.dto.EmployeeDTO;
import com.laya.ExcelVersion.entity.MasterExcelData;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MasterExcelEmployeeDataMapper {

    MasterExcelEmployeeDataMapper INSTANCE = Mappers.getMapper(MasterExcelEmployeeDataMapper.class);

    EmployeeDTO employeeDataToEmployeeDTO(MasterExcelData masterExcelData);
}
