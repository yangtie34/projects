package cn.gilight.personal.student.concat.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gilight.framework.uitl.product.EduUtils;
import cn.gilight.personal.student.concat.dao.ConcatDao;
import cn.gilight.personal.student.concat.service.ConcatService;

@Service("concatService")
public class ConcatServiceImpl implements ConcatService{
	
	@Autowired
	private ConcatDao concatDao;

	@Override
	public List<Map<String, Object>> getConcat(String stu_id, String param) {
		String[] xnxq = EduUtils.getSchoolYearTerm(new Date());
		return concatDao.getConcat(stu_id, xnxq[0], xnxq[1], param);
	}

}
