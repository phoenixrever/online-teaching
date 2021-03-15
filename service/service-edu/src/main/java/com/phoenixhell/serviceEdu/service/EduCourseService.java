package com.phoenixhell.serviceEdu.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.phoenixhell.serviceEdu.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.phoenixhell.serviceEdu.entity.vo.CompleteCourseInfo;
import com.phoenixhell.serviceEdu.entity.vo.Course;

import java.util.List;

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
    Boolean updateCourseById(Course course);
    CompleteCourseInfo getCompleteCourseInfoById(String id);
    IPage<CompleteCourseInfo> getCompleteCoursePage(IPage<CompleteCourseInfo> page, Wrapper<CompleteCourseInfo> queryWrapper);
    Boolean deleteCourse(String id);
}
