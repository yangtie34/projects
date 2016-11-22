package cn.gilight.framework.school.service;

public interface SchoolService {
	
	/**
	 * 通过学年学期获取开学时间
	 * @param year 学年
	 * @param term 学期
	 * @return 开学时间 yyyy-MM-dd
	 */
	public String getStartSchool(String year,String term);

}
