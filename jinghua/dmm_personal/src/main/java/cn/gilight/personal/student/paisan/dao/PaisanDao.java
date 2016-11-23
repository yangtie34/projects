package cn.gilight.personal.student.paisan.dao;

import java.util.Map;

import cn.gilight.framework.page.Page;

public interface PaisanDao {

	public Page getPaisan(String stu_id, String stu_name, String flag, Page page);

	public Map<String, Object> getPaisanStu(String stu_id);

}
