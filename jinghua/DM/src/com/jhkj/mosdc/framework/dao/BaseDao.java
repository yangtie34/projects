package com.jhkj.mosdc.framework.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.jdbc.core.JdbcTemplate;

import com.jhkj.mosdc.framework.bean.PageParam;
import com.jhkj.mosdc.framework.po.TsStsx;
import com.jhkj.mosdc.permiss.po.TpUser;
import com.jhkj.mosdc.permission.po.TsUser;
import com.jhkj.mosdc.xxzy.po.TbXxzyBjxxb;
import com.jhkj.mosdc.xxzy.po.TbXxzyJxzzjg;
import com.jhkj.mosdc.xxzy.po.TbXxzyXzzzjg;


public interface BaseDao {
	
	/**
	 * 获取hibernate session 连接
	 * @return
	 */
	public Session getHibernateSession();

	public void insert(Object object);
	

	/**
	 * 将该对象更新到数据库里
	 * 
	 * @param object：
	 *            被更新的对象
	 */
	public void update(Object object);
	/**
	 * 获取JdbcTemplate
	 * @return
	 */
	public JdbcTemplate getJdbcTemplate();
	/**
	 * 从数据库里物理删除该对象对应的数据
	 * 
	 * @param object：
	 *            被删除的对象
	 * @throws Exception
	 */
	public void delete(Object object);
	
	/**
	 * 执行sql语句删除
	 * @param sql
	 */
	public void deleteBySql(String sql);
	/**
	 * 从数据库里物理删除该对象对应的所有数据
	 * 
	 * @param object：
	 *            被删除的对象
	 * @throws Exception
	 */
	public void deleteAll(String entityName);
	/**
	 * 通过实体Class和id获得实体对象
	 * @param entityClass
	 * @param id
	 * @return
	 */
	public Object get(Class entityClass, Serializable id);
	/**
	 * 通过ID查询一个对象
	 * @param id
	 * @param entityName
	 */
	public Object queryById(String id, String entityName) throws Exception;
	
	public Object queryById(Long id,String entityName) throws Throwable;

	/**
	 * 通过实体名，进行修改操作
	 * @param entityName
	 * @param filedList 字段属性的值
	 */
	public void update(String entityName,String id, List filedList);

	/**
	 * 通过id和实体名进行删除操作
	 * @param id
	 * @param entityName
	 */
	public void deleteByIds(String ids, String entityName) throws Exception;

	/**
	 * 通过实体名对字段表进行查询，查出可作为查询的字段
	 * @param entityName
	 * @param paramNames
	 * @param values
	 * @param likeParamNames
	 * @param likeValues
	 * @return
	 */
	public List querySearchHeader(String entityName, String[] paramNames, Object[] values, String[] likeParamNames, Object[] likeValues);
	
	/**
	 * 通过实体名对字段表进行查询，查出可作为查询的字段
	 * @param entityName
	 * @param map JSON转化后的参数
	 * @return
	 */
	public List querySearchHeader(String entityName,Map map);

	/**
	 * 通过实体名对字段表进行查询，查出可作为列表头的字段
	 * @param entityName
	 * @return  List 字段表实体类列表
	 */
	public List queryTableHeader(String entityName,Long sflbzd);


