package com.jhkj.mosdc.output.dao;
/**
 * 个人信息查询接口
 * 
 * @Comments: 
 * Company: 河南精华科技有限公司
 * Created by zhangzg(vbxnt521_edu@163.com)
 * @DATE: 2012-12-20
 * @TIME: 下午12:01:33
 */
public interface IPersonalInfoDao {
	/**
	 * 根据用户id获取该用户的信息。
	 * @param userid
	 * @return
	 */
	public Object queryPersonInfo(String xgh);
	/**
	 * 保存用户的登录时间和登录ip
	 */
	public boolean savePersonInfo(String loginTime,String loginIP);
}
