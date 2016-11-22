package com.jhnu.product.wechat.parent.warn.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.product.wechat.parent.warn.dao.WechatWarnDao;
import com.jhnu.product.wechat.parent.warn.entity.WechatWarn;
import com.jhnu.product.wechat.parent.warn.service.WechatWarnService;
import com.jhnu.system.common.page.Page;
import com.jhnu.util.common.DateUtils;

@Service("wechatWarnService")
public class WechatWarnServiceImpl implements WechatWarnService{
	@Autowired
	private WechatWarnDao wechatWarnDao;

	@Override
	public void saveWechatWarn(List<WechatWarn> warns) {
		wechatWarnDao.saveWechatWarn(warns);
	}

	@Override
	public Page getWechatWarns(String stuId, Page page) {
		return wechatWarnDao.getWechatWarns(stuId, page);
	}

	@Override
	public void readWechatWarns(List<WechatWarn> warns) {
		wechatWarnDao.readWechatWarns(warns);
	}

	@Override
	public int countNoReadWarns(String stuId) {
		return wechatWarnDao.countNoReadWarns(stuId);
	}

	@Override
	public String getLastTimeByTypeCode(String warnTypeCode) {
		String lastTime=wechatWarnDao.getLastTimeByTypeCode(warnTypeCode);
		if(lastTime==null || "".equals(lastTime) || "null".equals(lastTime)){
			return DateUtils.SDF.format(new Date());
		}
		return lastTime;
	}

}
