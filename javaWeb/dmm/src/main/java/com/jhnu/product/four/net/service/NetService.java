package com.jhnu.product.four.net.service;


import com.jhnu.product.four.common.entity.ResultBean;

public interface NetService {

	public void saveAllNetMax();

	public void saveAllNetSum();

	public void saveAllNetJcsxsj();

	public ResultBean getNetMaxTime(String id);

	public ResultBean getNetSumTime(String id);

	public ResultBean getNetJcOnlineTime(String id);

	public ResultBean getNetBq(String id);

}
