package com.phoenixhell.serviceOrder.service;

import com.phoenixhell.serviceOrder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author phoeixhell
 * @since 2021-03-28
 */
public interface PayLogService extends IService<PayLog> {

    Map<String, String> createQR(String orderNo);

    Map<String, String> queryPayStatus(String orderNo);

    void updateOrderStatus(Map<String, String> map);
}
