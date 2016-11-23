package com.jhkj.mosdc.framework.util;
public class Tree
{
//根节点
public Node root;
//public Node root = new Node(new Long(0),new Long(-1),"根节点");

public void addNode(Node node)
{
  eqNode(root,node);
}
private void   eqNode(Node fN,Node node)
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
   for(Node n :fN.children)
   {
     eqNode(n,node);
   }
  }
}

public void display(Node node,String s)
{
  System.out.println(s+node.getText());
  s+="|------";
  if(node.children.size()>0)
  {
   for(Node n: node.children)
   {
    display(n,s);
   }
  }
}

public void setTreeLeaf(Node node)
{
  if(node.children.size()>0)
  {
   for(Node n: node.children)
   {
	   setTreeLeaf(n);
   }
  }else{
	  node.setLeaf(true);
  }
}

public Node getRoot() {
	return root;
}
public void setRoot(Node root) {
	this.root = root;
}
}

