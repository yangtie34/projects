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
	NET_TEA_WARN("hq:net:netTeaWarn:*","教师上网账号异常"),
	NET_TEA_RANK("hq:net:netTeaRank:*","教师上网排名"),
	NET_TEA("hq:net:netTea:*","教师上网情况"),
	
	


	
	//下钻shiro_tag
	YQSB_SBXZ("hq:yqsb:sbxz:*","设备下钻"),
	YQSB_RYXZ("hq:yqsb:ryxz:*","人员下钻"),
	BOOK_INFO_TSXZ("hq:book:info:tsxz:*","图书下钻"),
	BOOK_INFO_DZXZ("hq:book:info:dzxz:*","读者下钻"),
	BOOK_READ_JYXZ("hq:book:read:jyxz:*","借阅下钻"),
	BOOK_READRANK_JYXZ("hq:book:readRank:jyxz:*","借阅下钻"),
	BOOK_OVERDUE_JYXZ("hq:book:overdue:jyxz:*","借阅下钻"),
	BOOK_OVERDUERANK_JYXZ("hq:book:overdueRank:jyxz:*","借阅下钻"),
	BOOK_READSTU_JYXZ("hq:book:readStu:jyxz:*","借阅下钻"),
	BOOK_OVERDUESTU_JYXZ("hq:book:overdueStu:jyxz:*","借阅下钻"),
	DORM_DORMEMPLOY_SSXZ("hq:dorm:dormEmploy:ssxz:*","宿舍下钻"),
	DORM_DORMEMPLOY_CWXZ("hq:dorm:dormEmploy:cwxz:*","床位下钻"),
	NET_NETTEAWARN_IPXZ("hq:net:netTeaWarn:ipxz:*","ip下钻");

	
	
	
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