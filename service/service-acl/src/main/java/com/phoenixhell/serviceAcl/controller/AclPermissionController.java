//package com.phoenixhell.serviceAcl.controller;
//
//
//import com.phoenixhell.serviceAcl.entity.AclPermission;
//import com.phoenixhell.serviceAcl.entity.vo.TreeNode;
//import com.phoenixhell.serviceAcl.service.AclPermissionService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * <p>
// * 权限 前端控制器
// * </p>
// *
// * @author phoeixhell
// * @since 2021-03-31
// */
//@RestController
//@RequestMapping("/serviceAcl/acl-permission")
//public class AclPermissionController {
//    @Autowired
//    private AclPermissionService aclPermissionService;
//    @GetMapping("/listAll")
//    public  List<TreeNode> getList(){
//        List<AclPermission> list = aclPermissionService.list();
//        List child=null;
//        for(AclPermission p:list){
//             child = getChild(p, list);
//            System.out.println(child);
//        }
//        System.out.println(child);
//        return child;
//    }
//
//
//    private List<TreeNode> getChild(AclPermission permission, List<AclPermission> permissionList){
//        List<TreeNode> finalList=new ArrayList<>();
//        TreeNode treeNode=new TreeNode();
//        for(AclPermission x:permissionList){
//            if(permission.getId().equals(x.getPid())){
//                treeNode.setLabel(permission.getId());
//                treeNode.getChildren().add(getChild(x));
//            }
//            finalList.add(treeNode);
//        }
//        return finalList;
//    }
//    private void getParent(AclPermission permission, List<AclPermission> permissionList){
//        List<TreeNode> finalList=new ArrayList<>();
//        TreeNode treeNode=new TreeNode();
//        for(AclPermission x:permissionList){
//            if(x.getPid().equals(permission.getId())){
//                treeNode.setLabel(permission.getId());
//                treeNode.getChildren().add(getChild(x));
//            }
//            finalList.add(treeNode);
//        }
//        return finalList;
//    }
//
//
//}
//