	/**
	 * 通过实体名对相应的表进行查询,根据查询条件进行查询，可以分页
	 * @param entityName
	 * @param paramNames
	 * @param values
	 * @param likeParamNames
	 * @param likeValues
	 * @param pageSize
	 * @param pageNo
	 * @return List
	 */
	public List queryTableContent(String entityName, String[] paramNames, Object[] values, String[] likeParamNames, Object[] likeValues, String [] oerderBy ,String [] orderByValue, PageParam pageParam);
	/**
	 * 通过实体名对应的表进行查询,根据前台传入的条件查询分页
	 * @param entityName
	 * @param paramNames
	 * @param values
	 * @param likeParamNames
	 * @param likeValues
	 * @param oerderBy
	 * @param orderByValue
	 * @param pageParam
	 * @return
	 */
	public List queryTableContent(String entityName, List<String> paramNames, String seniorParam, Object[] likeValues, String [] oerderBy ,String [] orderByValue, PageParam pageParam);
	public Map queryTableContentByHql(String hql, String params, String sqlEnd, Boolean isAlias) throws Exception;
	public List queryTableContentByHql(String hql,String hqlEnd, List<String> paramNames,String  seniorParam, PageParam pageParam) throws Exception;
	/**
	 * 通过标准代码和代码字段获得代码表的id
	 * @param bzdm
	 * @param dm
	 * @return
	 */
	public Long getBzdmIdByDm(String bzdm,String dm);
	/**
	 * 通过标准代码和名称字段获得代码表的id
	 * @param bzdm
	 * @param mc
	 * @return
	 */
	public Long getBzdmIdByMc(String bzdm,String mc);
	/**
	 * 根据代码查名称
	 * @param bzdm　标准代码类型
	 * @param dm　　代码
	 * @return　　名称
	 */
	public String getBzdmMcByDm(String bzdm,String dm);
	
	/**
	 * 通过实体名对应的表进行查询,根据前台传入的条件查询分页
	 * @param entityName
	 * @param paramNames
	 * @param values
	 * @param likeParamNames
	 * @param likeValues
	 * @param oerderBy
	 * @param orderByValue
	 * @return
	 */
	public List queryTableContentSingle(String entityName, List<String> paramNames,
			String  seniorParam, Object[] likeValues, String[] oerderBy,
			String[] orderByValue);
	/**
	 * 通过实体名对相应的表进行查询，根据查询条件进行查询
	 * @param entityName
	 * @return  List 数据结果列表
	 */
	public List queryTableContent(String entityName, String[] paramNames, Object[] values, String[] likeParamNames, Object[] likeValues);
	/**
	 * 通过实体名对相应的表进行查询，根据查询条件进行查询
	 * @param entityName
	 * @return  List 数据结果列表
	 */
	public List queryTableContent(String entityName, String[] paramNames,
			Object[] values, String[] likeParamNames, Object[] likeValues, String[] oerderBy,
			String[] orderByValue) ;
	/**
	 * 通过实体名对相应的表进行查询，根据查询条件进行查询
	 * @param entityName 表名
	 * @param bzdm 标准代码
	 * @return  List 数据结果列表
	 */
	public List queryTree(String entityName,String bzdm);
	/**
	 * 通过实体名对相应的表进行查询，根据查询条件进行查询
	 * @param entityName
	 * @return  List 数据结果列表
	 */
	public List queryTree(String entityName);
	/**
	 * 查询所有的菜单
	 * @return List
	 */
	public List queryMenuTree() ;
	/**
	 * 通过实体名对相应的表进行查询，查询出所有的记录
	 * @param entityName
	 * @return  List 数据结果列表 Map
	 */
	public List queryTableContentForMap(String entityName);
	
	
	/**
	 * 一次获取多个id
	 * @param idNum
	 * @return
	 * @throws Exception
	 */
	public List<Long> getMutiId(Integer idNum) throws Exception;
	
	/**
	 * 一次获取1000个id
	 * @return
	 * @throws Exception
	 */
	public List<Long> getOneThousandId() throws Exception;
	/**
	 * 通过学校代码获取主键id
	 * @param xxdm
	 * @return  Long 主键id
	 */
	public Long getId() throws Exception;

    public List<TsStsx> queryExportTableHeaders(String entityName,String params) throws Exception;
    /**
     * 查询导出内容
     * @author LJH
     * @param tableName
     * @param headers
     * @param params
     * @return
     */
    public List<String> queryExportTableContent(String tableName, String headers, String params);
    /**
     *  根据sql查询分页数据
     * @param sqlString
     * @param params
     * @return
     */
    @SuppressWarnings("unchecked")
	public Map queryTableContentBySQL(String sqlString,Map params);
    
    
    /**
     * 查询实体对象，以实体的id等值条件
     * @param obj
     * @return
     */
    public void load(Object obj);
    
    /**
     * 查询实体列表，以实体的非空属性为等值条件
     * @param obj
     * @return
     */
    public List loadEqual(Object obj);
    
