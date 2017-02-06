package cn.gilight.dmm.xg.job;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhnu.syspermiss.permiss.entity.JobResultBean;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.business.util.DevUtils;
import cn.gilight.dmm.business.util.PmsUtils;
import cn.gilight.dmm.xg.entity.TStuWarningMailStatus;
import cn.gilight.dmm.xg.util.MailUtils;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.ExcelUtils;
import cn.gilight.framework.uitl.common.ListUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.common.StringUtils;

/**
 * 
 * 
 * @author xuebl
 * @date 2016年8月29日 上午10:19:25
 */
@Service("stuWarningMailJob")
public class StuWarningMailJob {

	@Resource
	private BaseDao baseDao;
	@Resource
	private BusinessDao businessDao;
	@Resource
	private HibernateDao hibernateDao;

	/** 疑似逃课 */
	private static final String Key_skipClasses = "skipClasses";
	/** 疑似未住宿 */
	private static final String Key_notStay   = "notStay";
	/** 疑似晚勤晚归 */
	private static final String Key_stayLate  = "stayLate";
	/** 疑似不在校 */
	private static final String Key_stayNotin = "stayNotin";
	/** 疑似逃课 */
	private static final String Name_skipClasses = "疑似逃课";
	/** 疑似未住宿 */
	private static final String Name_notStay   = "疑似未住宿";
	/** 疑似晚勤晚归 */
	private static final String Name_stayLate  = "疑似晚勤晚归";
	/** 疑似不在校 */
	private static final String Name_stayNotin = "疑似不在校";
	

	private Logger log = Logger.getLogger(this.getClass());


	@Transactional
	public JobResultBean sendWarningMail(){
		Long beginTime = System.currentTimeMillis();
		String yesterDay = DateUtils.getYesterday(); // 需要处理的数据时间
//		yesterDay = "2016-07-04"; // TODO del
		String jobName = "#考勤预警学生名单邮件推送"+yesterDay+"#";
		begin(jobName);
		// 判断昨天是否需要发送邮件
		boolean isDo = false;
		List<Map<String, Object>> list = baseDao.queryListInLowerKey("select t.start_date,t.end_date from t_school_start t order by t.school_year,t.term_code");
		for(Map<String, Object> map : list){
			String startDate = MapUtils.getString(map, "start_date"),
				   endDate   = MapUtils.getString(map, "end_date");
			if(DateUtils.compareEqual(yesterDay, startDate) && DateUtils.compareEqual(endDate, yesterDay)){
				isDo = true;
				break;
			}
		}
		//定义结果
		JobResultBean result = new JobResultBean();
		String info = jobName;
		if(!isDo){
			info += "，不在上课时间";
		}else{
			JobResultBean t = queryAndSendDetailAll(yesterDay);
			if(!t.getIsTrue()){
				return t;
			}else{
				info += t.getMsg();
			}
		}
		info += "，结束执行。共耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
		end(info);
		result.setMsg(info);
		DevUtils.p(info);
		return result;
	}
	
	
	private static String[] field  = {"no","name","sexmc","deptmc","majormc","classmc","bz"},
			 		header = {"学号","姓名","性别","院系","专业","班级",""};
	private static final List<String> fields  = ListUtils.ary2List(field),
									  headers = ListUtils.ary2List(header);
	private static String path = System.getProperty("user.dir")+"\\temp\\";
	
	
	/**
	 * 发送所有组织机构节点对应的邮件
	 * @param day
	 * @return JobResultBean
	 */
	private JobResultBean queryAndSendDetailAll(String day){
		JobResultBean result = new JobResultBean();
		
		String time = DateUtils.getNowDate2();
		/**
		 * 查询不同组织机构人员对应的邮箱
		 */
		List<Map<String, Object>> li = baseDao.queryListInLowerKey("select * from t_email t");
		if(li.isEmpty()){
			result.setIsTrue(true);
			result.setMsg("没有设置邮件接收人员");
			return result;
		}else{
			// 邮件发送状态
			List<TStuWarningMailStatus> saveList = new ArrayList<>();
			int success = 0, fail = 0;
			// 参数
			for(Map<String, Object> map : li){
				String deptId = MapUtils.getString(map, "id"),
					   mail   = MapUtils.getString(map, "email");
				TStuWarningMailStatus t = getAndSendDetail(day, deptId, null, mail);
				if(t.getStatus() == 1){
					success++;
				}else{
					fail++;
				}
				saveList.add(t);
			}
			if(!saveList.isEmpty()){
				try {
					hibernateDao.saveAll(saveList);
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				}
			}
			result.setMsg(day+"发送邮件：成功"+success+"封，失败"+fail+"封");
		}
		result.setIsTrue(true);
		return result;
	}

