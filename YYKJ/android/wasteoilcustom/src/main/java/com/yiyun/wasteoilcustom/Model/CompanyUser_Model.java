package com.yiyun.wasteoilcustom.model;


import com.chengyi.android.util.Convert;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class CompanyUser_Model  implements KvmSerializable {
	public CompanyUser_Model()
	{
	}
	//以下是生成的字段
	private long UserID=0;
	private long ComBrID=0;
	private String ManageComBrID="";
	private String UserName="";
	private String UserNumber="";
	private String Email="";
	private String Mobile="";
	private String Pwd="";
	private int EntryMode=0;
	private String Fullname="";
	private long DepID=0;
	private long DutyID=0;
	private String Sex="";
	private String Birthday="2016-11-01";
	private String EntryTime="2016-11-01";
	private String DepartTime="2016-11-01";
	private String IdCard="";
	private String QQNumber="";
	private String WeiXinNumber="";
	private String Photos="";
	private String Address="";
	private long VehicleDriverNumber=0;
	private boolean IsMainUser=false;
	private boolean IsCheckEmail=false;
	private boolean IsCheckMobile=false;
	private boolean FlagDelete=false;
	private boolean Disabled=false;
	private String CreateIp="";
	private String CreateTime="2016-11-01";
	private boolean Exist=false;

	//以下是生成的属性
	public  void setUserID(long userID){this.UserID=userID;}
	public long getUserID(){ return this.UserID;}
	public  void setComBrID(long comBrID){this.ComBrID=comBrID;}
	public long getComBrID(){ return this.ComBrID;}
	public  void setManageComBrID(String manageComBrID){this.ManageComBrID=manageComBrID;}
	public String getManageComBrID(){ return this.ManageComBrID;}
	public  void setUserName(String userName){this.UserName=userName;}
	public String getUserName(){ return this.UserName;}
	public  void setUserNumber(String userNumber){this.UserNumber=userNumber;}
	public String getUserNumber(){ return this.UserNumber;}
	public  void setEmail(String email){this.Email=email;}
	public String getEmail(){ return this.Email;}
	public  void setMobile(String mobile){this.Mobile=mobile;}
	public String getMobile(){ return this.Mobile;}
	public  void setPwd(String pwd){this.Pwd=pwd;}
	public String getPwd(){ return this.Pwd;}
	public  void setEntryMode(int entryMode){this.EntryMode=entryMode;}
	public int getEntryMode(){ return this.EntryMode;}
	public  void setFullname(String fullname){this.Fullname=fullname;}
	public String getFullname(){ return this.Fullname;}
	public  void setDepID(long depID){this.DepID=depID;}
	public long getDepID(){ return this.DepID;}
	public  void setDutyID(long dutyID){this.DutyID=dutyID;}
	public long getDutyID(){ return this.DutyID;}
	public  void setSex(String sex){this.Sex=sex;}
	public String getSex(){ return this.Sex;}
	public  void setBirthday(String birthday){this.Birthday=birthday;}
	public String getBirthday(){ return this.Birthday;}
	public  void setEntryTime(String entryTime){this.EntryTime=entryTime;}
	public String getEntryTime(){ return this.EntryTime;}
	public  void setDepartTime(String departTime){this.DepartTime=departTime;}
	public String getDepartTime(){ return this.DepartTime;}
	public  void setIdCard(String idCard){this.IdCard=idCard;}
	public String getIdCard(){ return this.IdCard;}
	public  void setQQNumber(String qQNumber){this.QQNumber=qQNumber;}
	public String getQQNumber(){ return this.QQNumber;}
	public  void setWeiXinNumber(String weiXinNumber){this.WeiXinNumber=weiXinNumber;}
	public String getWeiXinNumber(){ return this.WeiXinNumber;}
	public  void setPhotos(String photos){this.Photos=photos;}
	public String getPhotos(){ return this.Photos;}
	public  void setAddress(String address){this.Address=address;}
	public String getAddress(){ return this.Address;}
	public  void setVehicleDriverNumber(long vehicleDriverNumber){this.VehicleDriverNumber=vehicleDriverNumber;}
	public long getVehicleDriverNumber(){ return this.VehicleDriverNumber;}
	public  void setIsMainUser(boolean isMainUser){this.IsMainUser=isMainUser;}
	public boolean getIsMainUser(){ return this.IsMainUser;}
	public  void setIsCheckEmail(boolean isCheckEmail){this.IsCheckEmail=isCheckEmail;}
	public boolean getIsCheckEmail(){ return this.IsCheckEmail;}
	public  void setIsCheckMobile(boolean isCheckMobile){this.IsCheckMobile=isCheckMobile;}
	public boolean getIsCheckMobile(){ return this.IsCheckMobile;}
	public  void setFlagDelete(boolean flagDelete){this.FlagDelete=flagDelete;}
	public boolean getFlagDelete(){ return this.FlagDelete;}
	public  void setDisabled(boolean disabled){this.Disabled=disabled;}
	public boolean getDisabled(){ return this.Disabled;}
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
				arg2.name="UserID";
				break;

			case 1:
				arg2.type= PropertyInfo.LONG_CLASS;
				arg2.name="ComBrID";
				break;

			case 2:
				arg2.type= PropertyInfo.STRING_CLASS;
				arg2.name="ManageComBrID";
				break;

			case 3:
				arg2.type= PropertyInfo.STRING_CLASS;
				arg2.name="UserName";
				break;

			case 4:
				arg2.type= PropertyInfo.STRING_CLASS;
				arg2.name="UserNumber";
				break;

			case 5:
				arg2.type= PropertyInfo.STRING_CLASS;
				arg2.name="Email";
				break;

			case 6:
				arg2.type= PropertyInfo.STRING_CLASS;
				arg2.name="Mobile";
				break;

			case 7:
				arg2.type= PropertyInfo.STRING_CLASS;
				arg2.name="Pwd";
				break;

			case 8:
				arg2.type= PropertyInfo.INTEGER_CLASS;
				arg2.name="EntryMode";
				break;

			case 9:
				arg2.type= PropertyInfo.STRING_CLASS;
				arg2.name="Fullname";
				break;

			case 10:
				arg2.type= PropertyInfo.LONG_CLASS;
				arg2.name="DepID";
				break;

			case 11:
				arg2.type= PropertyInfo.LONG_CLASS;
				arg2.name="DutyID";
				break;

			case 12:
				arg2.type= PropertyInfo.STRING_CLASS;
				arg2.name="Sex";
				break;

			case 13:
				arg2.type= PropertyInfo.STRING_CLASS;
				arg2.name="Birthday";
				break;

			case 14:
				arg2.type= PropertyInfo.STRING_CLASS;
				arg2.name="EntryTime";
				break;

			case 15:
				arg2.type= PropertyInfo.STRING_CLASS;
				arg2.name="DepartTime";
				break;

			case 16:
				arg2.type= PropertyInfo.STRING_CLASS;
				arg2.name="IdCard";
				break;

			case 17:
				arg2.type= PropertyInfo.STRING_CLASS;
				arg2.name="QQNumber";
				break;

			case 18:
				arg2.type= PropertyInfo.STRING_CLASS;
				arg2.name="WeiXinNumber";
				break;

			case 19:
				arg2.type= PropertyInfo.STRING_CLASS;
				arg2.name="Photos";
				break;

			case 20:
				arg2.type= PropertyInfo.STRING_CLASS;
				arg2.name="Address";
				break;

			case 21:
				arg2.type= PropertyInfo.LONG_CLASS;
				arg2.name="VehicleDriverNumber";
				break;

			case 22:
				arg2.type= PropertyInfo.BOOLEAN_CLASS;
				arg2.name="IsMainUser";
				break;

			case 23:
				arg2.type= PropertyInfo.BOOLEAN_CLASS;
				arg2.name="IsCheckEmail";
				break;

			case 24:
				arg2.type= PropertyInfo.BOOLEAN_CLASS;
				arg2.name="IsCheckMobile";
				break;

			case 25:
				arg2.type= PropertyInfo.BOOLEAN_CLASS;
				arg2.name="FlagDelete";
				break;

			case 26:
				arg2.type= PropertyInfo.BOOLEAN_CLASS;
				arg2.name="Disabled";
				break;

			case 27:
				arg2.type= PropertyInfo.STRING_CLASS;
				arg2.name="CreateIp";
				break;

			case 28:
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
				UserID= Convert.ToInt64(arg1.toString());
				break;

			case 1:
				ComBrID=Convert.ToInt64(arg1.toString());
				break;

			case 2:
				ManageComBrID=arg1.toString();
				break;

			case 3:
				UserName=arg1.toString();
				break;

			case 4:
				UserNumber=arg1.toString();
				break;

			case 5:
				Email=arg1.toString();
				break;

			case 6:
				Mobile=arg1.toString();
				break;

			case 7:
				Pwd=arg1.toString();
				break;

			case 8:
				EntryMode=Convert.ToInt32(arg1.toString());
				break;

			case 9:
				Fullname=arg1.toString();
				break;

			case 10:
				DepID=Convert.ToInt64(arg1.toString());
				break;

			case 11:
				DutyID=Convert.ToInt64(arg1.toString());
				break;

			case 12:
				Sex=arg1.toString();
				break;

			case 13:
				Birthday=arg1.toString();
				break;

			case 14:
				EntryTime=arg1.toString();
				break;

			case 15:
				DepartTime=arg1.toString();
				break;

			case 16:
				IdCard=arg1.toString();
				break;

			case 17:
				QQNumber=arg1.toString();
				break;

			case 18:
				WeiXinNumber=arg1.toString();
				break;

			case 19:
				Photos=arg1.toString();
				break;

			case 20:
				Address=arg1.toString();
				break;

			case 21:
				VehicleDriverNumber=Convert.ToInt64(arg1.toString());
				break;

			case 22:
				IsMainUser=Convert.ToBoolean(arg1.toString());
				break;

			case 23:
				IsCheckEmail=Convert.ToBoolean(arg1.toString());
				break;

			case 24:
				IsCheckMobile=Convert.ToBoolean(arg1.toString());
				break;

			case 25:
				FlagDelete=Convert.ToBoolean(arg1.toString());
				break;

			case 26:
				Disabled=Convert.ToBoolean(arg1.toString());
				break;

			case 27:
				CreateIp=arg1.toString();
				break;

			case 28:
				CreateTime=arg1.toString();
				break;

			default:
				break;
		}
	}

	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 29;
	}

	@Override
	public Object getProperty(int arg0) {
		switch (arg0) {
			case 0:
				return UserID;

			case 1:
				return ComBrID;

			case 2:
				return ManageComBrID;

			case 3:
				return UserName;

			case 4:
				return UserNumber;

			case 5:
				return Email;

			case 6:
				return Mobile;

			case 7:
				return Pwd;

			case 8:
				return EntryMode;

			case 9:
				return Fullname;

			case 10:
				return DepID;

			case 11:
				return DutyID;

			case 12:
				return Sex;

			case 13:
				return Birthday;

			case 14:
				return EntryTime;

			case 15:
				return DepartTime;

			case 16:
				return IdCard;

			case 17:
				return QQNumber;

			case 18:
				return WeiXinNumber;

			case 19:
				return Photos;

			case 20:
				return Address;

			case 21:
				return VehicleDriverNumber;

			case 22:
				return IsMainUser;

			case 23:
				return IsCheckEmail;

			case 24:
				return IsCheckMobile;

			case 25:
				return FlagDelete;

			case 26:
				return Disabled;

			case 27:
				return CreateIp;

			case 28:
				return CreateTime;

			default:
				break;
		}
		return  null;
	}
	public boolean IsExist()
	{
		return this.Exist;
	}
}
