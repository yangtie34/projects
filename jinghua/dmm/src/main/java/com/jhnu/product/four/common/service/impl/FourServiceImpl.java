package com.jhnu.product.four.common.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.framework.spring.ApplicationComponentStaticRetriever;
import com.jhnu.product.four.common.dao.FourDao;
import com.jhnu.product.four.common.entity.FourMethod;
import com.jhnu.product.four.common.entity.FourNot;
import com.jhnu.product.four.common.entity.ResultBean;
import com.jhnu.product.four.common.service.FourService;

@Service("fourService")
public class FourServiceImpl implements FourService{
	
	@Autowired
	private FourDao fourDao;
	
	@Override
	public List<FourMethod> getFourMethods() {
		return fourDao.getFourMethods();
	}

	@Override
	public List<FourMethod> getFourMethods(String userid) {
		return fourDao.getFourMethods(userid);
	}

	@Override
	public List<FourNot> getFourNotByThis(FourNot fourNot) {
		return fourDao.getFourNotByThis(fourNot);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ResultBean exetMethod(String bean,String methodName,String userId){
		ResultBean result=null;
		//获取服务对象
        Object serviceBean = ApplicationComponentStaticRetriever.getComponentByItsName(bean);
        Class cls = serviceBean.getClass();
        //反射方法
        Method method;
		try {
			method = cls.getMethod(methodName,String.class);
			result = (ResultBean)method.invoke(serviceBean,userId);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return result;
	}
	@Override
	public int getFourYearDays(String enrollYear,int lengthSchooling){
		return 507;
	}
	
	@Override
	public boolean isFourShared(String stuid) {
		List<Map<String,Object>> list = fourDao.getFourIsShared(stuid);
		if(list.size()==0){ 
			return false;
		}else{
			return true;
		}
	}
	
	@Override
	public void saveShared(String stuid) {
		fourDao.saveFourShared(stuid);
	}
	
	@Override
	public void delFourShared(String stuid) {
		fourDao.delFourShared(stuid);
	}
	
	@Override
	public String getSchoolName() {
		return fourDao.getSchoolName();
	}
}
