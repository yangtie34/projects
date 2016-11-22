package com.jhnu.framework.solr.service;


import java.util.Map;

import org.apache.solr.common.SolrDocument;

import com.jhnu.framework.solr.entity.SolrQueryEntity;
import com.jhnu.system.common.page.Page;
import com.jhnu.system.menu.entity.Menu;

public interface MenuSolrService {

	/**
	 * 添加或更新 菜单索引 doc
	 * updateFiledMap 如果是null则根据Menu t 创建或更新索引
	 * updateFiledMap!=null，则从搜索引擎中取出对应doc,更新updateFiledMap里的filed字段
	 * @param t
	 * @return
	 * @throws Exception
	 */
	boolean addOrUpdateMenuDoc(Menu t,Map<String,Object> updateFiledMap);

	/**
	 * 
	 * 根据用户输入的内容 搜索数据
	 * @param
	 * SolrQueryEntity attr:
	 *             keyWords;//用户输入的查询关键字
	               queryType;//查询类型
	 * @return
	 */
	Page queryMenuDoc(SolrQueryEntity solrQueryEntity,Long userId,Page page);

	/**
	 * 根据 菜单id 删除菜单对应的doc
	 * @param menuId
	 * @return
	 * @throws Exception
	 */
	boolean delMenuDocByChanPinId(String menuId);

	/**
	 * 根据菜单id 返回单个doc文档
	 * @param id
	 * @return
	 */
	SolrDocument getMenuDocById(String id);

}

