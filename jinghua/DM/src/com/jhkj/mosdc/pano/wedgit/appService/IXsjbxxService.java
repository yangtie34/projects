package com.jhkj.mosdc.pano.wedgit.appService;

import java.util.List;
import java.util.Map;

/**
 * 全景-学生基本信息Service。
 * @ClassName: XsjbxxService 
 * @author zhangzg 
 * @date 2013年10月30日 上午10:36:23 
 *
 */
public interface IXsjbxxService extends IAppService{
	/**
	 * 根据学生Id获取学生基本信息数据。
	 * @Title: getXjxxById 
	 * @param @param xsId
	 * @param @return
	 * @return List<Map>
	 * @throws
	 */
	public List<Map> getXjxxByXsId(long xsId);
	/**
	 * 根据学生Id获取学生家庭情况信息。
	 * @Title: getJtqkByXsId 
	 * @param @param xsId
	 * @param @return
	 * @return List<Map>
	 * @throws
	 */
	public List<Map> getJtqkByXsId(long xsId);
	/**
	 * 根据学生Id获取其班主任信息（重要的是联系电话）。
	 * @Title: getBzrxxByXsId 
	 * @param @param xsId
	 * @param @return
	 * @return List<Map>
	 * @throws
	 */
	public List<Map> getBzrxxByXsId(long xsId);
	/**
	 * 根据学生Id获取学生宿舍信息。
	 * @Title: getSsxxByXsId 
	 * @param @param xsId
	 * @param @return
	 * @return List<Map>
	 * @throws
	 */
	public List<Map> getSsxxByXsId(long xsId);
	/**
	 * 根据学生Id获取学生的在校职务信息。
	 * @Title: getZwxxByXsId 
	 * @param @param xsId
	 * @param @return
	 * @return List<Map>
	 * @throws
	 */
	public List<Map> getZwxxByXsId(long xsId);
}
