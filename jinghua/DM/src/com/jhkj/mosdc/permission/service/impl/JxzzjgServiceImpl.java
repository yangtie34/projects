package com.jhkj.mosdc.permission.service.impl;

import java.lang.reflect.Method;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.JoinColumn;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.dao.BaseDao;
import com.jhkj.mosdc.framework.service.impl.BaseServiceImpl;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.framework.util.SysConstants;
import com.jhkj.mosdc.permission.dao.JxzzjgDao;
import com.jhkj.mosdc.permission.service.JxzzjgService;

/**
 * @comments: 教学组织结构service实现类
 * @author hanking(E-mail : IsaidIwillgoon@gmail.com)
 * @date:2012-8-1
 * @time:下午01:34:37
 * @version :
 */
public class JxzzjgServiceImpl extends BaseServiceImpl implements JxzzjgService {

	private JxzzjgDao jxzzjgDao;
	private BaseDao baseDao;

	public void setJxzzjgDao(JxzzjgDao jxzzjgDao) {
		this.jxzzjgDao = jxzzjgDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public String saveJxzzjg(String params) {
		JSONObject obj = JSONObject.fromObject(params);
		String id = obj.getString("id");
		String entityName = obj.getString("entityName");
		boolean flag = this.jxzzjgDao.isExitIdForEntity(entityName, id);
		if (!flag) {
			try {
				List list = Struts2Utils.requestJsonToLists(params);
				this.save(entityName, list,id);
			} catch (Exception e) {
				e.printStackTrace();
				return SysConstants.JSON_SUCCESS_FALSE;
			}
			return SysConstants.JSON_SUCCESS_TRUE;
		}
		return SysConstants.JSON_SUCCESS_FALSE;
	}

	/**
	 * 更改了原有在baseService的save方法，以实现教学组织结构的特殊需求
	 * @param entityName
	 * @param filedList
	 * @param id
	 * @throws Exception
	 */
	public void save(String entityName, List filedList,String id) throws Exception {
		if (null == filedList || filedList.size() <= 0) {
			return;
		}
		Class cls = Class.forName("com.jhkj.mosdc.framework.po."+entityName);
		Method[] methods = cls.getDeclaredMethods();
		// 创建对象
		Object obj = cls.newInstance();
		for (int k = 0; k < methods.length; k++) {
			String methodName = methods[k].getName();
			if (methodName.startsWith("get")) {
				String fieldName = methodName.substring(3, 4).toLowerCase()
						+ methodName.substring(4);
				for (int i = 0; i < filedList.size(); i++) {
					String[] tmp = (String[]) filedList.get(i);
					if (fieldName.equals(tmp[0])) {
						Column colAnn = methods[k]
								.getAnnotation(javax.persistence.Column.class);
						// String name = "";
						if (null != colAnn) {
							// name = colAnn.name();
							// 简单类型
							String setMethodName = "s"
									+ methodName.substring(1, methodName
											.length());
							// 方法对象
							Class clazz = methods[k].getReturnType();
							Method setMethod = cls.getMethod(setMethodName,
									new Class[] { clazz });
							// 转换类型
							String className = clazz.getName();

							Object aaa = null;
							if (className.equalsIgnoreCase("java.lang.String")) {
								aaa = tmp[1];
							} else if (className
									.equalsIgnoreCase("java.lang.Long")) {
								aaa = new Long("".equals(tmp[1]) ? "0" : tmp[1]);
							} else if (className
									.equalsIgnoreCase("java.lang.Byte")) {
								aaa = new Byte(tmp[1]);
							} else if (className
									.equalsIgnoreCase("java.lang.Double")) {
								aaa = new Double(tmp[1]);
							} else if (className
									.equalsIgnoreCase("java.lang.Integer")) {
								aaa = new Integer(tmp[1]);
							} else if (className
									.equalsIgnoreCase("java.lang.Boolean")) {
								aaa = new Boolean(tmp[1]);
							} else if (className
									.equalsIgnoreCase("java.lang.Float")) {
								aaa = new Float(tmp[1]);
							} else if (className
									.equalsIgnoreCase("java.lang.Short")) {
								aaa = new Short(tmp[1]);
							}

							// set值
							setMethod.invoke(obj, new Object[] { aaa });
						} else {
							JoinColumn colAjn = methods[k]
									.getAnnotation(javax.persistence.JoinColumn.class);
							// name = colAjn.name();
							// 主从表关联
							if (null != colAjn) {
								Class glb = methods[k].getReturnType();
								// 主键都是long型
								Class[] paraTypes = new Class[] { Long.class };
								// 创建关联表对象
								Object idObj = glb.newInstance();
								Method method = glb.getMethod("setId",
										paraTypes);
								// 保存关联表id
								method.invoke(idObj, new Object[] { new Long(
										tmp[1]) });
								// 方法名
								String setMethodName = "s"
										+ methodName.substring(1, methodName
												.length());
								// 方法对象
								Method setMethod = cls.getMethod(setMethodName,
										new Class[] { methods[k]
												.getReturnType() });
								// set值
								setMethod.invoke(obj, new Object[] { idObj });
							}
						}
					}
				}
			}
		}
		 //保存id
        Method setIdMethod = cls.getMethod("setId", new Class[]{Long.class});
        setIdMethod.invoke(obj, new Object[]{Long.valueOf(id)});
		
		this.baseDao.insert(obj);
	}

}
