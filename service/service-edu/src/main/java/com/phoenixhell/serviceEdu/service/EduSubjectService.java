package com.phoenixhell.serviceEdu.service;

import com.phoenixhell.serviceEdu.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

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
}
