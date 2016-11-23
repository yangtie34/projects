package com.jhkj.mosdc.framework.dao;

import java.util.List;

public interface BaseBatchDao {
	/**
	 * 每个对象中的id为空时用此方法，如果id不为空，则对象原有的id，会被覆盖
	 * @param objList
	 * @param clazz
	 * @throws Throwable
	 */
	public void batchInsertWidthoutId(final List<?> objList,Class clazz) throws Throwable;
	/**
	 * 每个对象的id不能为空
	 * @param objList
	 * @param clazz
	 * @throws Throwable
	 */
	public void batchInsertWidthId(final List<?> objList,Class clazz) throws Throwable;
	
	public void batchUpdate(final List<?> objList,Class clazz) throws Throwable;
}
