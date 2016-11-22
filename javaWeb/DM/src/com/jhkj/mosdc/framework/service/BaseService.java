package com.jhkj.mosdc.framework.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.bean.PageParam;
import com.jhkj.mosdc.framework.po.TsStsx;
import com.jhkj.mosdc.jwgl.po.TbJwPkzb;
import com.jhkj.mosdc.xggl.xjgl.po.TbXjdaXjxx;
import com.jhkj.mosdc.xxzy.po.TbXxzyBjxxb;
import com.jhkj.mosdc.xxzy.po.TbXxzyXq;

public interface BaseService {
	
	/**
	 * 根据角色代码获取角色id
	 * @param roleDm
	 * @return
	 */
	public Long getRoleIdByDm(String roleDm);
	
	/**
	 * 根据角色id获取角色代码
	 * @param roleId
	 * @return
	 */
	public String getRoleDmById(Long roleId);
	
	/**
	 * 获取本学年学期的周次数目
	 * @return
	 */
	public Integer getZcNumOfCurrentXnxq();
	
	/**
	 * 获取当前日期对应的周次
	 * @return
	 * @throws Throwable 
	 */
	public Integer getCurrentZc();
	
	/**
	 * 获取当前日期对应的周次
	 * @param str
	 * @return
	 * @throws Throwable 
	 */
	public Object getCurrentZc(String str) throws Throwable;
	
	/**
	 * 获取教学日历信息
	 * @param xnId
	 * @param xqId
	 * @return
	 */
	public Map getJxrlInfo(Long xnId,Long xqId);
	
	/**
	 * 获取上学年学期
	 * @return
	 */
	public Map getPreXnxq();
	/**
	 * 获取上学年学期，服务
	 * @param str
	 * @return
	 */
	public Object getPreXnxq(String str);
	
	/**
	 * 获取下学年学期
	 * @return
	 */
	public Map getNextXnxq();
	
	/**
	 * 获取下学年学期
	 */
	public Object getNextXnxq(String str);

	/**
	 * 获取当前学年学期 返回map
	 * @return
	 */
	public Map getCurrentXnxq();
	
	/**
	 * 
	* @Title: getCurrentZsjd 
	* @Description: 获取当前招生季度ID
	* @param @return    设定文件 
	* @return Long    返回类型 
	* @throws
	 */
	public Long getCurrentZsjd();
	/**
	 * 
	* @Title: getCurrentRxnjId 
	* @Description: 获取当前入学年级ID
	* @param @return    设定文件 
	* @return Long    返回类型 
	* @throws
	 */
	public Long getCurrentRxnjId();
	/**
	 * 获取当前学年学期，返回json
	 * @param str
	 * @return
	 */
	public Object getCurrentXnxq(String str);
	
	/**
	 * 获取排课总表
	 * @param xnId
	 * @param xqId
	 * @param xqkssj
	 * @param xqjssj
	 * @return
	 */
	public  Object updateOrGetPkzb(Long xnId,Long xqId,String xqkssj,String xqjssj);
	
	/**
	 * 切换学年学期
	 * @param str
	 * @return
	 * @throws Throwable 
	 */
	public Object updateSwitchXnxq(String str) throws Throwable;
	
	/**
	 * 获取学年学期的时间段
	 * @param str
	 * @return
	 */
	public Object getXnxqSjd(String str);
	
	/**
	 * 自动切换学年学期
	 * @return
	 * @throws Throwable
	 */
	public Object updateAutoSwitchXnxq()  throws Throwable;
	
	/**
	 * 获取学期编码表数据
	 * @param str
	 * @return
	 */
	public Object getXqBmbList(String str);
	
	/**
	 * 获取学年编码表数据
	 * @param str
	 * @return
	 */
	public Object getXnBmbList(String str);

    /**
     * 通过实体名对字段表进行查询，查出可作为查询的字段
     *
     * @param entityName
     * @return List 字段表实体类列表
     */
    public String querySearchHeader(String param);
    
