package com.jhnu.product.wechat.parent.warn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhnu.enums.WarnTypeEnum;
import com.jhnu.product.wechat.parent.warn.dao.CheckWarnDao;
import com.jhnu.product.wechat.parent.warn.service.CheckWarnService;
import com.jhnu.product.wechat.parent.warn.service.WechatWarnService;

@Service("checkWarnService")
public class CheckWarnServiceImpl implements CheckWarnService{
	
	@Autowired
	private CheckWarnDao checkWarnDao;
	@Autowired
	private WechatWarnService wechatWarnService;

	@Override
	@Transactional
	public void saveCheckWarn(String exe_time) {
		String last_time = wechatWarnService.getLastTimeByTypeCode(WarnTypeEnum.KQYJ.getCode());
		wechatWarnService.saveWechatWarn(checkWarnDao.getCheckWarn(last_time,exe_time));
	}

}
