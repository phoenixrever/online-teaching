package com.phoenixhell.serviceAcl.service.impl;

import com.phoenixhell.security.entity.SecurityUser;
import com.phoenixhell.security.entity.User;
import com.phoenixhell.serviceAcl.entity.AclPermission;
import com.phoenixhell.serviceAcl.entity.AclUser;
import com.phoenixhell.serviceAcl.entity.AclUserRole;
import com.phoenixhell.serviceAcl.mapper.TokenUserDetailsMapper;
import com.phoenixhell.serviceAcl.service.AclPermissionService;
import com.phoenixhell.serviceAcl.service.AclUserRoleService;
import com.phoenixhell.serviceAcl.service.AclUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.Permission;
import java.security.acl.Acl;
import java.util.List;

/**
 * @author phoenixhell
 * @since 2021/4/3 0003-下午 4:45
 */
@Service("userDetailsService")
public class TokenUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private AclUserService aclUserService;
    @Resource
    private TokenUserDetailsMapper tokenUserDetailsMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AclUser aclUser = aclUserService.query().eq("username", username).one();
        // 判断用户是否存在
        if (null == aclUser){
            throw new UsernameNotFoundException("用户名不存在！");
        }
        User user = new User();
        BeanUtils.copyProperties(aclUser, user);
        SecurityUser securityUser = new SecurityUser(user);
        List<String> permissionValueStringListAuthorities = tokenUserDetailsMapper.getPermissionValueListByUserId(aclUser.getId());
        securityUser.setPermissionList(permissionValueStringListAuthorities);
        return securityUser;
    }
}
