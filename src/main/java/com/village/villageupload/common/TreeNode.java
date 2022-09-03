package com.village.villageupload.common;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class TreeNode {
    protected String id;
    protected String parentId;
    protected String name;
    protected List<TreeNode> children = new ArrayList<TreeNode>();
    public void add(TreeNode node) {
        children.add(node);
    }
}
