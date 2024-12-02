package com.laya.ExcelVersion.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    @JsonProperty("UID")
    private Long uid;

    @JsonProperty("Location")
    private String location;

    @JsonProperty("DOJ")
    private String doj;

    @JsonProperty("Time with EPAM")
    private String timeWithEPAM;

    @JsonProperty("Title")
    private String title;

    @JsonProperty("Status")
    private String status;

    @JsonProperty("Production Category")
    private String productionCategory;

    @JsonProperty("Job Function")
    private String jobFunction;

    @JsonProperty("Resource Manager")
    private String resourceManager;

    @JsonProperty("PGM")
    private String pgm;

    @JsonProperty("Project Code")
    private String projectCode;

    @JsonProperty("JF Level")
    private String jfLevel;

    @JsonProperty("Competency Practice")
    private String competencyPractice;

    @JsonProperty("Primary Skill")
    private String primarySkill;

    @JsonProperty("Niche Skills")
    private String nicheSkills;

    @JsonProperty("Niche Skill Yes/No")
    private String nicheSkillYesNo;

    @JsonProperty("Talent Profile 2020")
    private String talentProfile_2020;

    @JsonProperty("Delivery Feedback TT Score 35%")
    private Long deliveryFeedbackTtScore_35_Percent;

    @JsonProperty("Practice Rating 30%")
    private Double practiceRating_30_Percent;

    @JsonProperty("Ability")
    private Double ability;

    @JsonProperty("Strategic Orientation")
    private Long strategicOrientation;

    @JsonProperty("Influence")
    private Long influence;

    @JsonProperty("Result Orientation")
    private Long resultOrientation;

    @JsonProperty("Communication")
    private Long communication;

    @JsonProperty("Decision Making")
    private Long decisionMaking;

    @JsonProperty("Aspiration")
    private Double aspiration;

    @JsonProperty("Ask for More")
    private Long askForMore;

    @JsonProperty("Agility")
    private Long agility;

    @JsonProperty("Visibility")
    private Long visibility;

    @JsonProperty("Feedback")
    private Long feedback;

    @JsonProperty("Initiative New and Different")
    private Long initiativeNewAndDifferent;

    @JsonProperty("Engagement")
    private Double engagement;

    @JsonProperty("Developing Org Capabilities")
    private Long developingOrgCapabilities;

    @JsonProperty("Stay")
    private Long stay;

    @JsonProperty("Personal Connect")
    private Long personalConnect;

    @JsonProperty("Excellence")
    private Long excellence;

    @JsonProperty("Say")
    private Long say;

    @JsonProperty("Contribution EngX Culture 10%")
    private Long contributionEngXCulture_10_Percent;

    @JsonProperty("Contribution Extra Miles 5%")
    private Long contributionExtraMiles_5_Percent;

    @JsonProperty("Culture Score from Feedback 20%")
    private Long cultureScoreFromFeedback_20_Percent;

    @JsonProperty("Overall Weighted Score for Merit 100%")
    private Double overallWeightedScoreForMerit_100_Percent;

    @JsonProperty("Ranking")
    private Long ranking;

    @JsonProperty("HRBP Mapping")
    private String hrbpMapping;

    @JsonProperty("DH")
    private String dh;

}
