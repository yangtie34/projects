package cn.gilight.personal.job;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;
import com.jhnu.syspermiss.permiss.entity.JobResultBean;
import cn.gilight.framework.uitl.common.MapUtils;

/**   
* @Description: 一卡通消费相关任务 
* @author Sunwg  
* @date 2016年4月11日 下午2:22:13   
*/
@Service("recommendBookInitService")
public class RecommendBookInitService {
	private Logger log = Logger.getLogger(this.getClass());
	
	@Resource
	private BaseDao baseDao;
	@Resource
	private HibernateDao hibernate;
		
	/** 
	* @Description: 一卡通日消费情况统计初始化
	* @return JobResultBean 任务调度返回结果
	*/
	@Transactional
	public JobResultBean recommendBook(){
		log.warn("========begin : 各图书分类推荐图书初始化任务开始执行 ==============");
		Long beginTime = System.currentTimeMillis();
		//定义结果
		JobResultBean result = new JobResultBean();
		String delsql = "DELETE FROM t_recommend_book ";
		int nums = baseDao.delete(delsql);
		log.warn("======== running : 各图书分类推荐图书初始化清空存储表，共"+nums+"条数据 ==============");
		String sql = "select bb.store_code,bb.store_name,bb.name_ book_name,bb.counts,bb.rn from ( "
				+ " select aa.store_code,aa.store_name,aa.name_,aa.counts,rank() over(partition by aa.store_name order by counts desc) rn from ("
				+ " select co.code_ store_code,co.name_ store_name,book.name_,count(*) counts from t_book_borrow t "
				+ " inner join t_book book on book.no_ = t.book_id  "
				+ " left join t_code co on co.code_type = 'BOOK_STORE_CODE' and co.code_ = book.store_code   "
				+ " group by co.code_,co.name_,book.name_ order by counts desc) aa order by store_name ) bb where bb.rn <= 5";
		try {
			final List<Map<String, Object>> datalist = baseDao.queryForList(sql);
			log.warn("======== running : 各图书分类推荐图书初始化开始插入结果，共"+datalist.size() +"条数据 ==============");
			baseDao.batchUpdate(
					"insert into t_recommend_book values(?,?,?,?,?)",
					new BatchPreparedStatementSetter() {    
			           //为prepared statement设置参数。这个方法将在整个过程中被调用的次数    
			        public void setValues(PreparedStatement ps, int i) throws SQLException {    
			                ps.setString(1,MapUtils.getString(datalist.get(i), "STORE_CODE"));
			                ps.setString(2,MapUtils.getString(datalist.get(i), "STORE_NAME"));
			                ps.setString(3,MapUtils.getString(datalist.get(i), "BOOK_NAME")); 
			                ps.setInt(4,MapUtils.getIntValue(datalist.get(i), "COUNTS")); 
			                ps.setInt(5,MapUtils.getIntValue(datalist.get(i), "RN")); 
			              }    
			              //返回更新的结果集条数  
			        public int getBatchSize() {
			        	return datalist.size(); 
			        }    
			 }); 
			result.setIsTrue(true);
			String info = "各图书分类推荐图书初始化结束执行,共耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
			result.setMsg(info);
			log.warn("======== stop :" + info+" ============== ");
		} catch (Exception e) {
			e.printStackTrace();
			result.setIsTrue(false);
			result.setMsg("数据保存出错:"+e.getStackTrace());
			log.error("======== error : 各图书分类推荐图书初始化出现错误，停止执行 ==============");
			e.printStackTrace();
		} 
		return result;
	}
		
}