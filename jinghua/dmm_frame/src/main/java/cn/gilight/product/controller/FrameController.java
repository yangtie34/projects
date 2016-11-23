package cn.gilight.product.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.gilight.framework.uitl.common.UserUtil;

import com.alibaba.fastjson.JSON;
import com.jhnu.syspermiss.GetCachePermiss;
import com.jhnu.syspermiss.permiss.entity.Resources;

@Controller
@RequestMapping("public")
public class FrameController {
	private int level=0;
	@RequestMapping(value="/{sysCode}",method=RequestMethod.GET)
	public ModelAndView pub(@PathVariable("sysCode") String sysCode){
		ModelAndView mv=new ModelAndView("/main");
		String username=UserUtil.getCasLoginName();
		List<Resources> resources=GetCachePermiss.getSysMenusByUserName(username);
		List<Resources> treeList=new ArrayList<Resources>();
		List<Long> pids=new ArrayList<Long>();
		for(Resources tree:resources){
			String[] urls=tree.getUrl_().split("/");
			if(urls[urls.length-1].equalsIgnoreCase(sysCode)){
				treeList.add(tree);
				pids.add(tree.getId());
				break;
			}
		}
		mv.addObject("res", JSON.toJSON(getlist(resources,pids)));
		mv.addObject("level", level);
		return mv;
	}
	
	public List<Resources> getlist(List<Resources> resources,List<Long> pids){
		
		List<Resources> resours=new ArrayList<Resources>();
		List<Long> pids_=new ArrayList<Long>();
		for(Resources tree:resources){
			for(long pid:pids){
				 if(pid-tree.getPid()==0){
					 if(level==0){
						 level=tree.getLevel_();
					 }
					 resours.add(tree);
					 pids_.add(tree.getId());
					 break;
				 }
			}
		}
		if(pids_.size()!=0){
			resours.addAll(getlist(resources,pids_));
		}
		return resours;
		
	}
}
