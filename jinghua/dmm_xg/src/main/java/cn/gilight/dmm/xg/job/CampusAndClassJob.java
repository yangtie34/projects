package cn.gilight.dmm.xg.job;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhnu.syspermiss.permiss.entity.JobResultBean;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.service.BusinessService;
import cn.gilight.dmm.xg.entity.TCampus;
import cn.gilight.dmm.xg.entity.TCodeCampusClass;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;
import cn.gilight.framework.uitl.product.EduUtils;
/**
 * 校区基本信息以及校区班级对应关系
 * @author hanpl
 */
@Service("campusAndClassJob")
public class CampusAndClassJob {
	@Resource
	private BaseDao baseDao;
	@Resource
	private HibernateDao hibernateDao;
	@Resource
	private BusinessService businessService;
	@Resource
	private BusinessDao businessDao;
	
	private Logger log = Logger.getLogger(this.getClass());
	@Transactional
	public JobResultBean doCampusClass(){
		String jobName = "校区班级对应关系";
		baseDao.delete("Delete from T_CODE_CAMPUS_CLASS");
		info("已删除校区班级对应表");
		int schoolYear = EduUtils.getSchoolYear4(); 
		Long beginTime = System.currentTimeMillis();
		//定义结果
		JobResultBean result = new JobResultBean();
		begin("#"+jobName+"#");
		String stu = businessDao.getStuSql(schoolYear, null, null);
		String str ="select id,path_ as qxm from t_dorm where level_type ='XQ'";
		StringBuffer str1 = new StringBuffer();StringBuffer str2 = new StringBuffer();
		List<Map<String,Object>> tempList = baseDao.queryListInLowerKey(str);
		if (tempList==null||tempList.size()==0){
			result.setIsTrue(false);
			result.setMsg("宿舍层次结构中不存在校区数据");
			return result;
		}
		String sql = "";String sql1 ="";
		for(int i=0;i<tempList.size();i++){
			String qxm = MapUtils.getString(tempList.get(i), "qxm");
			String id = MapUtils.getString(tempList.get(i), "id");
			String a ="(select a.class_id,count(*) as value ";
			String b ="(select a.class_id,'"+id+"'as campus_id, count(*) as value ";
			String c =" from ("+stu+") a left join t_dorm_berth_stu b on a.no_ = b.stu_id"
					 + " left join t_dorm_berth c on b.berth_id = c.id left join t_dorm d on c.dorm_id = d.id "
					 + " where a.stu_roll_code='1' and d.path_ like '"+qxm+"%' group by a.class_id)";
			String str3 = a+c;
			String str4 = b+c; 
			if(i==0){
				sql =str3;
				sql1 =str4;
			}else{
				sql = " union " +str3;
				sql1 = " union "+str4;
			}
			str1.append(sql);
			str2.append(sql1);
		}
		String xq = "select yxq.campus_id,wxq.class_id from (select class_id,max(value) as value from ("+str1.toString()+") group by class_id) wxq inner join ("
				+str2.toString()+") yxq on wxq.class_id = yxq.class_id and wxq.value = yxq.value ";
		try {
			List<TCodeCampusClass> list = baseDao.queryForListBean(xq, TCodeCampusClass.class);
			hibernateDao.saveAll(list);
			String info = jobName+"结束执行,共耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
			end(info);
			result.setIsTrue(true);
			result.setMsg(info);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			String info = "#"+jobName+"#数据保存出错,"+e.getStackTrace();
			error(info);
			result.setIsTrue(false);
			result.setMsg(info);
			e.printStackTrace();
		}
		return result;
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
	@Transactional
	public JobResultBean doCampus(){
		String jobName = "校区基本信息";
		baseDao.delete("Delete from T_CODE_CAMPUS");
		info("已删除校区基本信息表");
		Long beginTime = System.currentTimeMillis();
		//定义结果
		JobResultBean result = new JobResultBean();
		begin("#"+jobName+"#");
		String sql ="select id,name_,istrue from t_dorm where level_type ='XQ'";
		try {
			List<TCampus> list = baseDao.queryForListBean(sql, TCampus.class);
			hibernateDao.saveAll(list);
			String info = jobName+"结束执行,共耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
			end(info);
			result.setIsTrue(true);
			result.setMsg(info);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			String info = "#"+jobName+"#数据保存出错,"+e.getStackTrace();
			error(info);
			result.setIsTrue(false);
			result.setMsg(info);
			e.printStackTrace();
		}
		return result;
	}
}
