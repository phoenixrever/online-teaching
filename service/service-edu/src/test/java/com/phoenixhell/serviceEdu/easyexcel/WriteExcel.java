package com.phoenixhell.serviceEdu.easyexcel;

import com.alibaba.excel.EasyExcel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author phoenixhell
 * @since 2021/3/6 0006-下午 2:20
 */

public class WriteExcel {
    @Test
    public void WriteToExcel(){
        String excelFileName="D:\\student.xlsx";
        List<DemoData> list=new ArrayList<DemoData>();
        for (int i = 0; i < 10; i++) {
            list.add(new DemoData(i,"student"+i));
        }
        EasyExcel.write(excelFileName,DemoData.class).sheet("学生表").doWrite(list);
    }
}
