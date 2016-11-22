package cn.gilight.personal.student.main.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;
import cn.gilight.personal.po.TStuComm;
import cn.gilight.personal.student.main.service.StudentInfoService;

@Service("studentInfoService")
public class StudentInfoServiceImpl implements StudentInfoService {
	
	@Resource
	private BaseDao baseDao;
	@Resource
	private HibernateDao hibernate;
	
	@Override
	public Map<String, Object> getStudentSimpleInfo(String sno){
		String sql = "SELECT W.WECHAT_HEAD_IMG HEADIMG,"
					+ " T.NO_ TNO, T.NAME_ NAME, BJ.NAME_ DEPT, SEX.NAME_ GENDER "
				+ " FROM T_STU T"
					+ " LEFT JOIN T_WECHAT_BIND W ON W.USERNAME = T.NO_"
					+ " LEFT JOIN T_CLASSES BJ ON BJ.NO_ = T.CLASS_ID"
					+ " LEFT JOIN T_CODE SEX ON SEX.CODE_ = T.SEX_CODE AND SEX.CODE_TYPE = 'SEX_CODE'"
				+ " WHERE T.NO_ = '"+sno+"'";
		List<Map<String, Object>> userlist = baseDao.queryListInLowerKey(sql);
		if (userlist.size() > 0) {
			return userlist.get(0);
		} else 
			return null;
	}
	
	@Override
	public Map<String, Object> getStudentDetailInfo(String sno){
		String sql = "SELECT W.WECHAT_HEAD_IMG HEADIMG, T.NO_ SNO, T.NAME_ NAME, LXFS.TEL, T.EXAMINEE_NO KSH,BJ.NAME_ DEPT, SEX.NAME_ GENDER, T.BIRTHDAY,"
					+ " T.ENGLISH_NAME, T.IDNO, NATION.NAME_ NATION, ZZMM.NAME_ ZZMM, T.MARRIED,YX.NAME_ YX, ZY.NAME_ ZY, BJ.NAME_ BJ, T.LENGTH_SCHOOLING XZ, XL.NAME_ XL,"
					+ " XW.NAME_ XW, PYFS.NAME_ PYFS, PYCC.NAME_ PYCC, ZSLB.NAME_ ZSLB, XXFS.NAME_ XXFS, SYLB.NAME_ SYLB, XSLB.NAME_ XSLB, T.ENROLL_GRADE RXNJ, T.ENROLL_DATE, T.SCHOOLTAG"
				+ " FROM T_STU T"
					+ " LEFT JOIN T_WECHAT_BIND W ON W.USERNAME = T.NO_ "
					+ " LEFT JOIN T_STU_COMM LXFS ON T.NO_ = LXFS.STU_ID "
					+ " LEFT JOIN T_CODE NATION ON NATION.CODE_ = T.NATION_CODE AND NATION.CODE_TYPE = 'NATION_CODE'"
					+ " LEFT JOIN T_CODE ZZMM ON T.POLITICS_CODE = ZZMM.CODE_ AND ZZMM.CODE_TYPE = 'POLITICS_CODE'"
					+ " LEFT JOIN T_CODE_DEPT_TEACH YX ON T.DEPT_ID = YX.ID "
					+ " LEFT JOIN T_CODE_DEPT_TEACH ZY ON T.MAJOR_ID = ZY.ID "
					+ " LEFT JOIN T_CODE_EDUCATION XL ON XL.ID = T.EDU_ID"
					+ " LEFT JOIN T_CODE_DEGREE XW ON XW.ID = T.DEGREE_ID"
					+ " LEFT JOIN T_CODE_TRAINING PYFS ON PYFS.ID = T.TRAINING_ID"
					+ " LEFT JOIN T_CODE PYCC ON PYCC.CODE_ = T.TRAINING_LEVEL_CODE AND PYCC.CODE_TYPE = 'TRAINING_LEVEL_CODE' "
					+ " LEFT JOIN T_CODE XXFS ON XXFS.CODE_ = T.LEARNING_STYLE_CODE AND XXFS.CODE_TYPE = 'LEARNING_STYLE_CODE' "
					+ " LEFT JOIN T_CODE ZSLB ON ZSLB.CODE_ = T.RECRUIT_CODE AND ZSLB.CODE_TYPE = 'RECRUIT_CODE'"
					+ " LEFT JOIN T_CODE SYLB ON SYLB.CODE_ = T.STU_FROM_CODE AND SYLB.CODE_TYPE = 'STU_FROM_CODE'"
					+ " LEFT JOIN T_CODE_STU_CATEGORY XSLB ON T.STU_CATEGORY_ID = XSLB.ID"
					+ " LEFT JOIN T_CLASSES BJ ON BJ.NO_ = T.CLASS_ID "
					+ " LEFT JOIN T_CODE SEX ON SEX.CODE_ = T.SEX_CODE AND SEX.CODE_TYPE = 'SEX_CODE'"
				+ " WHERE T.NO_ = '"+ sno + "'";
		List<Map<String, Object>> userlist = baseDao.queryListInLowerKey(sql);
		if (userlist.size() > 0) {
			return userlist.get(0);
		} else
			return null;
	}
	
	@Override
	public boolean saveOrUpdateStudentTel(String sno,String tel) throws SecurityException, NoSuchFieldException{
		String querySql = "select t.stu_id,t.tel from t_stu_comm t where t.stu_id = '"+sno+"'";
		List<Map<String,Object>> stuList = baseDao.queryForList(querySql);
		if (stuList.size() == 1) {
			TStuComm stuComm = new TStuComm();
			stuComm.setStuId(sno);
			stuComm = hibernate.getById(sno, TStuComm.class);
			stuComm.setTel(tel);
			hibernate.update(stuComm);
		}else{
			TStuComm stuComm = new TStuComm();
			stuComm.setStuId(sno);
			stuComm.setTel(tel);
			hibernate.save(stuComm);
		}
		return true; 
	}
}