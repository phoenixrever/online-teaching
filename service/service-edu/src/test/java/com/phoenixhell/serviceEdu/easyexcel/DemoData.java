package com.phoenixhell.serviceEdu.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author phoenixhell
 * @since 2021/3/6 0006-下午 2:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DemoData {
    @ExcelProperty(value = "学生序号",index = 0)
    private Integer no;
    @ExcelProperty(value = "学生姓名",index = 1)
    private String name;
}
