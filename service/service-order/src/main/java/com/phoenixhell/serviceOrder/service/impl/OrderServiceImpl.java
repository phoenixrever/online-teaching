package com.phoenixhell.serviceOrder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.phoenixhell.serviceOrder.appBoot.DelayQueueManager;
import com.phoenixhell.serviceOrder.entity.Order;
import com.phoenixhell.serviceOrder.mapper.OrderMapper;
import com.phoenixhell.serviceOrder.service.FeignCourseService;
import com.phoenixhell.serviceOrder.service.FeignUcenterService;
import com.phoenixhell.serviceOrder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenixhell.servicebase.exceptionhandler.MyException;
import com.phoenixhell.utils.CommonResult;
import com.phoenixhell.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author phoeixhell
 * @since 2021-03-28
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    private FeignCourseService feignCourseService;
    @Autowired
    private FeignUcenterService feignUcenterService;
    @Autowired
    private DelayQueueManager delayQueueManager;
    @Override
    public String createOrder(String courseId, String userId) {
        CommonResult course = feignCourseService.getCourse(courseId);
        CommonResult userInfo = feignUcenterService.getUserInfoByTokenId(userId);
        Order order = new Order();
        if(course.getSuccess() && userInfo.getSuccess()){
            //创建订单
            Map<String,String> courseMap = (Map<String, String>) course.getData().get("courseInfo");
            Map<String,String> userInfoMap = (Map<String, String>) userInfo.getData().get("userInfo");
            System.out.println(courseMap);
            System.out.println(userInfoMap);
            order.setOrderNo(UUID.randomUUID().toString().substring(0,6));
            order.setCourseId(courseId);
            order.setCourseTitle(courseMap.get("title"));
            order.setCourseCover(courseMap.get("cover"));
            order.setTeacherName(courseMap.get("teacherName"));
            order.setTotalFee(new BigDecimal(String.valueOf(courseMap.get("price"))));
            order.setMemberId(userInfoMap.get("id"));
            order.setMobile(userInfoMap.get("mobile"));
            order.setNickname(userInfoMap.get("nickname"));
            order.setStatus(0);
            order.setPayType(1);
            order.setDelayTime(System.currentTimeMillis()+1000*60*30);
            boolean save = this.save(order);
            delayQueueManager.getDelayQueue().offer(order);
            if(!save){
                throw new MyException(20001, "创建订单失败");
            }
        }
        return order.getId();
    }

    @Override
    public String deleteOrder(String id) {
        Order order = this.getById(id);
//        delayQueueManager.getDelayQueue().forEach((e)->{
//            System.out.println(e.getId());
//        });
        boolean remove = delayQueueManager.getDelayQueue().remove(order);
        this.removeById(id);
        return null;
    }
}
