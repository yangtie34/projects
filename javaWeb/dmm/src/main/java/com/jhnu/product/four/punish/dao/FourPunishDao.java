package com.jhnu.product.four.punish.dao;

import java.util.List;
import java.util.Map;

public interface FourPunishDao {
	
	
	public List<Map<String,Object>> getPunishMsg();
	
	public void savePunishLog(List<Map<String,Object>> list);
	
	
	public List<Map<String,Object>> getPunishMsg(String Id);
    
}
