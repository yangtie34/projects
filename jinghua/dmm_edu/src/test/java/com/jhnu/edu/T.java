package com.jhnu.edu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.jhnu.edu.entity.TEDUSCHOOLDETAILS;
import com.jhnu.edu.entity.TEDUSCHOOLS;
import com.jhnu.edu.service.EduImportService;
import com.jhnu.edu.util.ImportData;
import com.jhnu.framework.base.dao.BaseDao;
import com.jhnu.spring.SpringTest;

public class T  extends SpringTest{

	@Resource
	private EduImportService eduImportService;
	@Resource
	private BaseDao baseDao;

	@Test
	public void testCreate(){
		String file = "C:\\Users\\Administrator\\Desktop\\新建文件夹\\教育厅\\导入.csv";
		Map map=new ImportData().getImportData(file);
		ArrayList<String> listMap = (ArrayList) map.get("key");
		String nameId=listMap.get(0);
		ArrayList<ArrayList<String>> listdata = (ArrayList) map.get("data");	
		for(int i=0;i<listdata.size();i++){
			ArrayList<String> data=listdata.get(i);
			TEDUSCHOOLS school=new TEDUSCHOOLS();
			school.setChName(data.get(0));
			String schId=baseDao.getId();
			school.setId(schId);
			eduImportService.insertSchool(school);
			List<TEDUSCHOOLDETAILS> deList=new ArrayList<TEDUSCHOOLDETAILS>();
			for(int j=1;j<listMap.size();j++){
				TEDUSCHOOLDETAILS scds=new TEDUSCHOOLDETAILS();
				scds.setId(baseDao.getId());
				scds.setSchoolId(schId);
				scds.setTitleId(listMap.get(j));
				scds.setValue(data.get(j));
				deList.add(scds);
			}
			eduImportService.insertSchooldetails(deList);
		}
		
	}
}
