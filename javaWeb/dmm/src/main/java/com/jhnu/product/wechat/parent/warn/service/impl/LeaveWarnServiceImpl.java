package com.jhnu.product.wechat.parent.warn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhnu.enums.WarnTypeEnum;
import com.jhnu.product.wechat.parent.warn.dao.LeaveWarnDao;
import com.jhnu.product.wechat.parent.warn.service.LeaveWarnService;
import com.jhnu.product.wechat.parent.warn.service.WechatWarnService;

@Service("leaveWarnService")
public class LeaveWarnServiceImpl implements LeaveWarnService{


	@Autowired
	private LeaveWarnDao leaveWarnDao;
	@Autowired
	private WechatWarnService wechatWarnService;

	
	@Override
	@Transactional
	public void saveLeaveWarn(String exe_time) {
		String last_time = wechatWarnService.getLastTimeByTypeCode(WarnTypeEnum.QJYJ.getCode());
		wechatWarnService.saveWechatWarn(leaveWarnDao.getLeaveWarn(exe_time, last_time));
	}


	@Override
	@Transactional
	public void saveCancelLeaveWarn(String exe_time) {
		String last_time = wechatWarnService.getLastTimeByTypeCode(WarnTypeEnum.QJYJ.getCode());
		wechatWarnService.saveWechatWarn(leaveWarnDao.getCancelLeaveWarn(exe_time, last_time));
	}


	@Override
	@Transactional
	public void saveKeepLeaveWarn(String exe_time) {
		leaveWarnDao.getKeepLeaveWarn(exe_time);
		leaveWarnDao.informKeepLeave();
	}
	
}
