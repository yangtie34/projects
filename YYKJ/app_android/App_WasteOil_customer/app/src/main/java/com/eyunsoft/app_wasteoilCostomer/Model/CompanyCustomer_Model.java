package com.eyunsoft.app_wasteoilCostomer.Model;



import com.eyunsoft.app_wasteoilCostomer.Publics.Convert;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class CompanyCustomer_Model implements KvmSerializable {
	public CompanyCustomer_Model()
	{
	}
	//以下是生成的字段
	private long ComCusID=0;
	private long ComCusSort=0;
	private long ComCusGrade=0;
	private String ComCusNumber="";
	private int LoginMode=0;
	private String LoginAccount="";
	private String LoginPwd="";
	private String Email="";
	private String Mobile="";
	private String IdCard="";
	private String Fullname="";
	private String Phone="";
	private String Sex="";
	private String Photo="";
	private String WeiXinNumber="";
	private int CantonRegion=0;
	private long Area=0;
	private String Address="";
	private String ComName="";
	private long BankID=0;
	private String BankCardName="";
	private String BankCardNumber="";
	private String BankOpenName="";
	private long BankOpenArea=0;
	private boolean IsCheckEmail=false;
	private boolean IsCheckMobile=false;
	private boolean IsCheckBankCard=false;
	private long VestingComID=0;
	private long VestingComBrID=0;
	private int AuditState=0;
	private String AuditCause="";
	private boolean Disabled=false;
	private boolean FlagDelete=false;
	private String DownloadTime="2016-11-01";
	private long CreateUserID=0;
	private long CreateComBrID=0;
	private long CreateComID=0;
	private String CreateIp="";
	private String CreateTime="2016-11-01";
	private boolean Exist=false;

	//以下是生成的属性
	public  void setComCusID(long comCusID){this.ComCusID=comCusID;}
	public long getComCusID(){ return this.ComCusID;}
	public  void setComCusSort(long comCusSort){this.ComCusSort=comCusSort;}
	public long getComCusSort(){ return this.ComCusSort;}
	public  void setComCusGrade(long comCusGrade){this.ComCusGrade=comCusGrade;}
	public long getComCusGrade(){ return this.ComCusGrade;}
	public  void setComCusNumber(String comCusNumber){this.ComCusNumber=comCusNumber;}
	public String getComCusNumber(){ return this.ComCusNumber;}
	public  void setLoginMode(int loginMode){this.LoginMode=loginMode;}
	public int getLoginMode(){ return this.LoginMode;}
	public  void setLoginAccount(String loginAccount){this.LoginAccount=loginAccount;}
	public String getLoginAccount(){ return this.LoginAccount;}
	public  void setLoginPwd(String loginPwd){this.LoginPwd=loginPwd;}
	public String getLoginPwd(){ return this.LoginPwd;}
	public  void setEmail(String email){this.Email=email;}
	public String getEmail(){ return this.Email;}
	public  void setMobile(String mobile){this.Mobile=mobile;}
	public String getMobile(){ return this.Mobile;}
	public  void setIdCard(String idCard){this.IdCard=idCard;}
	public String getIdCard(){ return this.IdCard;}
	public  void setFullname(String fullname){this.Fullname=fullname;}
	public String getFullname(){ return this.Fullname;}
	public  void setPhone(String phone){this.Phone=phone;}
	public String getPhone(){ return this.Phone;}
	public  void setSex(String sex){this.Sex=sex;}
	public String getSex(){ return this.Sex;}
	public  void setPhoto(String photo){this.Photo=photo;}
	public String getPhoto(){ return this.Photo;}
	public  void setWeiXinNumber(String weiXinNumber){this.WeiXinNumber=weiXinNumber;}
	public String getWeiXinNumber(){ return this.WeiXinNumber;}
	public  void setCantonRegion(int cantonRegion){this.CantonRegion=cantonRegion;}
	public int getCantonRegion(){ return this.CantonRegion;}
	public  void setArea(long area){this.Area=area;}
	public long getArea(){ return this.Area;}
	public  void setAddress(String address){this.Address=address;}
	public String getAddress(){ return this.Address;}
	public  void setComName(String comName){this.ComName=comName;}
	public String getComName(){ return this.ComName;}
	public  void setBankID(long bankID){this.BankID=bankID;}
	public long getBankID(){ return this.BankID;}
	public  void setBankCardName(String bankCardName){this.BankCardName=bankCardName;}
	public String getBankCardName(){ return this.BankCardName;}
	public  void setBankCardNumber(String bankCardNumber){this.BankCardNumber=bankCardNumber;}
	public String getBankCardNumber(){ return this.BankCardNumber;}
	public  void setBankOpenName(String bankOpenName){this.BankOpenName=bankOpenName;}
	public String getBankOpenName(){ return this.BankOpenName;}
	public  void setBankOpenArea(long bankOpenArea){this.BankOpenArea=bankOpenArea;}
	public long getBankOpenArea(){ return this.BankOpenArea;}
	public  void setIsCheckEmail(boolean isCheckEmail){this.IsCheckEmail=isCheckEmail;}
	public boolean getIsCheckEmail(){ return this.IsCheckEmail;}
	public  void setIsCheckMobile(boolean isCheckMobile){this.IsCheckMobile=isCheckMobile;}
	public boolean getIsCheckMobile(){ return this.IsCheckMobile;}
	public  void setIsCheckBankCard(boolean isCheckBankCard){this.IsCheckBankCard=isCheckBankCard;}
	public boolean getIsCheckBankCard(){ return this.IsCheckBankCard;}
	public  void setVestingComID(long vestingComID){this.VestingComID=vestingComID;}
	public long getVestingComID(){ return this.VestingComID;}
	public  void setVestingComBrID(long vestingComBrID){this.VestingComBrID=vestingComBrID;}
	public long getVestingComBrID(){ return this.VestingComBrID;}
	public  void setAuditState(int auditState){this.AuditState=auditState;}
	public int getAuditState(){ return this.AuditState;}
	public  void setAuditCause(String auditCause){this.AuditCause=auditCause;}
	public String getAuditCause(){ return this.AuditCause;}
	public  void setDisabled(boolean disabled){this.Disabled=disabled;}
	public boolean getDisabled(){ return this.Disabled;}
	public  void setFlagDelete(boolean flagDelete){this.FlagDelete=flagDelete;}
	public boolean getFlagDelete(){ return this.FlagDelete;}
	public  void setDownloadTime(String downloadTime){this.DownloadTime=downloadTime;}
	public String getDownloadTime(){ return this.DownloadTime;}
	public  void setCreateUserID(long createUserID){this.CreateUserID=createUserID;}
	public long getCreateUserID(){ return this.CreateUserID;}
	public  void setCreateComBrID(long createComBrID){this.CreateComBrID=createComBrID;}
	public long getCreateComBrID(){ return this.CreateComBrID;}
	public  void setCreateComID(long createComID){this.CreateComID=createComID;}
	public long getCreateComID(){ return this.CreateComID;}
	public  void setCreateIp(String createIp){this.CreateIp=createIp;}
	public String getCreateIp(){ return this.CreateIp;}
	public  void setCreateTime(String createTime){this.CreateTime=createTime;}
	public String getCreateTime(){ return this.CreateTime;}
	@Override
	public void getPropertyInfo(int arg0, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo arg2) {
		// TODO Auto-generated method stub
		switch (arg0) {
			case 0:
				arg2.type= PropertyInfo.LONG_CLASS;
				arg2.name="ComCusID";
				break;

			case 1:
				arg2.type= PropertyInfo.LONG_CLASS;
				arg2.name="ComCusSort";
				break;

			case 2:
				arg2.type= PropertyInfo.LONG_CLASS;
				arg2.name="ComCusGrade";
				break;

			case 3:
				arg2.type= PropertyInfo.STRING_CLASS;
				arg2.name="ComCusNumber";
				break;

			case 4:
				arg2.type= PropertyInfo.INTEGER_CLASS;
				arg2.name="LoginMode";
				break;

			case 5:
				arg2.type= PropertyInfo.STRING_CLASS;
				arg2.name="LoginAccount";
				break;

			case 6:
				arg2.type= PropertyInfo.STRING_CLASS;
				arg2.name="LoginPwd";
				break;

			case 7:
				arg2.type= PropertyInfo.STRING_CLASS;
				arg2.name="Email";
				break;

			case 8:
				arg2.type= PropertyInfo.STRING_CLASS;
				arg2.name="Mobile";
				break;

			case 9:
				arg2.type= PropertyInfo.STRING_CLASS;
				arg2.name="IdCard";
				break;

			case 10:
				arg2.type= PropertyInfo.STRING_CLASS;
				arg2.name="Fullname";
				break;

			case 11:
				arg2.type= PropertyInfo.STRING_CLASS;
				arg2.name="Phone";
				break;

			case 12:
				arg2.type= PropertyInfo.STRING_CLASS;
				arg2.name="Sex";
				break;

			case 13:
				arg2.type= PropertyInfo.STRING_CLASS;
				arg2.name="Photo";
				break;

			case 14:
				arg2.type= PropertyInfo.STRING_CLASS;
				arg2.name="WeiXinNumber";
				break;

			case 15:
				arg2.type= PropertyInfo.INTEGER_CLASS;
				arg2.name="CantonRegion";
				break;

			case 16:
				arg2.type= PropertyInfo.LONG_CLASS;
				arg2.name="Area";
				break;

			case 17:
				arg2.type= PropertyInfo.STRING_CLASS;
				arg2.name="Address";
				break;

			case 18:
				arg2.type= PropertyInfo.STRING_CLASS;
				arg2.name="ComName";
				break;

			case 19:
				arg2.type= PropertyInfo.LONG_CLASS;
				arg2.name="BankID";
				break;

			case 20:
				arg2.type= PropertyInfo.STRING_CLASS;
				arg2.name="BankCardName";
				break;

			case 21:
				arg2.type= PropertyInfo.STRING_CLASS;
				arg2.name="BankCardNumber";
				break;

			case 22:
				arg2.type= PropertyInfo.STRING_CLASS;
				arg2.name="BankOpenName";
				break;

			case 23:
				arg2.type= PropertyInfo.LONG_CLASS;
				arg2.name="BankOpenArea";
				break;

			case 24:
				arg2.type= PropertyInfo.BOOLEAN_CLASS;
				arg2.name="IsCheckEmail";
				break;

			case 25:
				arg2.type= PropertyInfo.BOOLEAN_CLASS;
				arg2.name="IsCheckMobile";
				break;

			case 26:
				arg2.type= PropertyInfo.BOOLEAN_CLASS;
				arg2.name="IsCheckBankCard";
				break;

			case 27:
				arg2.type= PropertyInfo.LONG_CLASS;
				arg2.name="VestingComID";
				break;

			case 28:
				arg2.type= PropertyInfo.LONG_CLASS;
				arg2.name="VestingComBrID";
				break;

			case 29:
				arg2.type= PropertyInfo.INTEGER_CLASS;
				arg2.name="AuditState";
				break;

			case 30:
				arg2.type= PropertyInfo.STRING_CLASS;
				arg2.name="AuditCause";
				break;

			case 31:
				arg2.type= PropertyInfo.BOOLEAN_CLASS;
				arg2.name="Disabled";
				break;

			case 32:
				arg2.type= PropertyInfo.BOOLEAN_CLASS;
				arg2.name="FlagDelete";
				break;

			case 33:
				arg2.type= PropertyInfo.STRING_CLASS;
				arg2.name="DownloadTime";
				break;

			case 34:
				arg2.type= PropertyInfo.LONG_CLASS;
				arg2.name="CreateUserID";
				break;

			case 35:
				arg2.type= PropertyInfo.LONG_CLASS;
				arg2.name="CreateComBrID";
				break;

			case 36:
				arg2.type= PropertyInfo.LONG_CLASS;
				arg2.name="CreateComID";
				break;

			case 37:
				arg2.type= PropertyInfo.STRING_CLASS;
				arg2.name="CreateIp";
				break;

			case 38:
				arg2.type= PropertyInfo.STRING_CLASS;
				arg2.name="CreateTime";
				break;

			default:
				break;
		}
	}
	@Override
	public void setProperty(int arg0, Object arg1) {
		switch (arg0) {
			case 0:
				ComCusID= Convert.ToInt64(arg1.toString());
				break;

			case 1:
				ComCusSort=Convert.ToInt64(arg1.toString());
				break;

			case 2:
				ComCusGrade=Convert.ToInt64(arg1.toString());
				break;

			case 3:
				ComCusNumber=arg1.toString();
				break;

			case 4:
				LoginMode=Convert.ToInt32(arg1.toString());
				break;

			case 5:
				LoginAccount=arg1.toString();
				break;

			case 6:
				LoginPwd=arg1.toString();
				break;

			case 7:
				Email=arg1.toString();
				break;

			case 8:
				Mobile=arg1.toString();
				break;

			case 9:
				IdCard=arg1.toString();
				break;

			case 10:
				Fullname=arg1.toString();
				break;

			case 11:
				Phone=arg1.toString();
				break;

			case 12:
				Sex=arg1.toString();
				break;

			case 13:
				Photo=arg1.toString();
				break;

			case 14:
				WeiXinNumber=arg1.toString();
				break;

			case 15:
				CantonRegion=Convert.ToInt32(arg1.toString());
				break;

			case 16:
				Area=Convert.ToInt64(arg1.toString());
				break;

			case 17:
				Address=arg1.toString();
				break;

			case 18:
				ComName=arg1.toString();
				break;

			case 19:
				BankID=Convert.ToInt64(arg1.toString());
				break;

			case 20:
				BankCardName=arg1.toString();
				break;

			case 21:
				BankCardNumber=arg1.toString();
				break;

			case 22:
				BankOpenName=arg1.toString();
				break;

			case 23:
				BankOpenArea=Convert.ToInt64(arg1.toString());
				break;

			case 24:
				IsCheckEmail=Convert.ToBoolean(arg1.toString());
				break;

			case 25:
				IsCheckMobile=Convert.ToBoolean(arg1.toString());
				break;

			case 26:
				IsCheckBankCard=Convert.ToBoolean(arg1.toString());
				break;

			case 27:
				VestingComID=Convert.ToInt64(arg1.toString());
				break;

			case 28:
				VestingComBrID=Convert.ToInt64(arg1.toString());
				break;

			case 29:
				AuditState=Convert.ToInt32(arg1.toString());
				break;

			case 30:
				AuditCause=arg1.toString();
				break;

			case 31:
				Disabled=Convert.ToBoolean(arg1.toString());
				break;

			case 32:
				FlagDelete=Convert.ToBoolean(arg1.toString());
				break;

			case 33:
				DownloadTime=arg1.toString();
				break;

			case 34:
				CreateUserID=Convert.ToInt64(arg1.toString());
				break;

			case 35:
				CreateComBrID=Convert.ToInt64(arg1.toString());
				break;

			case 36:
				CreateComID=Convert.ToInt64(arg1.toString());
				break;

			case 37:
				CreateIp=arg1.toString();
				break;

			case 38:
				CreateTime=arg1.toString();
				break;

			default:
				break;
		}
	}
	@Override
	public Object getProperty(int arg0) {
		switch (arg0) {
			case 0:
				return ComCusID;

			case 1:
				return ComCusSort;

			case 2:
				return ComCusGrade;

			case 3:
				return ComCusNumber;

			case 4:
				return LoginMode;

			case 5:
				return LoginAccount;

			case 6:
				return LoginPwd;

			case 7:
				return Email;

			case 8:
				return Mobile;

			case 9:
				return IdCard;

			case 10:
				return Fullname;

			case 11:
				return Phone;

			case 12:
				return Sex;

			case 13:
				return Photo;

			case 14:
				return WeiXinNumber;

			case 15:
				return CantonRegion;

			case 16:
				return Area;

			case 17:
				return Address;

			case 18:
				return ComName;

			case 19:
				return BankID;

			case 20:
				return BankCardName;

			case 21:
				return BankCardNumber;

			case 22:
				return BankOpenName;

			case 23:
				return BankOpenArea;

			case 24:
				return IsCheckEmail;

			case 25:
				return IsCheckMobile;

			case 26:
				return IsCheckBankCard;

			case 27:
				return VestingComID;

			case 28:
				return VestingComBrID;

			case 29:
				return AuditState;

			case 30:
				return AuditCause;

			case 31:
				return Disabled;

			case 32:
				return FlagDelete;

			case 33:
				return DownloadTime;

			case 34:
				return CreateUserID;

			case 35:
				return CreateComBrID;

			case 36:
				return CreateComID;

			case 37:
				return CreateIp;

			case 38:
				return CreateTime;

			default:
				break;
		}
		return null;
	}

	@Override
	public int getPropertyCount() {
		return 40;
	}

	public boolean IsExist()
	{
		return this.Exist;
	}

}