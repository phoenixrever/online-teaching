package com.phoenixhell.serviceAcl.service;


import com.phoenixhell.serviceAcl.entity.AclPermission;

import java.util.List;
import java.util.Map;

public interface IndexService {
    Map getUserInfoByName(String username);
    List<AclPermission> getPermissionListByUserId(String userId);
}