package com.phoenixhell.serviceAcl.service.impl;

import com.phoenixhell.serviceAcl.entity.AclUser;
import com.phoenixhell.serviceAcl.mapper.AclUserMapper;
import com.phoenixhell.serviceAcl.service.AclUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author phoeixhell
 * @since 2021-03-31
 */
@Service
public class AclUserServiceImpl extends ServiceImpl<AclUserMapper, AclUser> implements AclUserService {

}
