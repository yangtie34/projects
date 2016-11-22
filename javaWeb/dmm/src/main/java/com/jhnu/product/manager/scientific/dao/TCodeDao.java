package com.jhnu.product.manager.scientific.dao;

import java.util.List;
import java.util.Map;

import com.jhnu.product.manager.scientific.entity.TCode;
public interface TCodeDao {	
	/**
	 * 获取code表code，mc
	 * @return
	 */
	public List<TCode> getType(String kylx);
	/**
	 * 插入一条code信息
	 * @param name
	 * @param lb
	 * @return
	 */
	public boolean insertCode(List<TCode> tcode);
	String getCodeType(String key);

}
