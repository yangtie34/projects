package com.jhkj.mosdc.framework.dto;
/**
 * 个人中心栏目类。
 * 
 * @Comments: 
 * Company: 河南精华科技有限公司
 * Created by zhangzg(vbxnt521_edu@163.com)
 * @DATE: 2012-12-4
 * @TIME: 上午11:34:23
 */
public class ICenterPortlet {
	private long id;
	private String lmbt;
	private String serviceName;
	private String lmfz;
	private String lmbzxx;
	private String componentName;
	private String creater;
	private String createTime;
	private String imageFileName;
	private int quoterNum; // 多少位用户添加了它。
	
	public int getQuoterNum() {
		return quoterNum;
	}
	public void setQuoterNum(int quoterNum) {
		this.quoterNum = quoterNum;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getImageFileName() {
		return imageFileName;
	}
	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}
	public String getComponentName() {
		return componentName;
	}
	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLmbt() {
		return lmbt;
	}
	public void setLmbt(String lmbt) {
		this.lmbt = lmbt;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getLmfz() {
		return lmfz;
	}
	public void setLmfz(String lmfz) {
		this.lmfz = lmfz;
	}
	public String getLmbzxx() {
		return lmbzxx;
	}
	public void setLmbzxx(String lmbzxx) {
		this.lmbzxx = lmbzxx;
	}
}
