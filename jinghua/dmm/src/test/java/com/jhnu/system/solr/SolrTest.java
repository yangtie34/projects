package com.jhnu.system.solr;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.solr.common.SolrDocumentList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jhnu.framework.solr.entity.SolrQueryEntity;
import com.jhnu.framework.solr.service.MenuSolrService;
import com.jhnu.spring.SpringTest;
import com.jhnu.system.common.page.Page;
import com.jhnu.system.menu.entity.Menu;

public class SolrTest extends SpringTest{
	
	@Resource
	private MenuSolrService menuSolrService;
	
	
	@Before
	public void testBefore(){
		System.out.println("==先执行我==");
	}
	
//	@Test
	public void testAdd(){
		System.out.println("==========添加开始==========");
		Menu menu=new Menu();
		menu.setId(5l);
		menu.setName_("学生统计分析测试");
		menu.setDescription("这是一个通过学生进行的分析测试系统");
		menu.setKeyWord("学生 统计 分析");
		menuSolrService.addOrUpdateMenuDoc(menu, null);
		
		menu.setId(6l);
		menu.setName_("学生与科研统计分析测试");
		menu.setDescription("这是一个通过学生与科研进行的分析测试系统");
		menu.setKeyWord("学生 科研 统计 分析");
		menuSolrService.addOrUpdateMenuDoc(menu, null);
		
		menu.setId(7l);
		menu.setName_("科研统计分析测试");
		menu.setDescription("这是一个通过科研进行的分析测试系统");
		menu.setKeyWord("科研 统计 分析");
		menuSolrService.addOrUpdateMenuDoc(menu, null);
		
		menu.setId(8l);
		menu.setName_("老师与科研统计分析测试");
		menu.setDescription("这是一个通过老师与科研进行的分析测试系统");
		menu.setKeyWord("老师 科研 统计 分析");
		menuSolrService.addOrUpdateMenuDoc(menu, null);
		
		menu.setId(9l);
		menu.setName_("学生与老师统计分析测试");
		menu.setDescription("这是一个通过学生与老师进行的分析测试系统");
		menu.setKeyWord("学生 老师 统计 分析");
		menuSolrService.addOrUpdateMenuDoc(menu, null);
		
		menu.setId(10l);
		menu.setName_("学生、老师、科研统计分析测试");
		menu.setDescription("这是一个通过学生、老师、科研进行的分析测试系统");
		menu.setKeyWord("学生 老师 科研 统计 分析");
		menuSolrService.addOrUpdateMenuDoc(menu, null);
		System.out.println("==========添加结束==========");
	}
	
	@Test
	public void testQuery(){
		System.out.println("==========查询开始==========");
		SolrQueryEntity solrQueryEntity=new SolrQueryEntity();
		solrQueryEntity.setKeyWords("学生  统计");
		List<String> queryTypes=new ArrayList<String>();
		queryTypes.add("name");
		queryTypes.add("keyword");
		queryTypes.add("desc");
		solrQueryEntity.setQueryType(queryTypes);
		Page page =new Page();
		page.setCurrentPage(1);
		page.setNumPerPage(2); //每页显示数据数
		page.setStartIndex(); //设置起始数量
		page=menuSolrService.queryMenuDoc(solrQueryEntity,1l,page);
		SolrDocumentList list=page.getSolrList();
		System.out.println("总计：" + list.getNumFound() + "条，本批次:" + list.size() + "条");
		for (int i = 0; i < list.size(); i++) {
			System.out.println("id:"+list.get(i).get("id")+",name："+list.get(i).get("name")
					+",keyword："+list.get(i).get("keyword")+",desc："+list.get(i).get("desc"));
		}
		System.out.println("==========查询结束==========");
	}
//	@Test
	public void testDelete(){
		menuSolrService.delMenuDocByChanPinId("1");
	}
	@After
	public void testAter(){
		System.out.println("==我最后执行==");
	}
}
