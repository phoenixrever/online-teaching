package com.phoenixhell.serviceAcl.controller;


import com.phoenixhell.serviceAcl.entity.AclPermission;
import com.phoenixhell.serviceAcl.entity.vo.TreeNode;
import com.phoenixhell.serviceAcl.entity.vo.MenuVo;
import com.phoenixhell.serviceAcl.mapper.TokenUserDetailsMapper;
import com.phoenixhell.serviceAcl.service.AclPermissionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限 前端控制器
 * </p>
 *
 * @author phoeixhell
 * @since 2021-03-31
 */
@RestController
@RequestMapping("/serviceAcl/aclPermission")
public class AclPermissionController {
    @Autowired
    private AclPermissionService aclPermissionService;
    @Autowired
    private TokenUserDetailsMapper tokenUserDetailsMapper;
    @GetMapping("/listAll")
    public List<TreeNode> getList() {
        List<AclPermission> list = aclPermissionService.list();
        List<TreeNode> oneSubjectList = list.stream().
                filter(x -> x.getPid().equals(String.valueOf('0')))
                .map(y -> permissionVo(y))
                .collect(Collectors.toList());
        getSubjectList(list, oneSubjectList);
        System.out.println(oneSubjectList);
        return oneSubjectList;
    }
    @GetMapping("/listAll3/{id}")
    public List<MenuVo> getList3(@PathVariable String id) {
        List<AclPermission> list = tokenUserDetailsMapper.getPermissionListByUserId(id);
        System.out.println(list);
        List<MenuVo> oneSubjectList = list.stream().
                filter(x -> x.getPid().equals(String.valueOf('0')))
                .map(y -> menuVo(y))
                .collect(Collectors.toList());
        getSubjectList3(list, oneSubjectList);
        System.out.println(oneSubjectList);
        return oneSubjectList;
    }
    //方法1
    private void getSubjectList3(List<AclPermission> permissionList, List<MenuVo> oneSubjectList) {
        for (MenuVo oneSubject : oneSubjectList) {
            List<MenuVo> subList = new ArrayList<>();
            for (AclPermission permission : permissionList) {
                if (permission.getPid().equals(oneSubject.getId())) {
                    subList.add(menuVo(permission));
                }
            }
            oneSubject.setChildren(subList);//所有二级
            oneSubject.setRedirect(oneSubject.getPath()+"/"+oneSubject.getChildren().get(0).getPath());
            HashMap<String, String> map = new HashMap<>();
            map.put("title",oneSubject.getName());
            map.put("icon",oneSubject.getIcon());
            oneSubject.setMeta(map);
            getSubjectList3(permissionList, subList);
        }
    }
    //方法1
    private void getSubjectList(List<AclPermission> permissionList, List<TreeNode> oneSubjectList) {
        for (TreeNode oneSubject : oneSubjectList) {
            List<TreeNode> subList = new ArrayList<>();
            for (AclPermission permission : permissionList) {
                if (permission.getPid().equals(oneSubject.getId())) {
                    subList.add(permissionVo(permission));
                }
            }
            oneSubject.setChildren(subList);//所有二级
            getSubjectList(permissionList, subList);
        }
    }




    //方法2
    @GetMapping("/listAll2")
    public List<TreeNode> getList2() {
        List<AclPermission> list = aclPermissionService.list();
        List<TreeNode> oneSubjectList = list.stream().
                filter(x -> x.getPid().equals(String.valueOf('0')))
                .map(y -> permissionVo(y))
                .collect(Collectors.toList());
        TreeNode treeNode = oneSubjectList.get(0);
       oneSubjectList.add(getSubjectList2(list, treeNode));
        System.out.println(oneSubjectList);
        return oneSubjectList;
    }

    private TreeNode getSubjectList2(List<AclPermission> permissionList, TreeNode treeNode) {
        //防止后面赋值null 指针
        treeNode.setChildren(new ArrayList<TreeNode>());
        for (AclPermission permission : permissionList) {
            if (permission.getPid().equals(treeNode.getId())) {
                treeNode.getChildren()
                        .add(getSubjectList2(permissionList,
                                permissionVo(permission)));
            }
        }
        return treeNode;

    }


    private TreeNode permissionVo(AclPermission permission) {
//        return new TreeNode(permission.getId(), permission.getPid(), permission.getName());
        TreeNode treeNode = new TreeNode();
        BeanUtils.copyProperties(permission,treeNode );
        return treeNode;
    }
    private MenuVo menuVo(AclPermission permission) {
//        return new TreeNode(permission.getId(), permission.getPid(), permission.getName());
        MenuVo menuVo = new MenuVo();
        BeanUtils.copyProperties(permission,menuVo );
        if(!StringUtils.isEmpty(permission.getPath())){
            if(permission.getPath().contains("id")){
                menuVo.setHidden(true);
            }
        }
        return menuVo;
    }
}

