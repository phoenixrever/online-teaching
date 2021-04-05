package com.phoenixhell.serviceAcl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.phoenixhell.serviceAcl.entity.AclPermission;
import com.phoenixhell.serviceAcl.entity.AclUser;
import com.phoenixhell.serviceAcl.entity.AclUserRole;
import com.phoenixhell.serviceAcl.service.AclRoleService;
import com.phoenixhell.serviceAcl.service.AclUserRoleService;
import com.phoenixhell.serviceAcl.service.AclUserService;
import com.phoenixhell.serviceAcl.service.IndexService;
import com.phoenixhell.servicebase.exceptionhandler.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IndexServiceImpl implements IndexService {
    @Autowired
    private AclUserService aclUserService;
    @Autowired
    private AclRoleService aclRoleService;
    @Autowired
    private AclUserRoleService aclUserRoleService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Map getUserInfoByName(String username) {
        Map map = new HashMap();
        AclUser user = aclUserService.query().eq("username", username).one();
        if (null == user) {
            throw new MyException(20001, "没有此用户");
        }
        map.put("name",user.getUsername());
        List<AclUserRole> userRoles = aclUserRoleService.query().eq("user_id", user.getId()).list();
        ArrayList<String> roleList = new ArrayList<>();
        if (userRoles.size() == 0) {
            roleList.add("");
        } else {
            for (AclUserRole r : userRoles) {
                String roleName = aclRoleService.getById(r.getId()).getRoleName();
                roleList.add(roleName);
            }
        }
        map.put("roles", roleList);
//        List<String> list = (List<String>) redisTemplate.opsForValue().get(username);
//        map.put("permissions", list);
        map.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");

        return map;
    }
//
//    @Override
//    public List<JSONObject> getMenuByUserId(String userId) {
//        return null;
//    }
}
