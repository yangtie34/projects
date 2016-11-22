package com.jhnu.product.wechat.parent.band.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhnu.product.common.stu.entity.Student;
import com.jhnu.product.four.common.entity.ResultBean;
import com.jhnu.product.wechat.parent.band.dao.WechatPwdDao;
import com.jhnu.product.wechat.parent.band.entity.WechatPwd;
import com.jhnu.product.wechat.parent.band.service.WechatPwdService;
import com.jhnu.system.permiss.service.PasswordHelper;

@Service("wechatPwdService")
public class WechatPwdServiceImpl implements WechatPwdService{
	@Autowired
	private WechatPwdDao wechatPwdDao;
	@Autowired
	private PasswordHelper passwordHelper;
	
	@Override
	public boolean checkWeChatPassword(String stuId, String password) {
		WechatPwd wp=getOneById(stuId);
		if(wp!=null &&wp.getPwd() !=null){
			String oldPwd=wp.getPwd();
	        String newpwd=passwordHelper.simpleEncryptPassword(stuId,password);
	        if(oldPwd.equals(newpwd)){
	        	return true;
	        }
		}
		return false;
	}

	@Override
	public WechatPwd getOneById(String stuId) {
		return wechatPwdDao.getOneById(stuId);
	}

	@Override
	@Transactional
	public void changeWeChatPassword(WechatPwd pwd) {
		String password=passwordHelper.simpleEncryptPassword(pwd.getStu_id(), pwd.getPwd());
		pwd.setPwd(password);
		wechatPwdDao.changeWeChatPassword(pwd);
	}

	@Override
	@Transactional
	public ResultBean resetWechatPassword(WechatPwd pwd) {
		ResultBean rb=new ResultBean();
		String password=passwordHelper.simpleEncryptPassword(pwd.getStu_id(),pwd.getPwd() );
		pwd.setPwd(password);
		int result=wechatPwdDao.resetWeChatPassword(pwd);
		if(result>0){
			rb.setTrue(true);
			rb.setName("重置成功");
		}else{
			rb.setTrue(false);
			rb.setName("预留手机号或身份证号错误");
		}
		return rb;
	}

	@Override
	public boolean checkWechatPhone(String stuId, String phone) {
		String oldphone=getOneById(stuId).getPhone_no();
        if(oldphone.equals(phone)){
        	return true;
        }else{
        	return false;
        }
	}
	
	
	@Override
	public List<Student> getStusInSchoolNotInWechat() {
		return wechatPwdDao.getStusInSchoolNOdtInWechat();
	}
	
	@Override
	public void addWechatPasswords(List<WechatPwd> pwds) {
		wechatPwdDao.addWechatPasswords(pwds);	
	}

	@Override
	public boolean checkPwdIsChange(String stuId) {
		int n = wechatPwdDao.getOneById(stuId).getIs_change();
		boolean b = false;
		if(n == 1){
			b = true;
		}
		return b;
	}
}
