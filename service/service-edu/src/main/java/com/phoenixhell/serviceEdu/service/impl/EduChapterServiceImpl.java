package com.phoenixhell.serviceEdu.service.impl;

import com.phoenixhell.serviceEdu.entity.EduChapter;
import com.phoenixhell.serviceEdu.entity.EduVideo;
import com.phoenixhell.serviceEdu.mapper.EduChapterMapper;
import com.phoenixhell.serviceEdu.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-03-10
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Override
    public List<EduChapter> getChapterById(String id) {
        return baseMapper.getChapterById(id);
    }
}
