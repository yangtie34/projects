package cn.gilight.personal.job;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;
import com.jhnu.syspermiss.permiss.entity.JobResultBean;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.product.EduUtils;
import cn.gilight.personal.po.TClassesInstructor;

/**   
* @Description: 转化学校的辅导员信息
* @author Sunwg  
* @date 2016年5月12日 下午2:35:48   
*/
@Service("fdyChangeService")
public class FdyChangeService {
	private Logger log = Logger.getLogger(this.getClass());
	
	@Resource
	private BaseDao baseDao;
	@Resource
	private HibernateDao hibernate;
		
	/** 
	* @Description: 转化学校的辅导员信息
	* @return JobResultBean
	 * @throws Exception 
	*/
	@Transactional
	public JobResultBean changeFdyInfo() throws Exception{
		//定义结果
		JobResultBean result = new JobResultBean();
		log.warn("========begin : 转化学校的辅导员信息任务开始执行 ==============");
		Long beginTime = System.currentTimeMillis();
		String sql = "delete from T_CLASSES_INSTRUCTOR";
		int deleteCount = baseDao.delete(sql);
		log.debug("清空辅导员任职表（T_CLASSES_INSTRUCTOR）数据，共计" +  deleteCount+" 条数据");
		//查询源
		String tsql = "SELECT T.BH,T.FDYH,TO_CHAR(T.RZKSSJ,'YYYY-MM-DD') KSSJ,TO_CHAR(T.RZJSSJ,'YYYY-MM-DD') JSSJ,"
				+ " CASE WHEN CO.NAME_ = '专职辅导员' then 1 else 0 end isfulltime  FROM T_TEMP_FDYXXB T"
				+ " left join t_tea_stu_worker tsw on tsw.tea_id = t.fdyh left join t_code co "
				+ " on co.code_type = 'STU_WORKER_CODE' and co.code_ = tsw.stu_worker_code";
		List<Map<String, Object>> sourceList = baseDao.queryForList(tsql);
		try {
			int insertCount = 0;
			//遍历源，开始转化存储
			for (Map<String, Object> fdy : sourceList) {
				String bh = MapUtils.getString(fdy, "BH");
				String tno = MapUtils.getString(fdy, "FDYH");
				String kssj = MapUtils.getString(fdy, "KSSJ");
				String jssj = MapUtils.getString(fdy, "JSSJ");
				int isfulltime = MapUtils.getIntValue(fdy, "ISFULLTIME");
				String[] ksxnxq = EduUtils.getSchoolYearTerm(DateUtils.string2Date(kssj));
				String[] jsxnxq = new String[2];
				//判断结束任职时间，如果有效，直接使用，无效则取当前时间
				if(jssj.length() != 10){
					jsxnxq = EduUtils.getSchoolYearTerm(new Date());
				}else {
					jsxnxq = EduUtils.getSchoolYearTerm(DateUtils.string2Date(jssj));
				}
				//如果结束学年学期和开始学年学期不同，则插入开始学年学期，并取开始学年学期的下一个学年学期进行循环比较
				while(!(ksxnxq[0].equals(jsxnxq[0]) && ksxnxq[1].equals(jsxnxq[1]))){
					TClassesInstructor temp1 = new TClassesInstructor();
					temp1.setClassId(bh);
					temp1.setTeaId(tno);
					temp1.setSchoolYear(ksxnxq[0]);
					temp1.setTermCode(ksxnxq[1]);
					temp1.setIsfulltime(isfulltime);
					hibernate.save(temp1);
					insertCount++;
					if(ksxnxq[1].endsWith("01")){
						ksxnxq[1] = "02";
					}else{
						int ksn = new Integer(ksxnxq[0].substring(0,4));
						int jsn = new Integer(ksxnxq[0].substring(5,9));
						ksxnxq[0] = (ksn + 1) + "-" + (jsn +1);
						ksxnxq[1] = "01";
					}
				}
				//插入结束学年学期
				TClassesInstructor temp2 = new TClassesInstructor();
				temp2.setClassId(bh);
				temp2.setTeaId(tno);
				temp2.setSchoolYear(jsxnxq[0]);
				temp2.setTermCode(jsxnxq[1]);
				temp2.setIsfulltime(isfulltime);
				hibernate.save(temp2);
				insertCount++;
			}
			log.warn("======== stop :结束转换，共插入" + insertCount +"条数据 ,共耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒============== ");
			result.setIsTrue(true);
			result.setMsg("转化学校的辅导员信息完成");
		} catch (Exception e) {
			e.printStackTrace();
			result.setIsTrue(false);
			result.setMsg("转化学校的辅导员信息出错:"+e.getStackTrace());
			log.error("======== error :转化学校的辅导员信息异常 ：初始化出现错误，停止执行 ==============");
			e.printStackTrace();
			throw e;
		}
		return result;
	}
}