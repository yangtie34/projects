package com.yiyun.wasteoilcustom.model;


import com.chengyi.android.util.Convert;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class CompanyUser_Model  implements KvmSerializable {
	//以下是生成的字段
	private long ComCusID = 0;
	private long ComCusSort = 0;
	private long ComCusGrade = 0;
	private String ComCusNumber = "";
	private int LoginMode = 0;
	private String LoginAccount = "";
	private String LoginPwd = "";
	private String Email = "";
	private String Mobile = "";
	private String IdCard = "";
	private String Fullname = "";
	private String Phone = "";
	private String Sex = "";
	private String Photos = "";
	private String WeiXinNumber = "";
	private String CantonRegion = "";
	private long Area = 0;
	private String Address = "";
	private String ComName = "";
	private long BankID = 0;
	private String BankCardName = "";
	private String BankCardNumber = "";
	private String BankOpenName = "";
	private long BankOpenArea ;
	private boolean IsCheckEmail ;
	private boolean IsCheckMobile;
	private boolean IsCheckBankCard;
	private long VestingComID ;
	private long VestingComBrID ;
	private int AuditState ;
	private String AuditCause ;
	private boolean Disabled ;
	private boolean FlagDelete ;
	private long DownloadTime ;
	private long CreateUserID ;
	private long CreateComBrID ;
	private String CreateComID = "";
	private String CreateIp = "";
	private boolean CreateTime;
	private boolean Exist ;

	@Override
	public Object getProperty(int i) {
		return null;
	}

	@Override
	public int getPropertyCount() {
		return 0;
	}

	@Override
	public void setProperty(int i, Object o) {

	}

	@Override
	public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {

	}

	public String getCantonRegion() {
		return CantonRegion;
	}

	public void setCantonRegion(String cantonRegion) {
		CantonRegion = cantonRegion;
	}

	public long getArea() {
		return Area;
	}

	public void setArea(long area) {
		Area = area;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getComName() {
		return ComName;
	}

	public void setComName(String comName) {
		ComName = comName;
	}

	public long getBankID() {
		return BankID;
	}

	public void setBankID(long bankID) {
		BankID = bankID;
	}

	public String getBankCardName() {
		return BankCardName;
	}

	public void setBankCardName(String bankCardName) {
		BankCardName = bankCardName;
	}

	public String getBankCardNumber() {
		return BankCardNumber;
	}

	public void setBankCardNumber(String bankCardNumber) {
		BankCardNumber = bankCardNumber;
	}

	public String getBankOpenName() {
		return BankOpenName;
	}

	public void setBankOpenName(String bankOpenName) {
		BankOpenName = bankOpenName;
	}

	public long getBankOpenArea() {
		return BankOpenArea;
	}

	public void setBankOpenArea(long bankOpenArea) {
		BankOpenArea = bankOpenArea;
	}

	public boolean isCheckEmail() {
		return IsCheckEmail;
	}

	public void setCheckEmail(boolean checkEmail) {
		IsCheckEmail = checkEmail;
	}

	public boolean isCheckMobile() {
		return IsCheckMobile;
	}

	public void setCheckMobile(boolean checkMobile) {
		IsCheckMobile = checkMobile;
	}

	public boolean isCheckBankCard() {
		return IsCheckBankCard;
	}

	public void setCheckBankCard(boolean checkBankCard) {
		IsCheckBankCard = checkBankCard;
	}

	public long getVestingComID() {
		return VestingComID;
	}

	public void setVestingComID(long vestingComID) {
		VestingComID = vestingComID;
	}

	public long getVestingComBrID() {
		return VestingComBrID;
	}

	public void setVestingComBrID(long vestingComBrID) {
		VestingComBrID = vestingComBrID;
	}

	public int getAuditState() {
		return AuditState;
	}

	public void setAuditState(int auditState) {
		AuditState = auditState;
	}

	public String getAuditCause() {
		return AuditCause;
	}

	public void setAuditCause(String auditCause) {
		AuditCause = auditCause;
	}

	public boolean isDisabled() {
		return Disabled;
	}

	public void setDisabled(boolean disabled) {
		Disabled = disabled;
	}

	public boolean isFlagDelete() {
		return FlagDelete;
	}

	public void setFlagDelete(boolean flagDelete) {
		FlagDelete = flagDelete;
	}

	public long getDownloadTime() {
		return DownloadTime;
	}

	public void setDownloadTime(long downloadTime) {
		DownloadTime = downloadTime;
	}

	public long getCreateUserID() {
		return CreateUserID;
	}

	public void setCreateUserID(long createUserID) {
		CreateUserID = createUserID;
	}

	public long getCreateComBrID() {
		return CreateComBrID;
	}

	public void setCreateComBrID(long createComBrID) {
		CreateComBrID = createComBrID;
	}

	public String getCreateComID() {
		return CreateComID;
	}

	public void setCreateComID(String createComID) {
		CreateComID = createComID;
	}

	public String getCreateIp() {
		return CreateIp;
	}

	public void setCreateIp(String createIp) {
		CreateIp = createIp;
	}

	public boolean isCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(boolean createTime) {
		CreateTime = createTime;
	}

	public boolean isExist() {
		return Exist;
	}

	public void setExist(boolean exist) {
		Exist = exist;
	}

	public long getComCusID() {
		return ComCusID;
	}

	public void setComCusID(long comCusID) {
		ComCusID = comCusID;
	}

	public long getComCusSort() {
		return ComCusSort;
	}

	public void setComCusSort(long comCusSort) {
		ComCusSort = comCusSort;
	}

	public long getComCusGrade() {
		return ComCusGrade;
	}

	public void setComCusGrade(long comCusGrade) {
		ComCusGrade = comCusGrade;
	}

	public String getComCusNumber() {
		return ComCusNumber;
	}

	public void setComCusNumber(String comCusNumber) {
		ComCusNumber = comCusNumber;
	}

	public int getLoginMode() {
		return LoginMode;
	}

	public void setLoginMode(int loginMode) {
		LoginMode = loginMode;
	}

	public String getLoginAccount() {
		return LoginAccount;
	}

	public void setLoginAccount(String loginAccount) {
		LoginAccount = loginAccount;
	}

	public String getLoginPwd() {
		return LoginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		LoginPwd = loginPwd;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getMobile() {
		return Mobile;
	}

	public void setMobile(String mobile) {
		Mobile = mobile;
	}

	public String getIdCard() {
		return IdCard;
	}

	public void setIdCard(String idCard) {
		IdCard = idCard;
	}

	public String getFullname() {
		return Fullname;
	}

	public void setFullname(String fullname) {
		Fullname = fullname;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public String getSex() {
		return Sex;
	}

	public void setSex(String sex) {
		Sex = sex;
	}

	public String getPhotos() {
		return Photos;
	}

	public void setPhotos(String photos) {
		Photos = photos;
	}
}