package com.phoenixhell.serviceAcl.mapper;

import java.security.Permission;
import java.util.List;

public interface TokenUserDetailsMapper {
    List<Permission> getPermissionListByUserId(String userId);
    List<String> getPermissionStringListByUserId(String userId);
}
