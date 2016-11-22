package com.jhkj.mosdc.framework.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Table;

import net.sf.json.JSONObject;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.transform.Transformers;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;

import com.jhkj.mosdc.framework.bean.PageParam;
import com.jhkj.mosdc.framework.dao.BaseDao;
import com.jhkj.mosdc.framework.po.TcXxbzdmjg;
import com.jhkj.mosdc.framework.po.TsStsx;
import com.jhkj.mosdc.framework.po.TsXxdmJs;
import com.jhkj.mosdc.framework.util.EhcacheUtil;
import com.jhkj.mosdc.framework.util.JdbcSqlPage;
import com.jhkj.mosdc.framework.util.Page;
import com.jhkj.mosdc.framework.util.ReflectUtil;
import com.jhkj.mosdc.framework.util.SqlParamsChange;
import com.jhkj.mosdc.permiss.po.TpUser;
import com.jhkj.mosdc.permission.po.TsJs;
import com.jhkj.mosdc.permission.po.TsUser;
import com.jhkj.mosdc.xxzy.po.TbXxzyBjxxb;
import com.jhkj.mosdc.xxzy.po.TbXxzyJxzzjg;
import com.jhkj.mosdc.xxzy.po.TbXxzyXzzzjg;

/**
 * @author Administrator 基础DAO实现类
 * 
 */
@SuppressWarnings("rawtypes")
public class BaseDaoImpl extends HibernateDaoSupport implements BaseDao {
	private final String stsx_cacheName = "system_ts_stsx";// 实体属性cache
	// 获取JDBC方式执行方法
	private JdbcTemplate jdbcTemplate;

	private static Map<String, List> bzdmMap = new HashMap<String, List>();
	
	private String whereCloud = "";
	private static TpUser tpUser = null;
	
 
	public String getWhereCloud() {   
	    return whereCloud;   
	}   
	
	public void setWhereCloud(String whereCloud) {   
	    this.whereCloud = whereCloud;   
	}      


	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	@Override
	public TpUser getTpUser(){
		if(tpUser !=null){
			return tpUser;
		}
		String loginName = "\'admin\'";
		List<TpUser> userList =queryEntityList("TpUser", " loginname = ".concat(loginName));
		if(!userList.isEmpty()){
			return tpUser = userList.get(0);
		}
		return null;
	}
	
