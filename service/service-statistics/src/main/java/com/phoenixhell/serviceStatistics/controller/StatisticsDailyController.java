package com.phoenixhell.serviceStatistics.controller;


import com.phoenixhell.serviceStatistics.entity.StatisticsDaily;
import com.phoenixhell.serviceStatistics.service.FeignUcenterService;
import com.phoenixhell.serviceStatistics.service.StatisticsDailyService;
import com.phoenixhell.utils.CommonResult;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author phoeixhell
 * @since 2021-03-31
 */
@RestController
@RequestMapping("/serviceStatistics/statisticsDaily")
public class StatisticsDailyController {
    @Autowired
    private StatisticsDailyService statisticsDailyService;
    @Autowired
    private FeignUcenterService feignUcenterService;

    @PostMapping("/dailyCount/{startDate}/{endDate}")
    public CommonResult registerCount(@RequestBody Map<String, List<String>> map, @PathVariable String startDate, @PathVariable String endDate) {
        List<String> type = map.get("type");
        HashMap<String, List> daysMap = new HashMap<>();
        type.forEach(t -> {
            List<StatisticsDaily> daysList = statisticsDailyService.query().between("Date(date_calculated)", startDate, endDate).select(t).list();
            ArrayList<Integer> alist = new ArrayList<>();
            if (daysList != null) {
                daysList.forEach(d -> {
                    switch (t) {
                        case "register_num":
                            alist.add(d.getRegisterNum());
                            break;
                        case "login_num":
                            alist.add(d.getLoginNum());
                            break;
                        case "video_view_num":
                            alist.add(d.getVideoViewNum());
                            break;
                        case "course_num":
                            alist.add(d.getCourseNum());
                            break;
                    }
                });
            }
            daysMap.put(t, alist);
        });
        return CommonResult.ok().emptyData().data("dailyList", daysMap);
    }

    @GetMapping("/getDailyCount")
    @Scheduled(cron = "0/5 * * * * ?")
    public void getDailyCount() {
        System.out.println("scheduled*************");
        String s = LocalDate.now().plusDays(-1).toString();
        System.out.println(s + "------------------------");
        //获取4个资源
    }

    @GetMapping("/getRegisterCount/{day}")
    public void getRegisterCount(@PathVariable String day) {
        //验证是否日期格式 以后做
        Integer integer = feignUcenterService.registerCount(day);
        StatisticsDaily statisticsDaily = new StatisticsDaily();
        statisticsDaily.setRegisterNum(integer);
        statisticsDaily.setDateCalculated(day);
        statisticsDailyService.saveOrUpdate(statisticsDaily);
    }
}

