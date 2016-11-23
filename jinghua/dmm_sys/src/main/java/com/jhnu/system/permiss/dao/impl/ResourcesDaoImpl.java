package com.jhnu.system.permiss.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.framework.entity.NodeAngularTree;
import com.jhnu.framework.entity.NodeTree;
import com.jhnu.system.common.page.Page;
import com.jhnu.system.permiss.dao.ResourcesDao;
import com.jhnu.system.permiss.entity.Resources;
import com.jhnu.system.permiss.entity.Role;
import com.jhnu.system.permiss.entity.User;
import com.jhnu.util.common.StringUtils;

@Repository("resourcesDao")
public class ResourcesDaoImpl implements ResourcesDao {
	@Autowired
	private BaseDao baseDao;

	@Override
	public Resources createResource(final Resources resources) {
		final String SQL = "insert into t_sys_resources(id,name_,url_,description,pid,level_,path_,istrue,order_,keyword,resource_type_code,shiro_tag,sysgroup_,isshow) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		final long ID = baseDao.getBaseDao().getSeqGenerator().nextLongValue();
		Resources resourc=new Resources();
		resourc.setId(resources.getPid());
		String pPath=getResourcesByThis(resourc).get(0).getPath_();
		baseDao.getBaseDao()
				.getJdbcTemplate()
				.update(SQL,
						new Object[] { ID, resources.getName_(),
								resources.getUrl_(),
								resources.getDescription(), resources.getPid(),
								resources.getLevel_(),
								pPath + ID,
								resources.getIstrue(), resources.getOrder_(),
								resources.getKeyWord(),
								resources.getResource_type_code(),
								resources.getShiro_tag(),
								resources.getSysGroup_(),
								resources.getIsShow()});

		resources.setId(ID);
		return resources;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Resources> getResourcesByThis(Resources resources) {
		StringBuffer sql = new StringBuffer(
				"select t.*,co.name_ resource_type from t_sys_resources t "
						+ " left join t_code co on co.code_type = 'RESOURCE_TYPE_CODE' and co.code_ =  t.resource_type_code where 1=1 ");
		if (resources != null) {
			if (resources.getId() != null) {
				sql.append(" and t.id = " + resources.getId());
			}
			if (resources.getSysGroup_() != null) {
				sql.append(" and t.sysgroup_ = " + resources.getSysGroup_());
			}
			if (StringUtils.hasLength(resources.getName_())) {
				sql.append(" and t.name_ = '" + resources.getName_() + "'");
			}
			if (StringUtils.hasLength(resources.getUrl_())) {
				sql.append(" and t.url_ = '" + resources.getUrl_() + "'");
			}
			if (StringUtils.hasLength(resources.getDescription())) {
				sql.append(" and t.description like '%"
						+ resources.getDescription() + "%'");
			}
			if (resources.getPid() != null) {
				sql.append(" and t.pid = '" + resources.getPid() + "'");
			}
			if (resources.getLevel_() != null) {
				sql.append(" and t.level_ = '" + resources.getLevel_() + "'");
			}
			if (StringUtils.hasLength(resources.getPath_())) {
				sql.append(" and t.path_ = '" + resources.getPath_() + "'");
			}
			if (resources.getIstrue() != null) {
				sql.append(" and t.istrue = '" + resources.getIstrue() + "'");
			}
			if(resources.getIsShow() != null){
				sql.append(" and t.isshow = '"+ resources.getIsShow() +"'");
			}
			if (resources.getOrder_() != null) {
				sql.append(" and t.order_ = '" + resources.getOrder_() + "'");
			}
			if (StringUtils.hasLength(resources.getKeyWord())) {
				sql.append(" and t.keyword = '" + resources.getKeyWord() + "'");
			}
			if (StringUtils.hasLength(resources.getSysGroup_())) {
				sql.append(" and t.sysgroup_ = '" + resources.getSysGroup_() + "'");
			}
			if (StringUtils.hasLength(resources.getResource_type_code())) {
				sql.append(" and t.resource_type_code = '"
						+ resources.getResource_type_code() + "'");
			}
			if (StringUtils.hasLength(resources.getShiro_tag())) {
				sql.append(" and t.shiro_tag = '" + resources.getShiro_tag()
						+ "'");
			}
			sql.append(" order by t.level_,t.order_,t.id ");
		}
		return baseDao
				.getBaseDao()
				.getJdbcTemplate()
				.query(sql.toString(),
						(RowMapper) new BeanPropertyRowMapper(Resources.class));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Resources> getAllResources() {
		String sql = "select * from t_sys_resources start with pid=-1 connect by prior id=pid order siblings by level_,order_ ";
		return baseDao
				.getBaseDao()
				.getJdbcTemplate()
				.query(sql,
						(RowMapper<Resources>) new BeanPropertyRowMapper(
								Resources.class));
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Resources> getAllResources(String sys) {
		String sql = "select * from t_sys_resources  where istrue=1 and sysGroup_ in(select id from t_sys_resources where "+"';'||lower(url_)||';'"+" like '%;"+sys.toLowerCase()+";%' or "+"';'||lower(url_)||';'"+" like '%;"+sys.toLowerCase()+"/;%' ) start with pid=-1 connect by prior id=pid  order siblings by level_,order_ ";
		return baseDao
				.getBaseDao()
				.getJdbcTemplate()
				.query(sql,(RowMapper<Resources>) new BeanPropertyRowMapper(Resources.class));
	}
	@Override
	public void deleteResources(Long resourcesId) {
		String sql = "delete t_sys_resources t where t.id = ?";
		baseDao.getBaseDao().getJdbcTemplate().update(sql, resourcesId);
	}

	@Override
	public void updateResources(Resources resources) {
		if (resources != null) {
			String sql = "update  t_sys_resources t set t.path_=?,t.name_ = ?,t.url_= ?,t.description=?,t.istrue=?,t.order_=?,t.keyword=?,t.resource_type_code=?,t.shiro_tag=?,t.sysgroup_=?,t.isshow=? where t.id = ?";
			Resources resourc=new Resources();
			resourc.setId(resources.getPid());
			String pPath=getResourcesByThis(resourc).get(0).getPath_();
			baseDao.getBaseDao()
					.getJdbcTemplate()
					.update(sql,
							new Object[] {
									pPath + resources.getId(),
									resources.getName_(), resources.getUrl_(),
									resources.getDescription(),
									resources.getIstrue(),
									resources.getOrder_(),
									resources.getKeyWord(),
									resources.getResource_type_code(),
									resources.getShiro_tag(),
									resources.getSysGroup_(),resources.getIsShow() , resources.getId()});
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Resources findById(Long id) {
		String sql = "select t.*,t.resource_type_code resource_type from t_sys_resources t where t.id = ?";
		List<Resources> resList = baseDao
				.getBaseDao()
				.getJdbcTemplate()
				.query(sql,
						(RowMapper<Resources>) new BeanPropertyRowMapper(
								Resources.class), new Object[] { id });
		return resList.size() == 0 ? null : resList.get(0);
	}

	@Override
	public Page getResourcesPageByThis(int currentPage, int numPerPage,
			Resources resources) {
		StringBuffer sql = new StringBuffer(
				"select t.*,co.name_ resource_type from t_sys_resources t "
						+ " left join t_code co on co.code_type = 'RESOURCE_TYPE_CODE' and co.code_ =  t.resource_type_code where 1=1 ");
		if (resources != null) {
			if (resources.getId() != null) {
				sql.append(" and t.id = " + resources.getId());
			}
			if (StringUtils.hasLength(resources.getName_())) {
				sql.append(" and t.name_ like '%" + resources.getName_() + "%'");
			}
			if (StringUtils.hasLength(resources.getUrl_())) {
				sql.append(" and t.url_ = '" + resources.getUrl_() + "'");
			}
			if (resources.getSysGroup_() != null) {
				sql.append(" and t.sysgroup_ = " + resources.getSysGroup_());
			}
			if (StringUtils.hasLength(resources.getDescription())) {
				sql.append(" and t.description like '%"
						+ resources.getDescription() + "%'");
			}
			if (resources.getPid() != null) {
				sql.append(" and t.pid = '" + resources.getPid() + "'");
			}
			if (resources.getLevel_() != null) {
				sql.append(" and t.level_ = '" + resources.getLevel_() + "'");
			}
			if (StringUtils.hasLength(resources.getPath_())) {
				sql.append(" and t.path_ = '" + resources.getPath_() + "'");
			}
			if (resources.getIstrue() != null) {
				sql.append(" and t.istrue = '" + resources.getIstrue() + "'");
			}
			if(resources.getIstrue() != null){
				sql.append(" and t.istrue = '"+ resources.getIstrue() +"'");
			}
			if (resources.getOrder_() != null) {
				sql.append(" and t.order_ = '" + resources.getOrder_() + "'");
			}
			if (StringUtils.hasLength(resources.getKeyWord())) {
				sql.append(" and t.keyword = '" + resources.getKeyWord() + "'");
			}
			if (StringUtils.hasLength(resources.getSysGroup_())) {
				sql.append(" and t.sysgroup_ = '" + resources.getSysGroup_() + "'");
			}
			if (StringUtils.hasLength(resources.getResource_type_code())) {
				sql.append(" and t.resource_type_code = '"
						+ resources.getResource_type_code() + "'");
			}
			if (StringUtils.hasLength(resources.getShiro_tag())) {
				sql.append(" and t.shiro_tag = '" + resources.getShiro_tag()
						+ "'");
			}
			sql.append(" order by t.level_,t.order_,t.id ");
		}
		return new Page(sql.toString(), currentPage, numPerPage, baseDao
				.getBaseDao().getJdbcTemplate(), null, resources);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Resources getMainPageByRole(Role role) {
		StringBuffer sql = new StringBuffer(
				"select res.* from t_sys_resources res inner join t_sys_role role on res.id=role.resourceid "
						+ "where 1=1 ");
		if (role != null) {
			if (role.getId() != null) {
				sql.append(" and role.id=" + role.getId());
			}
			if (role.getIsmain() != null) {
				sql.append(" and role.ismain=" + role.getIsmain());
			}
			if (StringUtils.hasLength(role.getName_())) {
				sql.append(" and role.name_='" + role.getName_() + "' ");
			}
		}
		List<Resources> resList = baseDao
				.getBaseDao()
				.getJdbcTemplate()
				.query(sql.toString(),
						(RowMapper<Resources>) new BeanPropertyRowMapper(
								Resources.class));
		return resList.size() == 0 ? null : resList.get(0);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Resources getMainPageByUser(User user) {
		StringBuffer sql = new StringBuffer(
				"select res.* from t_sys_resources res inner join t_sys_role role on res.id=role.resourceid "
						+ "inner join t_sys_user_role ur on role.id= ur.role_id inner join t_sys_user u on ur.user_id=u.id "
						+ "where role.ismain=1 ");
		if (user != null) {
			if (user.getId() != null) {
				sql.append(" and u.id=" + user.getId());
			}
			if (StringUtils.hasLength(user.getUsername())) {
				sql.append(" and u.username='" + user.getUsername() + "' ");
			}
		}
		List<Resources> resList = baseDao
				.getBaseDao()
				.getJdbcTemplate()
				.query(sql.toString(),
						(RowMapper<Resources>) new BeanPropertyRowMapper(
								Resources.class));
		return resList.size() == 0 ? null : resList.get(0);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<NodeTree> getResourceTree() {
		StringBuffer sql = new StringBuffer(
				"select t.id,t.name_ mc,t.pid,level_ from t_sys_resources t "
						+ " left join t_code co on co.code_type = 'RESOURCE_TYPE_CODE' and co.code_ =  t.resource_type_code where 1=1 ");
		sql.append(" order by t.order_,t.id ");
		return baseDao
				.getBaseDao()
				.getJdbcTemplate()
				.query(sql.toString(),
						(RowMapper) new BeanPropertyRowMapper(
								NodeTree.class));
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<NodeAngularTree> getResourceAngularTree(Resources resources) {
		StringBuffer sql = new StringBuffer(
				"select t.id,t.name_ mc,t.pid from t_sys_resources t "
						+ " left join t_code co on co.code_type = 'RESOURCE_TYPE_CODE' and co.code_ =  t.resource_type_code where 1=1 ");
		if (resources != null) {
			if (resources.getId() != null) {
				sql.append(" and t.id = " + resources.getId());
			}
			if (StringUtils.hasLength(resources.getName_())) {
				sql.append(" and t.name_ like '%" + resources.getName_() + "%'");
			}
			if (resources.getSysGroup_() != null) {
				sql.append(" and t.sysgroup_ = " + resources.getSysGroup_());
			}
			if (StringUtils.hasLength(resources.getUrl_())) {
				sql.append(" and t.url_ = '" + resources.getUrl_() + "'");
			}
			if (StringUtils.hasLength(resources.getDescription())) {
				sql.append(" and t.description like '%"
						+ resources.getDescription() + "%'");
			}
			if (resources.getPid() != null) {
				sql.append(" and t.pid = '" + resources.getPid() + "'");
			}
			if (resources.getLevel_() != null) {
				sql.append(" and t.level_ = '" + resources.getLevel_() + "'");
			}
			if (StringUtils.hasLength(resources.getPath_())) {
				sql.append(" and t.path_ = '" + resources.getPath_() + "'");
			}
			if (resources.getIstrue() != null) {
				sql.append(" and t.istrue = '" + resources.getIstrue() + "'");
			}
			if (resources.getOrder_() != null) {
				sql.append(" and t.order_ = '" + resources.getOrder_() + "'");
			}
			if (StringUtils.hasLength(resources.getKeyWord())) {
				sql.append(" and t.keyword = '" + resources.getKeyWord() + "'");
			}
			if (StringUtils.hasLength(resources.getSysGroup_())) {
				sql.append(" and t.sysgroup_ = '" + resources.getSysGroup_() + "'");
			}
			if (StringUtils.hasLength(resources.getResource_type_code())) {
				sql.append(" and t.resource_type_code = '"
						+ resources.getResource_type_code() + "'");
			}
			if (StringUtils.hasLength(resources.getShiro_tag())) {
				sql.append(" and t.shiro_tag = '" + resources.getShiro_tag()
						+ "'");
			}
			sql.append(" order by t.order_,t.id ");
		}
		return baseDao
				.getBaseDao()
				.getJdbcTemplate()
				.query(sql.toString(),
						(RowMapper) new BeanPropertyRowMapper(
								NodeAngularTree.class));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Resources> getMenusByUserName(String username,String sys) {
		String sql="select r.* from "+
				"(select distinct id from (  "+
				"select rp.resource_id id from t_sys_user u left join t_sys_user_role ur on u.id=ur.user_id "+
				"left join t_sys_role_perm rp on ur.role_id=rp.role_id where u.username=? "+
				"union all "+
				"select up.resource_id id from t_sys_user u  "+
				"left join t_sys_user_perm up on u.id=up.user_id where u.username=?) )t "+
				"inner join t_sys_resources r on t.id=r.id "+
				"where (r.resource_type_code='01' or r.resource_type_code='02') and r.istrue=1 "+
				"and r.sysgroup_ = (select id from t_sys_resources where "+"';'||lower(url_)||';'"+" like '%;"+sys.toLowerCase()+";%' or "+"';'||lower(url_)||';'"+" like '%;"+sys.toLowerCase()+"/;%' ) "+
				"order  by  r.level_ ,r.order_ ";
		return baseDao.getBaseDao().getJdbcTemplate().query(sql, new Object[]{username,username},(RowMapper<Resources>)new BeanPropertyRowMapper(Resources.class));
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public boolean hasMenuByUserName(String username,String basePath,String sys) {
		String sql="select r.* from "+
				"(select distinct id from (  "+
				"select rp.resource_id id from t_sys_user u left join t_sys_user_role ur on u.id=ur.user_id "+
				"left join t_sys_role_perm rp on ur.role_id=rp.role_id where u.username=? "+
				"union all "+
				"select up.resource_id id from t_sys_user u  "+
				"left join t_sys_user_perm up on u.id=up.user_id where u.username=?) )t "+
				"inner join t_sys_resources r on t.id=r.id "+
				"where (r.resource_type_code='01' or r.resource_type_code='02') and r.istrue=1 "+
				"and r.sysgroup_ = (select id from t_sys_resources where "+"';'||lower(url_)||';'"+" like '%;"+basePath.toLowerCase()+";%' or "+"';'||lower(url_)||';'"+" like '%;"+basePath.toLowerCase()+"/;%' ) "
				+ "and r.url_=?"+
				"order  by  r.level_ ,r.order_ ";
		return baseDao.getBaseDao().getJdbcTemplate().query(sql, new Object[]{username,username,sys},(RowMapper<Resources>)new BeanPropertyRowMapper(Resources.class)).size()>0;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<NodeAngularTree> getPermssionsById(String flag, String id) {
		String sql = "select t.id,t.name_ mc,t.pid  "
				+ " from t_sys_resources t "
				+ " inner join  t_sys_"+flag+"_perm tt on t.id=tt.resource_id and tt."+flag+"_id='"+id+"' "
				+ " left join t_code co on co.code_type = 'RESOURCE_TYPE_CODE' and co.code_ =  t.resource_type_code order by t.level_,t.order_,t.id ";
		return baseDao
				.getBaseDao()
				.getJdbcTemplate()
				.query(sql,
						(RowMapper) new BeanPropertyRowMapper(
								NodeAngularTree.class));
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<NodeTree> getPermssionsJLById(String flag, String id) {//级联查询
		String sql = "select t.id ,t.name_ mc,pid ,level_ "
				+ " from t_sys_resources t "
				+ " inner join  t_sys_"+flag+"_perm tt on t.id=tt.resource_id and tt."+flag+"_id='"+id+"' "
				+ " left join t_code co on co.code_type = 'RESOURCE_TYPE_CODE' and co.code_ =  t.resource_type_code  ";
		/*sql="select id,name_ mc,pid ,level_ "
				+ " from t_sys_resources  "
				+ "start with id in ("+sql+")  "
				+" connect by prior pid=id   order siblings by order_ ";*///级联查询
		return baseDao
				.getBaseDao()
				.getJdbcTemplate()
				.query(sql,
						(RowMapper) new BeanPropertyRowMapper(
								NodeTree.class));
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Resources> getMenusByUserNameShiroTag(String username,String shiroTag) {
		String sql="select r.* from "+
				"(select distinct id from (  "+
				"select rp.resource_id id from t_sys_user u left join t_sys_user_role ur on u.id=ur.user_id "+
				"left join t_sys_role_perm rp on ur.role_id=rp.role_id where u.username=? "+
				"union all "+
				"select up.resource_id id from t_sys_user u  "+
				"left join t_sys_user_perm up on u.id=up.user_id where u.username=?) )t "+
				"inner join t_sys_resources r on t.id=r.id "+
				"where (r.resource_type_code='01' or r.resource_type_code='02') and r.istrue=1 "+
				"and (r.shiro_tag like '"+shiroTag+":%' or r.shiro_tag = '"+shiroTag+"') "+
				"order  by  r.level_ ,r.order_ ";
		return baseDao.getBaseDao().getJdbcTemplate().query(sql, new Object[]{username,username},(RowMapper<Resources>)new BeanPropertyRowMapper(Resources.class));
	}

	@Override
	public List<String> getAllPermssionByUserName(String username) {
		String sql = "select distinct id from (  "
				+ "select rp.wirldcard id from t_sys_user u left join t_sys_user_role ur on u.id=ur.user_id "
				+ "left join t_sys_role_perm rp on ur.role_id=rp.role_id where u.username=? and rp.wirldcard is not null "
				+ "union all "
				+ "select up.wirldcard id from t_sys_user u  "
				+ "left join t_sys_user_perm up on u.id=up.user_id where u.username=? and up.wirldcard is not null) ";
		sql="select a.id from t_sys_resources r inner join ( "+sql+" ) a on r.shiro_tag=substr(a.id,0,instr(a.id,':',-1,1)-1) where r.istrue=1 ";
		return baseDao
				.getBaseDao()
				.getJdbcTemplate()
				.queryForList(sql, new Object[] { username, username },
						String.class);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Resources> hasPermssion(String username, String shiroTag) {
		String star=shiroTag.substring(0,shiroTag.lastIndexOf(":")+1);
		String checkString="in ('"+shiroTag+"','"+star+"*') ";
		String sql="select r.* from "+
				"(select distinct id from (  "+
				"select rp.resource_id id from t_sys_user u left join t_sys_user_role ur on u.id=ur.user_id "+
					"left join t_sys_role_perm rp on ur.role_id=rp.role_id where u.username=? and rp.wirldcard "+checkString+
					" union all "+
					"select up.resource_id id from t_sys_user u  "+
					"left join t_sys_user_perm up on u.id=up.user_id where u.username=? and up.wirldcard "+checkString+" ) )t "+
					"inner join t_sys_resources r on t.id=r.id "+
					"where  r.istrue=1 "+
					"order  by  r.level_ ,r.order_ ";
		return baseDao
				.getBaseDao()
				.getJdbcTemplate()
				.query(sql,
						new Object[] { username, username },
						(RowMapper<Resources>) new BeanPropertyRowMapper(
								Resources.class));
	}
}
