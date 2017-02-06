package cn.gilight.research.kyjl.service;

public interface RefreshResultService {

	/**
	 * 刷新高层次项目立项奖励结果
	 * @param year
	 */
	public void refreshProjectSetup(String year);

	/**
	 * 刷新高层次项目结项奖励结果
	 * @param year
	 */
	public void refreshProjectEnd(String year);

	/**
	 * 刷新高层次获奖成果奖励结果
	 * @param year
	 */
	public void refreshAchievementAward(String year);

	/**
	 * 刷新高层次学术论文奖励结果
	 * @param year
	 */
	public void refreshThesisAward(String year);

	/**
	 * 刷新发明专利奖励结果
	 * @param year
	 */
	public void refreshPatentAward(String year);

	/**
	 * 刷新科研经费奖励结果
	 * @param year
	 */
	public void refreshProjectFundAward(String year);
	
	/**
	 * 刷新科研成果转化奖结果
	 * @param year
	 */
	public void refreshTransform(String year);
	
	

}
