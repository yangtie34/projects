package com.eyunsoft.app_wasteoil.Model;


import com.eyunsoft.app_wasteoil.Publics.Convert;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class CompanyCustomerApply_Model implements KvmSerializable {
	public CompanyCustomerApply_Model() {
	}

	//���������ɵ��ֶ�
	private long ApplyID = 0;
	private long ComCusID = 0;
	private String ContactName = "";
	private String ContactMobile = "";
	private String ContactEmail = "";
	private long ContactArea = 0;
	private String ContactAddress = "";
	private String WeiXinNumber = "";
	private String ComFullName = "";
	private int BusinessLicenseType ;
	private String BusinessLicenseNumber = "";
	private String BusinessLicenseImg = "";
	private String BusinessLicenseAddress = "";
	private String BusinessScope = "";
	private String LegalRepresentative = "";
	private String OrganizationCodeNumber = "";
	private String OrganizationCodeImg = "";
	private String DangerWasteName = "";
	private String DangerWasteNumber = "";
	private String DangerWasteLegalPerson = "";
	private String DangerWasteAddress = "";
	private String DangerWasteImg = "";
	private String DangerWasteBusinessCategory = "";
	private String DangerWasteBusinessMode = "";
	private String DangerWasteBusinessScale = "";
	private String DangerWasteValidityTimeStart = "2016-11-01";
	private String DangerWasteValidityTimeEnd = "2016-11-01";
	private int AuditState = 0;
	private String CreateIp = "";
	private String CreateTime = "2016-11-01";

	//���������ɵ�����
	public void setApplyID(long applyID) {
		this.ApplyID = applyID;
	}

	public long getApplyID() {
		return this.ApplyID;
	}

	public void setComCusID(long comCusID) {
		this.ComCusID = comCusID;
	}

	public long getComCusID() {
		return this.ComCusID;
	}

	public void setContactName(String contactName) {
		this.ContactName = contactName;
	}

	public String getContactName() {
		return this.ContactName;
	}

	public void setContactMobile(String contactMobile) {
		this.ContactMobile = contactMobile;
	}

	public String getContactMobile() {
		return this.ContactMobile;
	}

	public void setContactEmail(String contactEmail) {
		this.ContactEmail = contactEmail;
	}

	public String getContactEmail() {
		return this.ContactEmail;
	}

	public void setContactArea(long contactArea) {
		this.ContactArea = contactArea;
	}

	public long getContactArea() {
		return this.ContactArea;
	}

	public void setContactAddress(String contactAddress) {
		this.ContactAddress = contactAddress;
	}

	public String getContactAddress() {
		return this.ContactAddress;
	}

	public void setWeiXinNumber(String weiXinNumber) {
		this.WeiXinNumber = weiXinNumber;
	}

	public String getWeiXinNumber() {
		return this.WeiXinNumber;
	}

	public void setComFullName(String comFullName) {
		this.ComFullName = comFullName;
	}

	public String getComFullName() {
		return this.ComFullName;
	}

	public void setBusinessLicenseType(int businessLicenseType) {
		this.BusinessLicenseType = businessLicenseType;
	}

	public int getBusinessLicenseType() {
		return this.BusinessLicenseType;
	}

	public void setBusinessLicenseNumber(String businessLicenseNumber) {
		this.BusinessLicenseNumber = businessLicenseNumber;
	}

	public String getBusinessLicenseNumber() {
		return this.BusinessLicenseNumber;
	}

	public void setBusinessLicenseImg(String businessLicenseImg) {
		this.BusinessLicenseImg = businessLicenseImg;
	}

	public String getBusinessLicenseImg() {
		return this.BusinessLicenseImg;
	}

	public void setBusinessLicenseAddress(String businessLicenseAddress) {
		this.BusinessLicenseAddress = businessLicenseAddress;
	}

	public String getBusinessLicenseAddress() {
		return this.BusinessLicenseAddress;
	}

	public void setBusinessScope(String businessScope) {
		this.BusinessScope = businessScope;
	}

	public String getBusinessScope() {
		return this.BusinessScope;
	}

	public void setLegalRepresentative(String legalRepresentative) {
		this.LegalRepresentative = legalRepresentative;
	}

	public String getLegalRepresentative() {
		return this.LegalRepresentative;
	}

	public void setOrganizationCodeNumber(String organizationCodeNumber) {
		this.OrganizationCodeNumber = organizationCodeNumber;
	}

	public String getOrganizationCodeNumber() {
		return this.OrganizationCodeNumber;
	}

	public void setOrganizationCodeImg(String organizationCodeImg) {
		this.OrganizationCodeImg = organizationCodeImg;
	}

	public String getOrganizationCodeImg() {
		return this.OrganizationCodeImg;
	}

	public void setDangerWasteName(String dangerWasteName) {
		this.DangerWasteName = dangerWasteName;
	}

	public String getDangerWasteName() {
		return this.DangerWasteName;
	}

	public void setDangerWasteNumber(String dangerWasteNumber) {
		this.DangerWasteNumber = dangerWasteNumber;
	}

	public String getDangerWasteNumber() {
		return this.DangerWasteNumber;
	}

	public void setDangerWasteLegalPerson(String dangerWasteLegalPerson) {
		this.DangerWasteLegalPerson = dangerWasteLegalPerson;
	}

	public String getDangerWasteLegalPerson() {
		return this.DangerWasteLegalPerson;
	}

	public void setDangerWasteAddress(String dangerWasteAddress) {
		this.DangerWasteAddress = dangerWasteAddress;
	}

	public String getDangerWasteAddress() {
		return this.DangerWasteAddress;
	}

	public void setDangerWasteImg(String dangerWasteImg) {
		this.DangerWasteImg = dangerWasteImg;
	}

	public String getDangerWasteImg() {
		return this.DangerWasteImg;
	}

	public void setDangerWasteBusinessCategory(String dangerWasteBusinessCategory) {
		this.DangerWasteBusinessCategory = dangerWasteBusinessCategory;
	}

	public String getDangerWasteBusinessCategory() {
		return this.DangerWasteBusinessCategory;
	}

	public void setDangerWasteBusinessMode(String dangerWasteBusinessMode) {
		this.DangerWasteBusinessMode = dangerWasteBusinessMode;
	}

	public String getDangerWasteBusinessMode() {
		return this.DangerWasteBusinessMode;
	}

	public void setDangerWasteBusinessScale(String dangerWasteBusinessScale) {
		this.DangerWasteBusinessScale = dangerWasteBusinessScale;
	}

	public String getDangerWasteBusinessScale() {
		return this.DangerWasteBusinessScale;
	}

	public void setDangerWasteValidityTimeStart(String dangerWasteValidityTimeStart) {
		this.DangerWasteValidityTimeStart = dangerWasteValidityTimeStart;
	}

	public String getDangerWasteValidityTimeStart() {
		return this.DangerWasteValidityTimeStart;
	}

	public void setDangerWasteValidityTimeEnd(String dangerWasteValidityTimeEnd) {
		this.DangerWasteValidityTimeEnd = dangerWasteValidityTimeEnd;
	}

	public String getDangerWasteValidityTimeEnd() {
		return this.DangerWasteValidityTimeEnd;
	}

	public void setAuditState(int auditState) {
		this.AuditState = auditState;
	}

	public int getAuditState() {
		return this.AuditState;
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
	public void getPropertyInfo(int arg0, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo arg2) {
		// TODO Auto-generated method stub
		switch (arg0) {
			case 0:
				arg2.type = PropertyInfo.LONG_CLASS;
				arg2.name = "ApplyID";
				break;

			case 1:
				arg2.type = PropertyInfo.LONG_CLASS;
				arg2.name = "ComCusID";
				break;

			case 2:
				arg2.type = PropertyInfo.STRING_CLASS;
				arg2.name = "ContactName";
				break;

			case 3:
				arg2.type = PropertyInfo.STRING_CLASS;
				arg2.name = "ContactMobile";
				break;

			case 4:
				arg2.type = PropertyInfo.STRING_CLASS;
				arg2.name = "ContactEmail";
				break;

			case 5:
				arg2.type = PropertyInfo.LONG_CLASS;
				arg2.name = "ContactArea";
				break;

			case 6:
				arg2.type = PropertyInfo.STRING_CLASS;
				arg2.name = "ContactAddress";
				break;

			case 7:
				arg2.type = PropertyInfo.STRING_CLASS;
				arg2.name = "WeiXinNumber";
				break;

			case 8:
				arg2.type = PropertyInfo.STRING_CLASS;
				arg2.name = "ComFullName";
				break;

			case 9:
				arg2.type = PropertyInfo.INTEGER_CLASS;
				arg2.name = "BusinessLicenseType";
				break;

			case 10:
				arg2.type = PropertyInfo.STRING_CLASS;
				arg2.name = "BusinessLicenseNumber";
				break;

			case 11:
				arg2.type = PropertyInfo.STRING_CLASS;
				arg2.name = "BusinessLicenseImg";
				break;

			case 12:
				arg2.type = PropertyInfo.STRING_CLASS;
				arg2.name = "BusinessLicenseAddress";
				break;

			case 13:
				arg2.type = PropertyInfo.STRING_CLASS;
				arg2.name = "BusinessScope";
				break;

			case 14:
				arg2.type = PropertyInfo.STRING_CLASS;
				arg2.name = "LegalRepresentative";
				break;

			case 15:
				arg2.type = PropertyInfo.STRING_CLASS;
				arg2.name = "OrganizationCodeNumber";
				break;

			case 16:
				arg2.type = PropertyInfo.STRING_CLASS;
				arg2.name = "OrganizationCodeImg";
				break;

			case 17:
				arg2.type = PropertyInfo.STRING_CLASS;
				arg2.name = "DangerWasteName";
				break;

			case 18:
				arg2.type = PropertyInfo.STRING_CLASS;
				arg2.name = "DangerWasteNumber";
				break;

			case 19:
				arg2.type = PropertyInfo.STRING_CLASS;
				arg2.name = "DangerWasteLegalPerson";
				break;

			case 20:
				arg2.type = PropertyInfo.STRING_CLASS;
				arg2.name = "DangerWasteAddress";
				break;

			case 21:
				arg2.type = PropertyInfo.STRING_CLASS;
				arg2.name = "DangerWasteImg";
				break;

			case 22:
				arg2.type = PropertyInfo.STRING_CLASS;
				arg2.name = "DangerWasteBusinessCategory";
				break;

			case 23:
				arg2.type = PropertyInfo.STRING_CLASS;
				arg2.name = "DangerWasteBusinessMode";
				break;

			case 24:
				arg2.type = PropertyInfo.STRING_CLASS;
				arg2.name = "DangerWasteBusinessScale";
				break;

			case 25:
				arg2.type = PropertyInfo.STRING_CLASS;
				arg2.name = "DangerWasteValidityTimeStart";
				break;

			case 26:
				arg2.type = PropertyInfo.STRING_CLASS;
				arg2.name = "DangerWasteValidityTimeEnd";
				break;

			case 27:
				arg2.type = PropertyInfo.INTEGER_CLASS;
				arg2.name = "AuditState";
				break;

			case 28:
				arg2.type = PropertyInfo.STRING_CLASS;
				arg2.name = "CreateIp";
				break;

			case 29:
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
				ApplyID = Convert.ToInt64(arg1.toString());
				break;

			case 1:
				ComCusID = Convert.ToInt64(arg1.toString());
				break;

			case 2:
				ContactName = arg1.toString();
				break;

			case 3:
				ContactMobile = arg1.toString();
				break;

			case 4:
				ContactEmail = arg1.toString();
				break;

			case 5:
				ContactArea = Convert.ToInt64(arg1.toString());
				break;

			case 6:
				ContactAddress = arg1.toString();
				break;

			case 7:
				WeiXinNumber = arg1.toString();
				break;

			case 8:
				ComFullName = arg1.toString();
				break;

			case 9:
				BusinessLicenseType = Convert.ToInt32(arg1.toString());
				break;

			case 10:
				BusinessLicenseNumber = arg1.toString();
				break;

			case 11:
				BusinessLicenseImg = arg1.toString();
				break;

			case 12:
				BusinessLicenseAddress = arg1.toString();
				break;

			case 13:
				BusinessScope = arg1.toString();
				break;

			case 14:
				LegalRepresentative = arg1.toString();
				break;

			case 15:
				OrganizationCodeNumber = arg1.toString();
				break;

			case 16:
				OrganizationCodeImg = arg1.toString();
				break;

			case 17:
				DangerWasteName = arg1.toString();
				break;

			case 18:
				DangerWasteNumber = arg1.toString();
				break;

			case 19:
				DangerWasteLegalPerson = arg1.toString();
				break;

			case 20:
				DangerWasteAddress = arg1.toString();
				break;

			case 21:
				DangerWasteImg = arg1.toString();
				break;

			case 22:
				DangerWasteBusinessCategory = arg1.toString();
				break;

			case 23:
				DangerWasteBusinessMode = arg1.toString();
				break;

			case 24:
				DangerWasteBusinessScale = arg1.toString();
				break;

			case 25:
				DangerWasteValidityTimeStart = arg1.toString();
				break;

			case 26:
				DangerWasteValidityTimeEnd = arg1.toString();
				break;

			case 27:
				AuditState = Convert.ToInt32(arg1.toString());
				break;

			case 28:
				CreateIp = arg1.toString();
				break;

			case 29:
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
				return ApplyID;

			case 1:
				return ComCusID;

			case 2:
				return ContactName;

			case 3:
				return ContactMobile;

			case 4:
				return ContactEmail;

			case 5:
				return ContactArea;

			case 6:
				return ContactAddress;

			case 7:
				return WeiXinNumber;

			case 8:
				return ComFullName;

			case 9:
				return BusinessLicenseType;

			case 10:
				return BusinessLicenseNumber;

			case 11:
				return BusinessLicenseImg;

			case 12:
				return BusinessLicenseAddress;

			case 13:
				return BusinessScope;

			case 14:
				return LegalRepresentative;

			case 15:
				return OrganizationCodeNumber;

			case 16:
				return OrganizationCodeImg;

			case 17:
				return DangerWasteName;

			case 18:
				return DangerWasteNumber;

			case 19:
				return DangerWasteLegalPerson;

			case 20:
				return DangerWasteAddress;

			case 21:
				return DangerWasteImg;

			case 22:
				return DangerWasteBusinessCategory;

			case 23:
				return DangerWasteBusinessMode;

			case 24:
				return DangerWasteBusinessScale;

			case 25:
				return DangerWasteValidityTimeStart;

			case 26:
				return DangerWasteValidityTimeEnd;

			case 27:
				return AuditState;

			case 28:
				return CreateIp;

			case 29:
				return CreateTime;

			default:
				break;
		}
		return null;
	}

	@Override
	public int getPropertyCount() {
		return 30;
	}
}

