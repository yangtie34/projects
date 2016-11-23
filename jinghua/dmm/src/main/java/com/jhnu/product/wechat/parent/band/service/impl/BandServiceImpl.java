package com.jhnu.product.wechat.parent.band.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.jhnu.product.common.stu.entity.Student;
import com.jhnu.product.common.stu.service.StuService;
import com.jhnu.product.four.common.entity.ResultBean;
import com.jhnu.product.wechat.parent.band.dao.BandDao;
import com.jhnu.product.wechat.parent.band.entity.Band;
import com.jhnu.product.wechat.parent.band.service.BandService;
import com.jhnu.product.wechat.parent.band.service.WechatPwdService;
import com.jhnu.system.permiss.service.PasswordHelper;
import com.jhnu.util.common.HttpUtil;

@Service("bandService")
public class BandServiceImpl implements BandService{
	
	@Autowired 
	private BandDao bandDao;
	@Autowired
	private StuService stuService;
	@Autowired
	private WechatPwdService wechatPwdService;
	@Autowired
	private PasswordHelper passwordHelper;
	
	@Override
	@Transactional
	public ResultBean addBandByPassword(Band band,String password) {
		ResultBean rb=new ResultBean();
		if(!StringUtils.isEmpty(band)&&!StringUtils.isEmpty(band.getWeChat_no())&&!StringUtils.isEmpty(band.getStu_idno())){
			Student stu=stuService.getStudentInfoByIdno(band.getStu_idno());
			if(stu!=null&&!StringUtils.isEmpty(stu.getNo_())){
				boolean flag=wechatPwdService.checkWeChatPassword(stu.getNo_(), password);
				if(flag) {
					band.setStu_id(stu.getNo_());
					band.setStu_name(stu.getName_());
					int end=addorUpdateBand(band);
					if(end==0){
						rb.setTrue(false);
						rb.setName("绑定失败，请重试");
						return rb;
					}else{
						rb.setTrue(true);
						return rb;
					}
				}
			}
			rb.setTrue(false);
			rb.setName("用户名密码不正确");
			return rb;
		}else{
			rb.setTrue(false);
			rb.setName("请正确填写");
			return rb;
		}
	}

	@Override
	@Transactional
	public ResultBean removeBand(Band band) {
		ResultBean rb=new ResultBean();
		if(!StringUtils.isEmpty(band)&&!StringUtils.isEmpty(band.getStu_id())){
			bandDao.removeBandById(band.getStu_id());
			rb.setTrue(true);
		}else{
			rb.setTrue(false);
			rb.setName("请正确填写");
		}
		return rb;
	}

	@Override
	public List<Band> getBandByThis(Band band) {
		return bandDao.getBandByThis(band);
	}

	@Override
	public String getStuIdByWechatCode(String code) {
		Band band=new Band();
		JSONObject result=null;
		result = HttpUtil.getWechatOpenidByCode(code);
		if(result.get("openid")==null){
			return "error";
		}
		String openId=result.get("openid").toString();
		band.setWeChat_no(openId);
		List<Band> bands=getBandByThis(band);
		if(bands.size()>0){
			return bands.get(0).getStu_id();
		}else{
			return "";
		}
	}

	@Transactional
	private int addorUpdateBand(Band band){
		Band ba=new Band();
		ba.setWeChat_no(band.getWeChat_no());
		List<Band> bands= getBandByThis(ba);
		for (int i = 0; i < bands.size(); i++) {
			removeBand(bands.get(i));
		}
		return bandDao.addBand(band);
	}
	
	@Override
	@Transactional
	public ResultBean addBand(Band band) {
		ResultBean rb=new ResultBean();
		if(!StringUtils.isEmpty(band)&&!StringUtils.isEmpty(band.getWeChat_no())&&!StringUtils.isEmpty(band.getStu_idno())){
			Student stu=stuService.getStudentInfoByIdno(band.getStu_idno());
			if(stu!=null&&!StringUtils.isEmpty(stu.getNo_())){
				band.setStu_id(stu.getNo_());
				band.setStu_name(stu.getName_());
				int end=addorUpdateBand(band);
				if(end==0){
					rb.setTrue(false);
					rb.setName("绑定失败，请重试");
					return rb;
				}else{
					rb.setTrue(true);
					return rb;
				}
			}
			rb.setTrue(false);
			rb.setName("用户名密码不正确");
			return rb;
		}else{
			rb.setTrue(false);
			rb.setName("请正确填写");
			return rb;
		}
	}

	@Override
	public void addVisitLogging(String visitMenu, int is_wechat,
			String visitDate, String username) {
		bandDao.addVisitLogging(visitMenu,is_wechat,visitDate,username);
	}
	
}
