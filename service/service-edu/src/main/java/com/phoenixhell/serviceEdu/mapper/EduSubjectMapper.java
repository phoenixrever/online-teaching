package com.phoenixhell.serviceEdu.mapper;

import com.phoenixhell.serviceEdu.entity.EduSubject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phoenixhell.serviceEdu.entity.OneSubject;
import com.phoenixhell.serviceEdu.entity.TwoSubject;

import java.util.List;

/**
 * <p>
 * 课程科目 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-03-06
 */
public interface EduSubjectMapper extends BaseMapper<EduSubject> {
    List<OneSubject> getAllSubject();
}
