package com.jhkj.mosdc.permission.dao.impl;

import com.jhkj.mosdc.framework.dao.impl.BaseDaoImpl;
import com.jhkj.mosdc.framework.po.TbBjxx;
import com.jhkj.mosdc.framework.po.TbZyxx;
import com.jhkj.mosdc.permission.dao.JxzzjgDao;

/**
 * @comments:
 * @author hanking(E-mail : IsaidIwillgoon@gmail.com)
 * @date:2012-8-1 
 * @time:上午11:52:19
 * @version :
 */
public class JxzzjgDaoImpl extends BaseDaoImpl implements JxzzjgDao {
	

	@Override
	public boolean isExitIdForEntity(String entityName, String id) {
		// 如果存在返回true
		Object  obj = null;
		if(entityName.equals("TbZyxx")){
			 obj= this.getHibernateTemplate().get(TbZyxx.class, Long.valueOf(id));
		}else if(entityName.equals("TbBjxx")){
			 obj= this.getHibernateTemplate().get(TbBjxx.class, Long.valueOf(id));
		}
		if(obj == null ){
			return false;
		}
		return true;
	}

}


