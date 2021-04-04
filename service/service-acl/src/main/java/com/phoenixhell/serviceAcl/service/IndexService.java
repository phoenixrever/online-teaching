package com.phoenixhell.serviceAcl.service;

import com.phoenixhell.serviceAcl.entity.AclUser;

import java.util.Map;

public interface IndexService {
    Map getUserInfoByName(String username);
}