    /**
     * 查询实体列表，并按fieldFlag排序orderFlag，
     * fieldFlag 实体的属性名，如xh
     * orderFlag 取值asc 或者desc
     * @param obj
     * @param fieldFlag
     * @param orderFlag 
     * @return
     */
    public List loadEqualByOrder(Object obj,String fieldFlag,String orderFlag);
    
    /**sql
     * 查询实体所有列表
     * @param obj
     * @return
     */
    public List loadAll(Object obj);
    
    /**
     * 获取第一个实体
     * @param obj
     * @return
     */
    public Object loadFirstEqual(Object obj);
    
    /**
     * 获取最后一个实体
     * @param obj
     * @return
     */
    public Object loadLastEqual(Object obj);
  
    /**
     * 单个实体 等值匹配分页查询
     * @Title: loadPageEqual 
     * @param @param obj
     * @param @param orderField
     * @param @param orderFlag
     * @param @param start
     * @param @param limit
     * @param @return
     * @return List
     * @throws
     */
    public List loadPageEqual(Object obj,String orderField,String orderFlag,int start,int limit);
    
    
    /**
     * 查询数目，以实体的非空属性为等值条件
     * @param obj
     * @return
     */
    public Long countEqual(Object obj);
    
    /**
     * 批量删除实体，以实体的非空属性为等值条件
     * @param obj
     * @return
     */
	public void deleteEqual(Object obj);
	/**
	 * 根据条件更新记录
	 * @param sql
	 */
	public void updateSqlExec(String sql);
	
    /**
     * 执行hql
     */
	public void executeUpdateHql(String hql);
	/**
	 * 查询hql语句
	 * @param hql
	 * @return
	 */
	public List queryHql(String hql);
	/*public List<Object> queryJcsslxInfo();
	
	
	public List<Object> queryJcsslxCount(Long jcsslxId,String startDate,String endDate,String appointDate,String entityName);
	*/
	/**
	 * 删除list对象
	 * @param list
	 */
	public void deleteByList(List list );
	
	/**
	 * 根据代码ID和代码类别获取当前代码的名称
	 * @param id
	 * @param dmlb
	 * @return
	 */
	public String queryBmById(Long id,String dmlb);
	
	/**
	 * 根据代码Dm和代码类别获取当前代码的名称
	 * @param id
	 * @param dmlb
	 * @return
	 */
	public Long queryIdByBm(String dm,String dmlb);
	
	/**
	 * 根据树形表和层次类别ID获取当前层次下所有节点
	 * @param bmbstm
	 * @param bmdm
	 * @param permissIds权限参数
	 * @return
	 */
	public List queryNodes(String bmbstm, String bmdm,String permissIds);
	
	/**
	 * oralce 数据库批量插入
	 * @param objList 实体对象，只能是一个实体类的实体对象
	 */
	public Object oracleBatchInsert(List objList);
	/**
	 * 查询树
	 * @param entityName
	 * @return
	 */
	public List queryTreeCombobox(String entityName);
	/**
	 * 查询树形表记录
	 * @param entityName
	 * @return
	 */
	public List getTreeJson(String entityName, String fjdId);
	
	/**
	 * 获取用户数据权限
	 * @param userId
	 * @param nodeIds
	 * @return
	 */
	 public List getUserDataPermiss(Long userId, String nodeIds);
	 /**
	  * 查询代码对应的表记录
	  * @param entityName
	  * @param permissIds
	  * @return
	  */
	 public List queryTableContentForDm(String entityName, String permissIds);
	 /**
	  * 通过实体名和标准代码进行查询，查出普通的编码列表
	  * @param entityName
	  * @param bzdm
	  * @param permissIds
	  * @return
	  */
	 public List queryBM(String entityName, String bzdm, String permissIds); 
	 /**
	  * 通过实体名和标准代码进行查询，查出普通的编码列表
	  * @param entityName
	  * @param bzdm
	  * @return
	  */
	 public List queryBM(String entityName, String bzdm);
	 
