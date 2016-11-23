package cn.gilight.product.equipment.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhnu.syspermiss.permiss.entity.JobResultBean;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.product.equipment.dao.JobEquipmentDao;
import cn.gilight.product.equipment.service.JobEquipmentService;

@Service("jobEquipmentService")
public class JobEquipmentServiceImpl implements JobEquipmentService{

	@Autowired
	private JobEquipmentDao jobEquipmentDao;
	
	@Override
	@Transactional
	public JobResultBean initEquipmentYear() {
		JobResultBean jrb=new JobResultBean();
		int startYear=1990;
		int nowYear=Integer.parseInt(DateUtils.getNowYear());
		for (int i = startYear; i < nowYear; i++) {
			jobEquipmentDao.updateEquipmentYear(i);
		}
		jrb.setIsTrue(true);
		jrb.setMsg("执行成功");
		return jrb;
	}

	@Override
	@Transactional
	public JobResultBean equipmentYear() {
		JobResultBean jrb=new JobResultBean();
		jobEquipmentDao.updateEquipmentYear(Integer.parseInt(DateUtils.YEAR.format(new Date())));
		jrb.setIsTrue(true);
		jrb.setMsg("执行成功");
		return jrb;
	}

}
