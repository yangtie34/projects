package com.jhkj.mosdc.output.po;

public class Tree {
	private Node root;
	// 根据id获取节点递归方法用到的变量。
	private static Node anode;
	// 根据fid获取cclx为某个值得递归方法用的变量。
	private static Node cclxNode;
	public void addNode(Node node){
		eqNode(root,node);
	}
	private void eqNode(Node fN, Node node) {
		if(fN==null){
			root = node;
			return;
		}
		if (fN.getId().equals(node.getPid())) {
			fN.getChildren().add(node);
		} else {
			for (Node n : fN.getChildren()) {
				eqNode(n, node);
			}
		}
	}
	public Node getRoot() {
		return root;
	}
	public void setRoot(Node root) {
		this.root = root;
	}
	/**
	 * 根据id获取数节点。
	 * @param node 需要遍历的树根节点。
	 * @param id 树节点id
	 * @return 找到返回该节点，找不到返回null
	 */
	public static Node getNodeById(Node node,Long id){
		if(id.longValue()==node.getId().longValue()){
			anode = node;
			return anode;
		}else{
			for(Node cNode : node.getChildren()){
				getNodeById(cNode,id);
			}
		}
		return anode;
	}
	/**
	 * 根据节点id，层次类型和Node向上追溯到层次类型为XQ的节点。
	 * @param node
	 * @param id
	 * @param cclx
	 * @return
	 */
	public static Node getCclxByNodeId(Node root,Long pid ,String cclx){
		// 获取该节点的父节点
		Node fNode = getNodeById(root,pid);
		if(cclx.equals(fNode.getCclx())){
			cclxNode = fNode;
			return cclxNode;
		}else {
			getCclxByNodeId(root,fNode.getPid(),cclx);
		}
		return cclxNode;
	}
}
