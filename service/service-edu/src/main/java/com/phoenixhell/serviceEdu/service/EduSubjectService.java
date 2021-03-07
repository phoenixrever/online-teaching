package com.phoenixhell.serviceEdu.service;

import com.phoenixhell.serviceEdu.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.phoenixhell.serviceEdu.entity.OneSubject;
import com.phoenixhell.utils.CommonResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-03-06
 */
public interface EduSubjectService extends IService<EduSubject> {
    void readFromExcel(MultipartFile file) throws IOException;
    List<OneSubject> getAllSubject();
}
