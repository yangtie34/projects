package com.jhkj.mosdc.output.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.jhkj.mosdc.framework.dao.impl.BaseDaoImpl;
import com.jhkj.mosdc.output.dao.OutputCommonDao;
import com.jhkj.mosdc.output.po.Node;
import com.jhkj.mosdc.output.po.Tree;

public class OutputCommonDaoImpl extends BaseDaoImpl implements OutputCommonDao {

	@Override
	public List queryPageDataByPageId(String pageId) {
		List result = new ArrayList();
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		String sql = "select t1.pageid,t4.title pagetitle,t1.templateid,t5.title templatetitle,t8.ywmc as templatetype, "
				+ "t2.funcareaid,t2.sortnumber funareaxh,t6.fnname,t6.fndescription,t6.helpinfo,t7.mc,t7.componentstyle "
				+ ",t7.componenttype,t7.servicename,t3.componentid,t3.sortnumber compxh,t6.helpinfo "
				+ "from tb_sc_ym_mb t1,tb_sc_mb_gn t2,tb_sc_gnq_zj t3,tb_sc_ym t4,tb_sc_mb t5,tb_sc_gnq t6 ,tb_sc_zj t7,tb_tjgn_template t8,tb_sc_ym t9  "
				+ "where t1.pageid= t9.id and t9.pageid= "
				+ pageId
				+ " and t1.templateid = t5.id and t2.funcareaid=t6.id and t3.componentid = t7.id "
				+ "and t1.templateid = t2.templateid and t2.funcareaid = t3.funareaid and t4.id = t1.pageid  and t5.templatetype=t8.id order by funareaxh,compxh ";
		result = session.createSQLQuery(sql).list();
		session.close();
		return result;
	}

	@Override
	public List queryPageDataByPageIdAndTempId(String pageId, String templateId) {
		List result = new ArrayList();
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		String sql = "select t1.pageid,t4.title pagetitle,t1.templateid,t5.title templatetitle,t8.ywmc as templatetype, "
				+ "t2.funcareaid,t2.sortnumber funareaxh,t6.fnname,t6.fndescription,t6.helpinfo,t7.mc,t7.componentstyle  "
				+ ",t7.componenttype,t7.servicename,t3.componentid,t3.sortnumber compxh,t6.helpinfo "
				+ "from tb_sc_ym_mb t1,tb_sc_mb_gn t2,tb_sc_gnq_zj t3,tb_sc_ym t4,tb_sc_mb t5,tb_sc_gnq t6 ,tb_sc_zj t7,tb_tjgn_template t8,tb_sc_ym t9 "
				+ "where t1.pageid= t9.id and t9.pageid= "
				+ pageId
				+ "and t1.templateid= "
				+ templateId
				+ " and t1.templateid = t5.id and t2.funcareaid=t6.id and t3.componentid = t7.id "
				+ "and t1.templateid = t2.templateid and t2.funcareaid = t3.funareaid and t4.id = t1.pageid  and t5.templatetype=t8.id order by funareaxh,compxh ";
		result = session.createSQLQuery(sql).list();
		session.close();
		return result;
	}

	@Override
	public List queryCompoById(String id) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		List result = new ArrayList();
		String sql = "select * from tb_sc_zj where id=" + id;
		result = session.createSQLQuery(sql).list();
		session.close();
		return result;
	}

	@Override
	public List queryStartstopDataBydate(String currentDate) {
		List result = new ArrayList();
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		String sql = "select xqkssj,xqjssj from tb_jw_pkzb t where '"
				+ currentDate + "' between xqkssj and xqjssj";
		result = session.createSQLQuery(sql).list();
		session.close();
		return result;
	}

	@Override
	public List queryWbgnByWbid(String wbid) {
		List<Object> result = new ArrayList<Object>();
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		String sql = "select * from tb_sc_wbtjgnb where id=" + wbid;
		result = session.createSQLQuery(sql).list();
		session.close();
		return result;
	}
	
	public Node queryTreeData(String tableName, String nodeId) {
		List<Object> result = new ArrayList<Object>();
		Tree tree = new Tree();
		String sql = "select id,dm,mc,fjd_id,cc,cclx,sfyzjd,sfky from "+tableName;
		Map<String,Node> nodeMap = null;
		nodeMap = (Map<String, Node>) super.getJdbcTemplate().query(sql, new ResultSetExtractor() {
			public Object extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				// TODO Auto-generated method stub
				Map<String,Node> nodeMap = new HashMap<String, Node>();
				while(rs.next()){
					Node node = new Node();
					node.setId(rs.getLong("id"));
					node.setDm(rs.getString("dm"));
					node.setText(rs.getString("mc"));
					node.setPid(rs.getLong("fjd_id"));
					node.setCclx(rs.getString("cclx"));
					node.setLeaf(rs.getInt("sfyzjd")==1?true:false);
					nodeMap.put(node.getId().toString(), node);
				}
				return nodeMap;
			}
		});
		for(Entry<String, Node> entry : nodeMap.entrySet()){
	        Node node = entry.getValue();
	        Node pnode = nodeMap.get(node.getPid().toString());
	        if(pnode != null)pnode.getChildren().add(node);
		}
		return nodeMap.get(nodeId);
	}

}
