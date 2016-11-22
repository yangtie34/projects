package com.jhkj.mosdc.framework.dao;


/**
 * @Comments: 基础接口
 * Company: 河南精华科技有限公司
 * Created by gaodj(gaodongjie@126.com) 
 * @DATE:2012-12-3
 * @TIME: 下午2:59:12
 */
public interface IBaseDao {
	 /**
	  * 权限过滤
	  * @return
	  */
	 public abstract String getWhereCloud();
	 /**
	  * 权限过滤
	  * @param whereCloud
	  */
	 public abstract void setWhereCloud(String whereCloud); 
	
}


