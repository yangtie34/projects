package com.eyunsoft.app_wasteoilCostomer.Model;

import com.eyunsoft.app_wasteoilCostomer.Publics.Convert;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.io.Serializable;
import java.util.Hashtable;

public class TransferRec_Model  implements KvmSerializable ,Serializable{
		public TransferRec_Model()
		{
		}
		//���������ɵ��ֶ�
		private String RecNumber="";
		private long RecComID=0;
		private String RecTime="2016-11-01";
		private int RecSource=0;
		private int RecType=0;
		private int RecState=0;
		private long RecComCusID=0;
		private long RecComBrID=0;
		private String TransferTime="2016-11-01";
		private String TransferTaboo="";
		private String TransferAddress="";
		private long TransportComBrID=0;
		private String TransportComBrName="";
		private String DistNumber="";
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

		//���������ɵ�����
		public  void setRecNumber(String recNumber){this.RecNumber=recNumber;}
		public String getRecNumber(){ return this.RecNumber;}
		public  void setRecComID(long recComID){this.RecComID=recComID;}
		public long getRecComID(){ return this.RecComID;}
		public  void setRecTime(String recTime){this.RecTime=recTime;}
		public String getRecTime(){ return this.RecTime;}
		public  void setRecSource(int recSource){this.RecSource=recSource;}
		public int getRecSource(){ return this.RecSource;}
		public  void setRecType(int recType){this.RecType=recType;}
		public int getRecType(){ return this.RecType;}
		public  void setRecState(int recState){this.RecState=recState;}
		public int getRecState(){ return this.RecState;}
		public  void setRecComCusID(long recComCusID){this.RecComCusID=recComCusID;}
		public long getRecComCusID(){ return this.RecComCusID;}
		public  void setRecComBrID(long recComBrID){this.RecComBrID=recComBrID;}
		public long getRecComBrID(){ return this.RecComBrID;}
		public  void setTransferTime(String transferTime){this.TransferTime=transferTime;}
		public String getTransferTime(){ return this.TransferTime;}
		public  void setTransferTaboo(String transferTaboo){this.TransferTaboo=transferTaboo;}
		public String getTransferTaboo(){ return this.TransferTaboo;}
		public  void setTransferAddress(String transferAddress){this.TransferAddress=transferAddress;}
		public String getTransferAddress(){ return this.TransferAddress;}
		public  void setTransportComBrID(long transportComBrID){this.TransportComBrID=transportComBrID;}
		public long getTransportComBrID(){ return this.TransportComBrID;}
		public  void setTransportComBrName(String transportComBrName){this.TransportComBrName=transportComBrName;}
		public String getTransportComBrName(){ return this.TransportComBrName;}
		public  void setDistNumber(String distNumber){this.DistNumber=distNumber;}
		public String getDistNumber(){ return this.DistNumber;}
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
		return 30;
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
						arg2.name="RecComID";
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
						arg2.type=PropertyInfo.INTEGER_CLASS;
						arg2.name="RecType";
						break;

				case 5:
						arg2.type=PropertyInfo.INTEGER_CLASS;
						arg2.name="RecState";
						break;

				case 6:
						arg2.type=PropertyInfo.LONG_CLASS;
						arg2.name="RecComCusID";
						break;

				case 7:
						arg2.type=PropertyInfo.LONG_CLASS;
						arg2.name="RecComBrID";
						break;

				case 8:
						arg2.type=PropertyInfo.STRING_CLASS;
						arg2.name="TransferTime";
						break;

				case 9:
						arg2.type=PropertyInfo.STRING_CLASS;
						arg2.name="TransferTaboo";
						break;

				case 10:
						arg2.type=PropertyInfo.STRING_CLASS;
						arg2.name="TransferAddress";
						break;

				case 11:
						arg2.type=PropertyInfo.LONG_CLASS;
						arg2.name="TransportComBrID";
						break;

				case 12:
						arg2.type=PropertyInfo.STRING_CLASS;
						arg2.name="TransportComBrName";
						break;

				case 13:
						arg2.type=PropertyInfo.STRING_CLASS;
						arg2.name="DistNumber";
						break;

				case 14:
						arg2.type=PropertyInfo.LONG_CLASS;
						arg2.name="ProCategory";
						break;

				case 15:
						arg2.type=PropertyInfo.STRING_CLASS;
						arg2.name="ProCategoryName";
						break;

				case 16:
						arg2.type=PropertyInfo.LONG_CLASS;
						arg2.name="ProShape";
						break;

				case 17:
						arg2.type=PropertyInfo.STRING_CLASS;
						arg2.name="ProShapeName";
						break;

				case 18:
						arg2.type=PropertyInfo.STRING_CLASS;
						arg2.name="ProHazardNature";
						break;

