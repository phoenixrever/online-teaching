package com.phoenixhell.serviceEdu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phoenixhell.serviceEdu.entity.EduCourse;
import com.phoenixhell.serviceEdu.entity.vo.CompleteCourseInfo;

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
}
