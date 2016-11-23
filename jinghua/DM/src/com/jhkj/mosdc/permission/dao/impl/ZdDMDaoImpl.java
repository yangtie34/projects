package com.jhkj.mosdc.permission.dao.impl;

import java.util.List;

import com.jhkj.mosdc.framework.dao.impl.BaseDaoImpl;
import com.jhkj.mosdc.permission.dao.ZdDMDao;

/**
 * @Comments: 学校标准字典代码结构DAO实现类
 * Company: 河南精华科技有限公司
 * Created by gaodongjie(gaodongjie@126.com) 
 * @DATE:2012-5-24
 * @TIME: 上午9:48:59
 */
public class ZdDMDaoImpl extends BaseDaoImpl implements ZdDMDao {
	
	/**
	 * 获取字典代码分类数据
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	@Override
	public List queryZdDMType(String param) throws Exception{
		//根据条件查询所有类别
		StringBuffer hsql = new StringBuffer().append(" from TcXxbzdmjg c where 1=1 ");
		//判断条件是否为空
		if(!"".equals(param) && param != null){
			hsql.append(" and c.bzdm='"+param.toString()+"' ");
		}
		//
		hsql.append(" and (c.sfxtj = 0 or c.sfxtj is null) group by c.bzdm");
		List list = this.getSession().createQuery(hsql.toString()).list();
		return list;
	}

}