    /**
     * 获取班级的校区
     * @param bjId
     * @return
     * @throws Throwable
     */
    public TbXxzyXq getXqOfBj(Long bjId) throws Throwable ;


    /**
     * 通过实体名对相应的表进行查询,根据查询条件进行查询，可以分页
     *
     * @param entityName
     * @return List 数据结果列表
     */
    public List queryTableContent(String entityName, String[] paramNames, Object[] values, String[] likeParamNames, Object[] likeValues, String[] oerderBy, String[] orderByValue, PageParam pageParam);

    /**
     * 通过实体名对相应的表进行查询，根据查询条件进行查询
     *
     * @param entityName
     * @return List 数据结果列表
     */
    public List queryTableContent(String entityName, String[] paramNames,
                                  Object[] values, String[] likeParamNames, Object[] likeValues);

    /**
     * 通过ID查询一个对象，返回的json是对象
     *
     * @param param 参数集合
     */
    public String queryById(String param);
    //通过ID查询一个对象
    public Object queryById(Long id,String entityName) throws Throwable;
    

    /**
     * 通过ID查询一个对象，返回的json是对象
     *
     * @param param 参数集合
     */
    public String queryEntityNameById(String param);

    /**
     * 为新增表单提供表结构
     *
     * @param param 参数集合
     * @return 表结构集合
     */
    public String queryForAddByEntityName(String param);

    /**
     * 单表修改
     *
     * @param param
     * @return 成功或失败判断
     */
    public String update(String param) throws Exception;

    /**
     * 单表新增
     *
     * @param param
     * @return 成功或失败判断
     */
    public String save(String param) throws Exception;
    
    /**
     * 批量新增或修改
     *
     * @param param
     * @return 成功或失败判断
     */
    public String saveOrUpdate(String param);

    /**
     * 通过实体名对字段表进行查询，查出可作为列表头的字段
     *
     * @param param
     * @return String Json数据
     */
    public String queryTable(String param);

    /**
     * 通过虚拟实体名对字段表进行查询，查出可作为列表头的字段
     *
     * @param param
     * @return String Json数据
     */
    public String queryView(String param);

    /**
     * 通过实体名查询数据记录，带分页
     *
     * @param param
     * @return
     */
    public String queryTableContent(String param);
    /**
     * 通过hql查询数据记录，带分页
     * @param hql
     * @param params
     * @return
     * @throws Exception
     */
    public String queryTableContentBySql(String sql,String params,String sqlEnd) throws Exception;
    
    
    public String queryTableContentByHql(String hql,String params,String sqlEnd) throws Exception;
    /**
	 * 通过拼装的sql查询数据
	 * @param sql
	 * @return
	 */
    public List querySqlList(String sql);
    
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
	public String queryTableContentSingle(String param);

    /**
     * 通过虚拟实体名查询数据记录，带分页
     *
     * @param param
     */
    public void queryViewContent(String param);

    /**
     * 通过实体名对字段表进行查询，查出可作为单字段查询的字段
     *
     * @param param
     * @return String Json数据
     */
    public String querySelectOne(String param);

    /**
     * 获取菜单树
     *
     * @param param
     * @return String Json数据
     */
    public String queryMenuTree(String param);

    /**
     * 获取树
     *
     * @param param
     * @return String Json数据
     */
    public String queryTree(String param);

    /**
     * 获取标准代码树
     *
     * @param param
     * @return String Json数据
     */
    public String queryBzdmTree(String param);

    /**
     * 通过id和实体名进行删除操作
     *
     * @param param
     * @return String Json数据
     */
    public String deleteByIds(String param)throws Exception;

    /**
     * 通过实体名对字段表进行查询，查出可作为编码的字段
     *
     * @param param
     * @return String Json数据
     */
    public String queryBM(String param);

