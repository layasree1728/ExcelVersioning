package com.laya.ExcelVersion.mapper;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.laya.ExcelVersion.constants.ErrorMessages;
import com.laya.ExcelVersion.entity.MasterExcelData;
import com.laya.ExcelVersion.entity.MasterExcelVersion;
import com.laya.ExcelVersion.exception.EmptyFileException;
import com.laya.ExcelVersion.service.MasterExcelVersionService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
@Slf4j
public class ExcelRowListener extends AnalysisEventListener<MasterExcelData> {

    private final List<MasterExcelData> dataList = new ArrayList<>();
    private final MasterExcelVersionService masterExcelVersionServiceImpl;
    private final String fileName;
    private MasterExcelVersion masterExcelVersion;

    @Override
    public void invoke(MasterExcelData masterExcelDataRow, AnalysisContext context) {
        if (masterExcelVersion == null)
            masterExcelVersion = masterExcelVersionServiceImpl.saveVersion(fileName);

        masterExcelDataRow.setMasterExcelVersion(masterExcelVersion);
        dataList.add(masterExcelDataRow);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        if (dataList.isEmpty()) {
            throw new EmptyFileException(ErrorMessages.EMPTY_FILE_ERROR);
        }
        log.debug("All rows processed!");
    }

}
