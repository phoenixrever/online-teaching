package com.phoenixhell.serviceAcl.entity.vo;

import com.phoenixhell.serviceAcl.entity.AclPermission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author phoenixhell
 * @since 2021/3/31 0031-下午 4:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreeNode extends AclPermission {
//    private String id;
//    private String pid;
//    private String label;
    private List<TreeNode> children;

//    public TreeNode(String id, String pid, String label) {
//        this.id = id;
//        this.pid = pid;
//        this.label = label;
//    }
}
