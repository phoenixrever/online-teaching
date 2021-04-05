package com.phoenixhell.serviceAcl.mapper;

import com.phoenixhell.serviceAcl.entity.AclPermission;

import java.security.Permission;
import java.util.List;

public interface TokenUserDetailsMapper {
    List<AclPermission> getPermissionListByUserId(String userId);
    List<String> getPermissionValueListByUserId(String userId);
}
