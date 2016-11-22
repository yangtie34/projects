package cn.gilight.wechat.menu.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sword.wechat4j.exception.WeChatException;
import org.sword.wechat4j.menu.Menu;
import org.sword.wechat4j.menu.MenuButton;
import org.sword.wechat4j.menu.MenuManager;

import cn.gilight.framework.uitl.HttpResult;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
/**   
* @Description: TODO
* @author Sunwg  
* @date 2016年5月9日 下午6:14:39   
*/
@Controller("wechatMenuController")
@RequestMapping("/wechat/menu")
public class WechatMenuController {
	private Logger log = Logger.getLogger(WechatMenuController.class);
	
	/** 
	* @Description: 获取微信菜单
	* @return Menu
	*/
	@RequestMapping("/getMenus")
	@ResponseBody
	public Menu getMenu(){
		log.debug("获取微信的菜单列表");
		MenuManager manager = new MenuManager();
		return manager.getMenu();
	}
	
	/** 
	* @Description: 保存微信的菜单列表
	* @param menustr
	* @return HttpResult
	*/
	@RequestMapping("/saveMenus")
	@ResponseBody
	public HttpResult saveMenu(String menus){
		log.debug("保存微信的菜单列表");
		JSONObject m = JSONObject.parseObject(menus);
		Menu menuObj = changeJsonToMenuForWechat(m);
		MenuManager manager = new MenuManager();
		HttpResult result = new HttpResult();
		try {
			manager.create(menuObj);
			result.setSuccess(true);
		} catch (WeChatException e) {
			result.setSuccess(false);
			result.setErrmsg(e.getMessage());
		}
		return result;
	}
	
	private Menu changeJsonToMenuForWechat(JSONObject menuObj){
		Menu result = new Menu();
		List<MenuButton> buttons = new ArrayList<MenuButton>();
		JSONArray btns = menuObj.getJSONArray("button");		
		for (int i = 0; i < btns.size(); i++) {
			JSONObject it = (JSONObject) btns.get(i);
			MenuButton oneBtn = JSONObject.toJavaObject(it, MenuButton.class);
			List<MenuButton> subbtns = new ArrayList<MenuButton>();
			JSONArray subbtnObj = it.getJSONArray("subButton");
			if (subbtnObj != null) {
				for (int j = 0; j < subbtnObj.size(); j++) {
					MenuButton subbtn = JSONObject.toJavaObject(subbtnObj.getJSONObject(j), MenuButton.class);
					subbtns.add(subbtn);
				}
			}
			oneBtn.setSubButton(subbtns);
			buttons.add(oneBtn);
		}
		result.setButton(buttons);
		
		
		return result;
	}
}