	/**
	 * 保存方法无返回对象参数
	 */
	public void insert(Object object) {
		try {
			Method getIdMethod = object.getClass().getMethod("getId");
			Object idObj=getIdMethod.invoke(object, null);
			if(idObj==null){
				Method setIdMethod = object.getClass().getMethod("setId",
						new Class[] { Long.class });
				setIdMethod.invoke(object, new Object[] { this.getId() });
			}
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
		this.getHibernateTemplate().save(object);
	}
	//----------------------------------------2013-05-17-----------------------------------------------
	/**
	 * 保存对象并返回对象　(高东杰--新增)
	 * @param object　对象(不带ID)
	 * @return　object 对象(带ID)
	 * @throws Exception
	 */
	@Override
	public Object save(Object object) throws Exception{
		//获取getId()方法
		Method getIdMethod = object.getClass().getMethod("getId");
		//获取ID值
		Object idObj=getIdMethod.invoke(object, null);
		//判断ID是否为空
		if(idObj==null){
			Method setIdMethod = object.getClass().getMethod("setId",
					new Class[] { Long.class });
			setIdMethod.invoke(object, new Object[] { this.getId() });
		}
		//执行保存操作
		this.getHibernateTemplate().save(object);
		return object;
	}
	/**
	 * 批量保存对象　(高东杰--新增)
	 * @param list 对象列表(不带ＩＤ)
	 * @return　list 对象列表
	 * @throws Exception
	 */
	@Override
	public List<Object> save(List<Object> list) throws Exception{
		//判断list是否为空
		if(list.isEmpty()){
			return null;
		}
		List<Object> retList = new ArrayList<Object>();
        if (list != null && list.size() > 0) {  
                // 循环获取对象   
                for (int i = 0; i < list.size(); i++) {
                	Object obj = list.get(i);
                	retList.add(save(obj)); // 保存药品对象   
                    // 批插入的对象立即写入数据库并释放内存   
                    if (i % 10 == 0) {  
                    	this.getHibernateSession().close();
                        this.getHibernateTemplate().flush();  
                        this.getHibernateTemplate().clear();  
                    }  
                }  
        }
        return retList;
	}
	
		
	
	@Override
	public Object saveOrUpdate(Object object)throws Exception{
		//获取getId()方法
		Method getIdMethod = object.getClass().getMethod("getId");
		//获取ID值
		Object idObj=getIdMethod.invoke(object, null);
		//判断ID是否为空
		if(idObj==null){
			Method setIdMethod = object.getClass().getMethod("setId",
					new Class[] { Long.class });
			setIdMethod.invoke(object, new Object[] { this.getId() });
		}
		//执行保存操作
		this.getHibernateTemplate().saveOrUpdate(object);
		return object;
	}
	@Override
	public List<Object> saveOrUpdate(List<Object> list)throws Exception{
		//判断list是否为空
		if(list.isEmpty()){
			return null;
		}
		List<Object> retList = new ArrayList<Object>();
        if (list != null && list.size() > 0) {  
                // 循环获取对象   
                for (int i = 0; i < list.size(); i++) {
                	Object obj = list.get(i);
                	retList.add(saveOrUpdate(obj)); // 保存药品对象   
                    // 批插入的对象立即写入数据库并释放内存   
                    if (i % 10 == 0) {
                    	this.getHibernateSession().close();
                        this.getHibernateTemplate().flush();  
                        this.getHibernateTemplate().clear();  
                    }  
                }  
        }
        return retList;
	}

	//----------------------------------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 * @return 
	 */
	public void update(Object object) {
		this.getHibernateTemplate().update(object);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void update(String entityName, String id, List filedList) {
		if (null == filedList || filedList.size() <= 0) {
			return;
		}
		StringBuffer hql = new StringBuffer();
		hql.append(" update " + entityName + " set ");
		for (int i = 0; i < filedList.size(); i++) {
			String[] tmp = (String[]) filedList.get(i);
			if (i == filedList.size() - 1) {
				hql.append(tmp[0]).append(" = '").append(tmp[1]).append("' ");
			} else {
				hql.append(tmp[0]).append(" = '").append(tmp[1]).append("', ");
			}
		}
		hql.append(" where id = '" + id + "'");
		Query q = this.getSession().createQuery(hql.toString());
		q.executeUpdate();

	}

	/**
	 * {@inheritDoc}
	 */
	public void delete(Object object) {
		this.getHibernateTemplate().delete(object);
	}

	/**
	 * 通过SQL执行删除
	 */
	public void deleteBySql(String sql){
        this.getJdbcTemplate().execute(sql);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void deleteAll(String entityName) {
		Query query = this.getSession()
				.createQuery("delete from " + entityName);
		query.executeUpdate();
	}

	/**
	 * {@inheritDoc}
	 */
	public void deleteByIds(String ids, String entityName) throws Exception {
		if (null != ids && !"".equals(ids.trim())) {
			String[] id = ids.split(",");
			for (int i = 0; i < id.length; i++) {
				if (null != id[i] && !"".equals(id[i].trim())) {
					Object object = this.queryById(id[i].trim(), entityName);
					delete(object);
				}
			}
		}
	}

	public void deleteByList(List list) {
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (null != list.get(i) && !"".equals(list.get(i))) {
					Object obj = list.get(i);
					delete(obj);
				}
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Object get(Class entityClass, Serializable id) {
		return this.getHibernateTemplate().get(entityClass, id);
	}
	/**
	 * {@inheritDoc}
	 */
	public Object queryById(String id, String entityName) {
		Object object = null;
		try {
			object = this.getHibernateTemplate().get(Class.forName(entityName),
					new Long(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Object queryById(Long id,String entityName) throws Throwable{
		Object object = null;
		try {
			object = this.getHibernateTemplate().get(Class.forName(entityName),
					id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	

	/**
	 * {@inheritDoc}
	 */
	public List querySearchHeader(String entityName, String[] paramNames,
			Object[] values, String[] likeParamNames, Object[] likeValues) {
		StringBuffer sb = new StringBuffer();
		sb.append("from TsStsx where  ssstm = '" + entityName + "' ");
		if (null != paramNames && null != values
				&& paramNames.length == values.length && paramNames.length >= 1) {
			for (int i = 0; i < paramNames.length; i++) {
				sb.append(" and " + paramNames[i] + " = '" + values[i] + "' ");
			}
		}
		if (null != likeParamNames && null != likeValues
				&& likeParamNames.length == likeValues.length
				&& likeParamNames.length >= 1) {
			for (int i = 0; i < paramNames.length; i++) {
				sb.append(" and " + paramNames[i] + " like '%" + values[i]
						+ "%' ");
			}
		}
		sb.append(" order by pxxh");
		return getSession().createQuery(sb.toString()).list();
	}
	
	/**
	 * 查询表头数据，如果传入有其它条件，将条件增加到Sql后面
	 */
	public List querySearchHeader(String entityName,Map map){
		StringBuffer sb = new StringBuffer();
		sb.append("from TsStsx where  ssstm = '" + entityName + "' ");
		List list = (List) map.get("list");//获取条件
		String params = "";
		if(list !=null){//如果不为空，将条件转化为SQL串
			for(int i = 0;i<list.size();i++){
				params = params.concat(list.get(i).toString());
				if((i+1)!=list.size()){
					params = params.concat(",");
				}
			}
		}
		sb.append(params);
		sb.append("order by pxxh");//加上安排序号排序
		return getSession().createQuery(sb.toString()).list();
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List queryTableContent(String entityName, String[] paramNames,
			Object[] values, String[] likeParamNames, Object[] likeValues,
			String[] oerderBy, String[] orderByValue, PageParam pageParam) {
		StringBuffer sb = new StringBuffer();
		sb.append("from " + entityName + " ");
		boolean flag = false;
		if (null != paramNames && null != values
				&& paramNames.length == values.length && paramNames.length >= 1) {
			flag = true;
			sb.append(" where 1=1 ");
			Map start = new HashMap();
			Map end = new HashMap();
			for (int i = 0; i < paramNames.length; i++) {
				if (paramNames[i].endsWith("#date1")) {
					String tmp = paramNames[i].substring(0,
							paramNames[i].length() - 6);
					start.put(tmp, values[i]);
				} else if (paramNames[i].endsWith("#date2")) {
					String tmp = paramNames[i].substring(0,
							paramNames[i].length() - 6);
					end.put(tmp, values[i]);
				}
			}
			if (start.size() > 0 && end.size() > 0 & start.size() == end.size()) {
				Object[] tmp = start.keySet().toArray();
				for (int i = 0; i < tmp.length; i++) {
					String name = (String) tmp[i];

					String startValue = (String) start.get(name);
					String endValue = (String) end.get(name);
					sb.append(" and " + name + " between '" + startValue
							+ "'  and  '" + endValue + "' ");
				}
			}

			for (int i = 0; i < paramNames.length; i++) {
				if (!paramNames[i].endsWith("#date1")
						&& !paramNames[i].endsWith("#date2")) {
					if (null != values[i]
							&& !"".equals(values[i].toString().trim())) {
						sb.append(" and " + paramNames[i] + " = '"
								+ values[i].toString().trim() + "' ");
					}
				} else if (paramNames[i].equals("")) {

				}
			}
		}
		if (null != likeParamNames && null != likeValues
				&& likeParamNames.length == likeValues.length
				&& likeParamNames.length >= 1) {
			if (flag == false) {
				sb.append(" where 1=1 ");
			}
			for (int i = 0; i < likeParamNames.length; i++) {
				if (null != likeValues[i]
						&& !"".equals(likeValues[i].toString().trim())) {
					sb.append(" and " + likeParamNames[i] + " like '%"
							+ likeValues[i].toString().trim() + "%' ");
				}
			}
		}
		if (null != oerderBy && null != orderByValue
				&& oerderBy.length == orderByValue.length
				&& oerderBy.length >= 1) {
			sb.append(" order by ");
			for (int i = 0; i < oerderBy.length; i++) {
				if (null != orderByValue[i]
						&& !"".equals(orderByValue[i].toString().trim())) {
					if (i < oerderBy.length - 1) {
						sb.append(" " + oerderBy[i] + " "
								+ orderByValue[i].toString().trim() + ",");
					} else {
						sb.append(" " + oerderBy[i] + " "
								+ orderByValue[i].toString().trim() + " ");
					}
				}
			}
		}
		int recordCount = getResultCount(sb.toString());
		pageParam.setRecordCount(recordCount);
		return getSession().createQuery(sb.toString())
				.setFirstResult(pageParam.getStart())
				.setMaxResults(pageParam.getLimit()).list();
	}

	/**
	 * {@inheritDoc}
	 */
	public List queryTableContent(String entityName, String[] paramNames,
			Object[] values, String[] likeParamNames, Object[] likeValues) {
		StringBuffer sb = new StringBuffer();
		sb.append("from " + entityName + " where 1=1 ");
		if (null != paramNames && null != values
				&& paramNames.length == values.length && paramNames.length >= 1) {
			for (int i = 0; i < paramNames.length; i++) {
				if(!values[i].equals("")){//增加值 为空的判断,且不加入查询条件中
					sb.append(" and " + paramNames[i] + " = '" + values[i] + "' ");
				}
			}
		}
		if (null != likeParamNames && null != likeValues
				&& likeParamNames.length == likeValues.length
				&& likeParamNames.length >= 1) {
			for (int i = 0; i < likeParamNames.length; i++) {
				sb.append(" and " + likeParamNames[i] + " like '%"
						+ likeValues[i] + "%' ");
			}
		}
		/*if(whereCloud.length()>0){
			sb.append(whereCloud);
		}*/
		return getSession().createQuery(sb.toString()).list();
	}
	
	@Override
	public List queryTableContentForDm(String entityName,String permissIds) {
		StringBuffer sb = new StringBuffer();
		//根据实体名获取实体对象列表；
		List<TsStsx> list =querySearchHeader(entityName, null, null, null,
				null);
		sb.append("from " + entityName + " where 1=1 "); //and sfky = 1
		//遍历实体对象是否存在sfky字段；
		for(TsStsx tsStsx :list){
			if(tsStsx.getSx().equals("sfky")){
				sb.append(" and sfky = 1");
			}else{
				continue;
			}
		}
		//判断权限
		if(permissIds != null && permissIds.trim().length()>0){
			sb.append(" and id in("+permissIds+")");
		}
		return getSession().createQuery(sb.toString()).list();
	}
	/**
	 * {@inheritDoc}
	 */
	public List queryTableContent(String entityName, String[] paramNames,
			Object[] values, String[] likeParamNames, Object[] likeValues, String[] oerderBy,
			String[] orderByValue) {
		StringBuffer sb = new StringBuffer();
		sb.append("from " + entityName + " ");
		boolean flag = false;
		if (null != paramNames && null != values
				&& paramNames.length == values.length && paramNames.length >= 1) {
			flag = true;
			sb.append(" where 1=1 ");
			for (int i = 0; i < paramNames.length; i++) {
				if(!values[i].equals("")){//增加值 为空的判断,且不加入查询条件中
					sb.append(" and " + paramNames[i] + " = '" + values[i] + "' ");
				}
			}
		}
		if (null != likeParamNames && null != likeValues
				&& likeParamNames.length == likeValues.length
				&& likeParamNames.length >= 1) {
			if (flag == false) {
				sb.append(" where 1=1 ");
			}
			for (int i = 0; i < likeParamNames.length; i++) {
				sb.append(" and " + likeParamNames[i] + " like '%"
						+ likeValues[i] + "%' ");
			}
		}
		if (null != oerderBy && null != orderByValue
				&& oerderBy.length == orderByValue.length
				&& oerderBy.length >= 1) {
			sb.append(" order by ");
			for (int i = 0; i < oerderBy.length; i++) {
				if (null != orderByValue[i]
						&& !"".equals(orderByValue[i].toString().trim())) {
					if (i < oerderBy.length - 1) {
						sb.append(" " + oerderBy[i] + " "
								+ orderByValue[i].toString().trim() + ",");
					} else {
						sb.append(" " + oerderBy[i] + " "
								+ orderByValue[i].toString().trim() + " ");
					}
				}
			}
		}
		return getSession().createQuery(sb.toString()).list();
	}
	

	/**
	 * {@inheritDoc}
	 */
	public List queryTree(String entityName, String bzdm) {
		StringBuffer sb = new StringBuffer();
		sb.append("select new com.jhkj.mosdc.framework.util.Node(id,fjdId,mc,\"\",sfyzjd,cc) from "
				+ entityName + " where bzdm='" + bzdm + "'" + " order by id");
		return getSession().createQuery(sb.toString()).list();
	}
	
	@Override
	public List getTreeJson(String entityName,String fjdId) {
		StringBuffer sb = new StringBuffer();
		sb.append(" from "+entityName + " where fjdId = "+fjdId+" order by id");
		return getSession().createQuery(sb.toString()).list();
	}

	/**
	 * {@inheritDoc}
	 */
	public List queryTree(String entityName) {
		StringBuffer sb = new StringBuffer();
		sb.append("select new com.jhkj.mosdc.framework.util.Node(id,fjdId,mc,'',sfyzjd,cc,'',dm) from "
				+ entityName + " order by id");
		return getSession().createQuery(sb.toString()).list();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List queryTreeCombobox(String entityName) {
		StringBuffer sb = new StringBuffer();
		sb.append("select new com.jhkj.mosdc.permission.po.TreeNode(id,fjdId,mc,'',sfyzjd,cc,'',dm) from "
				+ entityName + " order by id");
		return getSession().createQuery(sb.toString()).list();
	}
	/**
	 * {@inheritDoc}
	 */
	public List queryMenuTree() {
		StringBuffer sb = new StringBuffer();
		sb.append("select new com.jhkj.mosdc.framework.util.Node( id,  fjdId,  mc,url,sfyzjd ) from TsCds order by id ");
		return getSession().createQuery(sb.toString()).list();
	}

	/**
	 * {@inheritDoc}
	 */
	public List queryTableContentForMap(String entityName) {
		Session session = this.getSession();
		Query query = session.createQuery(" from " + entityName + " ")
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}

	/**
	 * {@inheritDoc}
	 */
	public List queryTableHeader(String entityName, Long sflbzd) {
		StringBuffer sb = new StringBuffer();
		sb.append("select new com.jhkj.mosdc.framework.dto.Stsx("
				+ " id,  ssstm||'.'||sx,  sxzwm,  sxxslx, sxzjlx, sflbxszd,sflbzd, sfxz,  bmbstm, bmbbzdm,lbxssx,ssstm) "
				+ "from TsStsx where  ssstm = '" + entityName
				+ "' and sflbzd=" + sflbzd + "  order by  pxxh asc");
		return getSession().createQuery(sb.toString()).list();
	}
	@Override
	public List queryBM(String entityName, String bzdm,String permissIds) {
		StringBuffer sb = new StringBuffer();
		Session session = this.getSession();
		sb.append(" from " + entityName + " where bzdm='" + bzdm);
		if(entityName.equals("TbXzzzjg") && permissIds != null && permissIds.trim().length()>0){
			sb.append(" and id in ("+permissIds+")");
		}
		sb.append("' and (sfky = '1' ) order by dm");
		List list = session.createQuery(sb.toString()).list();
		return list;
	}
	
	@Override
	public String getBzdmMcById(String bzdmId,String entityName){
		Long id=Long.parseLong(bzdmId);
		//String sql = "select t.MC from "+entityName+" t where t.id = "+bzdmId;
		TcXxbzdmjg bzdm=(TcXxbzdmjg) this.getHibernateTemplate().get(entityName, id);
		String bzdmMc=bzdm.getBzdmmc();
		return bzdmMc;
	}
	@Override
	public String getMcById(String bzdmId,String entityName){
		Long id=Long.parseLong(bzdmId);
		//String sql = "select t.MC from "+entityName+" t where t.id = "+bzdmId;
		TcXxbzdmjg bzdm=(TcXxbzdmjg) this.getHibernateTemplate().get(entityName, id);
		String mc=bzdm.getMc();
		return mc;
	}
	
	@Override
	public List queryBM(String entityName, String bzdm) {
		StringBuffer sb = new StringBuffer();
		Session session = this.getSession();
		sb.append(" from " + entityName + " where bzdm='" + bzdm);
		sb.append("' and (sfky = '1' ) order by id");
		List list = session.createQuery(sb.toString()).list();
		return list;
	}
	
	@Override
	public List<Map> queryBMForIdMc(String entityName, String bzdm,String lx) {
		StringBuffer sb = new StringBuffer();
		Session session = this.getSession();
		sb.append("select id,mc from " + entityName + " where 1 = 1"); //拼接实体名
		if(bzdm != null){
			if(lx.equals("tree")){//判断 是否为tree
				sb.append(" and cclx='" + bzdm+"' and (sfky = '1' ) ");
			}else{
				sb.append(" and bzdm='" + bzdm+"' and (sfky = '1' ) ");
			}
		}
		sb.append(" order by id");
		List list = session.createQuery(sb.toString()).list();
		return list;
	}
	
	public Session getHibernateSession(){
		return this.getSessionFactory().openSession();
	}
	
	@Override
	public List<Long> getMutiId(Integer idNum) throws Exception{
		List<Long> idList = new ArrayList();
		Session session = this.getSessionFactory().openSession();
		Transaction tx = session.getTransaction();
		try {
			tx.begin();
			Query query = session.createQuery("  from TsXxdmJs js ");
			query.setLockMode("js", LockMode.UPGRADE);
			List list = query.list();
			if (null != list && list.size() > 0) {
				TsXxdmJs tsXxdmJs = (TsXxdmJs) list.get(0);
				Long xxdm = tsXxdmJs.getXxdm();
				Long js = tsXxdmJs.getJs();
				if (null == js) {
					js = new Long(1000);
					tsXxdmJs.setJs(js);
					session.update(tsXxdmJs);
				} else {
					tsXxdmJs.setJs(new Long(js + idNum));
					session.update(tsXxdmJs);
				}
				for(int i=0;i<idNum;i++){
					idList.add(xxdm * new Long("1000000000000") + new Long(js +i+ 1));
				}
				return idList;
			} else {
				throw new Exception("TS_XXDM_JS表记录不能为空，且只能有一条记录！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			tx.commit();
			session.close();
		}
	
	}
	
	/**
	 * 一次获取1000个id
	 * @return
	 * @throws Exception
	 */
	public List<Long> getOneThousandId() throws Exception{
		List<Long> idList = new ArrayList();
		Session session = this.getSessionFactory().openSession();
		Transaction tx = session.getTransaction();
		try {
			tx.begin();
			Query query = session.createQuery("  from TsXxdmJs js ");
			query.setLockMode("js", LockMode.UPGRADE);
			List list = query.list();
			if (null != list && list.size() > 0) {
				TsXxdmJs tsXxdmJs = (TsXxdmJs) list.get(0);
				Long xxdm = tsXxdmJs.getXxdm();
				Long js = tsXxdmJs.getJs();
				if (null == js) {
					js = new Long(1000);
					tsXxdmJs.setJs(js);
					session.update(tsXxdmJs);
				} else {
					tsXxdmJs.setJs(new Long(js + 1000));
					session.update(tsXxdmJs);
				}
				for(int i=0;i<1000;i++){
					idList.add(xxdm * new Long("1000000000000") + new Long(js +i+ 1));
				}
				return idList;
			} else {
				throw new Exception("TS_XXDM_JS表记录不能为空，且只能有一条记录！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			tx.commit();
			session.close();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Long getId() throws Exception {
		Session session = this.getSessionFactory().openSession();
		Transaction tx = session.getTransaction();
		try {
			tx.begin();
			Query query = session.createQuery("  from TsXxdmJs js ");
			query.setLockMode("js", LockMode.UPGRADE);
			List list = query.list();
			if (null != list && list.size() > 0) {
				TsXxdmJs tsXxdmJs = (TsXxdmJs) list.get(0);
				Long xxdm = tsXxdmJs.getXxdm();
				Long js = tsXxdmJs.getJs();
				if (null == js) {
					js = new Long(1);
					tsXxdmJs.setJs(js);
					session.update(tsXxdmJs);
				} else {
					tsXxdmJs.setJs(new Long(js + 1));
					session.update(tsXxdmJs);
				}
				return xxdm * new Long("1000000000000") + new Long(js + 1);
			} else {
				throw new Exception("TS_XXDM_JS表记录不能为空，且只能有一条记录！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			tx.commit();
			session.close();
		}
	}
	


	/**
	 * {@inheritDoc}
	 */
	public int getResultCount(String queryString) {
		String hqlstr = removeOrders(queryString);
		if (hasDistinctOrGroupBy(hqlstr)) {
			List lt = this.getHibernateTemplate().find(hqlstr);
			return lt != null ? lt.size() : 0;
		}
		String countQueryString = " select count (*) "
				+ removeSelect(removeOrders(queryString));
		List countlist = this.getHibernateTemplate().find(countQueryString);
		int count = 0;
		if (countlist.size() == 1)
			count = (new Long(countlist.get(0).toString())).intValue();
		else
			count = countlist.size();
		return count;
	}

	/**
	 * 去除group
	 * 
	 * @param str
	 * @return
	 */
	private boolean hasDistinctOrGroupBy(String str) {
		Pattern p = Pattern.compile("group\\s*by[\\w|\\W|\\s|\\S]*", 2);
		Matcher m = p.matcher(str);
		if (m.find()) {
			return true;
		} else {
			p = Pattern.compile("distinct ", 2);
			m = p.matcher(str);
			return m.find();
		}
	}

	/**
	 * 去除select
	 * 
	 * @param hql
	 * @return
	 */
	private String removeSelect(String hql) {
		int beginPos = hql.toLowerCase().indexOf("from");
		Assert.isTrue(beginPos != -1, " hql : " + hql
				+ " must has a keyword 'from'");
		return hql.substring(beginPos);
	}

	/**
	 * 去除order
	 * 
	 * @param hql
	 * @return
	 */
	private static String removeOrders(String hql) {
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", 2);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		for (; m.find(); m.appendReplacement(sb, ""))
			;
		m.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 查询导出列头
	 * 
	 * @param entityName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TsStsx> queryExportTableHeaders(String entityName,String params) throws Exception {
		List<TsStsx> result = null;
		String hql = " from TsStsx t where t.ssstm='"
				+ entityName + "' and t.sflbzd = 1 and t.sx <> 'id'";//是否列表字段为1说明创建此字段，只是显示不显示到列表中
		if(params != null){
			hql = hql.concat(" and t.sx in ("+params+") "); //根据用户选择的列导出
		}
		hql = hql.concat(" order by t.pxxh ");
		result = this.getSession().createQuery(hql).list();
		/*Query query = getSession().createQuery(hql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		result = query.list();*/
		return result;
	}

	/**
	 * 查询导出内容
	 * 
	 * @param tableName
	 * @param headers
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> queryExportTableContent(String tableName,
			String headers, String params) {
		StringBuffer sql = new StringBuffer("select ");
		sql.append(headers);
		/*for (int i = 0; i < headers.length; i++) {
			sql.append("t."+headers[i][0]);
			if (headers.length - 1 != i)
				sql.append(", ");

		}*/
		sql.append(" from " + tableName + " t where 1=1 ");
		if (null != params) {
			sql.append(" and ");
			sql.append(params);
		}
		List<String> result = null;
		try {
			Query query = getSession().createSQLQuery(sql.toString());
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			result = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (DataAccessResourceFailureException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} finally {
		}

		return result;
	}

	/**
	 * 根据sql查询分页数据和总记录数
	 * 
	 * @param sqlString
	 * @param params
	 * @return
	 */
	@Override
	public Map queryTableContentBySQL(String sqlString,
			Map params) {
		Map<String, Object> map = new HashMap<String, Object>();
		int start = new Integer(params.get("start").toString()).intValue();
		int limit = new Integer(params.get("limit").toString()).intValue();
		int count = 0;
		List list = new ArrayList();
		try {
			// 根据不同数据库加入不同分页参数。
			String[] sqlList = JdbcSqlPage.pageSql(sqlString, start, limit);

			// 使用SQL构造查询对象.
			Query query = this.getSession().createSQLQuery(sqlList[1]);
			// 将查询结果转换成list。
			// 采用如下方式，可以不再写Bean.同时也可以避免 query会把数据库中number类型的字段转成BigDecimal的情况发生。
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.list();
			// 根据countSql查询分页的记录总数
			List listCount = this.getSession().createSQLQuery(sqlList[0])
					.list();
			String result = listCount.get(0).toString();
			// 获取记录总数
			count = Integer.parseInt(result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		map.put("queryList", list);
		map.put("count", count);
		return map;
	}
	/**
	 * 根据SQL查询数据并返回List<Map>
	 * @param sqlString 查询SQL
	 * @return list<Map>
	 */
	@Override
	public List<Map> queryListMapBySQL(String sqlString) {
		List<Map> list = new ArrayList<Map>();
		Session session = this.getSession();
		try {
			// 使用SQL构造查询对象.
			Query query = session.createSQLQuery(sqlString);
			// 将查询结果转换成list。
			// 采用如下方式，可以不再写Bean.同时也可以避免 query会把数据库中number类型的字段转成BigDecimal的情况发生。
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		return list;
	}
	/**
	 * 根据SQL查询数据并返回List<Map>,其中Map的key值为小写
	 * @param sqlString
	 * return List<Map>
	 */
	@SuppressWarnings({ "unused", "rawtypes", "deprecation", "unchecked" })
	public List<Map> queryListMapInLowerKeyBySql(String sql){
		return this.getJdbcTemplate().query(sql, new RowMapper() {
			public ResultSetMetaData rsm = null;
			public int count = 0;

			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				if (rsm == null) {
					rsm = rs.getMetaData();
					count = rsm.getColumnCount();
				}
				HashMap map = new HashMap();
				for (int i = 0; i < count; i++) {
					String columnName = rsm.getColumnName((i + 1))
							.toLowerCase();
					int sqlType = rsm.getColumnType(i + 1);
					Object sqlView = rs.getObject(columnName);
					switch (sqlType) {
					case Types.CHAR:
						map.put(columnName, sqlView.toString().trim());
						break;
					case Types.NUMERIC:
						if (sqlView == null)
							sqlView = "";
						map.put(columnName, sqlView.toString());
						break;
					default:
						map.put(columnName,
								sqlView != null ? sqlView.toString() : "");
						break;
					}
				}
				return map;
			}
	});
}
	@Override
	public Map queryTableContentBySql(String sql, JSONObject obj, String sqlEnd, Boolean isAlias) throws Exception {
		Map paramList = SqlParamsChange.getSQLParams(obj, isAlias);
		String startStr = "";
		String limitStr = "";
		List<String> retList = null;
		if (paramList.size() >= 0 && paramList.get(0) == null) {
			startStr = (String) paramList.get("start");
			limitStr = (String) paramList.get("limit");
			retList = (List<String>) paramList.get("list");
		}
		int limit = null == limitStr ? 0 : new Integer(limitStr).intValue();
		int start = null == startStr ? 0 : new Integer(startStr).intValue();
		if (start == 0 && limit == 0) {
			limit = 25;
		}
		// 高级查询条件
		String seniorStr = (String) paramList.get("senior");
		StringBuffer sb = new StringBuffer();
		sb.append(sql);
		if (null != retList && retList.size() != 0
				&& retList.get(0).length() >= 1) {
			for (int i = 0; i < retList.size(); i++) {
				sb.append(" and " + retList.get(i));
			}
		}
		// 判断高级查询是否存在
		if (seniorStr != null && !seniorStr.equals("")) {
			sb.append(" and " + seniorStr);
		}
		if (null != sqlEnd && !"".equals(sqlEnd.trim())) {
			sb.append(" " + sqlEnd);
		}
		Map pageMap = new HashMap();
		pageMap.put("start", start);
		pageMap.put("limit", limit);
		// 根据条件获取数据
		Map map = queryTableContentBySQL(sb.toString(), pageMap);
		return map;
	}
	/**
	 * 获取根据前台传入的单字段查询或高级查询、表头查询数据转化为SQL条件,并返回Map(List,count);
	 * @param sql 组装的基本Sql
	 * @param map 各种条件(查询条件，分页参数)
	 * @return
	 */
	@Override
	public Map queryTableContentBySql(String sql,Map map){
//		Map<String, Object> retMap = new HashMap<String, Object>();
		int start = map.containsKey("start")?new Integer(map.get("start").toString()).intValue():0;
		int limit = map.containsKey("limit")?new Integer(map.get("limit").toString()).intValue():999999;
		String sqlParam = mapToSqlParams(map);
		int count = 0;
		List list = new ArrayList();
		try {
			// 根据不同数据库加入不同分页参数。
			String[] sqlList = JdbcSqlPage.pageSql(sql.concat(sqlParam), start, limit);

			// 使用SQL构造查询对象.
			Query query = this.getSession().createSQLQuery(sqlList[1]);
			// 将查询结果转换成list。
			// 采用如下方式，可以不再写Bean.同时也可以避免 query会把数据库中number类型的字段转成BigDecimal的情况发生。
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.list();
			// 根据countSql查询分页的记录总数
			List listCount = this.getSession().createSQLQuery(sqlList[0])
					.list();
			String result = listCount.get(0).toString();
			// 获取记录总数
			count = Integer.parseInt(result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		map.put("queryList", list);
		map.put("count", count);
		return map;
	}
	/**
	 * 把map转化为Sql的条件
	 * @param map
	 * @return
	 */
	@Override
	public String mapToSqlParams(Map map){
		List<String> retList = null;
		if (!map.isEmpty()) {
			retList = (List<String>) map.get("list");
		}
		// 高级查询条件
		String seniorStr = (String) map.get("senior");
		StringBuffer sb = new StringBuffer();
		if (null != retList && retList.size() != 0
				&& retList.get(0).length() >= 1) {
			for (int i = 0; i < retList.size(); i++) {
				sb.append(" and " + retList.get(i));
			}
		}
		// 判断高级查询是否存在
		if (seniorStr != null && !seniorStr.equals("")) {
			sb.append(" and " + seniorStr);
		}
		if(map.containsKey("group")){
			sb.append(map.get("group"));
		}
		if(map.containsKey("order")){
			sb.append(map.get("order"));
		}
		return sb.toString();
	}
	/**
	 * 根据对象的id，查询对象
	 * 
	 * @param obj
	 * @return
	 */
	public void load(Object obj) {
		Object id = null;
		try {
			Method getMethod = obj.getClass().getMethod("getId", null); // 获取getId方法
			id = getMethod.invoke(obj, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (id == null) {
			throw new RuntimeException("id 不能为空");
		}
		this.getHibernateTemplate().load(obj, Long.valueOf(String.valueOf(id)));
		
	}
	
	/**
	 * 根据对象的属性，查询对象的列表
	 * 
	 * @param obj
	 *            po对象
	 * @return
	 */
	public List loadEqual(Object obj) {
		String entityName = obj.getClass().getSimpleName(); // 获取对象的简单类名
		String packageName = obj.getClass().getPackage().getName(); // 获取包名
		StringBuffer sbf= new StringBuffer();
		sbf.append(" from ").append(packageName).append(".").append(entityName).append(" where 1=1 ");
		Field[] fields = obj.getClass().getDeclaredFields(); // 获取对象对应的类中设置的所有属性字段
		for (int i = 0; i < fields.length; i++) {
			Object fieldValue = null;
			Field field = fields[i];
			String fieldName = field.getName(); // 获取属性名
			if(fieldName.equals("serialVersionUID")){
				continue;
			}
			String letterName = fieldName.substring(0, 1).toUpperCase()
					+ fieldName.substring(1);
			String getName = "get" + letterName; // 获取属性名对应的get方法的名字
			try {
				Method getMethod = obj.getClass().getMethod(getName, null); // 根据方法名，获取方法
				fieldValue = getMethod.invoke(obj, null); // 获取属性值
				/*
				 * 如果属性值不为空，则加入查询条件
				 */
				if (fieldValue != null && !"".equals(fieldValue)) {
					if("String".equals(fieldValue.getClass().getSimpleName())){
						sbf.append(" and ").append(fieldName).append("='").append(fieldValue).append("'");
					}else{
						sbf.append(" and ").append(fieldName).append("=").append(fieldValue);
					}
					
				}
			} catch (Exception e) { //
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return this.getSession().createQuery(sbf.toString()).list(); // 执行sql语句
	}
	
	public List loadEqualByOrder(Object obj,String fieldFlag,String orderFlag){
		StringBuffer sbf= new StringBuffer();
		String entityName = obj.getClass().getSimpleName(); // 获取对象的简单类名
		String packageName = obj.getClass().getPackage().getName(); // 获取包名
		sbf.append(" from ").append(packageName).append(".").append(entityName).append(" where 1=1 ");
		Field[] fields = obj.getClass().getDeclaredFields(); // 获取对象对应的类中设置的所有属性字段
		for (int i = 0; i < fields.length; i++) {
			Object fieldValue = null;
			Field field = fields[i];
			String fieldName = field.getName(); // 获取属性名
			String letterName = fieldName.substring(0, 1).toUpperCase()
					+ fieldName.substring(1);
			String getName = "get" + letterName; // 获取属性名对应的get方法的名字
			try {
				Method getMethod = obj.getClass().getMethod(getName, null); // 根据方法名，获取方法
				fieldValue = getMethod.invoke(obj, null); // 获取属性值
				/*
				 * 如果属性值不为空，则加入查询条件
				 */
				if (fieldValue != null && !"".equals(fieldValue)) {
					if("String".equals(fieldValue.getClass().getSimpleName())){
						sbf.append(" and ").append(fieldName).append("='").append(fieldValue).append("'");
					}else{
						sbf.append(" and ").append(fieldName).append("=").append(fieldValue);
					}
					
				}
				
			} catch (Exception e) { //
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		
		if(fieldFlag!=null){
			sbf.append(" order by ").append(fieldFlag);
			if(orderFlag!=null){
				sbf.append(" ").append(orderFlag);
			}
		}		
		return this.getSession().createQuery(sbf.toString()).list(); // 执行sql语句
	}

	/**
	 * 查询po对应的表的所有记录
	 * 
	 * @param obj
	 * @return
	 */
	public List loadAll(Object obj) {
		return this.getHibernateTemplate().loadAll(obj.getClass());
	}
	/**
     * 获取第一个实体
     * @param obj
     * @return
     */
	public Object loadFirstEqual(Object obj){
		StringBuffer sbf= new StringBuffer();
		String entityName = obj.getClass().getSimpleName(); // 获取对象的简单类名
		String packageName = obj.getClass().getPackage().getName(); // 获取包名
		sbf.append(" from ").append(packageName).append(".").append(entityName).append(" where id=");
		sbf.append("(select min(id) from ").append(packageName).append(".").append(entityName).append(" where 1=1 ");
		Field[] fields = obj.getClass().getDeclaredFields(); // 获取对象对应的类中设置的所有属性字段
		for (int i = 0; i < fields.length; i++) {
			Object fieldValue = null;
			Field field = fields[i];
			String fieldName = field.getName(); // 获取属性名
			String letterName = fieldName.substring(0, 1).toUpperCase()
					+ fieldName.substring(1);
			String getName = "get" + letterName; // 获取属性名对应的get方法的名字
			try {
				Method getMethod = obj.getClass().getMethod(getName, null); // 根据方法名，获取方法
				fieldValue = getMethod.invoke(obj, null); // 获取属性值
				/*
				 * 如果属性值不为空，则加入查询条件
				 */
				if (fieldValue != null && !"".equals(fieldValue)) {
					if("String".equals(fieldValue.getClass().getSimpleName())){
						sbf.append(" and ").append(fieldName).append("='").append(fieldValue).append("'");
					}else{
						sbf.append(" and ").append(fieldName).append("=").append(fieldValue);
					}
					
				}
			} catch (Exception e) { //
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		sbf.append(")");
		List l=this.getSession().createQuery(sbf.toString()).list();
		if(l.size()>0){
			return l.get(0);
		}
		return null; // 执行sql语句
	}
	
	/**
     * 获取最后一个实体
     * @param obj
     * @return
     */
	public Object loadLastEqual(Object obj){
		StringBuffer sbf= new StringBuffer();
		String entityName = obj.getClass().getSimpleName(); // 获取对象的简单类名
		String packageName = obj.getClass().getPackage().getName(); // 获取包名
		sbf.append(" from ").append(packageName).append(".").append(entityName).append(" where id=");
		sbf.append("(select max(id) from ").append(packageName).append(".").append(entityName).append(" where 1=1 ");
		Field[] fields = obj.getClass().getDeclaredFields(); // 获取对象对应的类中设置的所有属性字段
		for (int i = 0; i < fields.length; i++) {
			Object fieldValue = null;
			Field field = fields[i];
			String fieldName = field.getName(); // 获取属性名
			String letterName = fieldName.substring(0, 1).toUpperCase()
					+ fieldName.substring(1);
			String getName = "get" + letterName; // 获取属性名对应的get方法的名字
			try {
				Method getMethod = obj.getClass().getMethod(getName, null); // 根据方法名，获取方法
				fieldValue = getMethod.invoke(obj, null); // 获取属性值
				/*
				 * 如果属性值不为空，则加入查询条件
				 */
				if (fieldValue != null && !"".equals(fieldValue)) {
					if("String".equals(fieldValue.getClass().getSimpleName())){
						sbf.append(" and ").append(fieldName).append("='").append(fieldValue).append("'");
					}else{
						sbf.append(" and ").append(fieldName).append("=").append(fieldValue);
					}
					
				}
			} catch (Exception e) { //
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		sbf.append(")");
		List l=this.getSession().createQuery(sbf.toString()).list();
		if(l.size()>0){
			return l.get(0);
		}
		return null; // 执行sql语句
	}
	
	@Override
	public List loadPageEqual(Object obj, String orderField, String orderFlag,
			int start, int limit) {
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Criteria criteria = session.createCriteria(obj.getClass()).add(Example.create(obj));
		if(null != orderField){
			if("DESC".equals(orderFlag.toUpperCase())){
				criteria.addOrder(Order.desc(orderField));
			}else{
				criteria.addOrder(Order.asc(orderField));
			}
		}
		criteria.setFirstResult(start);
		criteria.setMaxResults(start+limit);
		return criteria.list();
	}


	public void deleteEqual(Object obj) {
		String entityName = obj.getClass().getSimpleName(); // 获取对象的简单类名
		String packageName = obj.getClass().getPackage().getName(); // 获取包名
		StringBuffer sbf= new StringBuffer();
		sbf.append("delete from ").append(packageName).append(".").append(entityName).append(" where 1=1 ");
		Field[] fields = obj.getClass().getDeclaredFields(); // 获取对象对应的类中设置的所有属性字段
		for (int i = 0; i < fields.length; i++) {
			Object fieldValue = null;
			Field field = fields[i];
			String fieldName = field.getName(); // 获取属性名
			String letterName = fieldName.substring(0, 1).toUpperCase()
					+ fieldName.substring(1);
			String getName = "get" + letterName; // 获取属性名对应的get方法的名字
			try {
				Method getMethod = obj.getClass().getMethod(getName, null); // 根据方法名，获取方法
				fieldValue = getMethod.invoke(obj, null); // 获取属性值
				/*
				 * 如果属性值不为空，则加入查询条件
				 */
				if (fieldValue != null && !"".equals(fieldValue)) {
					if("String".equals(fieldValue.getClass().getSimpleName())){
						sbf.append(" and ").append(fieldName).append("='").append(fieldValue).append("'");
					}else{
						sbf.append(" and ").append(fieldName).append("=").append(fieldValue);
					}
					
				}
			} catch (Throwable e) { //
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		this.getSession().createQuery(sbf.toString()).executeUpdate();
	}
	
	
	
	@Override
	public Object oracleBatchInsert(List objList) {		
		Object o=objList.get(0);
		String tableName=o.getClass().getAnnotation(Table.class).name();
		Field[] fields = o.getClass().getDeclaredFields();
		String sql="";
		try{
			sql="insert into "+tableName+"(";
			for(int i=0;i<fields.length;i++){
				Field field=fields[i];
				String fieldName = field.getName(); // 获取属性名
				String letterName = fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1);
				String getName = "get" + letterName;
				Method getMethod = o.getClass().getMethod(getName, null);
				sql+=getMethod.getAnnotation(Column.class).name()+",";
			}			
			sql=sql.substring(0,sql.length()-1)+")";			
			Object obj=objList.get(0);
			Method setIdMethod = obj.getClass().getMethod("setId",
					new Class[] { Long.class });
			setIdMethod.invoke(obj, new Object[] { this.getId() });
			sql+=" "+this.getOneObjSelectSql(obj);
			for(int i=1;i<objList.size();i++){
				obj=objList.get(i);
				setIdMethod = obj.getClass().getMethod("setId",
						new Class[] { Long.class });
				setIdMethod.invoke(obj, new Object[] { this.getId() });
				sql+=" union all "+ this.getOneObjSelectSql(obj);
			}
		}catch(Throwable e){
			throw new RuntimeException("批量插入数据库错误",e);
		}
		return this.getSession().createSQLQuery(sql).executeUpdate();
		
	}
	
	private String getOneObjSelectSql(Object obj){
		Field[] fields = obj.getClass().getDeclaredFields();
		String sql="select ";
		for (int i = 0; i < fields.length; i++) {
			Object fieldValue = null;
			Field field = fields[i];
			String fieldName = field.getName(); // 获取属性名
			String letterName = fieldName.substring(0, 1).toUpperCase()
					+ fieldName.substring(1);
			String getName = "get" + letterName; // 获取属性名对应的get方法的名字
			try {
				Method getMethod = obj.getClass().getMethod(getName, null); // 根据方法名，获取方法
				fieldValue = getMethod.invoke(obj, null); // 获取属性值
				if(fieldValue!=null){
					if("Long".equalsIgnoreCase(getMethod.getReturnType().getSimpleName())){
						sql+=fieldValue+",";
					}else{						
						sql+="'"+fieldValue+"',";
					}
					
				}else {
					sql+="'',";
				}
			} catch (Throwable e) { //
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		sql=sql.substring(0,sql.length()-1);
		sql+= " from dual";
		return sql;
	}

	/**
	 * 查询满足条件的个数
	 * 
	 * @param obj
	 * @return
	 */
	public Long countEqual(Object obj) {
		String entityName = obj.getClass().getSimpleName(); // 获取对象的简单类名
		String packageName = obj.getClass().getPackage().getName(); // 获取包名
		String sql = "select count(*) from " + packageName + "." + entityName
				+ " where 1=1 ";
		Field[] fields = obj.getClass().getDeclaredFields(); // 获取对象对应的类中设置的所有属性字段
		for (int i = 0; i < fields.length; i++) {
			Object fieldValue = null;
			Field field = fields[i];
			String fieldName = field.getName(); // 获取属性名
			String letterName = fieldName.substring(0, 1).toUpperCase()
					+ fieldName.substring(1);
			String getName = "get" + letterName; // 获取属性名对应的get方法的名字
			try {
				Method getMethod = obj.getClass().getMethod(getName, null); // 根据方法名，获取方法
				fieldValue = getMethod.invoke(obj, null); // 获取属性值
				/*
				 * 如果属性值不为空，则加入查询条件
				 */
				if (fieldValue != null && !"".equals(fieldValue)) {
					if("String".equals(fieldValue.getClass().getSimpleName())){
						sql += " and " + fieldName + "='" + fieldValue + "'";
					}else{
						sql += " and " + fieldName + "=" + fieldValue ;
					}
					
				}
			} catch (Throwable e) { //
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

		return Long.valueOf(String.valueOf(this.getSession().createQuery(sql)
				.list().get(0)));
	}
    //-------------------------------------2013-05-16------------------------------------------
	/**
	 * 针对虚表查询封装 (高东杰 －－－－新增)
	 * @param sql[必传]
	 * @param map　(start当前页开始记录数，limit当前页记录数，list查询条件，senior高级查询条件)
	 * @param pageParam
	 * @return
	 */
	@Override
	public List queryTableContent(String sql,Map map,PageParam pageParam) throws Exception{
		//定义sql字符串
		StringBuffer sqlstr = new StringBuffer();
		//将原始的sql作为一个虚表,并拼装select
		sqlstr.append("select t.* from ( ");
		sqlstr.append(sql);
		sqlstr.append(") t where 1=1 ");
		sqlstr =mapToSqlConnect(sqlstr, map, pageParam,false);
		Map dataMap = getDataBySql(sqlstr.toString(),pageParam);
		//统计查询记录数
//		int recordCount = getResultCount(sqlstr.toString());
		//pageParam中设置总记录数
		pageParam.setRecordCount(Integer.valueOf(dataMap.get("count").toString()));
		//执行sql查询返回查询记录结果
		return (List) dataMap.get("queryList");
	}
	/**
	 * 针对虚表查询封装(带别名)(王永太-新增)
	 * @param sql[必传]
	 * @param map　(start当前页开始记录数，limit当前页记录数，list查询条件，senior高级查询条件)
	 * @param pageParam
	 * @return
	 * @throws Exception 
	 */
	public List queryTableContentWithAlias(String sql, Map map, PageParam pageParam) throws Exception{
		//定义sql字符串
				StringBuffer sqlstr = new StringBuffer();
				//将原始的sql作为一个虚表,并拼装select
				sqlstr.append("select t.* from ( ");
				sqlstr.append(sql);
				sqlstr.append(") t where 1=1 ");
				sqlstr = mapToSqlConnect(sqlstr, map, pageParam,true);
				Map dataMap = getDataBySql(sqlstr.toString(),pageParam);
				//统计查询记录数
//				int recordCount = getResultCount(sqlstr.toString());
				//pageParam中设置总记录数
				pageParam.setRecordCount(Integer.valueOf(dataMap.get("count").toString()));
				//执行sql查询返回查询记录结果
				return (List) dataMap.get("queryList");
	}
	/**
	 * 根据实体查询数据 (高东杰 －－－－新增)
	 * @param map　(entityName实体名[必传]，start当前页开始记录数，limit当前页记录数，list查询条件，senior高级查询条件)
	 * @param pageParam(分页类)
	 * @return
	 * @throws Exception
	 */
	@Override
	public List queryTableContent(Map map,PageParam pageParam) throws Exception{
		//字义实体类名变量　
		String entityName = "";
		if(!map.isEmpty() && map.containsKey("entityName")){
			entityName = map.get("entityName").toString();
		}
		//定义sql变量
		StringBuffer sqlstr = new StringBuffer();
		sqlstr.append("from " + entityName + " t ");
		sqlstr.append(" where 1=1 ");
		sqlstr =mapToSqlConnect(sqlstr, map, pageParam,false);
		sqlstr.append(" order by t.id desc ");
		int recordCount = getResultCount(sqlstr.toString());
		pageParam.setRecordCount(recordCount);
		return getSession().createQuery(sqlstr.toString())
				.setFirstResult(pageParam.getStart())
				.setMaxResults(pageParam.getLimit()).list();
	}
	/**
	 * 将map中的数据转化为sql (高东杰 －－－－新增)
	 * @param sqlstr
	 * @param map
	 * @param pageParam
	 * @return
	 */
	private StringBuffer  mapToSqlConnect(StringBuffer sqlstr,Map map,PageParam pageParam,Boolean isAlias){
		//字义start,limit,sql条件List,高级查询条件变量
		int start=0,limit =0;
		List sqlList = null;
//		JSONArray seniorSql = null;
		String seniorSql = "";
		//判断分解条件后的map是否为空
		if(map!=null && !map.isEmpty()){
			start = (map.containsKey("start") ? Integer.valueOf(map.get("start").toString()):0);//判断start是否存在，如果存在把值赋给start,否则为０
			limit = (map.containsKey("limit") ? Integer.valueOf(map.get("limit").toString()):0);//判断limit是否存在，如果存在把值赋给limit,否则为０
			sqlList = (List) (map.containsKey("list") ? map.get("list"):null); //判断list是否存在，如果存在把值赋给sqlList，否则为null;
//			seniorSql = (map.containsKey("seniorQuery") ? (JSONArray)map.get("seniorQuery"):null); //判断senior是否存在，如果存在就把值赋给seniorSql,否则为null;
			seniorSql = map.containsKey("senior")?String.valueOf(map.get("senior")):"";
			//移除map
			map.remove("start");
			map.remove("limit");
			map.remove("list");
//			map.remove("seniorQuery");
			map.remove("senior");
			map.remove("entityName");
			map.remove("singleReturnNoComponent");
		}
		//判断sql条件List是否为空如果不为空循环遍历条件
		if (null != sqlList && sqlList.size() != 0
				&& sqlList.get(0).toString().length() >= 1) {
			for (int i = 0; i < sqlList.size(); i++) {
					//把条件加入到sqlstr中
					if(isAlias){
						sqlstr.append("and " + sqlList.get(i)).append(" ");
					}else{
						sqlstr.append("and " + sqlList.get(i));
					}
					
			}
		}
		/*添加特殊参数配置**/
		if(map!=null && !map.isEmpty()){
			Set<Map.Entry<String,String>> set = map.entrySet();
			for(Map.Entry<String,String> entry : set){
				String key = entry.getKey();
				String value = entry.getValue();
				if(isAlias){
					sqlstr.append("and \"" + key).append("\"").append(" = \'").append(value).append("\'");
				}else{
					sqlstr.append("and " + key).append(" = \'").append(value).append("\'");
				}
			}
		}
		//判断高级查询是否存在
		if(!seniorSql.isEmpty()){
			sqlstr.append(" and "+seniorSql);
		}
//		if( seniorSql != null && seniorSql.size()>0){
//			//[{"field":"jcmc","logical":"","value":"测试","operator":"="}]
//			String logical = null;//逻辑操作符 and  or ,保存的是上条的信息
//			String operator = null,//关系符 like 
//				   field = null,
//				   value = null;
//			for(int i=0,len=seniorSql.size();i<len;i++){
//				JSONObject seniorObj = seniorSql.getJSONObject(i);
//				field = seniorObj.getString("field");
//				operator = seniorObj.getString("operator");
//				value = seniorObj.getString("value");
//				if(i==0){
//					logical = " and ";//第一次是的拼接逻辑符默认是and
//				}
//				if(isAlias){//拼接逻辑拼接关系和字段名称
//					sqlstr.append(logical).append(" (t.\""+field).append("\"");//是虚表情况下
//				}else{
//					sqlstr.append(logical).append(" t."+field);
//				}
//				if(operator.equals("like")){//拼接字段对应的关系符和值
//					sqlstr.append(" ").append(operator).append("\'%").append(value).append("%\')");
//				}else{
//					sqlstr.append(" ").append(operator).append("\'").append(value).append("\')");
//				}
//				logical = seniorObj.getString("logical");//为下次循环时拼接使用
//			}
//		}
		//判断分页的大小是否为０如果不为0时把页大小赋给pageParam.limit
		if(limit != 0){
			pageParam.setLimit(limit);
		}
		//设置start值
		if(start != 0){
			pageParam.setStart(start);
		}
		return sqlstr;
	}
	/**
	 * 通过Sql查询数据库数据 (高东杰 －－－－新增)
	 * @param sqlString　//SQL语句
	 * @param pageParam//传入的分页参数
	 * @return　返回记录和记录总数
	 */
	protected Map getDataBySql(String sqlString,PageParam pageParam) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		List list = new ArrayList();
		// 根据不同数据库加入不同分页参数。
		String[] sqlList = JdbcSqlPage.pageSql(sqlString, pageParam.getStart(), pageParam.getLimit());

		// 使用SQL构造查询对象.
		Query query = this.getSession().createSQLQuery(sqlList[1]);
		// 将查询结果转换成list。
		// 采用如下方式，可以不再写Bean.同时也可以避免 query会把数据库中number类型的字段转成BigDecimal的情况发生。
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		list = query.list();
		// 根据countSql查询分页的记录总数
		List listCount = this.getSession().createSQLQuery(sqlList[0])
				.list();
		String result = listCount.get(0).toString();
		// 获取记录总数
		count = Integer.parseInt(result);
		map.put("queryList", list);
		map.put("count", count);
		return map;
	}
	//-----------------------------------------------------------------------------------------
	@Override
	public List queryTableContent(String entityName, List<String> paramNames,
			String  seniorParam, Object[] likeValues, String[] oerderBy,
			String[] orderByValue, PageParam pageParam) {
		StringBuffer sb = new StringBuffer();
		sb.append("from " + entityName + " t ");
		sb.append(" where 1=1 ");
		if (null != paramNames && paramNames.size() != 0
				&& paramNames.get(0).length() >= 1) {
			for (int i = 0; i < paramNames.size(); i++) {
//				String[] tj = paramNames.get(i).split(",");
//				if(!tj[1].equals()){//增加值 为空的判断,且不加入查询条件中
					sb.append("and " + paramNames.get(i));
					
//				}
			}
		}
		if(entityName.equals("TsCdzy")){
			sb.append("and sfky = 1 ");
		}
		//判断高级查询是否存在
		if( seniorParam != null && !seniorParam.equals("")){
			sb.append(" and "+seniorParam);
		}
		if(whereCloud.length()>0){
			sb.append(whereCloud);
		}
		int recordCount = getResultCount(sb.toString());
		pageParam.setRecordCount(recordCount);
		return getSession().createQuery(sb.toString())
				.setFirstResult(pageParam.getStart())
				.setMaxResults(pageParam.getLimit()).list();
	}
	@Override
	public Map queryTableContentByHql(String hql, String params, String sqlEnd, Boolean isAlias) throws Exception{
		JSONObject jsonObj = new JSONObject().fromObject(params);
		Map paramList = SqlParamsChange.getSQLParams(jsonObj, isAlias);
		String startStr = "";
		String limitStr = "";
		List<String> retList = null;
		if (paramList.size() >= 0 && paramList.get(0) == null) {
			startStr = (String) paramList.get("start");
			limitStr = (String) paramList.get("limit");
			retList = (List<String>) paramList.get("list");
		}
		int limit = null == limitStr ? 0 : new Integer(limitStr).intValue();
		int start = null == startStr ? 0 : new Integer(startStr).intValue();
		if (start == 0 && limit == 0) {
			limit = 25;
		}
		// 高级查询条件
		String seniorStr = (String) paramList.get("senior");
		PageParam pageParam = new PageParam(start, limit);
		// 根据条件获取数据
		List list = queryTableContentByHql(hql, sqlEnd, retList, seniorStr, pageParam);
		
		Map map = new HashMap();
		map.put("queryList", list);
		map.put("count", list.size());
		return map;
	}
	
	@Override
	public List queryTableContentByHql(String hql,String hqlEnd, List<String> paramNames,
			String  seniorParam, PageParam pageParam) throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append(hql);
		if (null != paramNames && paramNames.size() != 0
				&& paramNames.get(0).length() >= 1) {
			for (int i = 0; i < paramNames.size(); i++) {
				String tmp = paramNames.get(i).trim();
//				String [] tmp = paramNames.get(i).trim().split(" ");
				sb.append(" and t." +tmp );
			}
		}
		//判断高级查询是否存在
		if( seniorParam != null && !seniorParam.equals("")){
			sb.append(" and "+seniorParam);
		}
		if(null!=hqlEnd&&!"".equals(hqlEnd.trim())){
			sb.append(hqlEnd);
		}
		
		int recordCount = getResultCount(sb.toString());
		pageParam.setRecordCount(recordCount);
		return getSession().createQuery(sb.toString())
		.setFirstResult(pageParam.getStart())
		.setMaxResults(pageParam.getLimit()).list();
	}
	
    /**
     * {@inheritDoc}
     */
	public Long getBzdmIdByDm(String bzdm,String dm) {
		List list = queryTableContent("TcXxbzdmjg", new String[]{"bzdm","dm","sfky"}, new Object[]{bzdm,dm,"1"}, null, null);
		if(null!=list&&list.size()>0){
			TcXxbzdmjg tcXxbzdmjg = (TcXxbzdmjg)list.get(0);
			return tcXxbzdmjg.getId();
		}else{
			return null;
		}
	}
    /**
     * {@inheritDoc}
     */
	public Long getBzdmIdByMc(String bzdm,String mc) {
		List list = queryTableContent("TcXxbzdmjg", new String[]{"bzdm","mc","sfky"}, new Object[]{bzdm,mc,"1"}, null, null);
		if(null!=list&&list.size()>0){
			TcXxbzdmjg tcXxbzdmjg = (TcXxbzdmjg)list.get(0);
			return tcXxbzdmjg.getId();
		}else{
			return null;
		}
	}
	//-------------------------------------------------------2013-05-20------------------------------------------------------------------
	/**
	 * 通过代码获取名称
	 */
	@Override
	public String getBzdmMcByDm(String bzdm,String dm){
		//根据条件查询数据
		List list = this.getHibernateTemplate().find(" from TcXxbzdmjg t where t.bzdm ='"+bzdm+"' and t.dm ='"+dm+"'");
		//判断结果是否为空
		if(null!=list&&list.size()>0){
			TcXxbzdmjg tcXxbzdmjg = (TcXxbzdmjg)list.get(0);
			//返回名称
			return tcXxbzdmjg.getMc();
		}else{
			return null;
		}
	}
	//-------------------------------------------------------2013-05-20------------------------------------------------------------------
	/**
	 * {@inheritDoc}
	 */
	public List queryTableContentSingle(String entityName, List<String> paramNames,
			String  seniorParam, Object[] likeValues, String[] oerderBy,
			String[] orderByValue) {
		StringBuffer sb = new StringBuffer();
		sb.append("from " + entityName + " t ");
		sb.append(" where 1=1 ");
		if (null != paramNames && paramNames.size() != 0
				&& paramNames.get(0).length() >= 1) {
			for (int i = 0; i < paramNames.size(); i++) {
				String[] tj = paramNames.get(i).split(",");
//				if(!tj[1].equals()){//增加值 为空的判断,且不加入查询条件中
				sb.append("and " + paramNames.get(i));
				
//				}
			}
		}
		//判断高级查询是否存在
		if(seniorParam != null && !seniorParam.equals("")){
			sb.append(" and "+seniorParam);
		}

		return this.queryHql(sb.toString());
	}

	/**
	 * 执行更新SQL
	 * 
	 * @param sql
	 */
	public void updateSqlExec(String sql) {
		this.jdbcTemplate.execute(sql);
	}

	/**
	 * {@inheritDoc}
	 */
	public void executeUpdateHql(String hql) {
		Query query = this.getSession().createQuery(hql);
		query.executeUpdate();
	}
	/**
	 * {@inheritDoc}
	 */
	public List queryHql(String hql){
		Query query = this.getSession().createQuery(hql);
		return query.list();
	}

	/**
	 * 功能说明：基于SQL的分页查询
	 * 
	 * @param sql
	 *            sql语句
	 * @param param
	 *            查询参数
	 * @param pageParam
	 *            分页参数
	 * @return Page 含结果集的分页bean
	 * @author: fenghc
	 * @DATE:2012-5-23 @TIME: 下午8:58:34
	 */
	@SuppressWarnings("unchecked")
	public Page findOracleSqlPage(String sql, Object[] param,
			PageParam pageParam) {
		if (pageParam.getLimit() == 0)
			pageParam.setLimit(25);
		Page page = new Page();
		page.setLimit(pageParam.getLimit());

		int start = pageParam.getStart() + 1;
		int end = pageParam.getStart() + pageParam.getLimit();
		String rowsSql = "select temp_b.* from ( select temp_a.*,rownum rowno from ( "
				+ sql
				+ " ) temp_a ) temp_b where temp_b.rowno between "
				+ start + " and " + end;
		System.out.println("JDBCTemplate：" + rowsSql);
		List rows = jdbcTemplate.queryForList(rowsSql, param);
		page.setRows(rows);

		int fromPos = sql.toLowerCase().indexOf("from");
		String totalSql = "select count(*) countColumn from ( select * "
				+ sql.substring(fromPos) + " ) ";
		System.out.println("JDBCTemplate：" + totalSql);
		page.setTotal(jdbcTemplate.queryForLong(totalSql, param));

		return page;
	}

	/**
	 * 
	 * 功能说明：通过HQL查询获得总记录数
	 * 
	 * @param hql
	 * @param paras
	 * @return
	 * @author: 党冬(mrdangdong@163.com)
	 * @DATE:2012-6-15 @TIME: 下午11:49:17
	 */
	public int findRecordCountByHQL(String hql, Object[] paras) {
		String _hql = "select count(*)  " + hql;
		List list;
		if (paras == null || paras.length == 0) {
			list = this.getHibernateTemplate().find(_hql);
		} else {
			list = this.getHibernateTemplate().find(_hql, paras);
		}
		return ((Long) list.get(0)).intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public String queryBmById(Long id, String dmlb) {
		List<TcXxbzdmjg> tcXxbzdmList = null;
		if (bzdmMap.size() > 0) {
			Set set = bzdmMap.keySet();
			for (Iterator it = set.iterator(); it.hasNext();) {
				String key = (String) it.next();
				if (key.equals(dmlb)) {
					tcXxbzdmList = (List<TcXxbzdmjg>) bzdmMap.get(key);
					break;
				}
			}
		} else if (bzdmMap.size() == 0) {
			bzdmMap.put(dmlb, queryBM("TcXxbzdmjg", dmlb));
			tcXxbzdmList = (List<TcXxbzdmjg>) bzdmMap.get(dmlb);
		}
		if (tcXxbzdmList == null) {
			bzdmMap.put(dmlb, queryBM("TcXxbzdmjg", dmlb));
			tcXxbzdmList = (List<TcXxbzdmjg>) bzdmMap.get(dmlb);
		}
		String bm = "";
		if (tcXxbzdmList != null) {
			for (int i = 0; i < tcXxbzdmList.size(); i++) {
				TcXxbzdmjg cdzyflDm = tcXxbzdmList.get(i);
				if (id.equals(cdzyflDm.getId())) {
					bm = cdzyflDm.getDm();
					break;
				}
			}
		}
		return bm;
	}

	@Override
	public List queryNodes(String bmbstm, String bmdm,String permissIds) {
		String hsql = " select t from " + bmbstm + " t where t.cclx = '" + bmdm
				+ "'";
		if(permissIds != null && permissIds.trim().length()>0){
			hsql = hsql.concat(" and t.id in("+permissIds+") order by dm asc");
		}else{
			hsql.concat("  order by dm asc");
		}
		if(bmbstm.equals("TbJxzzjg") || bmbstm.equals("TbXzzzjg") || bmbstm.equals("TcRxccBycc")){
			hsql = hsql.concat(" and t.sfky = 1 ");
		}
		List nodeList = this.getSession().createQuery(hsql).list();
		return nodeList;
	}

	private List queryIdByDm(String dmlb) {
		String hsql = "select t from TcXxbzdmjg t where t.bzdm = '" + dmlb
				+ "' order by dm asc";
		List<TcXxbzdmjg> list = this.getSession().createQuery(hsql).list();
		return list;
	}

	@Override
	public Long queryIdByBm(String dm, String dmlb) {
		List<TcXxbzdmjg> tcXxbzdmList = null;
		if (bzdmMap.size() > 0) {
			Set set = bzdmMap.keySet();
			for (Iterator it = set.iterator(); it.hasNext();) {
				String key = (String) it.next();
				if (key.equals(dmlb)) {
					tcXxbzdmList = (List<TcXxbzdmjg>) bzdmMap.get(key);
					break;
				}
			}
		} else if (bzdmMap.size() == 0) {
			bzdmMap.put(dmlb, queryIdByDm(dmlb));
			tcXxbzdmList = (List<TcXxbzdmjg>) bzdmMap.get(dmlb);
		}
		if (tcXxbzdmList == null) {
			bzdmMap.put(dmlb, queryIdByDm(dmlb));
			tcXxbzdmList = (List<TcXxbzdmjg>) bzdmMap.get(dmlb);
		}
		Long id = -1L;
		if (tcXxbzdmList != null) {
			for (int i = 0; i < tcXxbzdmList.size(); i++) {
				TcXxbzdmjg cdzyflDm = tcXxbzdmList.get(i);
				if (dm.trim().equals(cdzyflDm.getDm())) {
					id = cdzyflDm.getId();
					break;
				}
			}
		}
		return id;
	}
	
	
	
	private static void copyto(Object from,Object to){
		Field[] fields=from.getClass().getDeclaredFields();
		for(int i=0;i<fields.length;i++){
			Field field=fields[i];
			String fieldName=field.getName();
			String letterName=fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
			String getName="get"+letterName;
			String setName="set"+letterName;
			try {
				Method getMethod=from.getClass().getMethod(getName, null);
				Method setMethod=from.getClass().getMethod(setName, field.getType());
				setMethod.invoke(to, getMethod.invoke(from, null));
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getUserDataPermiss(Long userId,String nodeIds){
		String jxzzjgHql = " select j.id as id,j.fjdId as fjdId,j.mc as mc,j.sfyzjd as sfyzjd,j.cc as cc from TsUserDataPermiss t, TbJxzzjg j where t.zzjgId = j.id and t.userId = "+userId+" and t.zzjgId in ( "+nodeIds+") order by t.zzjgId ";
		List jxzzjgPermissList = this.getHibernateTemplate().find(jxzzjgHql);
		if(jxzzjgPermissList != null && jxzzjgPermissList.size()>0){
			return jxzzjgPermissList;
		}
		String hql = " select x.id as id,x.fjdId as fjdId,x.mc as mc,x.sfyzjd as sfyzjd,x.cc as cc from TsUserDataPermiss t, TbXzzzjg x where t.zzjgId = x.id and t.userId = "+userId+" and t.zzjgId in ( "+nodeIds+" ) order by t.zzjgId ";
		List xzzzjgPermissList = this.getHibernateTemplate().find(hql);
		if(xzzzjgPermissList != null && xzzzjgPermissList.size()>0){
			return xzzzjgPermissList;
		}
		return null;
	}
	
	@Override
	public int queryEntityCount(String entityName, String param) {
		String hsql = " from "+entityName+" where 1=1 ";
		if(param.length()>0){
			param ="and "+param;
			hsql = hsql.concat(param);
		}
		int count = this.getHibernateTemplate().find(hsql).size();
		return count;
	}
	@Override
	public List queryEntityList(String entityName,String param){
		String hsql = " from "+entityName+" where 1=1 ";
		if(param!=null && param.length()>0){
			param = "and "+param;
			hsql = hsql.concat(param);
		}
				
		List list = this.getSession().createQuery(hsql
				).list();
		return list;
	}
	
	@Override
	public List querySqlEntityList(String sql,Class cls){
		
		return this.getSession().createSQLQuery(sql).addEntity(cls).list();
		
	}
	@Override
	public List querySqlList(String sql) {
		List list = jdbcTemplate.queryForList(sql);
		return list;
	}
	@Override
	public int querySqlCount(String sql){
//		System.out.println(jdbcTemplate.queryForInt(sql));
		return jdbcTemplate.queryForInt(sql);
	}
	@Override
	public Long getRoleIdByDm(String dm){
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("select j.id  from ts_js j,tc_xxbzdmjg g where j.jslx_id = g.id  and g.bzdm = 'XXDM-QXJSLX' and g.dm = '"+dm+"'");
//		Long jsId = this.getJdbcTemplate().queryForLong(strBuffer.toString());
		return this.getJdbcTemplate().queryForLong(strBuffer.toString());
	}
	
	@Override
	public List<Object> getTreeData(String hql){
		return this.getSession().createQuery(hql).list();
	}
	//---------------------------------------------------2013-05-17---------------------------------------------------
	/**
	 * 部分修改实体对象　(王永太--新增)
	 * @param obj 实体对象
	 * @param fields 要更新的字段，字段是数组集合的形式
	 * @return
	 */
	@Override
	public boolean updateEntityPartField(Object obj,String[]fields){
		List<String> list = Arrays.asList(fields);
		return updateEntityPartField(obj, list);
	}
	/**
	 * 部分的修改实体对象　(王永太--新增)
	 * @param obj 实体对象
	 * @param fields  要更新的字段，字段是字符串的形式，字符串以 "field1,field2,field3,field4,field5"这样的形式存在
	 * @return
	 */
	@Override
	public boolean updateEntityPartField(Object obj,String fields){
		List<String> list = Arrays.asList(fields.split(","));
		return updateEntityPartField(obj, list);
	}
	/**
	 * 修改实体对象部分字段　(王永太--新增)
	 * @param obj 实体对象
	 * @param fieldnames  要更新的字段，字段是集合的形式存在
	 * @return
	 */
	@Override
	public boolean updateEntityPartField(final Object obj, final List<String> fieldnames){
		 String tablename = ReflectUtil.getTableName(obj);
		 StringBuilder sql = new StringBuilder("update "+tablename+" set ");
		 Class entityClass = obj.getClass();
		 for(String fieldname : fieldnames){
			 String columnName = ReflectUtil.getColumnName(entityClass, fieldname);
			 sql.append(columnName).append(" = ?,");
		 }
		 sql = new StringBuilder(StringUtils.removeEnd(sql.toString(), ","));
		 sql.append(" where id ="+ReflectUtil.getId(obj)+" ");
//		 System.out.println(sql);
		 int n = jdbcTemplate.update(sql.toString(), new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				for(int i=0;i<fieldnames.size();i++){
					Object o = ReflectUtil.invokeGetter(obj, fieldnames.get(i));
					String value = (o == null?null:o.toString());
					String type = ReflectUtil.getType(obj, fieldnames.get(i));
					if(value == null){
						ps.setObject(i+1, null);
					}else if(type.equals("String")){
						ps.setString(i+1, value);
					}else if(type.equals("Double")){
						ps.setDouble(i+1, value!=null?Double.parseDouble(value):null);
					}else if(type.equals("Long")){
						ps.setLong(i+1, value!=null?Long.parseLong(value):null);
					}else if(type.equals("Integer")){
						ps.setInt(i+1, value!=null?Integer.parseInt(value):null);
					}else if(type.equals("Boolean")){
						ps.setBoolean(i+1, value!=null?Boolean.parseBoolean(value):null);
					}else if(type.equals("Short")){
						ps.setShort(i+1, value!=null?Short.parseShort(value):null);
					}
				}
				
			}
		});
		return n>0;
	}
	/**
	 * 批量部分更新实体的部分字段  (高东杰--新增)
	 * @param list
	 * @param fields
	 * @return
	 */
	@Override
	public boolean batchUpdateEntityPartField(List<Object> list,List<String> fields){
		//默认给返回值设置为true
		boolean flag = true;
		//判断list是否为空
		if(list.isEmpty() || list.get(0).toString().equals("null") || fields.isEmpty() || fields.get(0).toString().equals("null") ){
			return false;
		}
		//批量执行更新实体表对应的部分字段
		for(int i=0;i<list.size();i++){
			 flag = updateEntityPartField(list.get(i),fields.get(i));
		}
		return flag;
	}
	//---------------------------------------------------2013-05-18------------------------------------------------------------------
	/**
	 * 批量更新实体(高东杰－－新增)
	 * @param list
	 * @return
	 */
	@Override
	public boolean update(List<Object> list) {
		int f =0;
		if(list.isEmpty() || list.get(0).toString().equals("null")){
			return false;
		}
		for(int i = 0;i<list.size();i++){
			this.getHibernateTemplate().update(list.get(i));
			f = i+1;
		}
		return f>0;
	}
	@Override
	public void delete(String ids,String tableName){
		 getJdbcTemplate().execute("delete from "+tableName+" where id in ( "+ids+" )");
	}
	/**
	 * 根据教学组织机构ID获取节点下所有的班级信息
	 * @param jxzzjgId　教学组织机构ID
	 * @return 班级列表
	 */
	@Override
	public List<TbXxzyBjxxb> getBjList(Long jxzzjgId){
		TbXxzyJxzzjg jxzzjg = (TbXxzyJxzzjg) this.get(TbXxzyJxzzjg.class, jxzzjgId);
		List<TbXxzyBjxxb> list = new ArrayList<TbXxzyBjxxb>();
		if(jxzzjg != null){
			list =(List<TbXxzyBjxxb>) getSession().createQuery(" select t from TbXxzyBjxxb t ,TbJxzzjg g where t.fjdId = g.id and g.qxm like '"+jxzzjg.getQxm()+"%'").list();
		}
		return list;
	}
	
	/**
	 * 根据插入SQL语句执行插入
	 * @param sql
	 */
	@Override
	public void insert(String sql){
		this.getJdbcTemplate().execute(sql);
	}
	/**
	 * 根据更新SQL语句执行更新操作
	 * @param sql
	 */
	@Override
	public void update(String sql){
		this.getJdbcTemplate().execute(sql);
	}
	@Override
	public Long getIdBySql(String sql){
		List<Object> list = querySqlList(sql);
		if(!list.isEmpty()){
			JSONObject obj = JSONObject.fromObject(list.get(0));
			return obj.getLong("id");
		}
		return null;
	}

	//-------------------------------------------------------------------------------------------------------------------------------
	/**
	 * 查询教学组织机构树
	 * @param pId
	 * @return
	 */
	@Override
	public List<TbXxzyJxzzjg> queryJxzzjgTree(Long pId) {
		Long pIdL = Long.valueOf(pId);
		String sql = "select {tbJxzzjg.*} from TB_JXZZJG tbJxzzjg start with tbJxzzjg.FJD_ID = " +pIdL+
				" connect by prior tbJxzzjg.ID = tbJxzzjg.FJD_ID order by tbJxzzjg.qxm,tbJxzzjg.pxh asc";
		List<TbXxzyJxzzjg> list = this.getSession().createSQLQuery(sql).addEntity("tbJxzzjg", TbXxzyJxzzjg.class).list();
		return list;
	}
	
	@Override
	public List<TbXxzyJxzzjg> queryJxzzjgTree(String ids) {
//		Long pIdL = Long.valueOf(pId);
		String sql = "select {tbJxzzjg.*} from TB_JXZZJG tbJxzzjg where tbJxzzjg.ID in (" +ids+
				") order by tbJxzzjg.qxm,tbJxzzjg.pxh asc";
		List<TbXxzyJxzzjg> list = this.getSession().createSQLQuery(sql).addEntity("tbJxzzjg", TbXxzyJxzzjg.class).list();
		return list;
	}
	/**
	 * 查询行政组织机构树
	 * @return
	 */
	@Override
	public List<TbXxzyXzzzjg> queryZzjgTree() {
		String sql = "select {tsZzjg.*} from TB_XZZZJG tsZzjg where tsZzjg.SFKY = 1 order by tsZzjg.qxm,tsZzjg.pxh asc";
		List<TbXxzyXzzzjg> list = this.getSession().createSQLQuery(sql).addEntity("tsZzjg", TbXxzyXzzzjg.class).list();
		return list;
	}
	@Override
	public List<TbXxzyXzzzjg> queryZzjgTree(String ids) {
		String sql = "select {tsZzjg.*} from TB_XZZZJG tsZzjg where tsZzjg.SFKY = 1 and tsZzjg.id in ("+ids+") order by tsZzjg.qxm,tsZzjg.pxh asc";
		List<TbXxzyXzzzjg> list = this.getSession().createSQLQuery(sql).addEntity("tsZzjg", TbXxzyXzzzjg.class).list();
		return list;
	}
	
	@Override
	public Long getRoleId(Long userId,Long jslxId){
		List<TsJs> tsJsList = this.getSession().createQuery("select t from TsUserJs uj, TsJs t where uj.jsId = t.id and t.jslxId ='"+jslxId+"' and uj.userId = "+userId).list();
		if(tsJsList != null && tsJsList.size()>0){
			TsJs js = tsJsList.get(0);
			return js.getId();
		}
		return null;
	}
	@Override
	public List getRoleIds(Long userId,String jslxIds){
		List<TsJs> tsJsList = this.getSession().createQuery("select t from TsUserJs uj,TsJs t where t.jslxId in ("+jslxIds+") and uj.userId = "+userId).list();
		String jsIds = "";
		if(tsJsList != null && tsJsList.size()>0){
			return tsJsList;
		}
		return null;
	}
	
	@Override
	public List queryEntityList(String entityName,String param,String order){
		StringBuffer hsqlbuf = new StringBuffer().append(" from ").append(entityName).append(" where 1=1 ");
		if(param.length()>0){
			param = "and "+param;
			hsqlbuf = hsqlbuf.append(param);
		}
		if(order.length()>0){
			hsqlbuf = hsqlbuf.append(" order by ").append(order);
		}
		List list = this.getSession().createQuery(hsqlbuf.toString()
				).list();
		return list;
	}
	@Override
	public List getStsx(String ssstm) {
		Object obj = EhcacheUtil.getObjFromCache(this.stsx_cacheName, ssstm);
		List list = (List) obj;
		if (obj == null || list.size() == 0) {
			TsStsx stsx = new TsStsx();
			stsx.setSsstm(ssstm);
			list = this.loadEqual(stsx);
			EhcacheUtil.putObjToCache(this.stsx_cacheName, ssstm, list);
		} else {
			list = (List) obj;
		}
		return list;
	}
	@Override
	public Query createSQLQuery(String sql){
		return getSession().createSQLQuery(sql);
	}
	/**
	 * 查询sql中的记录个数
	 * @param sql
	 * @return
	 */
	public Long queryForLongBySql(String sql){
//		List<Map> list = this.queryListMapInLowerKeyBySql(sql);
//		Map map = list.get(0);
//		Long num = Long.valueOf(map.get("num").toString());
//		return num;
		return this.jdbcTemplate.queryForLong(sql);
	}
	/**
	 * 
	 * @param num
	 * @return
	 */
	public String queryRxnjIdsByNum(int num){
		
		return null;
	}

}
