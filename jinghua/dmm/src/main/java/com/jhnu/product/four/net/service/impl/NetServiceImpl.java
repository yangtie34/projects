package com.jhnu.product.four.net.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.product.four.common.entity.ResultBean;
import com.jhnu.product.four.net.dao.NetDao;
import com.jhnu.product.four.net.service.NetService;

@Service("netService")  
public class NetServiceImpl implements NetService{
	@Autowired
	private NetDao netDao;
	private static final Logger logger = Logger.getLogger(NetServiceImpl.class);
	@Override
	public void saveAllNetMax(){
		List<Map<String,Object>> max=netDao.getMax();
		netDao.saveNetMax(max);
	}
	@Override
    public void saveAllNetSum(){
		List<Map<String,Object>> sum=netDao.getSum();
		  netDao.saveNetSum(sum);
     }
	@Override
    public void saveAllNetJcsxsj(){
		List<Map<String,Object>> jcsxsj=netDao.getJcsxsj();
		  netDao.saveNetJcsxsj(jcsxsj);
}
	@Override
	public ResultBean getNetMaxTime(String id) {
		ResultBean max=new ResultBean();
		max.setName("getNetMaxTime");
		max.getData().put("getNetMaxTime", netDao.getNetMaxTime(id));
		return max;
	}
	@Override
	public ResultBean getNetSumTime(String id) {
		ResultBean sum=new ResultBean();
		sum.setName("getNetSumTime");
		sum.getData().put("getNetSumTime", netDao.getNetSumTime(id));
		return sum;
	}
	@Override
	public ResultBean getNetBq(String id) {
		ResultBean bq=new ResultBean();
		bq.setName("getNetBq");
		bq.getData().put("getNetBq", netDao.getNetBq(id));
		return bq;
	}
	@Override
	public ResultBean getNetJcOnlineTime(String id) {
		ResultBean jc=new ResultBean();
		jc.setName("getNetJcOnlineTime");
		jc.getData().put("getNetJcOnlineTime", netDao.getNetJcOnlineTime(id));
		return jc;
	}
}
