package com.phoenixhell.serviceOrder.service;

import com.phoenixhell.serviceOrder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author phoeixhell
 * @since 2021-03-28
 */
public interface OrderService extends IService<Order> {

    String createOrder(String courseId, String userId);

    String deleteOrder(String id);
}
