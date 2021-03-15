package com.phoenixhell.serviceEdu.entity.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CompleteCourseInfo {
    private String id;
    private String title;
    private String cover;
    private String status;
    private String description;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private BigDecimal ltPrice;
    private String gtPrice;
    private Long limit;
    private BigDecimal price;//只用于显示
    private Date gmtModified;
}
