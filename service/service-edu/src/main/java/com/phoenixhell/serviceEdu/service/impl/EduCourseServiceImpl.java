package com.phoenixhell.serviceEdu.service.impl;

import com.phoenixhell.serviceEdu.entity.EduCourse;
import com.phoenixhell.serviceEdu.entity.EduCourseDescription;
import com.phoenixhell.serviceEdu.entity.vo.Course;
import com.phoenixhell.serviceEdu.mapper.EduCourseMapper;
import com.phoenixhell.serviceEdu.service.EduCourseDescriptionService;
import com.phoenixhell.serviceEdu.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenixhell.servicebase.exceptionhandler.MyException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-03-10
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Resource
    private EduCourse eduCourse;
    @Resource
    private Course course;
    @Resource
    private EduCourseDescription eduCourseDescription;
    @Resource
    private EduCourseDescriptionService eduCourseDescriptionService;
    @Override
    @Transactional
    public String saveCourse(Course course) {
        BeanUtils.copyProperties(course,eduCourse);
        boolean save = this.save(eduCourse);
        if(!save){
            throw new MyException(20001,"保存课程信息失败");
        }
        eduCourseDescription.setDescription(course.getDescription());
        BeanUtils.copyProperties(eduCourse,eduCourseDescription);
        boolean saveDescription = eduCourseDescriptionService.save(eduCourseDescription);
        if(!saveDescription){
            throw new MyException(20001,"保存课程信息失败");
        }
        return eduCourseDescription.getId();
    }

    @Override
    public Course getCourseById(String id) {
        EduCourse eduCourse = this.getById(id);
        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(id);
        BeanUtils.copyProperties(eduCourse, course);
        course.setDescription(eduCourseDescription.getDescription());
        return course;
    }

}
