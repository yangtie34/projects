package cn.gilight.personal.student.book.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.personal.student.book.dao.BookDao;

@Repository("bookDao")
public class BookDaoImpl implements BookDao{
	
	@Autowired
	private BaseDao baseDao;

	@Override
	public Page getBorrowList(String stu_id,Page page) {
		String date = DateUtils.getNowDate();
		String sql  = "select book.name_ book_name,t.borrow_time,"
         +" case when t.return_time is null then '应还' "
         +" when t.return_time is not null then '归还' end as time_type,"
         +" case when t.return_time is null then  t.should_return_time"
         +" when t.return_time is not null then t.return_time end as time_ ,"
         +" case when t.return_time is null and t.should_return_time < '"+ date +"' then '超期'"
         +" when t.return_time is null and t.should_return_time > '"+ date +"' then '在阅'"
         +" else '已还' end as flag from t_book_borrow t left join t_book_reader br on br.no_ = t.book_reader_id "
         +" left join t_book book on book.no_ = t.book_id"
         +" where br.people_id = '"+ stu_id +"' order by t.borrow_time desc";
		return baseDao.queryWithPageInLowerKey(sql, page);
	}

	@Override
	public int getBorrowCounts(String stu_id) {
		String sql = "select * from t_book_borrow t left join t_book_reader br on t.book_reader_id = br.no_ where br.people_id = '"+ stu_id +"'";
		return baseDao.queryForInt(sql);
	}
	@Override
	public Map<String,Object> getBorrowProportion(String stu_id){
		String sql = "select round(count(case when mynum > nums then 1 else null end)/count(*),2)*100||'%' proportion from (select no_,nums,mynum from ("
				+ " select tt.no_,nvl(dd.nums,0) nums ,sd.mynum from ("
				+ " select * from t_stu stu where stu.enroll_grade = (select s.enroll_grade from t_stu s where s.no_ = '"+ stu_id +"')) tt "
				+ " left join (select br.people_id,count(*) nums from t_book_borrow bb left join t_book_reader br on bb.book_reader_id = br.no_ group by br.people_id) dd "
				+ " on tt.no_ = dd.people_id left join (select br.people_id,count(*) mynum from t_book_borrow bb "
				+ " left join t_book_reader br on br.no_ = bb.book_reader_id "
				+ " where br.people_id is not null and br.people_id = '"+ stu_id +"' group by br.people_id) sd on 1=1 ))" ;
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		if(list!= null && list.size()>0){
			return list.get(0); 
		}
		return null;
	}

	@Override
	public List<Map<String,Object>> getRecommendBook(String stu_id) {
		String sql = "select rb.store_name,rb.book_name name_,rb.counts from (select tt.store_code ,tt.sums,rownum rm from ("
				+ " select book.store_code,count(*) sums from t_book_borrow t left join t_book_reader br on t.book_reader_id = br.no_"
				+ " left join t_book book on book.no_ = t.book_id where br.people_id = '"+stu_id+"' group by book.store_code order by "
				+ " sums desc) tt) dd right join t_recommend_book rb on rb.store_code = dd.store_code  where dd.rm<=3 order by counts desc";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public List<Map<String, Object>> getBorrowType(String stu_id) {
		String sql = "select co.name_ as name,count(*) as value from t_book_borrow t"
				+ " left join t_book_reader br on t.book_reader_id = br.no_ "
				+ " left join t_book book on book.no_ = t.book_id "
				+ " left join t_code co on co.code_type = 'BOOK_STORE_CODE' and co.code_ = book.store_code "
				+ " where br.people_id = '"+stu_id+"' group by co.name_";
		return baseDao.queryListInLowerKey(sql);
	}
}