	/**
	 * 发送一个组织机构节点所对应的邮件
	 * @param day 日期
	 * @param deptId 组织机构节点
	 * @param type 预警类型，如果为null，发送全部
	 * @param mail 收件人
	 * @return TStuWarningMailStatus
	 */
	public TStuWarningMailStatus getAndSendDetail(String day, String deptId, String type, String mail){
		TStuWarningMailStatus t = null;
		String title_ = day+"失联预警名单";
		List<Map<String, Object>> deptList = businessDao.queryLevelListByIds(deptId);
		if(!deptList.isEmpty()){
			title_ = title_+"（"+MapUtils.getString(deptList.get(0),"name")+"）";
		}
		// 参数
		List<AdvancedParam> advancedParamList = new ArrayList<>();
		AdvancedUtil.add(advancedParamList, new AdvancedParam(AdvancedUtil.Group_Common, AdvancedUtil.Common_DEPT_TEACH_ID, deptId));
		Map<String, Object> keyValue = new HashMap<>();
		keyValue.put("type", type);
		keyValue.put("date", day);
		keyValue.put("valueType", "count");
		List<Map<String, Object>> valList = baseDao.queryListInLowerKey(getDetailSql(advancedParamList, keyValue, fields));
		// 获取mail地址
		if(mail == null){
			List<Map<String, Object>> li = baseDao.queryListInLowerKey("select * from t_email t where id = '"+deptId+"'");
			if(!li.isEmpty()){
				mail = MapUtils.getString(li.get(0), "email");
			}
		}
		if(mail == null){
			return t;
		}
		HSSFWorkbook workBook = ExcelUtils.createExcel(valList, fields, headers, title_);
		String filePath = path+title_+".xls";
		try {
			FileOutputStream os = new FileOutputStream(new String(filePath.getBytes("utf-8"),"utf-8"));
			workBook.write(os);
			os.close();
			t = new TStuWarningMailStatus();
			t.setDept_id(deptId);
			t.setDate_(day);
			t.setTime_(DateUtils.getNowDate2());
			int status = 0;
			if(MailUtils.send(mail, filePath, title_)){
				status = 1;
			}
			t.setStatus(status);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}
	
	/**
	 * 获取 sql
	 * @param advancedParamList
	 * @param keyValue
	 * @param fields
	 * @return String
	 */
	private String getDetailSql(List<AdvancedParam> advancedParamList, Map<String, Object> keyValue, List<String> fields) {
		Map<String, Object> paramM = getParamsMapByParams(advancedParamList);
		List<String> deptList = (List<String>) paramM.get("deptList");
		List<AdvancedParam> stuAdvancedList = (List<AdvancedParam>) paramM.get("stuAdvancedList");
		// 处理keyValue中的参数
		String type = null, valueType = null; // 数据类型    数据字段
		if(keyValue != null)
			for(Map.Entry<String, Object> entry : keyValue.entrySet()){
				String key = entry.getKey(), value = entry.getValue()==null ? null : String.valueOf(entry.getValue());
				if("type".equals(key)){
					type = value;
				}else if("valueType".equals(key)){
					valueType = value;
				}
			}
		String stuSql = businessDao.getStuDetailSql(deptList, stuAdvancedList),
			   sql    = null;
		// 分析是哪中异常预警
		if(type == null){
			sql = getDetailSqlByType(stuSql, Key_skipClasses) + " union all " +
					getDetailSqlByType(stuSql, Key_stayLate) + " union all " +
					getDetailSqlByType(stuSql, Key_notStay) + " union all " +
					getDetailSqlByType(stuSql, Key_stayNotin);
		}else{
			sql = getDetailSqlByType(stuSql, type);
		}
		// 特殊参数处理
		if(keyValue != null)
			for(Map.Entry<String, Object> entry : keyValue.entrySet()){
				String key = entry.getKey(), value = entry.getValue()==null ? null : String.valueOf(entry.getValue());
				// 年龄  教龄  特殊处理
				String sql2 = "select * from ("+sql+") ";
				if(key.equals("date")){
					sql2 += " where date_ = '"+value+"'";
					sql = sql2;
				}
			}

		// 是人次、还是人数、比例
		switch (valueType) {
		case "count": // 人数
			sql = "select distinct(t.no) a_,t.* from ("+sql+") t";
			break;
		case "rc": // 人次
			
			break;
		case "scale": // 比例
			
			break;
		default:
			break;
		}
		// 预警
		String detailSql = sql;
		// 所要查询的字段
		if(fields!=null && !fields.isEmpty()){
			detailSql = "select "+StringUtils.join(fields, ",")+ " from ("+detailSql+")";
		}
		return detailSql;
	}

	/**
	 * 根据类型获得详情Sql
	 * @param stuSql 学生sql
	 * @param type 类型
	 * @return String
	 */
	private String getDetailSqlByType(String stuSql, String type){
		String sql = null;
		switch (type) {
		case Key_skipClasses:
			sql = "select t.date_,'"+Name_skipClasses+"：'||t.date_||','||s.period bz,stu.*"
				+ " from ("+stuSql+") stu, " + Constant.TABLE_T_STU_WARNING_SKIP_CLASSES+" t,"
				+ " T_COURSE_ARRANGEMENT s where t.course_arrangement_id = s.id and stu.no=t.stu_id";
			break;
		case Key_notStay:
			sql = "select t.date_,'"+Name_notStay+"' bz,stu.* from ("+stuSql+") stu, "+Constant.TABLE_T_STU_WARNING_NOTSTAY+" t where stu.no=t.stu_id";
			break;
		case Key_stayLate:
			sql = "select t.datetime date_,'"+Name_stayLate+"' bz,stu.* from ("+stuSql+") stu, "+Constant.TABLE_T_CARD_STAY_LATE+" t where stu.no=t.sno";
			break;
		case Key_stayNotin:
			sql = "select t.datetime date_,'"+Name_stayNotin+"' bz,stu.* from ("+stuSql+") stu, "+Constant.TABLE_T_CARD_STAY_NOTIN+" t where stu.no=t.sno";
			break;
		}
		return "select * from (" +sql+ " order by stu.deptid, stu.majorid, stu.classid, stu.no)";
	}
	
	/**
	 * 根据参数优化参数
	 * @return Map<String,Object>
	 */
	private Map<String, Object> getParamsMapByParams(List<AdvancedParam> advancedParamList){
		List<String> deptList = PmsUtils.getPmsAll();
		advancedParamList = advancedParamList!=null ? advancedParamList : new ArrayList<AdvancedParam>();
		List<AdvancedParam> teaAdvancedList = AdvancedUtil.getAdvancedParamTea(advancedParamList),
							stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList),
							businessAdvancedList = AdvancedUtil.getAdvancedParamBusiness(advancedParamList);
		// return
		Map<String, Object> map = new HashMap<>();
		map.put("advancedParamList", advancedParamList);
		map.put("teaAdvancedList", teaAdvancedList);
		map.put("stuAdvancedList", stuAdvancedList);
		map.put("businessAdvancedList", businessAdvancedList);
		map.put("deptList", deptList);
		return map;
	}
	
	private void begin(String info){
		log.warn("======== begin : "+info+" 初始化 ========");
	}
	private void info(String info){
		log.warn("======== info : "+info+" ========");
	}
	private void end(String info){
		log.warn("======== end : "+info+" ========");
	}
	private void error(String info){
		log.warn("======== error : "+info+" 停止执行========");
	}
}
