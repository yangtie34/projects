package cn.gilight.personal.student.four.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test.test1.util.MapUtils;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.uitl.common.MathUtils;
import cn.gilight.personal.student.four.service.FourBookService;


/**
 * 
* @Description: TODO
* @author Chenxt
* @date 2016年7月8日 下午3:35:04
 */
@Service("fourBookService")
public  class FourBookServiceImpl implements FourBookService{
	@Autowired
	private BaseDao baseDao;
	
	
	@Override
	public Map<String ,Object> borrowBook(String username){
		Map<String ,Object> resultMap=new HashMap<String ,Object>();
		//username借书数量
		String sql1 ="select count(*) js from t_book_borrow t left join t_book_reader br on br.no_ = t.book_reader_id where br.people_id = '"+username+"'";
		List<Map<String, Object>> amountList = baseDao.queryListInLowerKey(sql1);
		int js = MapUtils.getIntValue(amountList.get(0), "js");
		resultMap.put("js", js);
		
		//username同届人均借书
		String sql2="select round(js.borrow / tt.rs,2) rj "
                   +" from (select count(*)borrow"
                   +" from (select tbr.people_id people_id, tbb.book_id book"
                   +" from t_book_borrow tbb"
                   +" left join t_book_reader tbr"
                   +" on tbr.no_ = tbb.book_reader_id ) br"
                   +" left join t_stu tst"
                   +" on tst.no_ = br.people_id"
                   +" where tst.enroll_grade ="
                   +" (select s.enroll_grade from t_stu s where s.no_ = '"+username+"')) js "
                   +" left join (select count(stu.no_) rs "
                   +" from t_stu stu "
                   +" where stu.enroll_grade = "
                   +" (select s.enroll_grade "
                   +" from t_stu s "
                   +" where s.no_ = '"+username+"')) tt "
                   +" on 1 = 1 ";
		List<Map<String ,Object>> avgList = baseDao.queryListInLowerKey(sql2);
		int avg = MapUtils.getIntValue(avgList.get(0), "rj");
		resultMap.put("avg", avg);
		
		//比username借书少的人数
		String sql3 = "select count(br.people_id) rs"
			        +" from (select distinct tbr.people_id people_id, count(*) sl"
			        +" from t_book_reader tbr"
			        +" left join t_book_borrow tbb"
			        +" on tbb.book_reader_id = tbr.no_"
			        +" group by tbr.people_id) br"
			        +" left join t_stu ts"
			        +" on ts.no_ = br.people_id"
			        +" where ts.enroll_grade ="
			        +" (select s.enroll_grade from t_stu s where s.no_ = '"+username+"')"
			        +" and br.sl < (select count(*) js"
			        +" from t_book_borrow t"
			        +" left join t_book_reader br"
			        +" on br.no_ = t.book_reader_id"
			        +" where br.people_id = '"+username+"')";
		List<Map<String ,Object>> lessList=baseDao.queryListInLowerKey(sql3);
		int less = MapUtils.getIntValue(lessList.get(0), "rs");
		
		//username同届总人数
		String sql4=" select count(*) zs from t_stu t where t.enroll_grade= (select s.enroll_grade from t_stu s where s.no_='"+username+"')";
		List<Map<String ,Object>> sumList=baseDao.queryListInLowerKey(sql4);
		int total=MapUtils.getIntValue(sumList.get(0), "zs");
		
		String numLess = "";
		if(less==0){
			numLess="0%";
		}else{
			numLess=MathUtils.getPercent(less, total);
		}
		resultMap.put("numLess", numLess);
		
		//username借的频率最高的书
		String sql5="SELECT T.SM, T.SL"
                   +" FROM (SELECT TB.NAME_ SM, COUNT(*) SL"
                   +" FROM (SELECT TBR.PEOPLE_ID PEOPLE_ID, TBB.BOOK_ID BOOK_ID"
                   +" FROM T_BOOK_BORROW TBB"
                   +" LEFT JOIN T_BOOK_READER TBR"
                   +" ON TBR.NO_ = TBB.BOOK_READER_ID"
                   +" WHERE TBR.PEOPLE_ID = '"+username+"') BR"
                   +" LEFT JOIN T_BOOK TB"
                   +" ON BR.BOOK_ID = TB.NO_"
                   +" GROUP BY TB.NAME_"
                   +" ORDER BY SL DESC) T"
                   +" WHERE ROWNUM = 1";
		List<Map<String ,Object>> rateList=baseDao.queryListInLowerKey(sql5);
		String sm="";
		int sl=0;
		if(rateList!=null && rateList.size()>0){
			sm=MapUtils.getString(rateList.get(0) , "sm");
			sl=MapUtils.getIntValue(rateList.get(0), "sl");
		}else{
			sm = "无";
			sl = 0;
		}
		resultMap.put("sm", sm);
		resultMap.put("sl", sl);
		
		return resultMap;
	}

