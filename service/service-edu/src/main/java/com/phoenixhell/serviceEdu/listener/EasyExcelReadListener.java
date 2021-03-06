package com.phoenixhell.serviceEdu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.phoenixhell.serviceEdu.entity.EduSubject;
import com.phoenixhell.serviceEdu.entity.EduSubjectExcel;
import com.phoenixhell.serviceEdu.service.EduSubjectService;
import com.phoenixhell.servicebase.exceptionhandler.MyException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author phoenixhell
 * @since 2021/3/6 0006-下午 4:31
 */
@Component
public class EasyExcelReadListener extends AnalysisEventListener<EduSubjectExcel> {
    @Resource
    private EduSubjectService eduSubjectService;


    @Override
    public void invoke(EduSubjectExcel eduSubjectExcel, AnalysisContext analysisContext) {
        if (eduSubjectExcel == null) {
            throw new MyException(20001, "excel in null");
        }
        // 1级分类是否存在
        EduSubject eduSubjectOne = eduSubjectService.query().eq("title", eduSubjectExcel.getOneSubjectName()).eq("parent_id", 0).one();
        if (eduSubjectOne==null) {
            eduSubjectOne = new EduSubject();
            eduSubjectOne.setParentId("0");
            eduSubjectOne.setTitle(eduSubjectExcel.getOneSubjectName());
            eduSubjectService.save(eduSubjectOne);
        }
        //此1级分类已经存在有id号 判断其后面的二级分类是否重复，即有次名字并且parent_id=id
        EduSubject eduSubjectTwo = eduSubjectService.query().eq("title", eduSubjectExcel.getTwoSubjectName()).eq("parent_id", eduSubjectOne.getId()).one();
        if (eduSubjectTwo==null) {
            eduSubjectTwo = new EduSubject();
            eduSubjectTwo.setParentId(eduSubjectOne.getId());
            eduSubjectTwo.setTitle(eduSubjectExcel.getTwoSubjectName());
            eduSubjectService.save(eduSubjectTwo);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头" + headMap);
    }
}
