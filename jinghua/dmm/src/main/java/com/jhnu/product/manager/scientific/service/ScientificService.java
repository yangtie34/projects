package com.jhnu.product.manager.scientific.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.jhnu.product.manager.scientific.entity.APage;
import com.jhnu.product.manager.scientific.entity.TResKylrTemp;


public interface ScientificService {
	/**
	 * 获取科研表类别名称
	 * @return
	 */
	public List<Map<String,String>> getTablesName();
	/**
	 * 根据返回值获取科研表字段名称
	 * @return
	 */
	public Map<String,Map> getCloumn(String clas);
	/**
	 * 插入科研数据
	 * @return
	 */
	public boolean insert(String map);
	/**
	 * 删除科研数据
	 * @return
	 */
	public boolean delete(String map);
	/**
	 * 更新科研数据
	 * @return
	 */
	public boolean update(String map);
	/**
	 * 查看科研数据
	 * @return
	 */
	public List view(String clas, String flag);
	/**
	 * 根据条件查询数据
	 * @return
	 */
	public Map findOut(String str);
	String viewFindName(String str);
	HSSFWorkbook getXls(String titles, String sheetName);
	List<Map> readXls(MultipartFile file);
	
}
