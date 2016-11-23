package cn.gilight.personal.student.four.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test.test1.util.MapUtils;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.uitl.common.MathUtils;
import cn.gilight.personal.student.four.service.FourAwardService;

@Service("fourAwardService")
public class FourAwardServiceImpl implements FourAwardService{
	
	@Autowired
	private BaseDao baseDao;

	@Override
	public Map<String, Object> getAward(String username) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		//这个学生的获奖次数和总金额
		String sql1 = "select t.stu_id,count(t.stu_id) nums,nvl(sum(t.money),0) sums from t_stu_award t where t.stu_id = '"+username+"' group by stu_id";
		List<Map<String,Object>> list1 = baseDao.queryListInLowerKey(sql1);
		int nums = 0;
		int sums = 0;
		if(list1 != null && list1.size()>0 ){
			nums = MapUtils.getIntValue(list1.get(0), "nums");
			sums = MapUtils.getIntValue(list1.get(0), "sums");
		}
		resultMap.put("nums", nums);
		resultMap.put("sums", sums);
		
		//同届总人数
		String sql2 = "select nvl(count(*),0) nums from t_stu t where t.enroll_grade = (select stu.enroll_grade from t_stu stu where stu.no_ = '"+username+"')";
		List<Map<String,Object>> totalList = baseDao.queryListInLowerKey(sql2);
		int	total = MapUtils.getIntValue(totalList.get(0), "nums");
		
		
		//小于该学生获奖次数的人数
		String sql3 = "select nvl(count(tt.no_),0) nums from (select t.no_,count(sa.stu_id) nums,nvl(sum(sa.money),0) sums from t_stu t"
				+ " left join t_stu_award sa on sa.stu_id = t.no_ where t.enroll_grade = (select stu.enroll_grade "
				+ " from t_stu stu where stu.no_ = '"+username+"') group by t.no_)tt where tt.nums < "+nums;
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql3);
		int passNums = MapUtils.getIntValue(list.get(0), "nums");
		//小于该学生获奖总金额的人数
		String sql4 = "select nvl(count(tt.no_),0) nums from (select t.no_,count(sa.stu_id) nums,nvl(sum(sa.money),0) sums from t_stu t"
				+ " left join t_stu_award sa on sa.stu_id = t.no_ "
				+ " where t.enroll_grade = (select stu.enroll_grade from t_stu stu where stu.no_ = '"+username+"') group by t.no_)tt where tt.sums <"+sums;
		List<Map<String,Object>> passList = baseDao.queryListInLowerKey(sql4);
		int passSums = MapUtils.getIntValue(passList.get(0), "nums");
		
		String passNumsPro = "0%";
		String passSumsPro = "0%";
		if(total != 0){
			passNumsPro = MathUtils.getPercent(passNums,total);
			passSumsPro = MathUtils.getPercent(passSums,total);
		}
		resultMap.put("passNumsPro", passNumsPro);
		resultMap.put("passSumsPro", passSumsPro);
		
		return resultMap;
	}

	@Override
	public Map<String, Object> getPunish(String username) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String sql = "select t.stu_id,co.name_,count(*) nums from t_stu_punish t"
				+ " left join t_code co on co.code_type = 'PUNISH_CODE' and co.code_ = t.punish_code"
				+ " where t.stu_id = '"+username+"' group by t.stu_id,co.name_";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		int jiguo = 0;
		int yzjg = 0;
		int lxck = 0;
		int jg = 0;
		int total = 0;
		for(Map<String,Object> m : list){
			if("警告".equals(MapUtils.getString(m, "name_"))){
				jg = MapUtils.getIntValue(m, "nums");
			}
			if("严重警告".equals(MapUtils.getString(m, "name_"))){
				yzjg = MapUtils.getIntValue(m, "nums");
			}
			if("记过".equals(MapUtils.getString(m, "name_"))){
				jiguo = MapUtils.getIntValue(m, "nums");
			}
			if("留校察看".equals(MapUtils.getString(m, "name_"))){
				lxck = MapUtils.getIntValue(m, "nums");
			}
		}
		total = jiguo + yzjg + lxck +jg;
		resultMap.put("jiguo", jiguo);
		resultMap.put("jg", jg);
		resultMap.put("yzjg", yzjg);
		resultMap.put("lxck", lxck);
		resultMap.put("total", total);
		return resultMap;
	}

}
