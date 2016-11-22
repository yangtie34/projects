package com.jhkj.mosdc.framework.message.dao;

import java.util.List;

import com.jhkj.mosdc.framework.message.po.TsMsgSuggest;

public interface SuggestDao {

	public Boolean addSuggest(TsMsgSuggest suggest) throws Exception;
	
	public Boolean updateSuggest(TsMsgSuggest suggest);
	
	public Boolean deleteSuggest(String ids);
	
	public List<TsMsgSuggest> queryMineSuggest(TsMsgSuggest suggest,int start,int limit);
	
	public List<TsMsgSuggest> queryAllSuggest(TsMsgSuggest suggest,int start,int limit);
}
