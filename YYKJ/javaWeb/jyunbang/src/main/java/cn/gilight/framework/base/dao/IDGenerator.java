package cn.gilight.framework.base.dao;

import java.util.List;

/**
 * ID生成器
 * @Title: IDGenerator.java 
 * @Package cn.gilight.framework.dp.id 
 * @Description: (该文件做什么) 
 * @author wangyt
 * @date 2015年3月10日 下午4:01:09 
 * @version V1.0
 */
public interface IDGenerator {

	/**
	 * 生成一个ID
	 * @return
	 */
	public String getId();
	
	/**
	 * 生成指定数量的ID
	 */
	public List<String> getIds(int num);
}
