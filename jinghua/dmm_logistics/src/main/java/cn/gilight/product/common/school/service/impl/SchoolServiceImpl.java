package cn.gilight.product.common.school.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gilight.framework.entity.ResultBean;
import cn.gilight.framework.uitl.common.UserUtil;
import cn.gilight.product.common.school.dao.SchoolDao;
import cn.gilight.product.common.school.service.SchoolService;


@Service("schoolService")
public class SchoolServiceImpl implements SchoolService{
	@Autowired
	private SchoolDao schoolDao;

	@Override
	public String getStartSchool(String year, String term) {
		return schoolDao.getStartSchool(year, term);
	}

	@Override
	public ResultBean getTest() {
		ResultBean tb=new ResultBean();
		System.out.println(UserUtil.getCasLoginName());
		tb.setTrue(true);
		return tb;
	}
	

}
