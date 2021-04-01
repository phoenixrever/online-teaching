package com.phoenixhell.serviceAcl.controller;


import com.phoenixhell.serviceAcl.entity.AclPermission;
import com.phoenixhell.serviceAcl.entity.vo.TreeNode;
import com.phoenixhell.serviceAcl.service.AclPermissionService;
import com.phoenixhell.utils.CommonResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Permission;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private int  i=0;
    @GetMapping("/listAllSubject")
    public List<TreeNode> getList() {
        List<AclPermission> permissionList = aclPermissionService.list();
        List<TreeNode> rootSubjectList = permissionList.stream().
                filter(x -> x.getPid().equals(String.valueOf('0')))
                .map(y -> permissionVo(y))
                .collect(Collectors.toList());
        getSubjectList(permissionList, rootSubjectList);
        System.out.println(rootSubjectList);
        return rootSubjectList;
    }

    //方法1 递归查询所有子级目录
    private void getSubjectList(List<AclPermission> permissionList, List<TreeNode> rootSubjectList) {
        for (TreeNode oneSubject : rootSubjectList) {
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

    //递归方法
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

    //vo换bean方法
    private TreeNode permissionVo(AclPermission permission) {
        return new TreeNode(permission.getId(), permission.getPid(), permission.getName());
    }

    //=====================================================================================
    @DeleteMapping("/delete/{id}")
    public CommonResult deleteById(@PathVariable String id) {
        List<AclPermission> permissionList = aclPermissionService.query().select("id,pid").list();
//        方法1
//        deleteRecursion2(id, permissionList, aclPermissionService);

        //方法2
        List<String> toRemoveList = new ArrayList<>();
        deleteRecursion(id,toRemoveList,permissionList);
        aclPermissionService.removeByIds(toRemoveList);

        return CommonResult.ok().emptyData().data("integer",toRemoveList.size());
    }

    //
    private void deleteRecursion(String id,List<String> toRemoveList,List<AclPermission> permissionList){
        for(AclPermission p:permissionList){
            if(p.getPid().equals(id)){
                deleteRecursion(p.getId(),toRemoveList,permissionList);
            }
        }
        toRemoveList.add(id);
    }
    //此方法不好 需要多次连接数据库
    //循环调用看有没子节点有调用自己没有直接删除
    private void deleteRecursion2(String id,List<AclPermission> permissionList,AclPermissionService aclPermissionService){
        for(AclPermission p:permissionList){
            if(p.getPid().equals(id)){
                deleteRecursion2(p.getId(),permissionList,aclPermissionService);
            }
        }
        aclPermissionService.removeById(id);
    }
}

