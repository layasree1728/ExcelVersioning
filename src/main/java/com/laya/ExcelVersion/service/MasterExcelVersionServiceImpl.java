package com.laya.ExcelVersion.service;

import com.laya.ExcelVersion.constants.ErrorMessages;
import com.laya.ExcelVersion.entity.MasterExcelVersion;
import com.laya.ExcelVersion.exception.InvalidFileFormatException;
import com.laya.ExcelVersion.repository.MasterExcelVersionRepository;
import com.laya.ExcelVersion.security.CustomUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MasterExcelVersionServiceImpl implements MasterExcelVersionService {
    private final MasterExcelVersionRepository masterExcelVersionRepository;

    public MasterExcelVersion saveVersion(String fileName) {
        String versionName = extractVersionName(fileName);
        if (checkIfVersionExists(versionName)) {
            throw new IllegalArgumentException("Version already exists");
        }

        CustomUserPrincipal customUserPrincipal =
                (CustomUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        MasterExcelVersion masterExcelVersion =
                new MasterExcelVersion(
                        fileName,
                        versionName,
                        customUserPrincipal.getFullName()
                );

        return masterExcelVersionRepository.save(masterExcelVersion);
    }

    public void deleteVersion(MasterExcelVersion masterExcelVersion) {
        masterExcelVersionRepository.delete(masterExcelVersion);
    }

    public boolean checkIfVersionExists(String versionName) {
        return masterExcelVersionRepository.existsByVersionName(versionName);
    }

    public String extractVersionName(String fileName) {
        var regex = "STEP_\\d{4}_V\\d+\\.(xlsx|xls)$";
        if (!fileName.matches(regex)) {
            throw new InvalidFileFormatException(ErrorMessages.INVALID_FILE_FORMAT_ERROR);
        }
        return fileName.split("_")[2].split("\\.")[0];
    }
}
