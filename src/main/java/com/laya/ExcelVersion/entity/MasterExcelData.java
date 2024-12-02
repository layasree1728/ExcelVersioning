package com.laya.ExcelVersion.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Audited
public class MasterExcelData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private MasterExcelVersion masterExcelVersion;

    @ExcelProperty(index = 1)
    private Long uid;

    @ExcelProperty(index = 2)
    private String location;

    @ExcelProperty(index = 3)
    private String doj;

    @ExcelProperty(index = 4)
    private String timeWithEPAM;

    @ExcelProperty(index = 5)
    private String title;

    @ExcelProperty(index = 6)
    private String status;

    @ExcelProperty(index = 7)
    private String productionCategory;

    @ExcelProperty(index = 8)
    private String jobFunction;

    @ExcelProperty(index = 9)
    private String resourceManager;

    @ExcelProperty(index = 10)
    private String pgm;

    @ExcelProperty(index = 11)
    private String projectCode;

    @ExcelProperty(index = 12)
    private String jfLevel;

    @ExcelProperty(index = 13)
    private String competencyPractice;

    @ExcelProperty(index = 14)
    private String primarySkill;

    @ExcelProperty(index = 15)
    private String nicheSkills;

    @ExcelProperty(index = 16)
    private String nicheSkillYesNo;

    @ExcelProperty(index = 17)
    private String talentProfile_2020;

    @ExcelProperty(index = 18)
    private Long deliveryFeedbackTtScore_35_Percent;

    @ExcelProperty(index = 19)
    private Double practiceRating_30_Percent;

    @ExcelProperty(index = 20)
    private Double ability;

    @ExcelProperty(index = 21)
    private Long strategicOrientation;

    @ExcelProperty(index = 22)
    private Long influence;

    @ExcelProperty(index = 23)
    private Long resultOrientation;

    @ExcelProperty(index = 24)
    private Long communication;

    @ExcelProperty(index = 25)
    private Long decisionMaking;

    @ExcelProperty(index = 26)
    private Double aspiration;

    @ExcelProperty(index = 27)
    private Long askForMore;

    @ExcelProperty(index = 28)
    private Long agility;

    @ExcelProperty(index = 29)
    private Long visibility;

    @ExcelProperty(index = 30)
    private Long feedback;

    @ExcelProperty(index = 31)
    private Long initiativeNewAndDifferent;

    @ExcelProperty(index = 32)
    private Double engagement;

    @ExcelProperty(index = 33)
    private Long developingOrgCapabilities;

    @ExcelProperty(index = 34)
    private Long stay;

    @ExcelProperty(index = 35)
    private Long personalConnect;

    @ExcelProperty(index = 36)
    private Long excellence;

    @ExcelProperty(index = 37)
    private Long say;

    @ExcelProperty(index = 38)
    private Long contributionEngXCulture_10_Percent;

    @ExcelProperty(index = 39)
    private Long contributionExtraMiles_5_Percent;

    @ExcelProperty(index = 40)
    private Long cultureScoreFromFeedback_20_Percent;

    @ExcelProperty(index = 41)
    private Double overallWeightedScoreForMerit_100_Percent;

    @ExcelProperty(index = 42)
    private Long ranking;

    //private String percentile;

    @ExcelProperty(index = 44)
    private String hrbpMapping;

    @ExcelProperty(index = 45)
    private String dh;

}
