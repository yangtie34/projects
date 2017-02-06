package cn.gilight.research.mbrws.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gilight.framework.uitl.HttpResult;
import cn.gilight.research.mbrws.service.GdMbrwsService;

/**   
* @Description: 工业大学的教学单位目标任务书
* @author Sunwg
* @date 2016年12月6日 下午2:48:52   
*/
@Controller
@RequestMapping("/gd/mbrws")
public class GdMbrwsController {
	//private Logger log = Logger.getLogger(GdMbrwsController.class);

	@Resource
	private GdMbrwsService mbrwsService;

	/** 
	* @Description: 查询考核主题列表
	* @return: List<Map<String,Object>>
	*/
	@RequestMapping("/khztlist")
	@ResponseBody
	public HttpResult queryKhztList(){
		//String shiroTag = "research:mbrws:index:khztlist";
		HttpResult result = new HttpResult();
		result.setResult(mbrwsService.queryKhztList());
		result.setSuccess(true);
		return result;
	}
	
	/** 
	* @Description: 查询考核计划列表
	* @return: List<Map<String,Object>>
	*/
	@RequestMapping("/khjhlist")
	@ResponseBody
	public HttpResult queryKhjhList() {
		//String shiroTag = "research:mbrws:index:khjhlist";
		HttpResult result = new HttpResult();
		result.setResult(mbrwsService.queryKhjhList());
		result.setSuccess(true);
		return result;
	}

	/** 
	 * @Description: 查询学科排名考核结果
	 * @param zzjgid
	 * @param khjhid
	 * @return: HttpResult
	 */
	@RequestMapping("/xkpm")
	@ResponseBody
	public HttpResult queryXkpm(String zzjgid,String khjhid) {
		String shiroTag = "research:mbrws:index:xkpm";
		HttpResult result = new HttpResult();
		result.setResult(mbrwsService.queryXkpmList(zzjgid, khjhid, shiroTag));
		result.setSuccess(true);
		return result;
	}
	
	/** 
	* @Description: 查询考核主题结果
	* @param zzjgid
	* @param khjhid
	* @param khztid
	* @return: HttpResult
	*/
	@RequestMapping("/khzt")
	@ResponseBody
	public HttpResult queryKhzt(String zzjgid,String khjhid,String khztid) {
		//String shiroTag = "research:mbrws:index:khzt";
		HttpResult result = new HttpResult();
		result.setResult(mbrwsService.queryKhxmListOfkhzt(khztid, zzjgid, khjhid));
		result.setSuccess(true);
		return result;
	}
}