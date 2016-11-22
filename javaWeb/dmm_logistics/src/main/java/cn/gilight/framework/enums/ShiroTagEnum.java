package cn.gilight.framework.enums;

public enum ShiroTagEnum {
	
	CARD_RE("hq:card:recharge:*","一卡通充值"),
	CARD_CU("hq:card:cardEmploy:*","学生一卡通使用状况分析"),
	CARD_DR("hq:card:byPort","餐厅窗口分析"),
	CARD_HPT("hq:card:fxByXflx","分消费类型习惯分析"),
	CARD_HST("hq:card:fxByType","分学生类型习惯分析"),
	CARD_PP("hq:card:payAbility:*","学生消费能力"),
	BOOK_RS("hq:book:readStu:*","图书学生借阅分析"),
	BOOK_RDT("hq:book:readRank:DeptTeach:*","图书借阅排名教学组织机构"),
	BOOK_RD("hq:book:readRank:Dept:*","图书借阅排名组织机构"),
	BOOK_ODT("hq:book:overdueRank:DeptTeach:*","图书逾期排名教学组织机构"),
	BOOK_OS("hq:book:overdueStu:*","图书学生逾期分析"),
	BOOK_OD("hq:book:overdueRank:Dept:*","图书逾期排名组织机构"),
	BOOK_LR("hq:book:LibraryRke:*","图书馆门禁分析"),
	BOOK_SL("hq:book:StuBookRke:*","学生出入图书馆分析"),
	DORM_STU("hq:dorm:dormStu:*","学生住宿分布"),
	DORM_RKE_STU("hq:dorm:dormRke:*","学生住宿门禁分布"),
	NET_TYPE("hq:net:netType:*","上网类型分析"),
	NET_STU("hq:net:netStu:*","学生上网习惯分析"),
	NET_STU_WARN("hq:net:netStuWarn:*","学生上网预警"),
	NET_STU_DEPT("hq:net:netStuDept:*","各院系学生上网对比"),
	NET_TEA_WARN("hq:net:netTeaWarn:*","教师上网账号异常");
	
	
    private String code;  
    private String name;
    private ShiroTagEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    
    
}  