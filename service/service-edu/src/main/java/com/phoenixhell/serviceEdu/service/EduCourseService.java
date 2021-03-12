package com.phoenixhell.serviceEdu.service;

import com.phoenixhell.serviceEdu.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.phoenixhell.serviceEdu.entity.vo.Course;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-03-10
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourse(Course course);
    Course getCourseById(String id);
}
