package com.phoenixhell.serviceEdu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.phoenixhell.serviceEdu.entity.EduSubject;

import java.util.Map;

/**
 * @author phoenixhell
 * @since 2021/3/6 0006-下午 4:31
 */

public class EasyExcelReadListener extends AnalysisEventListener<EduSubject> {

    @Override
    public void invoke(EduSubject eduSubject, AnalysisContext analysisContext) {
        System.out.println(eduSubject);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头"+headMap);
    }
}
