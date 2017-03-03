package com.eyunsoft.app_wasteoil.Model;

import com.eyunsoft.app_wasteoil.Publics.Convert;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.io.Serializable;
import java.util.Hashtable;

public class VehicleDispathHandover_Model implements KvmSerializable ,Serializable {
	public VehicleDispathHandover_Model() {
	}
	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 26;
	}


	//以下是生成的字段
	private String HandoverNumber="";
	private long HandoverComID=0;
	private long HandoverComBrID=0;
	private long HandoverComCusID=0;
	private String HandoverTime="2016-11-01";
	private String HandoverMoney="0";
	private String HandoverPaidMoney="0";
	private String HandoverActualMoney="0";
	private int PaymentMode=0;
	private int SettlementMode=0;
	private String VehDispNumber="";
	private int VehDispState=0;
	private int VehDispForwardedState=0;
	private long ProCategory=0;
	private String ProCategoryName="";
	private String ProNumber="0";
	private String ProMeasureUnitName="";
	private String ActualProNumber="0";
	private String ActualProMeasureUnitName="";
	private long HandleUserID=0;
	private String Remark="";
	private long CreateUserID=0;
	private long CreateComBrID=0;
	private long CreateComID=0;
	private String CreateIp="";
	private String CreateTime="2016-11-01";
	private boolean Exist=false;

	//以下是生成的属性
	public  void setHandoverNumber(String handoverNumber){this.HandoverNumber=handoverNumber;}
	public String getHandoverNumber(){ return this.HandoverNumber;}
	public  void setHandoverComID(long handoverComID){this.HandoverComID=handoverComID;}
	public long getHandoverComID(){ return this.HandoverComID;}
	public  void setHandoverComBrID(long handoverComBrID){this.HandoverComBrID=handoverComBrID;}
	public long getHandoverComBrID(){ return this.HandoverComBrID;}
	public  void setHandoverComCusID(long handoverComCusID){this.HandoverComCusID=handoverComCusID;}
	public long getHandoverComCusID(){ return this.HandoverComCusID;}
	public  void setHandoverTime(String handoverTime){this.HandoverTime=handoverTime;}
	public String getHandoverTime(){ return this.HandoverTime;}
	public  void setHandoverMoney(String handoverMoney){this.HandoverMoney=handoverMoney;}
	public String getHandoverMoney(){ return this.HandoverMoney;}
	public  void setHandoverPaidMoney(String handoverPaidMoney){this.HandoverPaidMoney=handoverPaidMoney;}
	public String getHandoverPaidMoney(){ return this.HandoverPaidMoney;}
	public  void setHandoverActualMoney(String handoverActualMoney){this.HandoverActualMoney=handoverActualMoney;}
	public String getHandoverActualMoney(){ return this.HandoverActualMoney;}
	public  void setPaymentMode(int paymentMode){this.PaymentMode=paymentMode;}
	public int getPaymentMode(){ return this.PaymentMode;}
	public  void setSettlementMode(int settlementMode){this.SettlementMode=settlementMode;}
	public int getSettlementMode(){ return this.SettlementMode;}
	public  void setVehDispNumber(String vehDispNumber){this.VehDispNumber=vehDispNumber;}
	public String getVehDispNumber(){ return this.VehDispNumber;}
	public  void setVehDispState(int vehDispState){this.VehDispState=vehDispState;}
	public int getVehDispState(){ return this.VehDispState;}
	public  void setVehDispForwardedState(int vehDispForwardedState){this.VehDispForwardedState=vehDispForwardedState;}
	public int getVehDispForwardedState(){ return this.VehDispForwardedState;}
	public  void setProCategory(long proCategory){this.ProCategory=proCategory;}
	public long getProCategory(){ return this.ProCategory;}
	public  void setProCategoryName(String proCategoryName){this.ProCategoryName=proCategoryName;}
	public String getProCategoryName(){ return this.ProCategoryName;}
	public  void setProNumber(String proNumber){this.ProNumber=proNumber;}
	public String getProNumber(){ return this.ProNumber;}
	public  void setProMeasureUnitName(String proMeasureUnitName){this.ProMeasureUnitName=proMeasureUnitName;}
	public String getProMeasureUnitName(){ return this.ProMeasureUnitName;}
	public  void setActualProNumber(String actualProNumber){this.ActualProNumber=actualProNumber;}
	public String getActualProNumber(){ return this.ActualProNumber;}
	public  void setActualProMeasureUnitName(String actualProMeasureUnitName){this.ActualProMeasureUnitName=actualProMeasureUnitName;}
	public String getActualProMeasureUnitName(){ return this.ActualProMeasureUnitName;}
	public  void setHandleUserID(long handleUserID){this.HandleUserID=handleUserID;}
	public long getHandleUserID(){ return this.HandleUserID;}
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
	public void getPropertyInfo(int arg0, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo arg2) {
		// TODO Auto-generated method stub
		switch (arg0) {
			case 0:
				arg2.type=PropertyInfo.STRING_CLASS;
				arg2.name="HandoverNumber";
				break;

			case 1:
				arg2.type=PropertyInfo.LONG_CLASS;
				arg2.name="HandoverComID";
				break;

			case 2:
				arg2.type=PropertyInfo.LONG_CLASS;
				arg2.name="HandoverComBrID";
				break;

			case 3:
				arg2.type=PropertyInfo.LONG_CLASS;
				arg2.name="HandoverComCusID";
				break;

			case 4:
				arg2.type=PropertyInfo.STRING_CLASS;
				arg2.name="HandoverTime";
				break;

			case 5:
				arg2.type=PropertyInfo.STRING_CLASS;
				arg2.name="HandoverMoney";
				break;

			case 6:
				arg2.type=PropertyInfo.STRING_CLASS;
				arg2.name="HandoverPaidMoney";
				break;

			case 7:
				arg2.type=PropertyInfo.STRING_CLASS;
				arg2.name="HandoverActualMoney";
				break;

			case 8:
				arg2.type=PropertyInfo.INTEGER_CLASS;
				arg2.name="PaymentMode";
				break;

			case 9:
				arg2.type=PropertyInfo.INTEGER_CLASS;
				arg2.name="SettlementMode";
				break;

			case 10:
				arg2.type=PropertyInfo.STRING_CLASS;
				arg2.name="VehDispNumber";
				break;

			case 11:
				arg2.type=PropertyInfo.INTEGER_CLASS;
				arg2.name="VehDispState";
				break;

			case 12:
				arg2.type=PropertyInfo.INTEGER_CLASS;
				arg2.name="VehDispForwardedState";
				break;

			case 13:
				arg2.type=PropertyInfo.LONG_CLASS;
				arg2.name="ProCategory";
				break;

			case 14:
				arg2.type=PropertyInfo.STRING_CLASS;
				arg2.name="ProCategoryName";
				break;

			case 15:
				arg2.type=PropertyInfo.STRING_CLASS;
				arg2.name="ProNumber";
				break;

			case 16:
				arg2.type=PropertyInfo.STRING_CLASS;
				arg2.name="ProMeasureUnitName";
				break;

			case 17:
				arg2.type=PropertyInfo.STRING_CLASS;
				arg2.name="ActualProNumber";
				break;

			case 18:
				arg2.type=PropertyInfo.STRING_CLASS;
				arg2.name="ActualProMeasureUnitName";
				break;

			case 19:
				arg2.type=PropertyInfo.LONG_CLASS;
				arg2.name="HandleUserID";
				break;

			case 20:
				arg2.type=PropertyInfo.STRING_CLASS;
				arg2.name="Remark";
				break;

			case 21:
				arg2.type=PropertyInfo.LONG_CLASS;
				arg2.name="CreateUserID";
				break;

			case 22:
				arg2.type=PropertyInfo.LONG_CLASS;
				arg2.name="CreateComBrID";
				break;

			case 23:
				arg2.type=PropertyInfo.LONG_CLASS;
				arg2.name="CreateComID";
				break;

			case 24:
				arg2.type=PropertyInfo.STRING_CLASS;
				arg2.name="CreateIp";
				break;

			case 25:
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
				HandoverNumber=arg1.toString();
				break;

			case 1:
				HandoverComID=Convert.ToInt64(arg1.toString());
				break;

			case 2:
				HandoverComBrID=Convert.ToInt64(arg1.toString());
				break;

			case 3:
				HandoverComCusID=Convert.ToInt64(arg1.toString());
				break;

			case 4:
				HandoverTime=arg1.toString();
				break;

			case 5:
				HandoverMoney=arg1.toString();
				break;

			case 6:
				HandoverPaidMoney=arg1.toString();
				break;

			case 7:
				HandoverActualMoney=arg1.toString();
				break;

			case 8:
				PaymentMode=Convert.ToInt32(arg1.toString());
				break;

			case 9:
				SettlementMode=Convert.ToInt32(arg1.toString());
				break;

			case 10:
				VehDispNumber=arg1.toString();
				break;

			case 11:
				VehDispState=Convert.ToInt32(arg1.toString());
				break;

			case 12:
				VehDispForwardedState=Convert.ToInt32(arg1.toString());
				break;

			case 13:
				ProCategory=Convert.ToInt64(arg1.toString());
				break;

			case 14:
				ProCategoryName=arg1.toString();
				break;

			case 15:
				ProNumber=arg1.toString();
				break;

			case 16:
				ProMeasureUnitName=arg1.toString();
				break;

			case 17:
				ActualProNumber=arg1.toString();
				break;

			case 18:
				ActualProMeasureUnitName=arg1.toString();
				break;

			case 19:
				HandleUserID=Convert.ToInt64(arg1.toString());
				break;

			case 20:
				Remark=arg1.toString();
				break;

			case 21:
				CreateUserID=Convert.ToInt64(arg1.toString());
				break;

			case 22:
				CreateComBrID=Convert.ToInt64(arg1.toString());
				break;

			case 23:
				CreateComID=Convert.ToInt64(arg1.toString());
				break;

			case 24:
				CreateIp=arg1.toString();
				break;

			case 25:
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
				return HandoverNumber;

			case 1:
				return HandoverComID;

			case 2:
				return HandoverComBrID;

			case 3:
				return HandoverComCusID;

			case 4:
				return HandoverTime;

			case 5:
				return HandoverMoney;

			case 6:
				return HandoverPaidMoney;

			case 7:
				return HandoverActualMoney;

			case 8:
				return PaymentMode;

			case 9:
				return SettlementMode;

			case 10:
				return VehDispNumber;

			case 11:
				return VehDispState;

			case 12:
				return VehDispForwardedState;

			case 13:
				return ProCategory;

			case 14:
				return ProCategoryName;

			case 15:
				return ProNumber;

			case 16:
				return ProMeasureUnitName;

			case 17:
				return ActualProNumber;

			case 18:
				return ActualProMeasureUnitName;

			case 19:
				return HandleUserID;

			case 20:
				return Remark;

			case 21:
				return CreateUserID;

			case 22:
				return CreateComBrID;

			case 23:
				return CreateComID;

			case 24:
				return CreateIp;

			case 25:
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