    /**
     * 通过实体名和字段属性的值进行新增操作
     *
     * @param entityName
     * @param filedList  字段属性的值
     */
    public void save(String entityName, List filedList) throws Exception;

    /**
     * 查询导出列头
     *
     * @param entityName
     * @param type
     * @return
     * @author LJH
     * @throws Exception 
     */
    public List<TsStsx> queryExportTableHeader(String entityName, String fields) throws Exception;

    /**
     * 查询导出内容
     *
     * @param entityName
     * @param headers
     * @param params
     * @return
     * @author LJH
     */
    public List<String> queryExportTableContent(String entityName, List<TsStsx> headers, String params);

    
    /**
     *取得一个id
     * @param 
     */
	public String getId(String param);
    /**
     *保存一个对象
     * @param obj
     */
	public void insert(Object obj);
	
	/**
     *保存多个对象
     * @param obj
     */
	public void insert(List<Object> list) throws Exception;
	
    /**
     *取得一个id
     * @param 
     */
	public Long getId();
    /**
     * 通过action传入的参数解析为对应的条件参数,同时获取start,limit参数
     * @param maps
     * @return 参数和条件Map列表
     * @throws Exception 
     */
	public Map getSQLParams(Map maps) throws Exception;
    
    /**
     * 根据实体名、字段名和标准代码，查该字段名在编码表中的可选值
     * @param obj
     * @return
     */
    public List queryBmByEntityNameAndFieldName(String obj);
    
    /**
     * 根据多条件,批量更新数据
     * @param param
     * @throws Exception 
     */
    public void updateRecordsBySql(String param) throws Exception;
    
    /**
     * 根据当前节点查询上级节点对象
     * @param param
     * @return
     * @throws Exception 
     */
    public Map findParentObject(String param) throws Exception;
    
    /**
     * 记录业务日志 
     * @param operationTypeName  操作类型
     * @param content            操作内容
     */
	public void serviceLogger(String operationTypeName, String content) throws Exception;

	public TbJwPkzb getCurrentXnxqPkzb();
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map getDefXnxq(String params) throws Exception;
	
	public  String  queryXnxqId(String params) throws Exception;
	
	/**
	 * 根据编码表id，所属实体名，和属性，获取代码
	 * @param bmbId
	 * @param ssstm
	 * @param sx
	 * @return
	 */
	public Object getDmByIdAndStsx(Object bmbId,String ssstm,String sx);
	
	/**
	 * 根据编码表id，所属实体名，和属性，获取代码
	 * @param bmbId
	 * @param ssstm
	 * @param sx
	 * @return
	 */
	public String getDmByBmbidAndStSx(Long bmbId,String ssstm,String sx);
	
	/**
	 * 根据代码，所属实体名，属性，获取编码表的id
	 * @param dm
	 * @param ssstm
	 * @param sx
	 * @return
	 */
	public Long getBmbidByDmAndStSx(String dm,String ssstm,String sx);
	
	/**
	 * 根据代码，所属实体名，属性，获取编码表的id
	 * @param dm
	 * @param ssstm
	 * @param sx
	 * @return
	 */
	public Object getBmbidByDmAndStsx(String dm, String ssstm, String sx);
	
	
	/**
	 * 根据编码表id，所属实体名，和属性，获取名称
	 * @param bmbId
	 * @param ssstm
	 * @param sx
	 * @return
	 */
	public Object getMcByIdAndStsx(Object bmbId, String ssstm, String sx);
	/**
	 * 根据编码表id，所属实体名，和属性，获取名称
	 * @param bmbId
	 * @param ssstm
	 * @param sx
	 * @return
	 */
	public String getMcByBmbidAndStSx(Long bmbId,String ssstm,String sx);
	
	
	/**
	 * 根据编码表代码，所属实体名，和属性，获取名称
	 * @param dm
	 * @param ssstm
	 * @param sx
	 * @return
	 */
	public String getMcByDmAndStsx(String dm, String ssstm, String sx);
	
