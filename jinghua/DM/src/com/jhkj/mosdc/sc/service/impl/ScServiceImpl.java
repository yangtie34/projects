package com.jhkj.mosdc.sc.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.jhkj.mosdc.framework.service.impl.BaseServiceImpl;
import com.jhkj.mosdc.permiss.domain.User;
import com.jhkj.mosdc.permiss.util.UserPermiss;
import com.jhkj.mosdc.sc.service.ScService;
import com.jhkj.mosdc.sc.util.PackageTree;
import com.jhkj.mosdc.sc.util.TreeNode;

public class ScServiceImpl extends BaseServiceImpl implements ScService {
	
	@Override
	public Map getXxxx() {
		String sql ="SELECT ID,MC,QXM,CCLX,FJD_ID FROM TB_JXZZJG WHERE FJD_ID = -1 OR FJD_ID IS NULL";
		List<Map> result = baseDao.querySqlList(sql);
		if(result.size()==0){
			return null;
		}
		return result.get(0);
	}
    @Override
    public String getSszzjgTree(String params) {
    	User user = UserPermiss.getUser();
		String jxzzjgids = user.getCurrentJxzzjgIds();
		List<TreeNode> nodes = new ArrayList<TreeNode>();
    	if("".equals(jxzzjgids)||jxzzjgids==null){
    		Map xx = this.getXxxx();
        	if(xx == null){
        		return "{success:false,msg:'没有维护学校信息或者学校父节点id为-1'}";
        	}
        	String xxid = xx.get("ID").toString();
        	String sql ="SELECT CC,MC,ID,CASE WHEN CCLX='QY' THEN "+xxid
        			+" ELSE FJD_ID END AS FJD_ID FROM TB_DORM_CCJG WHERE CCLX='QY' OR CCLX='LY' ";
    		List<Map<String,Object>> result = baseDao.querySqlList(sql);
    		
    		TreeNode root = new TreeNode();
    		root.setCc(0l);
    		String xxmc = xx.get("MC").toString();
    		root.setText(xxmc);
    		root.setId(Long.parseLong(xxid));
    		root.setPid(-1l);
    		nodes.add(root);
    		for(Map<String,Object> obj : result){
    			TreeNode node = new TreeNode();
    			node.setCc(Long.parseLong(obj.get("CC")==null?"0":obj.get("CC").toString()));
    			node.setText(obj.get("MC").toString());
    			node.setId(Long.parseLong(obj.get("ID").toString()));
    			node.setPid(Long.parseLong(obj.get("FJD_ID")==null?"-1":obj.get("FJD_ID").toString()));
    			nodes.add(node);
    		}
		}else{
    		TreeNode root = new TreeNode();
    		root.setCc(0l);
    		String xxmc = "您所负责的学院";
    		root.setText(xxmc);
    		root.setId(0l);
    		root.setPid(-1l);
    		nodes.add(root);
    		String sql ="SELECT ID,MC,FJD_ID,CC FROM TB_JXZZJG"
    				  + " WHERE SFKY=1 and cc=1 and id in ("+jxzzjgids+") order by cc";
    		List<Map<String,Object>> result = baseDao.querySqlList(sql);
    		for(Map<String,Object> obj : result){
    			TreeNode node = new TreeNode();
    			node.setCc(Long.parseLong(obj.get("CC")==null?"0":obj.get("CC").toString()));
    			node.setText(obj.get("MC").toString());
    			node.setId(Long.parseLong(obj.get("ID").toString()));
    			node.setPid(Long.parseLong(obj.get("FJD_ID")==null?"-1":obj.get("FJD_ID").toString()));
    			nodes.add(node);
    			
    			String ssSql =" SELECT SSID ID,NAME MC FROM (SELECT B.SSID,B.NAME,B.CS,B.FJS,B.CWS,(A.CWS/B.CWS*100) ZSL "+
    					" FROM ( SELECT L.ID SSID,L.MC NAME ,COUNT(CW.ID) CWS FROM TB_DORM_CCJG L"+
    					" LEFT JOIN TB_DORM_CCJG C ON L.ID = C.FJD_ID "+
    					" LEFT JOIN TB_DORM_CCJG FJ ON C.ID = FJ.FJD_ID "+
    					" LEFT JOIN TB_DORM_CW CW ON CW.FJ_ID = FJ.ID "+
    					" left join tb_dorm_zy zy on zy.cw_id = cw.id "+
    					" left join tb_xjda_xjxx xj on xj.id = zy.xs_id "+
    					" left join tb_xzzzjg bz on bz.id = xj.yx_id "+
    					" WHERE L.CCLX = 'LY'and bz.id = "+obj.get("ID").toString()+" GROUP BY L.MC,L.ID ORDER BY L.ID ) a "+
    					" inner join (SELECT L.ID SSID,L.MC NAME,COUNT(DISTINCT(C.ID)) CS , "+
    					" COUNT(DISTINCT(FJ.ID)) FJS,COUNT(CW.ID) CWS FROM TB_DORM_CCJG L "+
    					" LEFT JOIN TB_DORM_CCJG C ON L.ID = C.FJD_ID "+
    					" LEFT JOIN TB_DORM_CCJG FJ ON C.ID = FJ.FJD_ID "+
    					" LEFT JOIN TB_DORM_CW CW ON CW.FJ_ID = FJ.ID "+
    					" WHERE L.CCLX = 'LY'GROUP BY L.MC,L.ID ORDER BY L.ID) b on a.ssid =b.ssid) where zsl>=50";
    			List<Map<String,Object>> ssResult = baseDao.querySqlList(ssSql);
    			for(Map<String,Object> ss : ssResult){
    				node= new TreeNode();
    				node.setCc(2l);
    				node.setText(ss.get("MC").toString());
    				node.setId(Long.parseLong(ss.get("ID").toString()));
    				node.setPid(Long.parseLong(obj.get("ID").toString()));
        			nodes.add(node);
    			}
    			
    		}
		}
		Map<Long,TreeNode> hashmap = PackageTree.list2HashNode(nodes);
		return map2Json(hashmap);
    }
    @Override
    public String getKyzzjgTree(String params) {
    	User user = UserPermiss.getUser();
		String jxzzjgids = user.getCurrentJxzzjgIds();
    	String sql ="SELECT * FROM TB_XZZZJG WHERE ID IN ("+jxzzjgids+") and id !=0";
    	if("".equals(jxzzjgids)||jxzzjgids==null){
			sql ="SELECT * FROM TB_XZZZJG";
		}
		List<Map<String,Object>> result = baseDao.querySqlList(sql);
		
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		for(Map<String,Object> obj : result){
			TreeNode node = new TreeNode();
			node.setCc(Long.parseLong(obj.get("CC")==null?"0":obj.get("CC").toString()));
			node.setText(obj.get("MC").toString());
			node.setId(Long.parseLong(obj.get("ID").toString()));
			node.setPid(Long.parseLong(obj.get("FJD_ID")==null?"-1":obj.get("FJD_ID").toString()));
			node.setQxm(obj.get("QXM")==null?"":obj.get("QXM").toString());
			node.setCclx(obj.get("CCLX")==null?"":obj.get("CCLX").toString());
			nodes.add(node);
		}
		
		Map<Long,TreeNode> hashmap = PackageTree.list2HashNode(nodes);
		return map2Json(hashmap);
    }
	@Override
	public String getJxzzjgTree(String params) {
		User user = UserPermiss.getUser();
		String jxzzjgids = user.getCurrentJxzzjgIds();
		String sql ="SELECT * FROM TB_JXZZJG WHERE SFKY=1 AND id in ("+jxzzjgids+") and id !=0";
		if("".equals(jxzzjgids)||jxzzjgids==null){
			sql ="SELECT * FROM TB_JXZZJG WHERE SFKY=1 ";
		}
		List<Map<String,Object>> result = baseDao.querySqlList(sql);
		
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		for(Map<String,Object> obj : result){
			TreeNode node = new TreeNode();
			node.setCc(Long.parseLong(obj.get("CC")==null?"0":obj.get("CC").toString()));
			node.setText(obj.get("MC").toString());
			node.setId(Long.parseLong(obj.get("ID").toString()));
			node.setPid(Long.parseLong(obj.get("FJD_ID")==null?"-1":obj.get("FJD_ID").toString()));
			node.setQxm(obj.get("QXM")==null?"":obj.get("QXM").toString());
			node.setCclx(obj.get("CCLX")==null?"":obj.get("CCLX").toString());
			nodes.add(node);
		}
		
		Map<Long,TreeNode> hashmap = PackageTree.list2HashNode(nodes);
		return map2Json(hashmap);
	}
	
