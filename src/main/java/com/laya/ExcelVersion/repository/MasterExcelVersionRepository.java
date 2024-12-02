package com.laya.ExcelVersion.repository;

import com.laya.ExcelVersion.entity.MasterExcelVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaAuditing
public interface MasterExcelVersionRepository extends JpaRepository<MasterExcelVersion, Long> {
    MasterExcelVersion findByVersionName(String versionName);
    boolean existsByVersionName(String versionName);
}
