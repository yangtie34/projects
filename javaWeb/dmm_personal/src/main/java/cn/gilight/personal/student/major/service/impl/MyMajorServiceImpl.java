package cn.gilight.personal.student.major.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.product.EduUtils;
import cn.gilight.personal.student.major.dao.MyMajorDao;
import cn.gilight.personal.student.major.service.MyMajorService;

@Service("myMajorService")
public class MyMajorServiceImpl implements MyMajorService{
	
	@Autowired
	private MyMajorDao myMajorDao;

	@Override
	public Map<String, Object> getMajor(String stu_id) {
		return myMajorDao.getMajor(stu_id);
		
	}

	@Override
	public List<Map<String, Object>> getCourse(String stu_id) {
		String nowDate = DateUtils.getNowDate();
		String[] xnxq = EduUtils.getSchoolYearTerm(DateUtils.string2Date(nowDate));
		return myMajorDao.getCourse(stu_id,xnxq[0],xnxq[1]);
	}

	@Override
	public List<Map<String, Object>> getCourseScore(String stu_id) {
		String nowDate = DateUtils.getNowDate();
		String[] xnxq = EduUtils.getSchoolYearTerm(DateUtils.string2Date(nowDate));
		return myMajorDao.getCourseScore(stu_id,xnxq[0],xnxq[1]);
	}

	@Override
	public Page getChooseCourse(int currpage) {
		Page page = new Page();
		page.setCurpage(currpage);
		return myMajorDao.getChooseCourse(page);
	}

	@Override
	public Page getPostgraduate(int currpage) {
		Page page = new Page();
		page.setCurpage(currpage);
		String year = DateUtils.getNowYear();
		int y = Integer.parseInt(year)-5;
		return myMajorDao.getPostgraduate(page,y);
	}

}
