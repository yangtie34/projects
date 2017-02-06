package cn.gilight.research.xkjs.service;


public interface InitXkDataService {
	
	//承担国家级科研项目（项）  id:1
	public void updateNationProject(String year);
	
	//承担国家级科研项目经费（万元） id:2
	public void updateNationProjectFund(String year);
	
	//承担省部级科研项目（项） id:3
	public void updateProvinceProject(String year);
	
	//承担省部级科研项目经费（万元） id:4
	public void updateProvinceProjectFund(String year);
	
	//在国内外核心刊物发表论文（篇） id : 15
	public void updateThesisCSSCI(String year);
	
	//其中被SCI/SSCI/AHCI、EI收录的论文（篇）  id :16
	public void updateThesisIn(String year);
	
	//出版专著（部） id:17
	public void updateWork(String year);
	
	//获得专利（项） id:18
	public void updatePatent(String year);
	
	//获省部级以上科研成果奖励（项） id:20
	public void updateAchievementAward(String year);
	
	//购置万元以上仪器设备（件、台） id:37
	public void updatePurchaseEquipment(String year);
	
	//仪器设备总值（万元） id:38
	public void updatePurchaseEquipmentFund(String year);
	
	
	
	
	
	
	
}
