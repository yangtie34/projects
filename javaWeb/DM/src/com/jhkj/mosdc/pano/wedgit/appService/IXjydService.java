package com.jhkj.mosdc.pano.wedgit.appService;

import java.util.List;
import java.util.Map;

/**
 * 全景-学籍异动Service。
 * @ClassName: XjydService 
 * @author zhangzg 
 * @date 2013年10月30日 下午3:46:17 
 *
 */
public interface IXjydService extends IAppService{
	/**
	 * 根据学生id获取该生异动记录信息。
	 * @Title: getYdxxById 
	 * @param xsId
	 * @return List<Map>
	 * @throws
	 */
	public List<Map> getYdxxById(long xsId);
}