	//我的借阅与人均借阅
	@Override
	public List<Map<String, Object>> myBorrow(String username) {
		String sql="select a.school_year xn, a.term_code xq, a.nums my, b.avg_ pj"
                  +"  from (select tt.school_year, tt.term_code, count(*) nums"
                  +"    from (select t.id,t.book_id,t.borrow_time,"
                  +"  CASE WHEN SUBSTR(BORROW_TIME, 6, 2) >= '07' THEN"
                  +"   SUBSTR(BORROW_TIME, 1, 4) || '-' ||"
                  +"   (TO_NUMBER(SUBSTR(BORROW_TIME, 1, 4)) + 1)"
                  +"  ELSE"
                  +"   (TO_NUMBER(SUBSTR(BORROW_TIME, 1, 4)) - 1) || '-' ||"
                  +"   SUBSTR(BORROW_TIME, 1, 4)"
                  +"  END SCHOOL_YEAR,"
                  +"  CASE WHEN SUBSTR(BORROW_TIME, 6, 2) >= '07' THEN"
                  +"     '01'"
                  +"    ELSE"
                  +"     '02'"
                  +"  END TERM_CODE"
                  +"  from t_book_borrow t"
                  +"  left join t_book_reader br"
                  +"    on br.no_ = t.book_reader_id"
                  +" where br.people_id = '"+username+"') tt"
                  +"   group by tt.school_year, tt.term_code) a"
                  +"  left join (select tt.school_year,"
                  +"       tt.term_code,"
                  +"       nvl(round(tt.nums / td.total, 2), 0) avg_"
                  +"  from (select ts.school_year, ts.term_code, count(*) nums"
                  +"  from (select t.id,"
                  +"  CASE WHEN SUBSTR(BORROW_TIME, 6, 2) >= '07' THEN"
                  +"     SUBSTR(BORROW_TIME, 1, 4) || '-' ||"
                  +"     (TO_NUMBER(SUBSTR(BORROW_TIME, 1, 4)) + 1)"
                  +"    ELSE"
                  +"     (TO_NUMBER(SUBSTR(BORROW_TIME, 1, 4)) - 1) || '-' ||"
                  +"     SUBSTR(BORROW_TIME, 1, 4)"
                  +"  END SCHOOL_YEAR,"
                  +"  CASE WHEN SUBSTR(BORROW_TIME, 6, 2) >= '07' THEN"
                  +"  '01'"
                  +" ELSE"
                  +"  '02'"
                  +"       END TERM_CODE"
                  +"  from t_book_borrow t"
                  +"  left join t_book_reader br"
                  +"    on br.no_ = t.book_reader_id"
                  +"  left join t_stu stu"
                  +"     on stu.no_ = br.people_id"
                  +"  where stu.enroll_grade ="
                  +" (select s.enroll_grade"
                  +"    from t_stu s"
                  +"   where s.no_ = '"+username+"')) ts"
                  +"  group by ts.school_year, ts.term_code) tt"
                  +"  left join (select count(*) total"
                  +"   from t_stu stu"
                  +"  where stu.enroll_grade ="
                  +" (select s.enroll_grade"
                  +"  from t_stu s"
                  +" where s.no_ = '"+username+"')) td"
                  +"  on 1 = 1) b"
                  +"  on a.school_year = b.school_year"
                  +"   and a.term_code = b.term_code"
                  +" order by a.school_year, a.term_code";
		return baseDao.queryListInLowerKey(sql);
	}
	//进出图书馆
	@Override
	public Map<String, Object> inOutLibr(String username) {
		Map<String ,Object> resultMap=new HashMap<String, Object>();
		//某生进出图书馆次数
	    String sql1="SELECT COUNT(*) PASS FROM T_STU TS LEFT JOIN T_BOOK_RKE TBR ON TBR.BOOK_READER_ID=TS.NO_ WHERE TS.NO_='"+username+"'";
	    List<Map<String , Object>> myList=baseDao.queryListInLowerKey(sql1);
	    int pass=MapUtils.getIntValue(myList.get(0), "pass");
	    resultMap.put("pass", pass);
	    //同届进出图书馆次数少于某生的人数
	    String sql2="SELECT COUNT(*) SY"
                   +" FROM (SELECT T.BOOK_READER_ID, COUNT(*) COUNT_"
                   +" FROM T_BOOK_RKE T"
                   +" LEFT JOIN T_BOOK_READER TB"
                   +" ON T.BOOK_READER_ID = TB.NO_"
                   +" GROUP BY T.BOOK_READER_ID) TBR"
                   +" LEFT JOIN T_STU TS"
                   +" ON TS.NO_ = TBR.BOOK_READER_ID"
                   +" WHERE TS.ENROLL_GRADE ="
                   +" (SELECT STU.ENROLL_GRADE"
                   +" FROM T_STU STU"
                   +" WHERE STU.NO_ = '"+username+"')"
                   +" AND TBR.COUNT_ < (SELECT COUNT(*)"
                   +" FROM T_BOOK_RKE TBR"
                   +" LEFT JOIN T_BOOK_READER TS"
                   +" ON TS.NO_ = TBR.BOOK_READER_ID"
                   +" WHERE TS.PEOPLE_ID = '"+username+"')";
	    List<Map<String , Object>> otherList=baseDao.queryListInLowerKey(sql2);
	    int sy=MapUtils.getIntValue(otherList.get(0), "sy");
	    //同届总人数
	    String sql3="SELECT  COUNT(*) ZS FROM T_STU S WHERE S.ENROLL_GRADE=( SELECT STU.ENROLL_GRADE FROM T_STU STU WHERE STU.NO_='"+username+"')";
	    List<Map<String , Object>> allList=baseDao.queryListInLowerKey(sql3);
	    int zs=MapUtils.getIntValue(allList.get(0), "zs");
	    String nums="";
	    if(sy==0){
	    	nums="0%";
	    }else{
	    	nums=MathUtils.getPercent(sy, zs);
	    }
	    resultMap.put("nums", nums);
	    //进出图书馆高峰期
	    String sql4="select jc.tim  hot"
	    		+" from (select tt.td tim, count(*)"
	    		+" from (select t.*,"
	    		+" case"
	    		+" when to_number(substr(t.time_, 12, 2)) between 7 and 9 then"
	    		+" '07:00-10:00'"
	    		+" when to_number(substr(t.time_, 12, 2)) between 10 and 13 then"
	    		+" '10:00-14:00'"
	    		+" when to_number(substr(t.time_, 12, 2)) between 14 and 17 then"
	    		+" '14:00-18:00'"
	    		+" when to_number(substr(t.time_, 12, 2)) between 18 and 21 then"
	    		+" '18:00-22:00'"
	    		+" end td"
	    		+" from t_book_rke t"
	    		+" left join t_book_reader b"
	    		+" on b.no_ = t.book_reader_id"
	    		+" where b.people_id = '"+username+"') tt"
	    		+" group by tt.td"
	    		+" order by count(*) desc) jc"
	    		+" where rownum = 1";
	    List<Map<String , Object>> comList=baseDao.queryListInLowerKey(sql4);
	    String hot="";
		if(comList!=null && comList.size()>0){
			hot=MapUtils.getString(comList.get(0) , "hot");
		}else{
			hot = "无";
		}
		resultMap.put("hot", hot);
	    return resultMap;
	}
	//本届人均进出与我的进出对比
	@Override
	public List<Map<String, Object>> myLibrs(String username) {
		String sql="select a.xn xn, a.xq xq, a.sl my, rj.pj pj"
				  +" from (select tbr.school_year xn, tbr.term_code xq, count(*) sl"
				  +" from t_book_rke tbr"
				  +" where tbr.book_reader_id = '"+username+"'"
				  +" group by tbr.school_year, tbr.term_code) a"
				  +" left join (select bs.xn, bs.xq, round(bs.count_ / rs.total, 2) pj"
				  +" from (select b.school_year xn, b.term_code xq, count(*) count_"
				  +" from t_book_rke b"
				  +" left join t_stu s"
				  +" on s.no_ = b.book_reader_id"
				  +" where s.enroll_grade ="
				  +" (select t.enroll_grade"
				  +" from t_stu t"
				  +" where t.no_ = '"+username+"')"
				  +" group by b.school_year, b.term_code) bs"
				  +" left join (select count(*) total"
				  +" from t_stu st"
				  +" where st.enroll_grade ="
				  +" (select stu.enroll_grade"
				  +" from t_stu stu"
				  +" where stu.no_ = '"+username+"')) rs"
				  +" on 1 = 1) rj"
				  +" on a.xn = rj.xn"
				  +" and a.xq = rj.xq"
				  +" order by a.xn, a.xq";
		return baseDao.queryListInLowerKey(sql);
	}
	
}
