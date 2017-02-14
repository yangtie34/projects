package com.yiyun.wasteoilcustom.Model;



import com.chengyi.android.util.Convert;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class SysNoticeRead_Model implements KvmSerializable {
	public SysNoticeRead_Model() {
	}

	//锟斤拷锟斤拷锟斤拷锟斤拷锟缴碉拷锟街讹拷
	private long ReadID = 0;
	private long NoticeID = 0;
	private long CreateComCusID = 0;
	private long CreateUserID = 0;
	private long CreateComBrID = 0;
	private long CreateComID = 0;
	private String CreateIp = "";
	private String CreateTime = "2016-11-01";
	private boolean Exist = false;

	//锟斤拷锟斤拷锟斤拷锟斤拷锟缴碉拷锟斤拷锟斤拷
	public void setReadID(long readID) {
		this.ReadID = readID;
	}

	public long getReadID() {
		return this.ReadID;
	}

	public void setNoticeID(long noticeID) {
		this.NoticeID = noticeID;
	}

	public long getNoticeID() {
		return this.NoticeID;
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
	public void setExist(boolean exist){this.Exist=exist;}

	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 8;
	}

	@Override
	public void getPropertyInfo(int arg0, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo arg2) {
		// TODO Auto-generated method stub
		switch (arg0) {
			case 0:
				arg2.type = PropertyInfo.LONG_CLASS;
				arg2.name = "ReadID";
				break;

			case 1:
				arg2.type = PropertyInfo.LONG_CLASS;
				arg2.name = "NoticeID";
				break;

			case 2:
				arg2.type = PropertyInfo.LONG_CLASS;
				arg2.name = "CreateComCusID";
				break;

			case 3:
				arg2.type = PropertyInfo.LONG_CLASS;
				arg2.name = "CreateUserID";
				break;

			case 4:
				arg2.type = PropertyInfo.LONG_CLASS;
				arg2.name = "CreateComBrID";
				break;

			case 5:
				arg2.type = PropertyInfo.LONG_CLASS;
				arg2.name = "CreateComID";
				break;

			case 6:
				arg2.type = PropertyInfo.STRING_CLASS;
				arg2.name = "CreateIp";
				break;

			case 7:
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
				ReadID = Convert.ToInt64(arg1.toString());
				break;

			case 1:
				NoticeID = Convert.ToInt64(arg1.toString());
				break;

			case 2:
				CreateComCusID = Convert.ToInt64(arg1.toString());
				break;

			case 3:
				CreateUserID = Convert.ToInt64(arg1.toString());
				break;

			case 4:
				CreateComBrID = Convert.ToInt64(arg1.toString());
				break;

			case 5:
				CreateComID = Convert.ToInt64(arg1.toString());
				break;

			case 6:
				CreateIp = arg1.toString();
				break;

			case 7:
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
				return ReadID;

			case 1:
				return NoticeID;

			case 2:
				return CreateComCusID;

			case 3:
				return CreateUserID;

			case 4:
				return CreateComBrID;

			case 5:
				return CreateComID;

			case 6:
				return CreateIp;

			case 7:
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