	@Override
	public String getYxzzjgTree(String params) {
		User user = UserPermiss.getUser();
		String jxzzjgids = user.getCurrentJxzzjgIds();
		String sql ="SELECT * FROM TB_XZZZJG WHERE (CC=1 AND SFKY=1 AND ID IN ("+jxzzjgids+") and id !=0) ";
		if("".equals(jxzzjgids)||jxzzjgids==null){
			sql ="SELECT * FROM TB_XZZZJG WHERE (CC=1 AND SFKY=1) OR FJD_ID =-1 OR FJD_ID IS NULL ";
		}
		List<Map<String,Object>> result = baseDao.querySqlList(sql);
		
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		for(Map<String,Object> obj : result){
			TreeNode node = new TreeNode();
			node.setCc(Long.parseLong(obj.get("CC")==null?"0":obj.get("CC").toString()));
			node.setText(obj.get("MC").toString());
			node.setId(Long.parseLong(obj.get("ID").toString()));
			node.setPid(Long.parseLong(obj.get("FJD_ID")==null?"-1":obj.get("FJD_ID").toString()));
			node.setQxm(obj.get("QXM")==null?"":obj.get("QXM").toString());
			node.setCclx(obj.get("CCLX")==null?"":obj.get("CCLX").toString());
			nodes.add(node);
		}
		
		Map<Long,TreeNode> hashmap = PackageTree.list2HashNode(nodes);
		return map2Json(hashmap);
	}
	@Override
	public String getYxCcTree(String params) {
		User user = UserPermiss.getUser();
		String jxzzjgids = user.getCurrentJxzzjgIds();
		String sql ="SELECT * FROM TB_JXZZJG WHERE (CC=1 AND MC !='继续教育学院' AND ID IN ("+jxzzjgids+") and id !=0) ";
		if("".equals(jxzzjgids)||jxzzjgids==null){
			sql ="SELECT * FROM TB_JXZZJG WHERE (CC=1 AND MC !='继续教育学院' ) OR FJD_ID =-1 OR FJD_ID IS NULL ";
		}
		List<Map<String,Object>> result = baseDao.querySqlList(sql);
		
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		for(Map<String,Object> obj : result){
			TreeNode node = new TreeNode();
			node.setCc(Long.parseLong(obj.get("CC")==null?"0":obj.get("CC").toString()));
			node.setText(obj.get("MC").toString());
			node.setId(Long.parseLong(obj.get("ID").toString()));
			node.setPid(Long.parseLong(obj.get("FJD_ID")==null?"-1":obj.get("FJD_ID").toString()));
			node.setQxm(obj.get("QXM")==null?"":obj.get("QXM").toString());
			node.setCclx(obj.get("CCLX")==null?"":obj.get("CCLX").toString());
			nodes.add(node);
		}
		
		Map<Long,TreeNode> hashmap = PackageTree.list2HashNode(nodes);
		return map2Json(hashmap);
	}
	@Override
	public TreeNode getJxNodeById(String nodeid) {
		String sql ="Select * from tb_jxzzjg where id ="+nodeid;
		List<Map> result = baseDao.querySqlList(sql);
		TreeNode node = new TreeNode();
		if(result.size()!=0){
			String qxm = result.get(0).get("QXM")==null?"":result.get(0).get("QXM").toString();
			String text = result.get(0).get("MC")==null?"":result.get(0).get("MC").toString();
			String cclx = result.get(0).get("CCLX")==null?"":result.get(0).get("CCLX").toString();
			node.setId(Long.parseLong(nodeid));
			node.setQxm(qxm);
			node.setText(text);
			node.setCclx(cclx);
		}
		return node;
	}
	@Override
	public TreeNode getXzNodeById(String nodeid) {
		String sql ="Select * from tb_xzzzjg where id ="+nodeid;
		List<Map> result = baseDao.querySqlList(sql);
		TreeNode node = new TreeNode();
		if(result.size()!=0){
			String qxm = result.get(0).get("QXM")==null?"":result.get(0).get("QXM").toString();
			String text = result.get(0).get("MC")==null?"":result.get(0).get("MC").toString();
			String cclx = result.get(0).get("CCLX")==null?"":result.get(0).get("CCLX").toString();
			node.setId(Long.parseLong(nodeid));
			node.setQxm(qxm);
			node.setText(text);
			node.setCclx(cclx);
		}
		return node;
	}
	
	/**
	 * 转换Map 2 JSON
	 * @param map
	 * @return
	 */
	private String map2Json(Map<Long,TreeNode> map){
		StringBuffer json = new StringBuffer();
    	json.append("{");
    	Iterator<Long> it = map.keySet().iterator();
    	while(it.hasNext()){
    		Long key = it.next();
    		TreeNode value = map.get(key);
    		json.append(key).append(":").append(value.toString()).append(",");
    	}
    	json.setCharAt(json.length() - 1, '}');
		
    	return json.toString();
	}
}
