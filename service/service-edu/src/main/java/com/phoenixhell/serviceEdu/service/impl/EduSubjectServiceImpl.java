package com.phoenixhell.serviceEdu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenixhell.serviceEdu.entity.EduSubject;
import com.phoenixhell.serviceEdu.entity.EduSubjectExcel;
import com.phoenixhell.serviceEdu.entity.OneSubject;
import com.phoenixhell.serviceEdu.listener.EasyExcelReadListener;
import com.phoenixhell.serviceEdu.mapper.EduSubjectMapper;
import com.phoenixhell.serviceEdu.service.EduSubjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

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
    @Resource
    private EasyExcelReadListener easyExcelReadListener;

    @Override
    public void readFromExcel(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), EduSubjectExcel.class,easyExcelReadListener).sheet().doRead();
    }

    @Override
    public List<OneSubject> getAllSubject() {
        //service 里面调用basemapper  或者调用this 自己本身的方法
        return baseMapper.getAllSubject();
    }
}
