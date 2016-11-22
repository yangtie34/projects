package cn.gilight.personal.student.paisan.service;

import java.util.Map;

import cn.gilight.framework.page.Page;

public interface PaisanService {

	/**
	 * 获取带筛选的老乡
	 * @param stu_id
	 * @param stu_name
	 * @param flag
	 * @param page
	 * @return
	 */
	public Page getPaisan(String stu_id, String stu_name, String flag, Page page);

	/**
	 * 获取老乡信息
	 * @param stu_id
	 * @return
	 */
	public Map<String, Object> getPaisanStu(String stu_id);

}
