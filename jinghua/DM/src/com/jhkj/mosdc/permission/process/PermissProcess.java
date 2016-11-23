package com.jhkj.mosdc.permission.process;

/**
 * @Comments: 权限资源处理接口
 * Company: 河南精华科技有限公司
 * Created by gaodongjie(gaodongjie@126.com) 
 * @DATE:2012-6-19
 * @TIME: 下午5:20:19
 */
public interface PermissProcess {
	
	/**
	 * 获取用户权限菜单按钮树
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	public Object queryPermissCdzy(String param) throws Exception;
}