	 /**
	 * 获取实体记录数量
	 * @param entityName
	 * @param param
	 * @return
	 */
	public int queryEntityCount(String entityName,String param);
	/**
	 * 获取实体表的记录
	 * @param entityName
	 * @param param
	 * @return
	 */
	public List queryEntityList(String entityName, String param);
	/**
	 * 通过拼装的sql查询数据返回对象
	 * @Title: querySqlEntityList 
	 * @param @param sql
	 * @param @param entityName
	 * @param @return
	 * @return List
	 * @throws
	 */
	public List querySqlEntityList(String sql,Class cls);
	/**
	 * 通过拼装的sql查询数据
	 * @param sql
	 * @return
	 */
	public List querySqlList(String sql);
	/**
	 * 获取代码数据包括ＩＤ和名称
	 * @param entityName
	 * @param bzdm
	 * @return
	 */
	public List<Map> queryBMForIdMc(String entityName, String bzdm,String lx);
	/**
	 * 获取代码名称
	 * @param bzdmId
	 * @param entityName
	 * @return
	 */
	public String getBzdmMcById(String bzdmId, String entityName);
	/**
	 * 获取名称
	 * @Title: getMcById 
	 * @param @param bzdmId
	 * @param @param entityName
	 * @param @return
	 * @return String
	 * @throws
	 */
	public String getMcById(String bzdmId,String entityName);
	/**
	 * 通过代码获取角色ID
	 * @param dm
	 * @return
	 */
	public Long getRoleIdByDm(String dm);
	
	/**
	 * 获取tree数据
	 * @param sql
	 * @return
	 */
	public List<Object> getTreeData(String sql);
	/**
	 * 针对虚表查询封装
	 * @param sql[必传]
	 * @param map　(start当前页开始记录数，limit当前页记录数，list查询条件，senior高级查询条件)
	 * @param pageParam
	 * @return
	 * @throws Exception 
	 */
	public List queryTableContent(String sql, Map map, PageParam pageParam) throws Exception;
	/**
	 * 针对虚表查询封装(带别名)
	 * @param sql[必传]
	 * @param map　(start当前页开始记录数，limit当前页记录数，list查询条件，senior高级查询条件)
	 * @param pageParam
	 * @return
	 * @throws Exception 
	 */
	public List queryTableContentWithAlias(String sql, Map map, PageParam pageParam) throws Exception;
	/**
	 * 根据实体查询数据
	 * @param map　(entityName实体名[必传]，start当前页开始记录数，limit当前页记录数，list查询条件，senior高级查询条件)
	 * @param pageParam(分页类)
	 * @return
	 * @throws Exception
	 */
	public List queryTableContent(Map map, PageParam pageParam) throws Exception;
	//-----------------------------------------------------------------------------------------------------
	/**
	 * 批量部分更新实体的部分字段  (高东杰--新增)
	 * @param list
	 * @param fields
	 * @return
	 */
	public boolean batchUpdateEntityPartField(List<Object> list, List<String> fields);
	/**
	 * 修改实体对象部分字段　(王永太--新增)
	 * @param obj 实体对象
	 * @param fieldnames  要更新的字段，字段是集合的形式存在
	 * @return
	 */
	public boolean updateEntityPartField(Object obj, List<String> fieldnames);
	/**
	 * 部分的修改实体对象　(王永太--新增)
	 * @param obj 实体对象
	 * @param fields  要更新的字段，字段是字符串的形式，字符串以 "field1,field2,field3,field4,field5"这样的形式存在
	 * @return
	 */
	public boolean updateEntityPartField(Object obj, String fields);
	/**
	 * 部分修改实体对象　(王永太--新增)
	 * @param obj 实体对象
	 * @param fields 要更新的字段，字段是数组集合的形式
	 * @return
	 */
	public boolean updateEntityPartField(Object obj, String[] fields);
	/**
	 * 批量更新实体(高东杰－－新增)
	 * @param list 批量实体对象
	 * @return　ture或false
	 */
	public boolean update(List<Object> list);
	/**
	 * 批量保存对象　(高东杰--新增)
	 * @param list 对象列表(不带ＩＤ)
	 * @return　list 对象列表
	 * @throws Exception
	 */
	public List<Object> save(List<Object> list) throws Exception;
	/**
	 * 保存对象并返回对象　(高东杰--新增)
	 * @param object　对象(不带ID)
	 * @return　object 对象(带ID)
	 * @throws Exception
	 */
	public Object save(Object object) throws Exception;
	/**
	 * 根据父节点查询教学组织机构树　(高东杰-新增)
	 * @param pId
	 * @return
	 */
	public List<TbXxzyJxzzjg> queryJxzzjgTree(Long pId);
	/**
	 * 查夜行政组织机构　(高东杰-新增)
	 * @return
	 */
	public List<TbXxzyXzzzjg> queryZzjgTree();
	
