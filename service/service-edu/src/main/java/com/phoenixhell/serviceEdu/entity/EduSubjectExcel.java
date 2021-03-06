package com.phoenixhell.serviceEdu.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class EduSubjectExcel {
    @ExcelProperty(index = 0)
    private String oneSubjectName;
    @ExcelProperty(index = 1)
    private String twoSubjectName;
}
