package cn.gilight.personal.student.paisan.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gilight.framework.page.Page;
import cn.gilight.personal.student.paisan.dao.PaisanDao;
import cn.gilight.personal.student.paisan.service.PaisanService;

@Service("paisanService")
public class PaisanServiceImpl implements PaisanService{
	
	@Autowired
	private PaisanDao paisanDao;

	@Override
	public Page getPaisan(String stu_id, String stu_name, String flag, Page page) {
		return paisanDao.getPaisan(stu_id,stu_name,flag,page);
	}

	@Override
	public Map<String, Object> getPaisanStu(String stu_id) {
		return paisanDao.getPaisanStu(stu_id);
	}

}
