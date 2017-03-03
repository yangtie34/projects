package com.eyunsoft.app_wasteoil.Model;

import com.eyunsoft.app_wasteoil.Publics.Convert;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.io.Serializable;
import java.util.Hashtable;

public class SysNotice_Model implements KvmSerializable ,Serializable {
	public SysNotice_Model() {
	}

	//以下是生成的字段
	private long NoticeID = 0;
	private int NoticeType = 0;
	private String NoticeRecNumber = "";
	private int NoticeTarget = 0;
	private int NoticeAcceptMode = 0;
	private String NoticeTime = "2016-11-01";
	private String NoticeTitle = "";
	private String NoticeContent = "";
	private boolean IsAudit = false;
	private long CreateUserID = 0;
	private long CreateComBrID = 0;
	private long CreateComID = 0;
	private String CreateIp = "";
	private String CreateTime = "2016-11-01";
	private boolean Exist = false;

	//以下是生成的属性
	public void setNoticeID(long noticeID) {
		this.NoticeID = noticeID;
	}

	public long getNoticeID() {
		return this.NoticeID;
	}

	public void setNoticeType(int noticeType) {
		this.NoticeType = noticeType;
	}

	public int getNoticeType() {
		return this.NoticeType;
	}

	public void setNoticeRecNumber(String noticeRecNumber) {
		this.NoticeRecNumber = noticeRecNumber;
	}

	public String getNoticeRecNumber() {
		return this.NoticeRecNumber;
	}

	public void setNoticeTarget(int noticeTarget) {
		this.NoticeTarget = noticeTarget;
	}

	public int getNoticeTarget() {
		return this.NoticeTarget;
	}

	public void setNoticeAcceptMode(int noticeAcceptMode) {
		this.NoticeAcceptMode = noticeAcceptMode;
	}

	public int getNoticeAcceptMode() {
		return this.NoticeAcceptMode;
	}

	public void setNoticeTime(String noticeTime) {
		this.NoticeTime = noticeTime;
	}

	public String getNoticeTime() {
		return this.NoticeTime;
	}

	public void setNoticeTitle(String noticeTitle) {
		this.NoticeTitle = noticeTitle;
	}

	public String getNoticeTitle() {
		return this.NoticeTitle;
	}

	public void setNoticeContent(String noticeContent) {
		this.NoticeContent = noticeContent;
	}

	public String getNoticeContent() {
		return this.NoticeContent;
	}

	public void setIsAudit(boolean isAudit) {
		this.IsAudit = isAudit;
	}

	public boolean getIsAudit() {
		return this.IsAudit;
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


	public void setExist(boolean exist){this.Exist=true;}
	public boolean IsExist() {
		return this.Exist;
	}

	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 14;
	}

	@Override
	public void getPropertyInfo(int arg0, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo arg2) {
		// TODO Auto-generated method stub
		switch (arg0) {
			case 0:
				arg2.type = PropertyInfo.LONG_CLASS;
				arg2.name = "NoticeID";
				break;

			case 1:
				arg2.type = PropertyInfo.INTEGER_CLASS;
				arg2.name = "NoticeType";
				break;

			case 2:
				arg2.type = PropertyInfo.STRING_CLASS;
				arg2.name = "NoticeRecNumber";
				break;

			case 3:
				arg2.type = PropertyInfo.INTEGER_CLASS;
				arg2.name = "NoticeTarget";
				break;

			case 4:
				arg2.type = PropertyInfo.INTEGER_CLASS;
				arg2.name = "NoticeAcceptMode";
				break;

			case 5:
				arg2.type = PropertyInfo.STRING_CLASS;
				arg2.name = "NoticeTime";
				break;

			case 6:
				arg2.type = PropertyInfo.STRING_CLASS;
				arg2.name = "NoticeTitle";
				break;

			case 7:
				arg2.type = PropertyInfo.STRING_CLASS;
				arg2.name = "NoticeContent";
				break;

			case 8:
				arg2.type = PropertyInfo.BOOLEAN_CLASS;
				arg2.name = "IsAudit";
				break;

			case 9:
				arg2.type = PropertyInfo.LONG_CLASS;
				arg2.name = "CreateUserID";
				break;

			case 10:
				arg2.type = PropertyInfo.LONG_CLASS;
				arg2.name = "CreateComBrID";
				break;

			case 11:
				arg2.type = PropertyInfo.LONG_CLASS;
				arg2.name = "CreateComID";
				break;

			case 12:
				arg2.type = PropertyInfo.STRING_CLASS;
				arg2.name = "CreateIp";
				break;

			case 13:
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
				NoticeID = Convert.ToInt64(arg1.toString());
				break;

			case 1:
				NoticeType = Convert.ToInt32(arg1.toString());
				break;

			case 2:
				NoticeRecNumber = arg1.toString();
				break;

			case 3:
				NoticeTarget = Convert.ToInt32(arg1.toString());
				break;

			case 4:
				NoticeAcceptMode = Convert.ToInt32(arg1.toString());
				break;

			case 5:
				NoticeTime = arg1.toString();
				break;

			case 6:
				NoticeTitle = arg1.toString();
				break;

			case 7:
				NoticeContent = arg1.toString();
				break;

			case 8:
				IsAudit = Convert.ToBoolean(arg1.toString());
				break;

			case 9:
				CreateUserID = Convert.ToInt64(arg1.toString());
				break;

			case 10:
				CreateComBrID = Convert.ToInt64(arg1.toString());
				break;

			case 11:
				CreateComID = Convert.ToInt64(arg1.toString());
				break;

			case 12:
				CreateIp = arg1.toString();
				break;

			case 13:
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
				return NoticeID;

			case 1:
				return NoticeType;

			case 2:
				return NoticeRecNumber;

			case 3:
				return NoticeTarget;

			case 4:
				return NoticeAcceptMode;

			case 5:
				return NoticeTime;

			case 6:
				return NoticeTitle;

			case 7:
				return NoticeContent;

			case 8:
				return IsAudit;

			case 9:
				return CreateUserID;

			case 10:
				return CreateComBrID;

			case 11:
				return CreateComID;

			case 12:
				return CreateIp;

			case 13:
				return CreateTime;

			default:
				break;
		}
		return null;
	}


}