				case 19:
						arg2.type=PropertyInfo.STRING_CLASS;
						arg2.name="ProPackName";
						break;

				case 20:
						arg2.type=PropertyInfo.STRING_CLASS;
						arg2.name="ProNumber";
						break;

				case 21:
						arg2.type=PropertyInfo.STRING_CLASS;
						arg2.name="ProMeasureUnitName";
						break;

				case 22:
						arg2.type=PropertyInfo.STRING_CLASS;
						arg2.name="ProDangerComponent";
						break;

				case 23:
						arg2.type=PropertyInfo.STRING_CLASS;
						arg2.name="Remark";
						break;

				case 24:
						arg2.type=PropertyInfo.LONG_CLASS;
						arg2.name="CreateComCusID";
						break;

				case 25:
						arg2.type=PropertyInfo.LONG_CLASS;
						arg2.name="CreateUserID";
						break;

				case 26:
						arg2.type=PropertyInfo.LONG_CLASS;
						arg2.name="CreateComBrID";
						break;

				case 27:
						arg2.type=PropertyInfo.LONG_CLASS;
						arg2.name="CreateComID";
						break;

				case 28:
						arg2.type=PropertyInfo.STRING_CLASS;
						arg2.name="CreateIp";
						break;

				case 29:
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
						RecNumber=arg1.toString();
						break;

				case 1:
						RecComID= Convert.ToInt64(arg1.toString());
						break;

				case 2:
						RecTime=arg1.toString();
						break;

				case 3:
						RecSource=Convert.ToInt32(arg1.toString());
						break;

				case 4:
						RecType=Convert.ToInt32(arg1.toString());
						break;

				case 5:
						RecState=Convert.ToInt32(arg1.toString());
						break;

				case 6:
						RecComCusID=Convert.ToInt64(arg1.toString());
						break;

				case 7:
						RecComBrID=Convert.ToInt64(arg1.toString());
						break;

				case 8:
						TransferTime=arg1.toString();
						break;

				case 9:
						TransferTaboo=arg1.toString();
						break;

				case 10:
						TransferAddress=arg1.toString();
						break;

				case 11:
						TransportComBrID=Convert.ToInt64(arg1.toString());
						break;

				case 12:
						TransportComBrName=arg1.toString();
						break;

				case 13:
						DistNumber=arg1.toString();
						break;

				case 14:
						ProCategory=Convert.ToInt64(arg1.toString());
						break;

				case 15:
						ProCategoryName=arg1.toString();
						break;

				case 16:
						ProShape=Convert.ToInt64(arg1.toString());
						break;

				case 17:
						ProShapeName=arg1.toString();
						break;

				case 18:
						ProHazardNature=arg1.toString();
						break;

				case 19:
						ProPackName=arg1.toString();
						break;

				case 20:
						ProNumber=arg1.toString();
						break;

				case 21:
						ProMeasureUnitName=arg1.toString();
						break;

				case 22:
						ProDangerComponent=arg1.toString();
						break;

				case 23:
						Remark=arg1.toString();
						break;

				case 24:
						CreateComCusID=Convert.ToInt64(arg1.toString());
						break;

				case 25:
						CreateUserID=Convert.ToInt64(arg1.toString());
						break;

				case 26:
						CreateComBrID=Convert.ToInt64(arg1.toString());
						break;

				case 27:
						CreateComID=Convert.ToInt64(arg1.toString());
						break;

				case 28:
						CreateIp=arg1.toString();
						break;

				case 29:
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
						 return RecComID;

				case 2:
						 return RecTime;

				case 3:
						 return RecSource;

				case 4:
						 return RecType;

				case 5:
						 return RecState;

				case 6:
						 return RecComCusID;

				case 7:
						 return RecComBrID;

				case 8:
						 return TransferTime;

				case 9:
						 return TransferTaboo;

				case 10:
						 return TransferAddress;

				case 11:
						 return TransportComBrID;

				case 12:
						 return TransportComBrName;

				case 13:
						 return DistNumber;

				case 14:
						 return ProCategory;

				case 15:
						 return ProCategoryName;

				case 16:
						 return ProShape;

				case 17:
						 return ProShapeName;

				case 18:
						 return ProHazardNature;

				case 19:
						 return ProPackName;

				case 20:
						 return ProNumber;

				case 21:
						 return ProMeasureUnitName;

				case 22:
						 return ProDangerComponent;

				case 23:
						 return Remark;

				case 24:
						 return CreateComCusID;

				case 25:
						 return CreateUserID;

				case 26:
						 return CreateComBrID;

				case 27:
						 return CreateComID;

				case 28:
						 return CreateIp;

				case 29:
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
