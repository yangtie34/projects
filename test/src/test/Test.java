package test;

import java.util.Map;

public class Test {
	public static void main(String[] args) {
		new Test().main();
	}
	public void main(){
		/*for(int i=1;i<20;i++){
			System.out.println("select t.id+9000*"+i+" id,t.book_id,t.book_reader_id,"+
"to_char(add_months(to_date(t.borrow_time,'yyyy-MM-dd hh24:mi:ss'),"+i+"),'yyyy-MM-dd hh24:mi:ss') borrow_time,"+
"to_char(add_months(to_date(t.should_return_time,'yyyy-MM-dd hh24:mi:ss'),"+i+"),'yyyy-MM-dd hh24:mi:ss') should_return_time,"+
"to_char(add_months(to_date(t.return_time,'yyyy-MM-dd hh24:mi:ss'),"+i+"),'yyyy-MM-dd hh24:mi:ss') return_time,"+
"to_char(add_months(to_date(t.renew_time,'yyyy-MM-dd hh24:mi:ss'),"+i+"),'yyyy-MM-dd hh24:mi:ss') renew_time,"+
"t.renew_count "+
"from t_book_borrow t "+
"where substr(borrow_time,0,7)='2015-04' union all ");
		}*/
		int i=5;
		int j=i++;
		System.out.println(j+","+i);
	}
	public Map<String,Object> clone(Map<String,Object> map){
		Map<String,Object> map1 =map;
		return map;
	}
}
