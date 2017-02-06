package cn.gilight.dmm.teaching.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.gilight.dmm.business.service.BusinessService;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.business.util.DevUtils;
import cn.gilight.dmm.teaching.dao.StudentsQualityDao;
import cn.gilight.dmm.teaching.service.StudentsQualityService;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.MapUtils;

@Service("studentsQualityService")
public class StudentsQualityServiceImpl implements StudentsQualityService{

	@Resource
	private StudentsQualityDao studentsQualityDao;
	@Resource
	private  BusinessService businessService;
	@Resource
	private BaseDao baseDao;
	
	//页面总查询,从选定表中获取某字段
	@Override
	public Map<String, Object> querySelectType() {
		Map<String,Object> temp = DevUtils.MAP();
		temp.put("list", businessService.queryBzdmSchoolYear(Constant.TABLE_T_STU_SYZL, "YEAR"));
		return temp;
		
	}
	//查询本科生各项数据
	@Override
	public Map<String, Object> queryStudents(String year) {
		return studentsQualityDao.queryStudents(year);
	}
	//查询所有科类
	@Override
	public List<Map<String, Object>> queryAllSub(String year){
		return studentsQualityDao.queryAllSub(year);
	}
	//选择某科类时,查询科类对应数据 sub:科类ID
	@Override
	public List<Map<String, Object>> queryStudentsSub(String year,  List<String> sub) {
		return studentsQualityDao.queryStudentsSub(year, sub);
	}
	//查询各省分数线
	@Override
	public Map<String, Object> queryScoreLineByPro(Page page,String flag,String year,
			String majorId) {
		return studentsQualityDao.queryScoreLineByPro(page,flag,year, majorId);
	}
	//查询超出某分数的专业
	@Override
	public Map<String, Object> queryStudentsScore(Page page,String year,String point,String flag) {
		return studentsQualityDao.queryStudentsScore(page,year,point,flag);
	}
	//调剂率
	@Override
	public Map<String, Object> queryStudentsAdjust(Page page,String year,String flag) {
		return studentsQualityDao.queryStudentsAdjust(page,year,flag);
	}
	//自主招生录取率
	@Override
	public Map<String, Object> queryStudentsEnroll(Page page,String year,String flag) {
		return studentsQualityDao.queryStudentsEnroll(page,year,flag);
	}
	//未报到学生数量
	@Override
	public Map<String, Object> queryStudentsNotReport(String year) {
		return studentsQualityDao.queryStudentsNotReport(year);
	}
	//未报到学生地理分布
	@Override
	public Map<String, Object> queryStudentsNotReportByLocal(String year,String xzqh,Boolean updown) {
		xzqh = xzqh==null?"0":xzqh;
		List<Map<String,Object>> list = studentsQualityDao.queryStudentsNotReportByLocal(year,xzqh,updown);
		List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
		for (int i = 1; i < list.size(); i++) {
			Map<String, Object> map1 = list.get(i);
			list1.add(map1);
		}
		int max=0;
		if(list1.size()>1){
			max = MapUtils.getIntValue(list1.get(1), "value");
			if (max==0){
				max=10;
			}
		}else{
			max = 10;
		}
		if(max<10){
			max = 10;
		}
		Map<String,Object> map = DevUtils.MAP();
		map.put("max",max);
		map.put("list", list1);
		map.put("maptype", MapUtils.getString(list.get(0), "maptype"));
		if(MapUtils.getString(list.get(0), "maptype").equals("中国")){
			map.put("cc",1);
		}else{
		map.put("cc", MapUtils.getString(list1.get(0), "cc"));}
		return map;
	}
	//学生未报到原因分布
	@Override
	public Map<String, Object> queryStudentsNotReportReason(String year) {
		return studentsQualityDao.queryStudentsNotReportReason(year);
	}
	@Override
	public Map<String, Object> getNotReportReason(Page page,String year,String values,
			List<String> fields){
		String sql = "select ta.examinee_no xsno,ta.name_ xsmc,tb.name_ zymc,tc.name_ sydmc,td.name_ reasonmc" 
                +" from t_stu_encruit ta left join t_code_dept_teach tb on ta.major_id=tb.code_"
                +" left join t_code_admini_div tc on tc.code_=ta.sf_id left join t_code_not_report_reason" 
                +" td on ta.not_report_reason_id=td.id where ta.is_report='0' and "
                +" ta.year="+year+" and ta.not_report_reason_id='"+values+"'";
		Map<String, Object> map = baseDao.createPageQueryInLowerKey(sql, page);
 		return map;
	}
	//未报到学生详细信息
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryWbdDetail(Page page,String pid,String year,List<String> fields){
		String str = " ta.sf_id=tb.code_",str2="";
		String s1="",s2="";
		if(pid.equals("0")){
			str = " ta.sf_id=tb.code_";
			str2 = " tb.pid='"+pid+"'";
		}else{
			s1=pid.substring(2);
			s2=pid.substring(4);
			if(s2.equals("00")){
				if(s1.equals("0000")){
					str = " ta.s_id=tb.code_";
					str2 = " ta.sf_id = '"+pid+"'";
				}else{
					str = " ta.x_id=tb.code_";
					str2 = " ta.s_id = '"+pid+"'";
				}
			};
		};
		String sql = "select rownum rn,ta.examinee_no xh,ta.year nj,ta.name_ name_,tc.name_ sexmc,tb.name_ sfmc,"
			   +" te.name_ yxmc,td.name_ zymc,tf.name_ wbdmc"
			   +" from t_stu_encruit ta"
               +" left join t_code_admini_div tb on "+str
               +" left join t_code tc on tc.code_=ta.sex_code and tc.code_type='SEX_CODE'"
               +" left join t_code_dept_teach td on ta.major_id=td.code_ AND TD.Level_='2'"
               +" left join t_code_dept_teach te on te.code_=ta.dept_id and te.level_='1'"
               +" left join t_code_not_report_reason tf on ta.not_report_reason_id=tf.code_"
               +" where "+str2+" and ta.is_report='0' and ta.year='"+year+"'";
		Map<String, Object> map = baseDao.createPageQueryInLowerKey(sql, page);
		List<Map<String, Object >> list = new ArrayList<Map<String,Object>>();
		list = (List<Map<String, Object>>) map.get("rows");
		String mc = "";
		for(Map<String, Object> it : list){
			mc = MapUtils.getString(it, "sfmc");
			if(mc==""){
				it.put("sfmc", "--");
			}
		}
		return map;
	}
}
