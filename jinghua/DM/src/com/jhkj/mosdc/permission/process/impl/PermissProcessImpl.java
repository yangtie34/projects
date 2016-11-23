package com.jhkj.mosdc.permission.process.impl;

import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.permission.po.MenuTree;
import com.jhkj.mosdc.permission.process.PermissProcess;
import com.jhkj.mosdc.permission.service.PermissService;

/**
 * @Comments: 获取权限
 * Company: 河南精华科技有限公司
 * Created by gaodongjie(gaodongjie@126.com) 
 * @DATE:2012-6-19
 * @TIME: 下午7:09:15
 */
public class PermissProcessImpl implements PermissProcess{
	//权限信息服务
	private PermissService permissService;
	
	public void setPermissService(PermissService permissService) {
		this.permissService = permissService;
	}

	@Override
	public Object queryPermissCdzy(String param) throws Exception {
		 MenuTree tree = permissService.queryPermissCdzy(param);
		if(tree != null){
	        return Struts2Utils.objects2Json(tree.root);
		}else{
			return "{success:false,errorInfo:'登录超时，请重新登录！'}"; 
		}
	};
	
	
	/*private TreeNode getChildNode(TreeNode treeNode){
		TsCdzy tsCdzy = cdzyList.get(i);
		Long bm = getCdzyBm(tsCdzy.getCdssflId());
		if(bm == 1){
			int j = 0;
			while(j<cdzyList.size()){
				TsCdzy tsCdzy2 = cdzyList.get(j);
				Long bm2 = getCdzyBm(tsCdzy2.getCdssflId());
				if(!bm2.equals(4) && !bm2.equals(5) && tsCdzy.getId().equals(tsCdzy2.getSjzyId())){
					if(bm2.equals(3)){
						//代码为3时是菜单,获取按钮权限
						List<String> permissList = getButtons(userInfo.getId(), tsCdzy.getId());
						treeNode = new TreeNode(tsCdzy2.getId(), tsCdzy2.getSjzyId(), tsCdzy2.getCdzymc(), tsCdzy2.getCdlj() == null ? "":tsCdzy2.getCdlj(), "0", bm, "buttons:{"+permissList.toString()+"}");
						tree.addNode(treeNode);
						break;
					}else{
						treeNode = new TreeNode(tsCdzy2.getId(), tsCdzy2.getSjzyId(), tsCdzy2.getCdzymc(), tsCdzy2.getCdlj() == null ? "":tsCdzy2.getCdlj(), "1", bm, "");
						tree.addNode(treeNode);
					}
				}
				j++;
			}
		}
		return null;
	}*/
	
	/*private MenuTree getChild(List list,Long id,Long userId) throws Exception{
		TreeNode treeNode = null;
		MenuTree tree = null;
		for(int i = 0;i<list.size();i++){
			TsCdzy tsCdzy = (TsCdzy) list.get(i);
			Long bm = getCdzyBm(tsCdzy.getCdssflId());
			if(bm == 3 ){
				//代码为3时是菜单,获取按钮权限
				List<String> permissList = getButtons(userId, tsCdzy.getId());
				treeNode = new TreeNode(tsCdzy.getId(), tsCdzy.getSjzyId(), tsCdzy.getCdzymc(), tsCdzy.getCdlj() == null ? "":tsCdzy.getCdlj(), "0", bm, "buttons:{"+permissList.toString()+"}");
				tree.addNode(treeNode);
				continue;
			}else {
				getChild(list,tsCdzy.getId(),userId);
				treeNode = new TreeNode(tsCdzy.getId(), tsCdzy.getSjzyId(), tsCdzy.getCdzymc(), tsCdzy.getCdlj() == null ? "":tsCdzy.getCdlj(), "1", bm, "");
				tree.addNode(treeNode);
			}
		}
		return tree;
	}*/

}
