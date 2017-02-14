package com.yiyun.wasteoilcustom.Model;


import com.chengyi.android.util.Convert;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class VehicleDispath_Model implements KvmSerializable {
		public VehicleDispath_Model()
		{
		}
		//���������ɵ��ֶ�
		private String VehDispNumber="";
		private long VehDispComID=0;
		private String VehDispTime="2016-11-01";
		private int VehDispState=0;
		private int VehDispForwardedState=0;
		private long VehNumber=0;
		private long VehDrNumber=0;
		private int CarriageMode=0;
		private String CarriageMoney="0";
		private String PaidCarriageMoney="0";
		private String Remark="";
		private long CreateUserID=0;
		private long CreateComBrID=0;
		private long CreateComID=0;
		private String CreateIp="";
		private String CreateTime="2016-11-01";
        private boolean Exist=false;

		//���������ɵ�����
		public  void setVehDispNumber(String vehDispNumber){this.VehDispNumber=vehDispNumber;}
		public String getVehDispNumber(){ return this.VehDispNumber;}
		public  void setVehDispComID(long vehDispComID){this.VehDispComID=vehDispComID;}
		public long getVehDispComID(){ return this.VehDispComID;}
		public  void setVehDispTime(String vehDispTime){this.VehDispTime=vehDispTime;}
		public String getVehDispTime(){ return this.VehDispTime;}
		public  void setVehDispState(int vehDispState){this.VehDispState=vehDispState;}
		public int getVehDispState(){ return this.VehDispState;}
		public  void setVehDispForwardedState(int vehDispForwardedState){this.VehDispForwardedState=vehDispForwardedState;}
		public int getVehDispForwardedState(){ return this.VehDispForwardedState;}
		public  void setVehNumber(long vehNumber){this.VehNumber=vehNumber;}
		public long getVehNumber(){ return this.VehNumber;}
		public  void setVehDrNumber(long vehDrNumber){this.VehDrNumber=vehDrNumber;}
		public long getVehDrNumber(){ return this.VehDrNumber;}
		public  void setCarriageMode(int carriageMode){this.CarriageMode=carriageMode;}
		public int getCarriageMode(){ return this.CarriageMode;}
		public  void setCarriageMoney(String carriageMoney){this.CarriageMoney=carriageMoney;}
		public String getCarriageMoney(){ return this.CarriageMoney;}
		public  void setPaidCarriageMoney(String paidCarriageMoney){this.PaidCarriageMoney=paidCarriageMoney;}
		public String getPaidCarriageMoney(){ return this.PaidCarriageMoney;}
		public  void setRemark(String remark){this.Remark=remark;}
		public String getRemark(){ return this.Remark;}
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
	public void setExist(boolean exist){this.Exist=exist;}
	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 16;
	}
@Override
public void getPropertyInfo(int arg0, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo arg2) {
		// TODO Auto-generated method stub
		switch (arg0) {
				case 0:
						arg2.type= PropertyInfo.STRING_CLASS;
						arg2.name="VehDispNumber";
						break;

				case 1:
						arg2.type= PropertyInfo.LONG_CLASS;
						arg2.name="VehDispComID";
						break;

				case 2:
						arg2.type= PropertyInfo.STRING_CLASS;
						arg2.name="VehDispTime";
						break;

				case 3:
						arg2.type= PropertyInfo.INTEGER_CLASS;
						arg2.name="VehDispState";
						break;

				case 4:
						arg2.type= PropertyInfo.INTEGER_CLASS;
						arg2.name="VehDispForwardedState";
						break;

				case 5:
						arg2.type= PropertyInfo.LONG_CLASS;
						arg2.name="VehNumber";
						break;

				case 6:
						arg2.type= PropertyInfo.LONG_CLASS;
						arg2.name="VehDrNumber";
						break;

				case 7:
						arg2.type= PropertyInfo.INTEGER_CLASS;
						arg2.name="CarriageMode";
						break;

				case 8:
						arg2.type= PropertyInfo.STRING_CLASS;
						arg2.name="CarriageMoney";
						break;

				case 9:
						arg2.type= PropertyInfo.STRING_CLASS;
						arg2.name="PaidCarriageMoney";
						break;

				case 10:
						arg2.type= PropertyInfo.STRING_CLASS;
						arg2.name="Remark";
						break;

				case 11:
						arg2.type= PropertyInfo.LONG_CLASS;
						arg2.name="CreateUserID";
						break;

				case 12:
						arg2.type= PropertyInfo.LONG_CLASS;
						arg2.name="CreateComBrID";
						break;

				case 13:
						arg2.type= PropertyInfo.LONG_CLASS;
						arg2.name="CreateComID";
						break;

				case 14:
						arg2.type= PropertyInfo.STRING_CLASS;
						arg2.name="CreateIp";
						break;

				case 15:
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
						VehDispNumber=arg1.toString();
						break;

				case 1:
						VehDispComID= Convert.ToInt64(arg1.toString());
						break;

				case 2:
						VehDispTime=arg1.toString();
						break;

				case 3:
						VehDispState= Convert.ToInt32(arg1.toString());
						break;

				case 4:
						VehDispForwardedState=Convert.ToInt32(arg1.toString());
						break;

				case 5:
						VehNumber=Convert.ToInt64(arg1.toString());
						break;

				case 6:
						VehDrNumber=Convert.ToInt64(arg1.toString());
						break;

				case 7:
						CarriageMode=Convert.ToInt32(arg1.toString());
						break;

				case 8:
						CarriageMoney=arg1.toString();
						break;

				case 9:
						PaidCarriageMoney=arg1.toString();
						break;

				case 10:
						Remark=arg1.toString();
						break;

				case 11:
						CreateUserID=Convert.ToInt64(arg1.toString());
						break;

				case 12:
						CreateComBrID=Convert.ToInt64(arg1.toString());
						break;

				case 13:
						CreateComID=Convert.ToInt64(arg1.toString());
						break;

				case 14:
						CreateIp=arg1.toString();
						break;

				case 15:
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
						 return VehDispComID;

				case 2:
						 return VehDispTime;

				case 3:
						 return VehDispState;

				case 4:
						 return VehDispForwardedState;

				case 5:
						 return VehNumber;

				case 6:
						 return VehDrNumber;

				case 7:
						 return CarriageMode;

				case 8:
						 return CarriageMoney;

				case 9:
						 return PaidCarriageMoney;

				case 10:
						 return Remark;

				case 11:
						 return CreateUserID;

				case 12:
						 return CreateComBrID;

				case 13:
						 return CreateComID;

				case 14:
						 return CreateIp;

				case 15:
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
