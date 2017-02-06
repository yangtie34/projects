package cn.gilight.business.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import cn.gilight.business.model.TCode;

/**
 * 业务service
 * 
 * @author xuebl
 * @date 2016年5月3日 下午12:31:07
 */
public interface BusinessService {

	/**
	 * 获取学校ID
	 * @return String
	 */
	public String getSchoolId();

	/**
	 * 获取学校 name
	 * @return String
	 */
	public String getSchoolName();

	/**
	 * 标准代码
	 * @param codeType 代码类型
	 * @return List<TCode>
	 */
	public List<TCode> queryBzdmList(String codeType);

	/**
	 * 标准代码
	 * @param codeType 代码类型
	 * @param codes 代码（null查询所有）
	 * @return List<TCode>
	 */
	public List<TCode> queryBzdmList(String codeType, String codes);
	
	/**
	 * 标准代码
	 * @param codeType 代码类型
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryBzdmListMap(String codeType);

	/**
	 * 标准代码-名称
	 * @param codeType 代码类型
	 * @param codes 代码（null查询所有）
	 * @return List<String> [name1, name2]
	 */
	public List<String> queryBzdmNameList(String codeType, String codes);

	/**
	 * 标准代码-代码
	 * @param codeType 代码类型
	 * @return List<String> [code1, code2]
	 */
	public List<String> queryBzdmCodeList(String codeType);
	
	/**
	 * 标准代码-学工口-学生学历标准代码（本/专、本、专）
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryBzdmStuEducationList();

	/**
	 * 标准代码-学年（默认近5个学年）
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryBzdmSchoolYear();

	/**
	 * 标准代码-学年（表中实际学年数据）
	 * @param table 表名
	 * @param column_schoolYear 列名
	 */
	public List<Map<String, Object>> queryBzdmSchoolYear(String table, String column_schoolYear);
	
	/**
	 * 学科门类代码
	 * @param column_schoolYear 列名
	 */
	public List<Map<String, Object>> queryCodeSubject();

	/** 
	* @Description: 根据节点id和页面标识符获取行政组织机构树的数据权限sql
	* @param deptId 行政组织机构树节点
	* @param shiroTag 页面标识符
	* @return: String 所拥有的行政组织机构数据权限id拼接的字符串
	*/
	public String getDeptDataPermissIdsQuerySql(String deptId,String shiroTag);
	
	/** 
	* @Description: 获取权限配置的行政组织机构树
	* @param shiroTag 页面标识符
	* @return: JSONObject 行政组织机构树权限
	*/
	public JSONObject getDeptDataPermissTree(String shiroTag);

	/** 
	 * @Description: 获取权限配置的教学组织机构树
	 * @param shiroTag 页面标识符
	 * @return: JSONObject 行政组织机构树权限
	 */
	public JSONObject getTeachDeptDataPermissTree(String shiroTag);
	
	/** 
	* @Description: 根据节点id和页面标识符获取教学组织机构树的数据权限sql
	* @param deptId 行政组织机构树节点
	* @param shiroTag 页面标识符
	* @return: String 所拥有的行政组织机构数据权限id拼接的字符串
	*/
	String getTeachDeptDataPermissIdsQuerySql(String deptId, String shiroTag);
	
	/** 
	* @Description: 获取修改密码系统的地址
	* @return: String
	*/
	public String queryChangePasswdSystemUrl();
}