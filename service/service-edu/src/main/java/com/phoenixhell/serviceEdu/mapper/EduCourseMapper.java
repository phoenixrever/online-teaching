package com.phoenixhell.serviceEdu.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.phoenixhell.serviceEdu.entity.EduCourse;
import com.phoenixhell.serviceEdu.entity.vo.CompleteCourseInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-03-10
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    CompleteCourseInfo getCompleteCourseInfoById(String id);
    IPage<CompleteCourseInfo> getCompleteCoursePage(IPage<CompleteCourseInfo> page, @Param(Constants.WRAPPER) Wrapper<CompleteCourseInfo> queryWrapper);
}
