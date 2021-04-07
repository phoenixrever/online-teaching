package com.phoenixhell.serviceAcl.utils;

import com.phoenixhell.serviceAcl.entity.AclPermission;
import com.phoenixhell.serviceAcl.entity.vo.MenuVo;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MenuHelper {
    public static void getSubjectList(List<AclPermission> permissionList, List<MenuVo> oneSubjectList) {
        for (MenuVo oneSubject : oneSubjectList) {
            List<MenuVo> subList = new ArrayList<>();
            for (AclPermission permission : permissionList) {
                if (permission.getPid().equals(oneSubject.getId())) {
                    subList.add(menuVo(permission));
                }
            }
            String redirectPath = null;
            Collections.sort(subList);
            oneSubject.setChildren(subList);//所有二级
            if (!StringUtils.isEmpty(oneSubject.getPath()) && oneSubject.getChildren().size() != 0) {
                redirectPath = oneSubject.getPath() + "/" + oneSubject.getChildren().get(0).getPath();
            } else {
                redirectPath = null;
            }
            oneSubject.setRedirect(redirectPath);
            HashMap<String, String> map = new HashMap<>();
            map.put("title", oneSubject.getName());
            map.put("icon", oneSubject.getIcon());
            oneSubject.setMeta(map);
            getSubjectList(permissionList, subList);
        }
    }

    public static MenuVo menuVo(AclPermission permission) {
        MenuVo menuVo = new MenuVo();
        BeanUtils.copyProperties(permission, menuVo);
//        menuVo.setName(permission.getName()+permission.getId());
        if (!StringUtils.isEmpty(permission.getPath())) {
//            if (permission.getPath().contains("id")) {
            if (permission.getType()==2) {
                menuVo.setHidden(true);
            }
        }
        return menuVo;
    }
}
