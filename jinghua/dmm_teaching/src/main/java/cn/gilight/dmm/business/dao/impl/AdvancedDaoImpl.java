package cn.gilight.dmm.business.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import cn.gilight.dmm.business.dao.AdvancedDao;
import cn.gilight.dmm.business.entity.AdvancedSource;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.entity.NodeAngularTree;

/**
 * 高级查询
 * 
 * @author xuebl
 * @date 2016年5月18日 下午12:03:38
 */
@Repository("advancedDao")
public class AdvancedDaoImpl implements AdvancedDao {

	@Resource
	private BaseDao baseDao;

	@Override
	public List<AdvancedSource> getAdvancedList(String tag){
		String sql = "select s.group_ as \"group\", s.type_ type, t.code_ code, s.name_ name, t.isall isAll, s.table_ as \"table\","
				+ " case when t.service is not null then t.service else s.service end service,"
				+ " case when t.param is not null then t.param else s.param end param"
				+ " from t_service_advanced t, t_service_advanced_source s"
				+ " where t.code_=s.code_ and t.istrue=1 and t.tag='" +tag+ "' order by t.order_";
		return baseDao.queryForListBean(sql, AdvancedSource.class);
	}

	@Cacheable(value="xgTeaCache",key="'AdvancedDao.getDeptTeachList'+#deptSql")
	@Override
	public List<NodeAngularTree> getDeptTeachList(String deptSql){
		String sql = "select t.id, t.name_ mc, t.pid pid from "+Constant.TABLE_T_Code_Dept_Teach+" t,(" +deptSql+ ") s where t.id=s.id";
		return baseDao.queryForListBean(sql, NodeAngularTree.class);
	}

	@Cacheable(value="xgTeaCache",key="'AdvancedDao.getDeptList'+#deptSql")
	@Override
	public List<NodeAngularTree> getDeptList(String deptSql){
		String sql = "select t.id, t.name_ mc, t.pid pid from "+Constant.TABLE_T_Code_Dept+" t,(" +deptSql+ ") s where t.id=s.id";
		return baseDao.queryForListBean(sql, NodeAngularTree.class);
	}

	@Override
	public List<NodeAngularTree> getOriginList(){
		String sql = "select t.id, t.name_ mc, t.pid pid from t_code_admini_div t where t.istrue = 1";
		return baseDao.queryForListBean(sql, NodeAngularTree.class);
	}
	
}
