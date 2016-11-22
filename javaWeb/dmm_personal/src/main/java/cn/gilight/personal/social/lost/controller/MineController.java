package cn.gilight.personal.social.lost.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jhnu.syspermiss.util.ContextHolderUtils;

import cn.gilight.framework.base.dao.HibernateDao;
import cn.gilight.framework.uitl.HttpResult;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.personal.po.TLostfound;
import cn.gilight.personal.social.lost.service.MineService;



@Controller("socialLostMineController")
@RequestMapping("/social/lost/mine")
public class MineController {

	private Logger log = Logger.getLogger(MineController.class);
	
	@Resource
	private HibernateDao hibernate;
	
	@Resource
	private MineService mineService;
	
	/**
	* @Description: 根据id查询“我的发文”
	* @return: HttpResult
	* @param myId
	* @return
	 */
	@RequestMapping("/queryMyTopic")
	@ResponseBody
	public HttpResult queryMyTopic(String username){
		HttpResult result = new HttpResult();
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String myUsername = principal.getName();
		log.debug(myUsername+ "查询我的发文");
		try {
			result.setResult(mineService.queryMyTopic(myUsername));
			result.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}
	
	/**
	* @Description: 根据id修改“是否解决”的状态
	* @return: HttpResult
	* @param myId
	* @param is_solve
	* @return
	 */
	@RequestMapping("/modifyStatus")
	@ResponseBody
	@Transactional
	public HttpResult modifyStatus(String id){
		HttpResult result = new HttpResult();
		try {
			mineService.modifyStatus(id);
			result.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}
	
	/**
	* @Description: 根据username删除“我的发文”
	* @return: HttpResult
	* @param id
	* @return
	 */
	@RequestMapping("/deleteTopic")
	@ResponseBody
	@Transactional
	public HttpResult deleteTopic(String id) {
		HttpResult result = new HttpResult();
		try {
			mineService.deleteTopic(id);
			result.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}

	/**
	* @Description: 保存“新建发文”
	* @return: HttpResult
	* @param topic
	* @return
	 */
	@RequestMapping("/saveTopic")
	@ResponseBody
	@Transactional
	public HttpResult saveTopic(TLostfound topic){
		HttpResult result = new HttpResult();
		try {
			AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
			String username = principal.getName();
			topic.setUsername(username);
			topic.setCreatTime(DateUtils.getCurrentTime());
			hibernate.save(topic);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			log.error(e.getMessage());
			result.setErrmsg("保存失败，请重试！");
		}
		return result;
	}
	
}
