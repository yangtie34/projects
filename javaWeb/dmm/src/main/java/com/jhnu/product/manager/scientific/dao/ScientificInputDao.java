package com.jhnu.product.manager.scientific.dao;

import java.util.List;
import java.util.Map;

import com.jhnu.product.manager.scientific.entity.APage;
import com.jhnu.product.manager.scientific.entity.Dept;
import com.jhnu.product.manager.scientific.entity.FindList;
import com.jhnu.product.manager.scientific.entity.TCodeSubject;
import com.jhnu.product.manager.scientific.entity.TResKylrTemp;

public interface ScientificInputDao  {
	///////////////////录入编辑
	/**
	 * 获取学校部门
	 * @return
	 */
	public List<Dept> getDept();
	/**
	 * 获取学科门类
	 * @return
	 */
	public List<TCodeSubject> getXKML();
	/**
	 * 根据名字及类别获取科研作者信息
	 * @param name
	 * @param rylb
	 * @return
	 */
	public List getAuthorInfo(String name,String lb);
	
	/**
	 * 插入对象信息
	 * @param name
	 * @param lb
	 * @return
	 */
	public boolean insert(List list);
	/**
	 * 删除对象信息
	 * @param name
	 * @param lb
	 * @return
	 */
	public boolean delete(List list);
	/**
	 * 更新对象信息
	 * @param name
	 * @param lb
	 * @return
	 */
	public boolean update(List list);
	///////////////////校验审核
	/**
	 * 根据用户id或部门id获取录入科研信息
	 * @param name
	 * @param rylb
	 * @return
	 */
	public APage getKyInfo(APage apage,Object ky,TResKylrTemp temp,Map authMap);
	
	/**
	 * 根据ids及科研类别获取科研录入未审核信息
	 * @param dept_id
	 * @param kylb
	 * @return
	 */
	public List getUnaudited (String ids,Object ky);//id='a','b','c','d','d'
	/**
	 * 根据科研id及科研作者类别获取科研作者信息
	 * @param name
	 * @param rylb
	 * @return
	 */
	public List<Object> getAuthorInfo(String kyid,Object kyzz);
	/**
	 * 根据科研对象得到录入temp科研类别代码
	 * @param name
	 * @param rylb
	 * @return
	 */
	public String getTempKyCode(Object ky);
	List<FindList> getkyId(String name, String lb);
	String selectAId();
	void excutexmcc(String xmid, String kyid, String clas);
	String selectxmccId(String kyid, String clas);
	String getTheName(String id, String lb);
}
