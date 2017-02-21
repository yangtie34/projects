package com.eyunsoft.app_wasteoil.Model;

import com.eyunsoft.app_wasteoil.Publics.Convert;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;


public class ProduceRec_Model  implements KvmSerializable {
	public ProduceRec_Model()
	{
	}
	//以下是生成的字段
	private String RecNumber="";
	private long SysComID=0;
	private String RecTime="2016-11-01";
	private int RecSource=0;
	private String UseProName="";
	private String UseProSpec="";
	private String UseProModel="";
	private String UseProNumber="0";
	private String UseProMeasureUnitName="";
	private long ProCategory=0;
	private String ProCategoryName="";
	private long ProShape=0;
	private String ProShapeName="";
	private String ProHazardNature="";
	private String ProPackName="";
	private String ProNumber="0";
	private String ProMeasureUnitName="";
	private String ProDangerComponent="";
	private String Remark="";
	private long CreateComCusID=0;
	private long CreateUserID=0;
	private long CreateComBrID=0;
	private long CreateComID=0;
	private String CreateIp="";
	private String CreateTime="2016-11-01";
	private boolean Exist=false;

	//以下是生成的属性
	public  void setRecNumber(String recNumber){this.RecNumber=recNumber;}
	public String getRecNumber(){ return this.RecNumber;}
	public  void setSysComID(long sysComID){this.SysComID=sysComID;}
	public long getSysComID(){ return this.SysComID;}
	public  void setRecTime(String recTime){this.RecTime=recTime;}
	public String getRecTime(){ return this.RecTime;}
	public  void setRecSource(int recSource){this.RecSource=recSource;}
	public int getRecSource(){ return this.RecSource;}
	public  void setUseProName(String useProName){this.UseProName=useProName;}
	public String getUseProName(){ return this.UseProName;}
	public  void setUseProSpec(String useProSpec){this.UseProSpec=useProSpec;}
	public String getUseProSpec(){ return this.UseProSpec;}
	public  void setUseProModel(String useProModel){this.UseProModel=useProModel;}
	public String getUseProModel(){ return this.UseProModel;}
	public  void setUseProNumber(String useProNumber){this.UseProNumber=useProNumber;}
	public String getUseProNumber(){ return this.UseProNumber;}
	public  void setUseProMeasureUnitName(String useProMeasureUnitName){this.UseProMeasureUnitName=useProMeasureUnitName;}
	public String getUseProMeasureUnitName(){ return this.UseProMeasureUnitName;}
	public  void setProCategory(long proCategory){this.ProCategory=proCategory;}
	public long getProCategory(){ return this.ProCategory;}
	public  void setProCategoryName(String proCategoryName){this.ProCategoryName=proCategoryName;}
	public String getProCategoryName(){ return this.ProCategoryName;}
	public  void setProShape(long proShape){this.ProShape=proShape;}
	public long getProShape(){ return this.ProShape;}
	public  void setProShapeName(String proShapeName){this.ProShapeName=proShapeName;}
	public String getProShapeName(){ return this.ProShapeName;}
	public  void setProHazardNature(String proHazardNature){this.ProHazardNature=proHazardNature;}
	public String getProHazardNature(){ return this.ProHazardNature;}
	public  void setProPackName(String proPackName){this.ProPackName=proPackName;}
	public String getProPackName(){ return this.ProPackName;}
	public  void setProNumber(String proNumber){this.ProNumber=proNumber;}
	public String getProNumber(){ return this.ProNumber;}
	public  void setProMeasureUnitName(String proMeasureUnitName){this.ProMeasureUnitName=proMeasureUnitName;}
	public String getProMeasureUnitName(){ return this.ProMeasureUnitName;}
	public  void setProDangerComponent(String proDangerComponent){this.ProDangerComponent=proDangerComponent;}
	public String getProDangerComponent(){ return this.ProDangerComponent;}
	public  void setRemark(String remark){this.Remark=remark;}
	public String getRemark(){ return this.Remark;}
	public  void setCreateComCusID(long createComCusID){this.CreateComCusID=createComCusID;}
	public long getCreateComCusID(){ return this.CreateComCusID;}
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
		return 25;
	}

	@Override
	public void getPropertyInfo(int arg0, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo arg2) {
		// TODO Auto-generated method stub
		switch (arg0) {
			case 0:
				arg2.type=PropertyInfo.STRING_CLASS;
				arg2.name="RecNumber";
				break;

			case 1:
				arg2.type=PropertyInfo.LONG_CLASS;
				arg2.name="SysComID";
				break;

			case 2:
				arg2.type=PropertyInfo.STRING_CLASS;
				arg2.name="RecTime";
				break;

			case 3:
				arg2.type=PropertyInfo.INTEGER_CLASS;
				arg2.name="RecSource";
				break;

			case 4:
				arg2.type=PropertyInfo.STRING_CLASS;
				arg2.name="UseProName";
				break;

			case 5:
				arg2.type=PropertyInfo.STRING_CLASS;
				arg2.name="UseProSpec";
				break;

			case 6:
				arg2.type=PropertyInfo.STRING_CLASS;
				arg2.name="UseProModel";
				break;

			case 7:
				arg2.type=PropertyInfo.STRING_CLASS;
				arg2.name="UseProNumber";
				break;

			case 8:
				arg2.type=PropertyInfo.STRING_CLASS;
				arg2.name="UseProMeasureUnitName";
				break;

			case 9:
				arg2.type=PropertyInfo.LONG_CLASS;
				arg2.name="ProCategory";
				break;

			case 10:
				arg2.type=PropertyInfo.STRING_CLASS;
				arg2.name="ProCategoryName";
				break;

			case 11:
				arg2.type=PropertyInfo.LONG_CLASS;
				arg2.name="ProShape";
				break;

			case 12:
				arg2.type=PropertyInfo.STRING_CLASS;
				arg2.name="ProShapeName";
				break;

			case 13:
				arg2.type=PropertyInfo.STRING_CLASS;
				arg2.name="ProHazardNature";
				break;

			case 14:
				arg2.type=PropertyInfo.STRING_CLASS;
				arg2.name="ProPackName";
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
				arg2.name="ProDangerComponent";
				break;

			case 18:
				arg2.type=PropertyInfo.STRING_CLASS;
				arg2.name="Remark";
				break;

			case 19:
				arg2.type=PropertyInfo.LONG_CLASS;
				arg2.name="CreateComCusID";
				break;

			case 20:
				arg2.type=PropertyInfo.LONG_CLASS;
				arg2.name="CreateUserID";
				break;

			case 21:
				arg2.type=PropertyInfo.LONG_CLASS;
				arg2.name="CreateComBrID";
				break;

			case 22:
				arg2.type=PropertyInfo.LONG_CLASS;
				arg2.name="CreateComID";
				break;

			case 23:
				arg2.type=PropertyInfo.STRING_CLASS;
				arg2.name="CreateIp";
				break;

			case 24:
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
				RecNumber=arg1.toString();
				break;

			case 1:
				SysComID=Convert.ToInt64(arg1.toString());
				break;

			case 2:
				RecTime=arg1.toString();
				break;

			case 3:
				RecSource=Convert.ToInt32(arg1.toString());
				break;

			case 4:
				UseProName=arg1.toString();
				break;

			case 5:
				UseProSpec=arg1.toString();
				break;

			case 6:
				UseProModel=arg1.toString();
				break;

			case 7:
				UseProNumber=arg1.toString();
				break;

			case 8:
				UseProMeasureUnitName=arg1.toString();
				break;

			case 9:
				ProCategory=Convert.ToInt64(arg1.toString());
				break;

			case 10:
				ProCategoryName=arg1.toString();
				break;

			case 11:
				ProShape=Convert.ToInt64(arg1.toString());
				break;

			case 12:
				ProShapeName=arg1.toString();
				break;

			case 13:
				ProHazardNature=arg1.toString();
				break;

			case 14:
				ProPackName=arg1.toString();
				break;

			case 15:
				ProNumber=arg1.toString();
				break;

			case 16:
				ProMeasureUnitName=arg1.toString();
				break;

			case 17:
				ProDangerComponent=arg1.toString();
				break;

			case 18:
				Remark=arg1.toString();
				break;

			case 19:
				CreateComCusID=Convert.ToInt64(arg1.toString());
				break;

			case 20:
				CreateUserID=Convert.ToInt64(arg1.toString());
				break;

			case 21:
				CreateComBrID=Convert.ToInt64(arg1.toString());
				break;

			case 22:
				CreateComID= Convert.ToInt64(arg1.toString());
				break;

			case 23:
				CreateIp=arg1.toString();
				break;

			case 24:
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
				return RecNumber;

			case 1:
				return SysComID;

			case 2:
				return RecTime;

			case 3:
				return RecSource;

			case 4:
				return UseProName;

			case 5:
				return UseProSpec;

			case 6:
				return UseProModel;

			case 7:
				return UseProNumber;

			case 8:
				return UseProMeasureUnitName;

			case 9:
				return ProCategory;

			case 10:
				return ProCategoryName;

			case 11:
				return ProShape;

			case 12:
				return ProShapeName;

			case 13:
				return ProHazardNature;

			case 14:
				return ProPackName;

			case 15:
				return ProNumber;

			case 16:
				return ProMeasureUnitName;

			case 17:
				return ProDangerComponent;

			case 18:
				return Remark;

			case 19:
				return CreateComCusID;

			case 20:
				return CreateUserID;

			case 21:
				return CreateComBrID;

			case 22:
				return CreateComID;

			case 23:
				return CreateIp;

			case 24:
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
