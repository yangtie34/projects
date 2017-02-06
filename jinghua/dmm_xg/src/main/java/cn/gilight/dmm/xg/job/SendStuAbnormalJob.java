package cn.gilight.dmm.xg.job;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.service.BusinessService;
import cn.gilight.dmm.business.util.PmsUtils;
import cn.gilight.dmm.xg.dao.StuCostDao;
import cn.gilight.dmm.xg.service.StuHighCostService;
import cn.gilight.dmm.xg.service.StuLowCostService;
import cn.gilight.dmm.xg.util.MailUtils;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;

import com.jhnu.syspermiss.permiss.entity.JobResultBean;

import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.ExcelUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.product.EduUtils;

@Service("sendStuAbnormalJob")
public class SendStuAbnormalJob {
	@Resource
	private BaseDao baseDao;
	@Resource
	private HibernateDao hibernateDao;
	@Resource
	private BusinessService businessService;
	@Resource
	private BusinessDao businessDao;
	@Resource
	private StuHighCostService stuHighCostService;
	@Resource
	private StuLowCostService stuLowCostService;
	@Resource
	private StuCostDao stuCostDao;
	
	private Logger log = Logger.getLogger(this.getClass());
	@Transactional
	public JobResultBean sendHighCost(){
		String jobName= "上月高消费学生名单发送";
		JobResultBean result = new JobResultBean();
		begin("#"+jobName+"#");
	    List<Map<String,Object>> list = stuHighCostService.getMonthList();
	    String[] timeAry = MapUtils.getString(list.get(0), "id").split(",");
	    int month =Integer.parseInt(timeAry[2]);
	    String[] ary = timeAry[0].split("~");
	    String start = ary[0],end = ary[1];
	    Map<String,Object> sss = stuHighCostService.getCostCode();
	    String[] type = (String[]) MapUtils.getObject(sss, "type");
	    String type_ =  MapUtils.getString(sss, "abnormal");
	    List<String> deptList = PmsUtils.getPmsAll();
	    List<Map<String,Object>> list1 =  businessDao.queryYxList(deptList);
	    for(Map<String,Object> temp :list1){
	    	String pid = MapUtils.getString(temp, "id");
	    	String deptname = MapUtils.getString(temp, "name");
	    result = getExportData(start,end,month,pid,deptname,type,type_);
	    }
		return result;
	}
	@Transactional
	public JobResultBean sendLowCost(){
		String jobName= "上月低消费学生名单发送";
		JobResultBean result = new JobResultBean();
		begin("#"+jobName+"#");
		  List<Map<String,Object>> list = stuHighCostService.getMonthList();
		  String[] timeAry = MapUtils.getString(list.get(0), "id").split(",");
		    int month =Integer.parseInt(timeAry[2]);
		    String[] ary = timeAry[0].split("~");
		    String start = ary[0],end = ary[1];
		    Map<String,Object> sss = stuLowCostService.getCostCode();
		    String[] type = (String[]) MapUtils.getObject(sss, "type");
		    String type_ =  MapUtils.getString(sss, "abnormal");
		    List<String> deptList = PmsUtils.getPmsAll();
	    List<Map<String,Object>> list1 =  businessDao.queryYxList(deptList);
	    for(Map<String,Object> temp :list1){
	    	String pid = MapUtils.getString(temp, "id");
	    	String deptname = MapUtils.getString(temp, "name");
	    result = getExportData(start,end,month,pid,deptname,type,type_);
	    }
		return result;
	}
	private void begin(String info){
		log.warn("======== begin["+DateUtils.getNowDate2()+"]: "+info+" 初始化 ========");
	}
	private void info(String info){
		log.warn("======== info["+DateUtils.getNowDate2()+"]: "+info+" ========");
	}
	private void end(String info){
		log.warn("======== end["+DateUtils.getNowDate2()+"]: "+info+" ========");
	}
	private void error(String info){
		log.warn("======== error["+DateUtils.getNowDate2()+"]: "+info+" 停止执行========");
	}
	
	@SuppressWarnings("unchecked")
	private JobResultBean getExportData(String schoolYear,String termCode ,int month,String pid,String deptname,String[] type,String type_){
		String title=deptname+type[3];
		Map<String,Object> codes = stuHighCostService.getCostCode();
		List<String> deptList =businessService.packageDeptListById(pid);
		String filename=System.getProperty("user.dir")+"/"+title+type[2]+".xls";
		List<Map<String,Object>> list = stuCostDao.queryExportData(schoolYear, termCode, month, type, deptList, new ArrayList<AdvancedParam>(),type_);
		 	String emailFileName = null;
		    List<Map<String,Object>> emailList=stuCostDao.queryEmailList();//得到所有院系的email
		    for(int i=0;i<emailList.size();i++){
		    	if(pid.equals(MapUtils.getString(emailList.get(i), "ID"))){
		    		emailFileName=MapUtils.getString(emailList.get(i), "EMAIL");
		    	}
		    }
		    HSSFWorkbook workBook = ExcelUtils.createExcel(list,(List<String>)MapUtils.getObject(codes, "field"), (List<String>)MapUtils.getObject(codes, "heard"),  title+type[2]+"名单"); 
			try {
				FileOutputStream os = new FileOutputStream(new String(filename.getBytes("utf-8"),"utf-8"));
				workBook.write(os);
				os.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Map<String,Object> reMap=new HashMap<>();
			reMap.put("fh", "发送失败!");
			reMap.put("status", 0);
			
				try {
					if(MailUtils.send(emailFileName,filename,title)){
						reMap.put("fh", "发送成功!");
						reMap.put("status", 1);
					}else{
					
					}
				} catch (Exception e) {
					
				}
			JobResultBean result = stuHighCostService.saveSendStatus(schoolYear, termCode, month, MapUtils.getIntValue(reMap, "status"), pid, title, type);
			return result;
	}
}
