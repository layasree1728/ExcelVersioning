package com.laya.ExcelVersion.service;

import com.laya.ExcelVersion.entity.MasterExcelVersion;

public interface MasterExcelVersionService {

    public MasterExcelVersion saveVersion(String fileName);

    public void deleteVersion(MasterExcelVersion masterExcelVersion);

    public boolean checkIfVersionExists(String versionName);

    public String extractVersionName(String fileName);
}
