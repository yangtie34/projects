package com.jhnu.syspermiss.permiss.dao.impl;

import java.util.List;

import com.jhnu.syspermiss.permiss.dao.ResourcesDao;
import com.jhnu.syspermiss.permiss.entity.Resources;
import com.jhnu.syspermiss.permiss.entity.Role;
import com.jhnu.syspermiss.permiss.entity.User;
import com.jhnu.syspermiss.util.StringUtils;
import com.jhnu.syspermiss.util.jdbcUtil.BaseDao;

public class ResourcesDaoImpl implements ResourcesDao {
	private BaseDao baseDao=BaseDao.getInstance();
	private ResourcesDaoImpl() {
		
	}  
    private static ResourcesDaoImpl ResourcesDaoImpl=null;
	
	public static ResourcesDaoImpl getInstance() {
		if (ResourcesDaoImpl == null){
			synchronized (new String()) {
				if (ResourcesDaoImpl == null){
					ResourcesDaoImpl = new ResourcesDaoImpl();
				}
			}
		}
		return ResourcesDaoImpl;
	}
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<Resources> getAllResources() {
		String sql = "select * from t_sys_resources start with pid=-1 connect by prior id=pid order siblings by level_,order_ ";
		return baseDao.query(sql, Resources.class);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Resources> getAllResources(String sys) {
		String sql = "select * from t_sys_resources  where istrue=1 and (resource_type_code='01' or resource_type_code='02' or(resource_type_code='06' and level_='3')) "
				+ "and sysGroup_ in(select id from t_sys_resources where "+"';'||lower(url_)||';'"+" like '%;"+sys.toLowerCase()+";%' or "+"';'||lower(url_)||';'"+" like '%;"+sys.toLowerCase()+"/;%' ) start with pid=-1 connect by prior id=pid order siblings by level_,order_";
		return baseDao.query(sql, Resources.class);
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public Resources findById(Long id) {
		String sql = "select t.*,t.resource_type_code resource_type from t_sys_resources t where t.id = ?";
		List<Resources> resList=baseDao.query(sql,Resources.class,new Object[]{id});
		return resList.size()==0?null:resList.get(0);
	}


	@SuppressWarnings({ "unchecked" })
	@Override
	public Resources getMainPageByRole(Role role) {
		StringBuffer sql =new StringBuffer("select res.* from t_sys_resources res inner join t_sys_role role on res.id=role.resourceid "+
					"where 1=1 ");
		if(role!=null){
			if(role.getId()!=null){
				sql.append(" and role.id="+role.getId());
			}
			if(role.getIsmain()!=null){
				sql.append(" and role.ismain="+role.getIsmain());
			}
			if(StringUtils.hasLength(role.getName_())){
				sql.append(" and role.name_='"+role.getName_()+"' ");
			}
		}
		List<Resources> resList=baseDao.query(sql.toString(),Resources.class);
		return resList.size()==0?null:resList.get(0);
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public Resources getMainPageByUser(User user) {
		StringBuffer sql =new StringBuffer("select res.* from t_sys_resources res inner join t_sys_role role on res.id=role.resourceid "
				+ "inner join t_sys_user_role ur on role.id= ur.role_id inner join t_sys_user u on ur.user_id=u.id "+
				"where role.ismain=1 ");
		if(user!=null){
			if(user.getId()!=null){
				sql.append(" and u.id="+user.getId());
			}
			if(StringUtils.hasLength(user.getUsername())){
				sql.append(" and u.username='"+user.getUsername()+"' ");
			}
		}
		List<Resources> resList=baseDao.query(sql.toString(),Resources.class);
		return resList.size()==0?null:resList.get(0);
	}
    

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<Resources> getMenusByUserName(String username) {
		String sql="select r.* from "+
					"(select distinct id from (  "+
					"select rp.resource_id id from t_sys_user u left join t_sys_user_role ur on u.id=ur.user_id "+
					"left join t_sys_role_perm rp on ur.role_id=rp.role_id where u.username=? "+
					"union all "+
					"select up.resource_id id from t_sys_user u  "+
					"left join t_sys_user_perm up on u.id=up.user_id where u.username=?) )t "+
					"inner join t_sys_resources r on t.id=r.id "+
					"where (r.resource_type_code='01' or r.resource_type_code='02') and r.istrue=1 "+
					"order  by  r.level_ ,r.order_ ";
		return baseDao.query(sql,Resources.class, new Object[]{username,username});
	}
	@SuppressWarnings({ "unchecked" })
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
				"where (r.resource_type_code='01' or r.resource_type_code='02' or(resource_type_code='06' and level_='3')) and r.istrue=1 "+
				"and r.sysgroup_ = (select id from t_sys_resources where "+"';'||lower(url_)||';'"+" like '%;"+sys.toLowerCase()+";%' or "+"';'||lower(url_)||';'"+" like '%;"+sys.toLowerCase()+"/;%' ) "+
				"order  by  r.level_ ,r.order_ ";
	return baseDao.query(sql,Resources.class, new Object[]{username,username});
}
	@SuppressWarnings({ "unchecked" })
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
	return baseDao.query(sql,Resources.class, new Object[]{username,username});
}
            
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllPermssionByUserName(String username) {
		//动作和冗余字段wirldcard不使用   
		/*String sql = "select distinct id from (  "
				+ "select rp.wirldcard id from t_sys_user u left join t_sys_user_role ur on u.id=ur.user_id "
				+ "left join t_sys_role_perm rp on ur.role_id=rp.role_id where u.username=? and rp.wirldcard is not null "
				+ "union all "
				+ "select up.wirldcard id from t_sys_user u  "
				+ "left join t_sys_user_perm up on u.id=up.user_id where u.username=? and up.wirldcard is not null) ";
		sql="select a.id from t_sys_resources r inner join ( "+sql+" ) a on r.shiro_tag=substr(a.id,0,instr(a.id,':',-1,1)-1) where r.istrue=1 ";*/
		
		
		String sql1= "select rp.resource_id from t_sys_user u, t_sys_role r, t_sys_user_role ur, t_sys_role_perm rp where u.username=? "
        		+ "and u.id=ur.user_id and r.id=ur.role_id and r.id=rp.role_id and r.istrue=1 ";
        String sql2= "select up.resource_id from t_sys_user u, t_sys_user_perm up where u.username = ? and u.id = up.user_id ";
        String sql="select distinct resource_id from ( "+sql1+" union all "+sql2+" )";
        sql="select r.shiro_tag||':*' id from t_sys_resources r where r.id in ( "+sql+" ) and r.istrue=1 ";
	return baseDao.queryForList(sql,String.class, new Object[]{username,username});
}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<Resources> hasPermssion(String username, String shiroTag) {
		//动作和冗余字段wirldcard不使用   
		/*String star=shiroTag.substring(0,shiroTag.lastIndexOf(":")+1);
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
					"order  by  r.level_ ,r.order_ ";*/
		
		String sql1= "select rp.resource_id from t_sys_user u, t_sys_role r, t_sys_user_role ur, t_sys_role_perm rp where u.username=? "
        		+ "and u.id=ur.user_id and r.id=ur.role_id and r.id=rp.role_id and r.istrue=1 ";
        String sql2= "select up.resource_id from t_sys_user u, t_sys_user_perm up where u.username = ? and u.id = up.user_id ";
        String sql="select distinct resource_id from ( "+sql1+" union all "+sql2+" )";
        sql="select r.* from t_sys_resources r where r.id in ( "+sql+" ) and r.istrue=1 and r.shiro_tag||':*'= ? ";
	return baseDao.query(sql,Resources.class, new Object[]{username,username});
}

	@SuppressWarnings({ "unchecked"})
	@Override
	public List<Resources> getResourcesByThis(Resources resources) {
		StringBuffer sql =new StringBuffer("select t.*,co.name_ resource_type from t_sys_resources t "
				+ " left join t_code co on co.code_type = 'RESOURCE_TYPE_CODE' and co.code_ =  t.resource_type_code where 1=1 ");
		if(resources != null){
			if(resources.getId() != null){
				sql.append(" and t.id = "+resources.getId());
			}
			if(resources.getSysGroup_()!= null){
				sql.append(" and t.sysgroup_ = "+resources.getSysGroup_());
			}
			if(StringUtils.hasLength(resources.getName_())){
				sql.append(" and t.name_ = '"+ resources.getName_() +"'");
			}
			if(StringUtils.hasLength(resources.getUrl_())){
				sql.append(" and lower(t.url_) = '"+ resources.getUrl_().toLowerCase() +"'");
			}
			if(StringUtils.hasLength(resources.getDescription())){
				sql.append(" and t.description like '%"+ resources.getDescription() +"%'");
			}
			if(resources.getPid() != null){
				sql.append(" and t.pid = '"+ resources.getPid() +"'");
			}
			if(resources.getLevel_() != null){
				sql.append(" and t.level_ = '"+ resources.getLevel_() +"'");
			}
			if(StringUtils.hasLength(resources.getPath_())){
				sql.append(" and t.path_ = '"+ resources.getPath_() +"'");
			}
			if(resources.getIstrue() != null){
				sql.append(" and t.istrue = '"+ resources.getIstrue() +"'");
			}
			if(resources.getIsShow() != null){
				sql.append(" and t.isshow = '"+ resources.getIsShow() +"'");
			}
			if(resources.getOrder_() != null){
				sql.append(" and t.order_ = '"+ resources.getOrder_() +"'");
			}
			if(StringUtils.hasLength(resources.getKeyWord())){
				sql.append(" and t.keyword = '"+ resources.getKeyWord() +"'");
			}
			if(StringUtils.hasLength(resources.getResource_type_code())){
				sql.append(" and t.resource_type_code = '"+ resources.getResource_type_code() +"'");
			}
			if(StringUtils.hasLength(resources.getShiro_tag())){
				sql.append(" and t.shiro_tag = '"+ resources.getShiro_tag() +"'");
			}
		}
		return baseDao.query(sql.toString(),Resources.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Resources findByURL(String basePath, String path) {
		String sql = "select * from t_sys_resources  where istrue=1 and lower(url_)='"+path.toLowerCase()+"' and sysGroup_ in(select id from t_sys_resources where lower(url_) = '"+basePath.toLowerCase()+"' )";
		List<Resources> resList=baseDao.query(sql,Resources.class);
		return resList.size()==0?null:resList.get(0);
	}
}
