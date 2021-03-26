package com.phoenixhell.serviceEdu.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenixhell.serviceEdu.entity.EduCourse;
import com.phoenixhell.serviceEdu.entity.EduCourseDescription;
import com.phoenixhell.serviceEdu.entity.EduVideo;
import com.phoenixhell.serviceEdu.entity.vo.CompleteCourseInfo;
import com.phoenixhell.serviceEdu.entity.vo.Course;
import com.phoenixhell.serviceEdu.mapper.EduCourseMapper;
import com.phoenixhell.serviceEdu.service.EduCourseDescriptionService;
import com.phoenixhell.serviceEdu.service.EduCourseService;
import com.phoenixhell.serviceEdu.service.EduVideoService;
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
    @Resource
    private EduCourseMapper eduCourseMapper;
    @Resource
    private EduVideoService eduVideoService;

    @Override
    @Transactional
    public String saveCourse(Course course) {
        BeanUtils.copyProperties(course, eduCourse);
        boolean save = this.save(eduCourse);
        if (!save) {
            throw new MyException(20001, "保存课程信息失败");
        }
        eduCourseDescription.setDescription(course.getDescription());
        BeanUtils.copyProperties(eduCourse, eduCourseDescription);
        boolean saveDescription = eduCourseDescriptionService.save(eduCourseDescription);
        if (!saveDescription) {
            throw new MyException(20001, "保存课程信息失败");
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

    @Override
    @Transactional
    public Boolean updateCourseById(Course course) {
        BeanUtils.copyProperties(course, eduCourse);
        boolean update = this.updateById(eduCourse);
        if (!update) {
            throw new MyException(20001, "更新课程信息失败");
        }
        eduCourseDescription.setDescription(course.getDescription());
        BeanUtils.copyProperties(eduCourse, eduCourseDescription);
        boolean updateDescription = eduCourseDescriptionService.updateById(eduCourseDescription);
        if (!updateDescription) {
            throw new MyException(20001, "保存课程信息失败");
        }
        return true;
    }

    @Override
    public CompleteCourseInfo getCompleteCourseInfoById(String id) {
        return eduCourseMapper.getCompleteCourseInfoById(id);
    }

    @Override
    public Page<CompleteCourseInfo> getCompleteCoursePage(IPage<CompleteCourseInfo> page, Wrapper<CompleteCourseInfo> queryWrapper) {
        return eduCourseMapper.getCompleteCoursePage(page, queryWrapper);
    }


    @Override
    @Transactional//必须要手动抛异常 不然不会回滚
    public Boolean deleteCourse(String id) {
        boolean video = eduVideoService.remove(new QueryWrapper<EduVideo>().eq("course_id", "id"));
        boolean description = eduCourseDescriptionService.removeById(id);
        boolean eduCourse = this.removeById(id);
        if (video && description && eduCourse) {
            return true;
        } else {
            throw new MyException(200001, "删除失败回滚");
        }
    }
}
