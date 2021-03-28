package com.phoenixhell.serviceOrder.service.impl;

import com.phoenixhell.serviceOrder.entity.PayLog;
import com.phoenixhell.serviceOrder.mapper.PayLogMapper;
import com.phoenixhell.serviceOrder.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author phoeixhell
 * @since 2021-03-28
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

}
