package com.thinkgem.jeesite.modules.sys.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/5/24.
 */
public class UserTreeNode {
    private String title;

    private String name;

    private String className;

    private String side;//0 左边   1右边

    private List<UserTreeNode> children;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<UserTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<UserTreeNode> children) {
        this.children = children;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    private String parentId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public UserTreeNode(String title, String name, String className, String parentId, String id,String side) {
        this.title = title;
        this.name = name;
        this.className = className;
        this.parentId = parentId;
        this.id = id;
    }

    public UserTreeNode(){

    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }
}
