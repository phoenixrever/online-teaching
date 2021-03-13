package com.phoenixhell.serviceEdu.mapper;

import com.phoenixhell.serviceEdu.entity.EduChapter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-03-10
 */
public interface EduChapterMapper extends BaseMapper<EduChapter> {
    List<EduChapter> getChapterById(String id);
}
