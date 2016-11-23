package com.jhkj.mosdc.permission.po;


/**
 * @Comments: 菜单资源树
 * Company: 河南精华科技有限公司
 * Created by gaodongjie(gaodongjie@126.com) 
 * @DATE:2012-6-2
 * @TIME: 下午8:27:59
 */
public class Tree
{
//根节点
public TreeNode root;
//根据ID获取到的TreeNode对象
private static TreeNode anode;
//public Node root = new Node(new Long(0),new Long(-1),"根节点");

public void addNode(TreeNode node)
{
  eqNode(root,node);
}
private void eqNode(TreeNode fN,TreeNode node)
{
	if(null==node.getPid()){
		node.setPid(new Long(0));
	}
  if(fN.getId().equals(node.getPid()))
  {
   fN.children.add(node);
  }
  else
  {
   for(TreeNode n :fN.children)
   {
     eqNode(n,node);
   }
  }
}

public void display(TreeNode node,String s)
{
  System.out.println(s+node.getText());
  s+="|------";
  if(node.children.size()>0)
  {
   for(TreeNode n: node.children)
   {
    display(n,s);
   }
  }
}
//----------------------------------------------------2013-05-20--------------------------------------------
/**
 * 通过ID找TreeNode
 * @param node 顶级节点
 * @param id　上级节点
 * @return　node
 */
public static TreeNode getNodeById(TreeNode node,Long id){
	if(id.longValue()==node.getId().longValue()){
		anode = node;
		return anode;
	}else{
		for(TreeNode cNode : node.getChildren()){
			getNodeById(cNode,id);
		}
	}
	return anode;
}
//-------------------------------------------------------------------------------------------------------------
public TreeNode getRoot() {
	return root;
}
public void setRoot(TreeNode root) {
	this.root = root;
}
}

