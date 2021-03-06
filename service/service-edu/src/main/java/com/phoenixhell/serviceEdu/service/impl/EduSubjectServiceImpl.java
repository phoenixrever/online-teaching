package com.phoenixhell.serviceEdu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.phoenixhell.serviceEdu.entity.EduSubject;
import com.phoenixhell.serviceEdu.listener.EasyExcelReadListener;
import com.phoenixhell.serviceEdu.mapper.EduSubjectMapper;
import com.phoenixhell.serviceEdu.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-03-06
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void readFromExcel(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(),EduSubject.class,new EasyExcelReadListener()).sheet().doRead();
    }
}
