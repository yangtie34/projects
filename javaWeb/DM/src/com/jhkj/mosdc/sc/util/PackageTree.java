package com.jhkj.mosdc.sc.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 将结果集封装成哈希表和树根节点。
 * User: Administrator
 * Date: 13-5-16
 * Time: 下午5:23
 */
public class PackageTree {
    /**
     * 根据结果集封装Hashnode数据。
     * @param nodes
     * @return
     */
    public static Map<Long,TreeNode> list2HashNode(List<TreeNode> nodes){
        Map<Long,TreeNode> nodeMap = new TreeMap<Long,TreeNode>();
        for (TreeNode node : nodes){
            nodeMap.put(node.getId(),node);
        }
        Iterator<Long> it = nodeMap.keySet().iterator();
        while(it.hasNext()){
            long index = it.next();
            TreeNode treeNode =nodeMap.get(index);
            boolean isExist = nodeMap.containsKey(treeNode.getPid());
            if(isExist){
                nodeMap.get(treeNode.getPid()).getChildren().add(treeNode);
            }
        }
        return nodeMap;
    }
    /**
     * 将传递过来的数组集合转换成属性结构的数据。
     * @param  nodes
     * @return
     */
    public static TreeNode getRootFromList(List<TreeNode> nodes){
        Map<Long,TreeNode> nodeMap = new HashMap<Long,TreeNode>();
        long rootid = 0l;
        for (TreeNode node : nodes){
            nodeMap.put(node.getId(),node);
        }
        Iterator<Long> it = nodeMap.keySet().iterator();
        while(it.hasNext()){
            long index = it.next();
            TreeNode treeNode =nodeMap.get(index);
            boolean isExist = nodeMap.containsKey(treeNode.getPid());
            if(isExist){
                TreeNode pNode = nodeMap.get(treeNode.getPid());
                pNode.getChildren().add(treeNode);
                pNode.setLeaf(false);
            }else{
                rootid = treeNode.getId();
                treeNode.setLeaf(false);
            }
        }
        return nodeMap.get(rootid);
    }
}
