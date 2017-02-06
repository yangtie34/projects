package cn.gilight.framework.base.dao;

import java.io.Serializable;
import java.util.List;

/**   
* @Description: hibernate操作dao
* @author Sunwg  
* @date 2016年3月8日 上午9:58:53   
*/
public interface HibernateDao {

	/** 
	* @Title: createQuery 
	* @Description: 创建普通查询
	* @param queryString
	* @return List<Object>
	*/
	public abstract List<Object> createQuery(String queryString);

	/** 
	* @Title: save 
	* @Description: 保存对象
	* @param model
	* @return Object
	*/
	public abstract Object save(Object model) throws SecurityException, NoSuchFieldException;

	/**
	 * 保存 或 更新
	 * @param t
	 * @throws SecurityException
	 * @throws NoSuchFieldException void
	 */
	public void saveOrUpdate(Object t) throws SecurityException, NoSuchFieldException ;
	
	/** 
	* @Title: saveAll 
	* @Description: 保存对象集合
	* @param list
	* @throws SecurityException
	* @throws NoSuchFieldException
	* @return void
	*/
	public void saveAll(List<?> list) throws SecurityException, NoSuchFieldException;

	/** 
	* @Title: update 
	* @param model
	* @Description: 更新对象
	*/
	public abstract void update(Object model);
	
	/** 
	* @Title: updates 
	* @Description: 更新对象集合
	* @param list
	* @return void
	*/
	public void updates(List<?> list);

	/** 
	* @Title: delete 
	* @Description: 删除对象
	* @param model
	* @return void
	*/
	public abstract void delete(Object model);

	/** 
	* @Title: deletes 
	* @Description: 删除对象集合
	* @param list
	* @return void
	*/
	public void deletes(List<?> list);
	
	/** 
	* @Title: deleteByIds 
	* @Description: 根据ID集合删除对象集合
	* @param ids
	* @param cls
	* @return void
	*/
	public <T> void deleteByIds(List<?> ids, Class<T> cls);
	
	/** 
	* @Title: deleteAll 
	* @Description: 清空一个表
	* @param cls
	* @return void
	*/
	public <T> void deleteAll(Class<T> cls);
	
	/** 
	* @Title: createHqlQuery 
	* @Description: 创建hql查询
	* @param hql
	* @return List
	*/
	@SuppressWarnings("rawtypes")
	public abstract List createHqlQuery(String hql);

	/** 
	* @Title: getById 
	* @Description: 根据主键获取对象
	* @param id
	* @param cls
	* @return T
	*/
	@SuppressWarnings("hiding")
	public abstract <T> T getById(Serializable id, Class<T> cls);

	/** 
	* @Title: findByExample 
	* @Description: 根据对象属性查找对象集合
	* @param t
	* @return List<T>
	*/
	@SuppressWarnings("hiding")
	public abstract <T> List<T> findByExample(T t);

	/** 
	* @Title: getByIds 
	* @Description: 根据主键集合查找对象集合
	* @param ids
	* @param cls
	* @return List<T>
	*/
	public abstract <T>List<T> getByIds(String[] ids, Class<T> cls);

	/** 
	* @Title: getTotalCount 
	* @Description: TODO 获取对象对应数据库记录数
	* @param cls
	* @return Long
	*/
	public abstract <T>Long getTotalCount(T cls);

	/** 
	* @Title: update 
	* @Description: TODO 根据对象属性更新对象集合，返回更新数量
	* @param hql
	* @param field
	* @return int
	*/
	public abstract int update(String hql, Object... field);
	/** 
	* @Title: saveAll 
	* @Description: 保存对象集合（自动提交）
	* @param list
	* @throws SecurityException
	* @throws NoSuchFieldException
	* @return void
	*/
	public void saveAllAutoCommit(List<?> list) throws SecurityException,
			NoSuchFieldException;
}