	/**
	 * 根据插入SQL语句执行插入(高东杰-新增)
	 * @param sql
	 */
	public void insert(String sql);
	/**
	 * 根据更新SQL语句执行更新操作(高东杰-新增)
	 * @param sql
	 */
	public void update(String sql);
	/**
	 * 通过用户ＩＤ和角色类型ID查询角色ID
	 * @param userId
	 * @param jslxId
	 * @return 角色ID
	 */
	public Long getRoleId(Long userId,Long jslxId);
	/**
	 * 根据ids删除表记录
	 * @param ids　记录IDs
	 * @param tableName 表名
	 */
	public void delete(String ids,String tableName);
	/**
	 * 根据教学组织机构ID获取节点下所有的班级信息
	 * @param jxzzjgId　教学组织机构ID
	 * @return 班级列表
	 */
	public List<TbXxzyBjxxb> getBjList(Long jxzzjgId);
	/**
	 * 获取根据前台传入的单字段查询或高级查询、表头查询数据转化为SQL条件,并返回Map(List,count);
	 * @param sql 组装的基本Sql
	 * @param map 各种条件(查询条件，分页参数)
	 * @return
	 */
	public Map queryTableContentBySql(String sql, Map map);
	public Map queryTableContentBySql(String sql, JSONObject obj, String sqlEnd, Boolean isAlias) throws Exception;
	/**
	 * 根据SQL查询数据并返回List<Map>
	 * @param sqlString 查询SQL
	 * @return list<Map>
	 */
	public List<Map> queryListMapBySQL(String sqlString);
	/**
	 * 根据SQL查询数据并返回List<Map>,其中Map的key值为小写
	 * @param sqlString
	 * return List<Map>
	 */
	@SuppressWarnings({ "unused", "rawtypes", "deprecation", "unchecked" })
	public List<Map> queryListMapInLowerKeyBySql(String sql);
	
	/**
	 * 查询sql中的记录个数
	 * @param sql
	 * @return
	 */
	public Long queryForLongBySql(String sql);
	/**
	 * 根据SQL查询记录数
	 * @param sql
	 * @return
	 */
	public int querySqlCount(String sql);
	/**
	 * 根据角色类型ID获取jsId
	 * @param userId
	 * @param jslxIds
	 * @return
	 */
	public List getRoleIds(Long userId, String jslxIds);
	/**
	 * 保存或更新对象
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public Object saveOrUpdate(Object object) throws Exception;
	/**
	 * 保存或更新列表对象
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public List<Object> saveOrUpdate(List<Object> list) throws Exception;
	/**
	 * 
	 * @return
	 */
	public TpUser getTpUser();

	public Long getIdBySql(String sql);

	/**
	 * 根据实体名、条件和排序字段组装hql并返回结果集
	 * @param entityName
	 * @param param 如 t.name = '**' and  t.xb_id =1000234343
	 * @param order 如：t.id,t.name
	 * @return
	 */
	public List queryEntityList(String entityName, String param, String order);
	/**
	 * 根据实体名获取实体类
	 * @param ssstm
	 * @return
	 */
	public List getStsx(String ssstm);
	/**
	 * 将map转化为Sql条件
	 * @param map
	 * @return
	 */
	public String mapToSqlParams(Map map);
	/**
	 * 根据权限组织获取教学组织机构节点
	 * @param ids
	 * @return
	 */
	public List<TbXxzyJxzzjg> queryJxzzjgTree(String ids);
	/**
	 * 根据权限组织获取行政组织机构节点
	 * @param ids
	 * @return
	 */
	public List<TbXxzyXzzzjg> queryZzjgTree(String ids);

	public Query createSQLQuery(String sql);

}


