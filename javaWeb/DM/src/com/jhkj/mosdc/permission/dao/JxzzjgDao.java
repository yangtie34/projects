package com.jhkj.mosdc.permission.dao;

import com.jhkj.mosdc.framework.dao.BaseDao;


/**
 * @comments:教学组织结构dao层   包含教学组织结构层次中：班级信息 和专业信息
 * @author hanking(E-mail : IsaidIwillgoon@gmail.com)
 * @date:2012-8-1 
 * @time:上午11:46:31
 * @version :
 */
public interface JxzzjgDao{
    /**
     * 判断在这个实体内是否存在指定的id
     * @param id id
     * @param entityName 实体名
     * @return
     */
	public boolean isExitIdForEntity(String entityName, String id);

}


