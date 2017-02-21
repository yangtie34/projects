package com.eyunsoft.app_wasteoil.Model;

import com.eyunsoft.app_wasteoil.Publics.Convert;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class VehicleDispathForwardedState_Model implements KvmSerializable {
	public VehicleDispathForwardedState_Model() {
	}

	//以下是生成的字段
	private String VehDispNumber="";
	private int VehDispForwardedState=0;
	private String Remark="";
	private int LocationZoom=0;
	private String LocationLng="0";
	private String LocationLat="0";
	private long HandleUserID=0;
	private String HandleUserName="";
	private long HandleComCusID=0;
	private String HandleComCusName="";
	private long ProcessUpperComBrID=0;
	private long ProcessUpperComCusID=0;
	private long ProcessLowerComBrID=0;
	private long ProcessLowerComCusID=0;
	private long CreateUserID=0;
	private long CreateComBrID=0;
	private long CreateComID=0;
	private String CreateIp="";
	private String CreateTime="2016-11-01";
	private boolean Exist=false;

	public boolean IsExist()
	{
		return this.Exist;
	}

	//以下是生成的属性
	public  void setVehDispNumber(String vehDispNumber){this.VehDispNumber=vehDispNumber;}
	public String getVehDispNumber(){ return this.VehDispNumber;}
	public  void setVehDispForwardedState(int vehDispForwardedState){this.VehDispForwardedState=vehDispForwardedState;}
	public int getVehDispForwardedState(){ return this.VehDispForwardedState;}
	public  void setRemark(String remark){this.Remark=remark;}
	public String getRemark(){ return this.Remark;}
	public  void setLocationZoom(int locationZoom){this.LocationZoom=locationZoom;}
	public int getLocationZoom(){ return this.LocationZoom;}
	public  void setLocationLng(String locationLng){this.LocationLng=locationLng;}
	public String getLocationLng(){ return this.LocationLng;}
	public  void setLocationLat(String locationLat){this.LocationLat=locationLat;}
	public String getLocationLat(){ return this.LocationLat;}
	public  void setHandleUserID(long handleUserID){this.HandleUserID=handleUserID;}
	public long getHandleUserID(){ return this.HandleUserID;}
	public  void setHandleUserName(String handleUserName){this.HandleUserName=handleUserName;}
	public String getHandleUserName(){ return this.HandleUserName;}
	public  void setHandleComCusID(long handleComCusID){this.HandleComCusID=handleComCusID;}
	public long getHandleComCusID(){ return this.HandleComCusID;}
	public  void setHandleComCusName(String handleComCusName){this.HandleComCusName=handleComCusName;}
	public String getHandleComCusName(){ return this.HandleComCusName;}
	public  void setProcessUpperComBrID(long processUpperComBrID){this.ProcessUpperComBrID=processUpperComBrID;}
	public long getProcessUpperComBrID(){ return this.ProcessUpperComBrID;}
	public  void setProcessUpperComCusID(long processUpperComCusID){this.ProcessUpperComCusID=processUpperComCusID;}
	public long getProcessUpperComCusID(){ return this.ProcessUpperComCusID;}
	public  void setProcessLowerComBrID(long processLowerComBrID){this.ProcessLowerComBrID=processLowerComBrID;}
	public long getProcessLowerComBrID(){ return this.ProcessLowerComBrID;}
	public  void setProcessLowerComCusID(long processLowerComCusID){this.ProcessLowerComCusID=processLowerComCusID;}
	public long getProcessLowerComCusID(){ return this.ProcessLowerComCusID;}
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
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 19;
	}

	@Override
	public void getPropertyInfo(int arg0, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo arg2) {
		// TODO Auto-generated method stub
		switch (arg0) {
			case 0:
				arg2.type=PropertyInfo.STRING_CLASS;
				arg2.name="VehDispNumber";
				break;

			case 1:
				arg2.type=PropertyInfo.INTEGER_CLASS;
				arg2.name="VehDispForwardedState";
				break;

			case 2:
				arg2.type=PropertyInfo.STRING_CLASS;
				arg2.name="Remark";
				break;

			case 3:
				arg2.type=PropertyInfo.INTEGER_CLASS;
				arg2.name="LocationZoom";
				break;

			case 4:
				arg2.type=PropertyInfo.STRING_CLASS;
				arg2.name="LocationLng";
				break;

			case 5:
				arg2.type=PropertyInfo.STRING_CLASS;
				arg2.name="LocationLat";
				break;

			case 6:
				arg2.type=PropertyInfo.LONG_CLASS;
				arg2.name="HandleUserID";
				break;

			case 7:
				arg2.type=PropertyInfo.STRING_CLASS;
				arg2.name="HandleUserName";
				break;

			case 8:
				arg2.type=PropertyInfo.LONG_CLASS;
				arg2.name="HandleComCusID";
				break;

			case 9:
				arg2.type=PropertyInfo.STRING_CLASS;
				arg2.name="HandleComCusName";
				break;

			case 10:
				arg2.type=PropertyInfo.LONG_CLASS;
				arg2.name="ProcessUpperComBrID";
				break;

			case 11:
				arg2.type=PropertyInfo.LONG_CLASS;
				arg2.name="ProcessUpperComCusID";
				break;

			case 12:
				arg2.type=PropertyInfo.LONG_CLASS;
				arg2.name="ProcessLowerComBrID";
				break;

			case 13:
				arg2.type=PropertyInfo.LONG_CLASS;
				arg2.name="ProcessLowerComCusID";
				break;

			case 14:
				arg2.type=PropertyInfo.LONG_CLASS;
				arg2.name="CreateUserID";
				break;

			case 15:
				arg2.type=PropertyInfo.LONG_CLASS;
				arg2.name="CreateComBrID";
				break;

			case 16:
				arg2.type=PropertyInfo.LONG_CLASS;
				arg2.name="CreateComID";
				break;

			case 17:
				arg2.type=PropertyInfo.STRING_CLASS;
				arg2.name="CreateIp";
				break;

			case 18:
				arg2.type=PropertyInfo.STRING_CLASS;
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
				VehDispNumber=arg1.toString();
				break;

			case 1:
				VehDispForwardedState=Convert.ToInt32(arg1.toString());
				break;

			case 2:
				Remark=arg1.toString();
				break;

			case 3:
				LocationZoom=Convert.ToInt32(arg1.toString());
				break;

			case 4:
				LocationLng=arg1.toString();
				break;

			case 5:
				LocationLat=arg1.toString();
				break;

			case 6:
				HandleUserID=Convert.ToInt64(arg1.toString());
				break;

			case 7:
				HandleUserName=arg1.toString();
				break;

			case 8:
				HandleComCusID=Convert.ToInt64(arg1.toString());
				break;

			case 9:
				HandleComCusName=arg1.toString();
				break;

			case 10:
				ProcessUpperComBrID=Convert.ToInt64(arg1.toString());
				break;

			case 11:
				ProcessUpperComCusID=Convert.ToInt64(arg1.toString());
				break;

			case 12:
				ProcessLowerComBrID=Convert.ToInt64(arg1.toString());
				break;

			case 13:
				ProcessLowerComCusID=Convert.ToInt64(arg1.toString());
				break;

			case 14:
				CreateUserID=Convert.ToInt64(arg1.toString());
				break;

			case 15:
				CreateComBrID=Convert.ToInt64(arg1.toString());
				break;

			case 16:
				CreateComID=Convert.ToInt64(arg1.toString());
				break;

			case 17:
				CreateIp=arg1.toString();
				break;

			case 18:
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
				return VehDispNumber;

			case 1:
				return VehDispForwardedState;

			case 2:
				return Remark;

			case 3:
				return LocationZoom;

			case 4:
				return LocationLng;

			case 5:
				return LocationLat;

			case 6:
				return HandleUserID;

			case 7:
				return HandleUserName;

			case 8:
				return HandleComCusID;

			case 9:
				return HandleComCusName;

			case 10:
				return ProcessUpperComBrID;

			case 11:
				return ProcessUpperComCusID;

			case 12:
				return ProcessLowerComBrID;

			case 13:
				return ProcessLowerComCusID;

			case 14:
				return CreateUserID;

			case 15:
				return CreateComBrID;

			case 16:
				return CreateComID;

			case 17:
				return CreateIp;

			case 18:
				return CreateTime;

			default:
				break;
		}
		return  null;
	}

}
