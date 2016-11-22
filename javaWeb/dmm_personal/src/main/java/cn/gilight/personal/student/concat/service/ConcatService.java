package cn.gilight.personal.student.concat.service;

import java.util.List;
import java.util.Map;

public interface ConcatService {
	
	public List<Map<String,Object>> getConcat(String stu_id,String param);
}
