package com.laya.ExcelVersion.repository;

import com.laya.ExcelVersion.entity.MasterExcelData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaAuditing
public interface MasterExcelDataRepository extends JpaRepository<MasterExcelData, Long> {
    MasterExcelData findByUid(Long uid);
}
