package com.phoenixhell.serviceEdu.easyexcel;

import com.alibaba.excel.EasyExcel;

/**
 * @author phoenixhell
 * @since 2021/3/6 0006-下午 4:09
 */

public class ReadExcel {
    public static void main(String[] args) {
        String excelFileName="D:\\student.xlsx";
        EasyExcel.read(excelFileName,DemoData.class,new ExcelListener()).sheet().doRead();
    }
}
