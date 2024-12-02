package com.laya.ExcelVersion.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Audited
public class MasterExcelVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String versionName;

    private LocalDateTime uploadDate;

    private String uploadedBy;

    public MasterExcelVersion(String fileName, String versionName, String uploadedBy) {
        this.fileName = fileName;
        this.versionName = versionName;
        this.uploadDate = LocalDateTime.now();
        this.uploadedBy = uploadedBy;
    }

}
