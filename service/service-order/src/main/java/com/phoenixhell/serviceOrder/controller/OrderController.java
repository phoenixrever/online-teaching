package com.phoenixhell.serviceOrder.controller;


import com.phoenixhell.serviceOrder.service.OrderService;
import com.phoenixhell.servicebase.exceptionhandler.MyException;
import com.phoenixhell.utils.CommonResult;
import com.phoenixhell.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author phoeixhell
 * @since 2021-03-28
 */
@RestController
@RequestMapping("/serviceOrder/order")
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/create/{courseId}")
    public CommonResult createOrder(@PathVariable String courseId, HttpServletRequest request){
        String userId = JwtTokenUtil.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(userId)){
            throw new MyException(20001, "请登录");
        }
        String orderId = orderService.createOrder(courseId,userId);
        return CommonResult.ok().data("orderId",orderId);
    }

    @GetMapping("/get/{id}")
    public CommonResult getOrder(@PathVariable String id){
        return CommonResult.ok().data("order",orderService.getById(id));
    }

    @PostMapping("/delete/{id}")
    public CommonResult deleteOrder(@PathVariable String id){
        String deletedOrder = orderService.deleteOrder(id);
        return CommonResult.ok().data("order",orderService.getById(id));
    }
    @GetMapping("/payStatus/{courseId}/{userId}")
    public CommonResult payStatus(@PathVariable String courseId, @PathVariable String userId){
        Integer count = orderService.query().eq("course_id", courseId).eq("member_id", userId)
                .eq("status",1).count();
        if(count>0){
            return CommonResult.ok().data("isPayed",true);
        }else{
            return CommonResult.ok().data("isPayed",false);
        }
    }
}

