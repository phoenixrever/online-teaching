package com.phoenixhell.serviceOrder.controller;


import com.phoenixhell.serviceOrder.service.PayLogService;
import com.phoenixhell.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author phoeixhell
 * @since 2021-03-28
 */
@RestController
@RequestMapping("/serviceOrder/payLog")
@CrossOrigin
public class PayLogController {
    @Autowired
    private PayLogService payLogService;

//    生成二维码
    @GetMapping("/createQR/{orderNo}")
    public CommonResult createQR(@PathVariable String orderNo){
        Map<String,Object> map = payLogService.createQR(orderNo);
        return CommonResult.ok().emptyData().data(map);
    }

    @GetMapping("/queryPayStatus/{orderNo}")
    public CommonResult queryPayStatus(@PathVariable String orderNo) {
        //调用查询接口
        Map<String, String> map = payLogService.queryPayStatus(orderNo);
        if (map == null) {//出错
            return CommonResult.error("支付出错").emptyData();
        }
        if ("SUCCESS".equals(map.get("trade_state"))) {//如果成功
            //更改订单状态
            payLogService.updateOrderStatus(map);
            return CommonResult.ok().emptyData().data("state","success");
        }else{
            return CommonResult.ok().emptyData().data("code",25000);
        }
    }
}

