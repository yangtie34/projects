package com.yiyun.wasteoilcustom.Model;


import com.chengyi.android.util.Convert;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class TransferRecState_Model implements KvmSerializable {
	public TransferRecState_Model() {
	}

	//���������ɵ��ֶ�
	private String RecNumber = "";
	private int RecState = 0;
	private String Remark = "";
	private long CreateComCusID = 0;
	private long CreateUserID = 0;
	private long CreateComBrID = 0;
	private long CreateComID = 0;
	private String CreateIp = "";
	private String CreateTime = "2016-11-01";
	private boolean Exist = false;

	//���������ɵ�����
	public void setRecNumber(String recNumber) {
		this.RecNumber = recNumber;
	}

	public String getRecNumber() {
		return this.RecNumber;
	}

	public void setRecState(int recState) {
		this.RecState = recState;
	}

	public int getRecState() {
		return this.RecState;
	}

	public void setRemark(String remark) {
		this.Remark = remark;
	}

	public String getRemark() {
		return this.Remark;
	}

	public void setCreateComCusID(long createComCusID) {
		this.CreateComCusID = createComCusID;
	}

	public long getCreateComCusID() {
		return this.CreateComCusID;
	}

	public void setCreateUserID(long createUserID) {
		this.CreateUserID = createUserID;
	}

	public long getCreateUserID() {
		return this.CreateUserID;
	}

	public void setCreateComBrID(long createComBrID) {
		this.CreateComBrID = createComBrID;
	}

	public long getCreateComBrID() {
		return this.CreateComBrID;
	}

	public void setCreateComID(long createComID) {
		this.CreateComID = createComID;
	}

	public long getCreateComID() {
		return this.CreateComID;
	}

	public void setCreateIp(String createIp) {
		this.CreateIp = createIp;
	}

	public String getCreateIp() {
		return this.CreateIp;
	}

	public void setCreateTime(String createTime) {
		this.CreateTime = createTime;
	}

	public String getCreateTime() {
		return this.CreateTime;
	}

	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 9;
	}

	@Override
	public void getPropertyInfo(int arg0, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo arg2) {
		// TODO Auto-generated method stub
		switch (arg0) {
			case 0:
				arg2.type = PropertyInfo.STRING_CLASS;
				arg2.name = "RecNumber";
				break;

			case 1:
				arg2.type = PropertyInfo.INTEGER_CLASS;
				arg2.name = "RecState";
				break;

			case 2:
				arg2.type = PropertyInfo.STRING_CLASS;
				arg2.name = "Remark";
				break;

			case 3:
				arg2.type = PropertyInfo.LONG_CLASS;
				arg2.name = "CreateComCusID";
				break;

			case 4:
				arg2.type = PropertyInfo.LONG_CLASS;
				arg2.name = "CreateUserID";
				break;

			case 5:
				arg2.type = PropertyInfo.LONG_CLASS;
				arg2.name = "CreateComBrID";
				break;

			case 6:
				arg2.type = PropertyInfo.LONG_CLASS;
				arg2.name = "CreateComID";
				break;

			case 7:
				arg2.type = PropertyInfo.STRING_CLASS;
				arg2.name = "CreateIp";
				break;

			case 8:
				arg2.type = PropertyInfo.STRING_CLASS;
				arg2.name = "CreateTime";
				break;

			default:
				break;
		}
	}

	@Override
	public void setProperty(int arg0, Object arg1) {
		switch (arg0) {
			case 0:
				RecNumber = arg1.toString();
				break;

			case 1:
				RecState = Convert.ToInt32(arg1.toString());
				break;

			case 2:
				Remark = arg1.toString();
				break;

			case 3:
				CreateComCusID = Convert.ToInt64(arg1.toString());
				break;

			case 4:
				CreateUserID = Convert.ToInt64(arg1.toString());
				break;

			case 5:
				CreateComBrID = Convert.ToInt64(arg1.toString());
				break;

			case 6:
				CreateComID = Convert.ToInt64(arg1.toString());
				break;

			case 7:
				CreateIp = arg1.toString();
				break;

			case 8:
				CreateTime = arg1.toString();
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
				return RecState;

			case 2:
				return Remark;

			case 3:
				return CreateComCusID;

			case 4:
				return CreateUserID;

			case 5:
				return CreateComBrID;

			case 6:
				return CreateComID;

			case 7:
				return CreateIp;

			case 8:
				return CreateTime;

			default:
				break;
		}
		return null;
	}

	public boolean IsExist() {
		return this.Exist;
	}
}