	/**
	 * 根据编码表代码，所属实体名，和属性，获取名称
	 * @param dm
	 * @param ssstm
	 * @param sx
	 * @return
	 */
	public String getMcByBmbdmAndStSx(String dm,String ssstm,String sx);

	/**
	 * 判断一个实体的属性，是否引用编码表
	 * @param ssstm
	 * @param sx
	 * @return
	 */
	public Boolean isBmField(String ssstm, String sx);
	
	/**
	 * 根据代码，所属实体名，属性，获取编码表list
	 * @param ssstm
	 * @param sx
	 * @return
	 */
	public List getBmbDataListByStmAndSx(String ssstm,String sx);
	
	/**
	 * 获取编码表数据
	 * @param str
	 * @return
	 */
	public Object getBmbDataList(String str);
	
	/**
	 * 通用组装树结构JSON
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String getTreeJson(String params) throws Exception;

	/**
	 * 查询标准代码通过DM或NAME
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String queryBzdmByDmOrName(String params) throws Exception;

	/**
	 * 查询结果记录
	 * @param params
	 * @return
	 */
	public List queryEntityList(String params);

	/**
	 * 根据代码获取ID
	 * @param entityName
	 * @param bzdm
	 * @param dm
	 * @return
	 */
	Long getIdByDm(String entityName, String bzdm, String dm);

	/**
	 * 
	 * @param object
	 */
	public void updateObj(Object object);

	/**
	 * 更新对象数据
	 * @param obj
	 * @param fieldnames
	 * @return
	 * @throws Exception
	 */
	public boolean updateEntityPartFields(Object obj, List<String> fieldnames)
			throws Exception;

	/**
	 * 更新对象数据
	 * @param obj
	 * @param fields
	 * @return
	 * @throws Exception
	 */
	public boolean updateEntityPartFields(Object obj, String[] fields)
			throws Exception;

	/**
	 * 更新对象数据
	 * @param obj
	 * @param fields 
	 * @return
	 * @throws Exception
	 */
	public boolean updateEntityPartFields(Object obj, String fields) throws Exception;
	
	/**
	 * 批量更新对象数据
	 * @param obj
	 * @param fields
	 * @return
	 * @throws Exception
	 */
	public boolean bathupdateEntityPartFields(List<Object> list, List<String> fields)
			throws Exception;
	//-------------------------------------------2013-05-23---------------------------------------
	/**
	 * 通过SQL查询数据记录　(高东杰--新增方法)
	 * @param sql 带有业务逻辑的ＳＱＬ
	 * @param params 前台传入的参数
	 * @param pageParam　分页实体
	 * @return　返回成功或失败的信息
	 * @throws Exception
	 */
	public String queryTableDataBySql(String sql, String params) throws Exception;
	/**
	 * 通过SQL查询数据记录别名 的方法　(王永太--新增方法)
	 * @param sql 带有业务逻辑的ＳＱＬ
	 * @param params 前台传入的参数
	 * @param pageParam　分页实体
	 * @return　返回成功或失败的信息
	 * @throws Exception
	 */
	public String queryTableDataBySqlWithAlias(String sql, String params) throws Exception;

