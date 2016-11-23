package com.jhnu.product.wechat.parent.band.dao;

import java.util.List;

import com.jhnu.product.wechat.parent.band.entity.Band;

public interface BandDao {

	/**
	 * 添加一条绑定信息
	 * @param band
	 * @return
	 */
	public int addBand(Band band);
	
	
	/**
	 * 解除一条绑定信息
	 * @param id 学生ID
	 * @return
	 */
	public int removeBandById(String stuId);
	
	
	/**
	 * 通过参数信息，查找符合的绑定信息集合
	 * @param band
	 * @return
	 */
	public List<Band> getBandByThis(Band band);


	public void addVisitLogging(String visitMenu, int is_wechat,
			String visitDate, String username);
	
	
}
