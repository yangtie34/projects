package cn.gilight.product.dorm.dao;


public interface DormJobDao {
	
	/**
	 * 初始化学生住宿情况
	 * @return
	 */
	public int dormStuJob();
	
	/**
	 * 初始化住宿学生的门禁使用情况
	 * @return
	 */
	public int dormRkeUsedStuJob(String yearMonth);
	
	
	/**
	 * 保存住宿趋势
	 * @param yearMonth
	 * @return
	 */
	public int dormTrend(String yearMonth);
	
}