	/**
	 * 通过SQL查询数据记录　(高东杰--新增方法)
	 * @param sql 带有业务逻辑的ＳＱＬ
	 * @param map　参数包括查询条件、高级查询条件、分页参数
	 * @param pageParam　分页实体
	 * @return　返回成功或失败的信息
	 * @throws Exception
	 */
	public String queryTableDataBySql(String sql, Map map) throws Exception;
	/**
	 * 通过实体类查询数据记录 (高东杰--新增方法)
	 * @param params　前台传入的参数
	 * @param pageParam　分页实体
	 * @return　返回成功或失败的信息
	 * @throws Exception
	 */
	public String queryTableDataByHql(String params) throws Exception;
	/**
	 * 通过实体类查询数据记录 (高东杰--新增方法)
	 * @param map　参数包括实体类名、查询条件、高级查询条件、分页参数
	 * @param pageParam　分页实体
	 * @return　返回成功或失败的信息
	 * @throws Exception
	 */
	public String queryTableDataByHql(Map map) throws Exception;
	/**
	 * 根据ids删除记录
	 * @param ids　String
	 * @param entity 实体类
	 * @return　删除的记录数
	 */
	public void delete(String ids, Class entity);
	/**
	 * 根据传入的ids列表删除记录
	 * @param list　ids<list>
	 * @param entity 实体类
	 * @return　删除的记录数据
	 */
	public void delete(List<Object> list, Class entity);
	/**
	 * 根据传入的ids列表删除记录
	 * @param str[]　ids数组
	 * @param entity 实体类
	 * @return　删除的记录数据
	 */
	public void delete(String[] str, Class entity);
	/**
	 * 根据教学组织机构ID获取班级信息
	 * @param jxzzjgId
	 * @return
	 */
	public List<TbXxzyBjxxb> getBjList(Long jxzzjgId);
	/**
	 * 根据SqL查询List<Map>
	 * @param sql
	 * @return list<Map>
	 */
	public List<Map> queryListMapBySql(String sql);
	
	/**
	 * 根据SQL查询数据并返回List<Map>,其中Map的key值为小写
	 * @param sqlString
	 * return List<Map>
	 */
	public List<Map> queryListMapInLowerKeyBySql(String sql);
	
	/**
	 * 根据实体名和字段中文名获取代码实体名和代码类型
	 * @param entityName
	 * @param fieldZwm
	 * @return
	 */
	public TsStsx getBzdmByStmAndSxZwm(String entityName,String fieldZwm);
	/**
	 * 根据传入的值和代码实体名和代码类型获取代码ID
	 * @param value
	 * @param map
	 * @param dm
	 * @return
	 */
	public Long getBzdmIdByValueOrDmAndMap(String value,String dm,TsStsx stsx);

	//--------------------------------------------------------------------------------------------

	/**
	 * 查询sql中的记录个数
	 * @param sql
	 * @return
	 */
	public Long queryForLongBySql(String sql);
	/**
	 * 根据实体名获取实体属性列表 
	 * @param ssstm
	 * @return
	 */
	public List getStsxByName(String ssstm);
	/**
	 * 根据传入的教学组织机构条件，获取所包含所有班级
	 * @param params
	 * @return
	 */
	public List queryBjxxByJxzzjg(String params);
	
	/**
	 * 根据SQL和参数执行查询方法
	 * @param sql
	 * @param obj
	 * @param sqlEnd
	 * @param isAlias
	 * @return
	 * @throws Exception
	 */
	public String queryTableContentBySql(String sql, JSONObject obj, String sqlEnd,Boolean isAlias)
			throws Exception;
	/**
	 * 根据教学组织机构ＩＤ获取班级组织机构树
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String queryJxzzzjgAndBigBj(String params) throws Exception;
	/**
	 * 教学组织机构和班级结构树
	 * @param params
	 * @return
	 */
	public String queryJxzzjgAddBjxxMenuTree(String params);

	public String getBzdmByTreemenu(String params) throws Exception;
	/**
	 * 
	 * @param params
	 * @return
	 */
	public String queryBjxxByJxzzjgId(String params);
	
	/**
	 * 
	* @Title: getStuYjbyNf 
	* @Description: 获取学生预计毕业年份
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public String getStuYjbySj(TbXjdaXjxx xs)throws Throwable;
	/**
	 * 获取学制年限
	 * @return
	 */
	public String getXzNx(Long xzId)throws Throwable;
	/**
	 * 获取入学年级标准代码
	 * @param params
	 * @return
	 */
	public String getRxnjList(String params);
}
