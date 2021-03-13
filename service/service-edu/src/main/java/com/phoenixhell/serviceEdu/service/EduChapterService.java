package com.phoenixhell.serviceEdu.service;

import com.phoenixhell.serviceEdu.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.phoenixhell.serviceEdu.entity.EduVideo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-03-10
 */
public interface EduChapterService extends IService<EduChapter> {
    List<EduChapter> getChapterById(String id);
}
