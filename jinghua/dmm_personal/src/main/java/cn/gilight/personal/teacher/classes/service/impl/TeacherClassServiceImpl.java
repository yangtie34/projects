package cn.gilight.personal.teacher.classes.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.product.EduUtils;
import cn.gilight.personal.teacher.classes.service.TeacherClassService;

@Service("teacherClassService")
public class TeacherClassServiceImpl implements TeacherClassService {
	
	@Resource
	private BaseDao baseDao;
	@Resource
	private HibernateDao hibernate;
	
	@Override
	public Map<String, Object> queryClassesTotalInfo(String username) {
		Map<String, Object> result = new HashMap<String, Object>();
		int total=0,
			male = 0,
			female = 0;
		List<Map<String,Object>> bjlist = queryClassList(username);
		for (int i = 0; i < bjlist.size(); i++) {
			Map<String,Object> bj = bjlist.get(i);
			total += MapUtils.getIntValue(bj, "total");
			male += MapUtils.getIntValue(bj, "male");
			female += MapUtils.getIntValue(bj, "female");
		}
		result.put("nums", bjlist.size());
		result.put("total", total);
		result.put("male", male);
		result.put("female", female);
		return result;
	}
	
	@Override
	public List<Map<String, Object>> queryClassList(String username){
		String[] xnxq = EduUtils.getSchoolYearTerm(new Date());
		String currentXn =xnxq[0];
		String currentXq =xnxq[1];
		String sql = "SELECT  C.NO_,C.NAME_ BJMC,ZY.NAME_ ZYMC,COUNT(S.NO_) TOTAL,"
				+ " SUM(DECODE(D.NAME_,'男',1,0)) MALE,"
				+ " SUM(DECODE(D.NAME_,'女',1,0)) FEMALE "
				+ " FROM T_CLASSES_INSTRUCTOR T"
				+ " INNER JOIN T_CLASSES C ON T.CLASS_ID = C.NO_ "
				+ " INNER JOIN T_CODE_DEPT_TEACH ZY ON ZY.ID = C.TEACH_DEPT_ID"
				+ " INNER JOIN T_STU S ON C.NO_ = S.CLASS_ID"
				+ " INNER JOIN T_CODE D ON S.SEX_CODE = D.CODE_ AND D.CODE_TYPE = 'SEX_CODE'"
				+ " WHERE T.SCHOOL_YEAR = '"+currentXn+"'"
				+ " AND T.TERM_CODE = '"+currentXq+"' "
				+ " AND T.TEA_ID = '"+username+"'"
				+ "GROUP BY   C.NO_,C.NAME_ ,ZY.NAME_";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public Map<String, Object> queryClassInfo(String classId) {
		String sql = "SELECT T.NAME_ BJMC,ZY.NAME_ ZYMC, YX.NAME_ YXMC, COUNT(S.NO_) TOTAL,"
				+ "MAX(S.ENROLL_GRADE) RXNJ,"
				+ "SUM(DECODE(SEX.NAME_, '男', 1, 0)) MALE,"
				+ "SUM(DECODE(SEX.NAME_, '女', 1, 0)) FEMALE "
				+ " FROM T_CLASSES T "
				+ " INNER JOIN T_CODE_DEPT_TEACH ZY ON ZY.ID = T.TEACH_DEPT_ID"
				+ " INNER JOIN T_CODE_DEPT_TEACH YX ON ZY.PID = YX.ID"
				+ " LEFT JOIN T_STU S ON S.CLASS_ID = T.NO_ "
				+ " LEFT JOIN T_CODE SEX ON SEX.CODE_ = S.SEX_CODE AND SEX.CODE_TYPE = 'SEX_CODE'"
				+ " WHERE T.NO_ = '"+classId+"' GROUP BY T.NAME_, ZY.NAME_, YX.NAME_";
		List<Map<String, Object>> bjinfo = baseDao.queryListInLowerKey(sql);
		if(bjinfo.size() > 0){
			return bjinfo.get(0);
		}else return null;
	}

	@Override
	public List<Map<String, Object>> queryStudentsListOfClass(String classId) {
		String sql = "SELECT T.NO_ SNO, T.NAME_ NAME, SEX.NAME_ GENDER, L.TEL TEL,W.WECHAT_HEAD_IMG HEADIMG"
				+ " FROM T_STU T"
				+ " INNER JOIN T_CODE SEX ON SEX.CODE_ = T.SEX_CODE AND SEX.CODE_TYPE = 'SEX_CODE'"
				+ " LEFT JOIN T_STU_COMM L ON L.STU_ID = T.NO_"
				+ " LEFT JOIN T_WECHAT_BIND W ON W.USERNAME = T.NO_"
				+ " WHERE T.CLASS_ID = '"+classId+"' ORDER BY T.NAME_" ;
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public List<Map<String, Object>> queryStudentsList(String username) {
		String[] xnxq = EduUtils.getSchoolYearTerm(new Date());
		String currentXn =xnxq[0];
		String currentXq =xnxq[1];
		String sql = "SELECT T.NO_ SNO,T.NAME_ NAME,SEX.NAME_ GENDER,L.TEL TEL, C.NAME_ BJMC,"
				+ "W.WECHAT_HEAD_IMG HEADIMG FROM T_CLASSES_INSTRUCTOR A"
				+ " INNER JOIN T_STU T ON T.CLASS_ID = A.CLASS_ID"
				+ " INNER JOIN T_CLASSES C ON C.NO_ = T.CLASS_ID"
				+ " INNER JOIN T_CODE SEX ON SEX.CODE_ = T.SEX_CODE AND SEX.CODE_TYPE = 'SEX_CODE'"
				+ " LEFT JOIN T_STU_COMM L ON L.STU_ID = T.NO_"
				+ " LEFT JOIN T_WECHAT_BIND W ON W.USERNAME = T.NO_"
				+ " WHERE A.TEA_ID = '"+username+"' "
				+ " AND A.SCHOOL_YEAR = '"+currentXn+"'"
				+ " AND A.TERM_CODE = '"+currentXq+"' ORDER BY T.NAME_";
		return baseDao.queryListInLowerKey(sql);
	}
	
	
}