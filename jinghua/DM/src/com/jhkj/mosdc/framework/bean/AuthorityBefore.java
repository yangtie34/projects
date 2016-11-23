package com.jhkj.mosdc.framework.bean;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

import com.jhkj.mosdc.framework.dao.BaseDao;

public class AuthorityBefore implements MethodBeforeAdvice {

    /* （非 Javadoc）
     * @see org.springframework.aop.MethodBeforeAdvice#before(java.lang.reflect.Method, java.lang.Object[], java.lang.Object);
     */
    public void before(Method arg0, Object[] args, Object target) throws Throwable {
    	
        String objname = target.getClass().getSimpleName().substring(0,target.getClass().getSimpleName().lastIndexOf("DaoImpl"));
        BaseDao dao = (BaseDao)target;
        String whereCloud = "";
        	//这里根据权限，组织条件语句
        whereCloud = " and bjId in ('1001000000000018','1001000000000016') ";
        	
//        UserInfo userInfo = UserPermissionUtil.getUserInfo();
//        
//        String jsIds = userInfo.getRoleIds();
//        if(jsIds.length()>0){}
        	//获取角色
        	//根据角色确定当前用户访问的部门
        	//根据数据权限获取当前用户所能访问的班级或科室
        	
        //获取
//        dao.setWhereCloud(whereCloud);
    }
}


