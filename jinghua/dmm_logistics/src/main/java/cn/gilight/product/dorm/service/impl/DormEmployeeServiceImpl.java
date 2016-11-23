package cn.gilight.product.dorm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gilight.framework.page.Page;
import cn.gilight.product.dorm.dao.DormEmployeeDao;
import cn.gilight.product.dorm.service.DormEmployeeService;

import com.jhnu.syspermiss.school.entity.Dept;
@Service("dormEmployeeService")
public class DormEmployeeServiceImpl implements DormEmployeeService {
	@Autowired
	private DormEmployeeDao dormEmployeeDao;
	
	@Override
	public Object getDormTree() {
		List<Dept> deptTeachList1=new ArrayList<Dept>();
		deptTeachList1=dormEmployeeDao.GetDormTree();
		Map<String,Dept> deptMap1=new HashMap<String,Dept>();
		Dept root=null;
		for(Dept dept:deptTeachList1){
			deptMap1.put(dept.getId(), dept);
		}
		for(Dept dept:deptTeachList1){
			if(dept.getLevel_().intValue()==0){
				root=dept;
			}else{
				if(deptMap1.get(dept.getPid())!=null)
					deptMap1.get(dept.getPid()).getChildren().add(dept);
			}
			
		}
		if(root!=null){
			return root;
		}
		return null;
	}

	@Override
	public Map<String, Object> getDormInfo(Map<String,String> dorm) {
		return dormEmployeeDao.getDormInfo(dorm);
	}

	@Override
	public List<Map<String, Object>> getDormByType(Map<String,String> dorm) {
		
		return dormEmployeeDao.getDormByType(dorm);
	}

	@Override
	public List<Map<String, Object>> getDormByNews(Map<String,String> dorm) {
		
		return dormEmployeeDao.getDormByNews(dorm);
	}

	@Override
	public List<Map<String, Object>> getDormByStuType(Map<String,String> dorm,
			String stuType) {
		
		return dormEmployeeDao.getDormByStuType(dorm, stuType);
	}

	@Override
	public Page getDormTopByGroup(int currentPage, int numPerPage,
			int totalRow, Map<String, String> dorm, String type) {
		return dormEmployeeDao.getDormTopByGroup(currentPage, numPerPage, totalRow, dorm, type);
	}

	@Override
	public Page getDormTopPage(int currentPage, int numPerPage,
			int totalRow, Map<String, String> dorm, String type, String id) {
		return dormEmployeeDao.getDormTopPage(currentPage, numPerPage, totalRow, dorm, type, id);
	}

}
