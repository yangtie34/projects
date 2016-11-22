package cn.gilight.personal.student.concat.dao;

import java.util.List;
import java.util.Map;

public interface ConcatDao {

	public List<Map<String,Object>> getConcat(String stu_id,String school_year,String term_code,String param);
}
