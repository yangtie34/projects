package cn.gilight.dmm.business.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gilight.dmm.business.service.AdvancedService;

/**
 * 
 * 
 * @author xuebl
 * @date 2016年5月18日 下午8:29:45
 */
@Controller
@RequestMapping("advanced")
public class AdvancedCtol {

	@Resource
	private AdvancedService advancedService;
	
	@RequestMapping()
	@ResponseBody
	public List<Map<String, Object>> query(String tag){
		return advancedService.queryAdvancedList(tag);
	}
	
}
