package com.jhnu.product.wechat.parent.warn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhnu.enums.WarnTypeEnum;
import com.jhnu.product.wechat.parent.warn.dao.PunishWarnDao;
import com.jhnu.product.wechat.parent.warn.service.PunishWarnService;
import com.jhnu.product.wechat.parent.warn.service.WechatWarnService;

@Service("punishWarnService")
public class PunishWarnServiceImpl implements PunishWarnService{


	@Autowired
	private PunishWarnDao punishWarnDao;
	@Autowired
	private WechatWarnService wechatWarnService;

	
	@Override
	@Transactional
	public void savePunishWarn(String exe_time) {
		String last_time = wechatWarnService.getLastTimeByTypeCode(WarnTypeEnum.CFYJ.getCode());
		wechatWarnService.saveWechatWarn(punishWarnDao.getPunishWarn(exe_time, last_time));
	}

}
