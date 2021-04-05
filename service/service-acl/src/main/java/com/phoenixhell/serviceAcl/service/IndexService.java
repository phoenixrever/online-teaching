package com.phoenixhell.serviceAcl.service;

import com.alibaba.fastjson.JSONObject;
import com.phoenixhell.serviceAcl.entity.AclUser;

import java.util.List;
import java.util.Map;

public interface IndexService {
    Map getUserInfoByName(String username);
//    List<JSONObject> getMenuByUserId(String userId);
}
