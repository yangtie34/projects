package com.jhnu.framework.solr.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.common.SolrDocumentList;


public class SolrQueryEntity {

	private String keyWords;//用户输入的查询关键字
	private List<String> queryType=new ArrayList<String>(0);//查询类型
	SolrDocumentList solrList=new SolrDocumentList(); 
	 
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	public List<String> getQueryType() {
		return queryType;
	}
	public void setQueryType(List<String> queryType) {
		this.queryType = queryType;
	}
	public SolrDocumentList getSolrList() {
		return solrList;
	}
	public void setSolrList(SolrDocumentList solrList) {
		this.solrList = solrList;
	}
	
}
