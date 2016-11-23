package com.jhnu.system.mian.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController{
	
	//@Autowired
//	private MenuService menuService;
//	@Autowired
//	private MenuSolrService menuSolrService;
	
	@RequestMapping(method=RequestMethod.GET, value="/main")
	public ModelAndView getMain() {
		ModelAndView mv=new ModelAndView("/main");
		return mv;
	}
	
/*	@RequiresRoles("teacher")
	@RequestMapping(method=RequestMethod.GET, value="/main")
	public ModelAndView getMain() {
		ModelAndView mv=new ModelAndView("/main");
		HttpSession session = ContextHolderUtils.getSession();
		User user =(User)session.getAttribute(Globals.USER_SESSION);
		UserExpandInfo ue=(UserExpandInfo)session.getAttribute(Globals.USER_EXPAND_INFO);
		Menu menu=new Menu(1);
		menu.setLevel_(1);
		List<Menu> menus=menuService.getMenuByThis(menu,user.getId());
		mv.addObject("menus", menus);
		mv.addObject("ue", ue);
		return mv;
	}
	
	@RequiresRoles("teacher")
	@ResponseBody
	@RequestMapping(value="/main/{path}",method=RequestMethod.POST)
	public ResultBean getBookBorrow(@PathVariable String path){
		ResultBean rb=new ResultBean();
		HttpSession session = ContextHolderUtils.getSession();
		User user =(User)session.getAttribute(Globals.USER_SESSION);
		//获取相关页面列表
		Menu menu=new Menu(1);
		menu.setLevel_(2);
		menu.setPath(path);
		List<Menu> menus=menuService.getMenuByThis(menu,user.getId());
		rb.setObject(menus);
		return rb;
	}
	
	@RequiresRoles("teacher")
	@RequestMapping(method=RequestMethod.GET, value="/searchmenu/{q}/{num}")
	public ModelAndView searchMenu(@PathVariable("q") String q,@PathVariable("num") int num) {
		ModelAndView mv=new ModelAndView("/pro/search");
		HttpSession session = ContextHolderUtils.getSession();
		User user =(User)session.getAttribute(Globals.USER_SESSION);
		UserExpandInfo ue=(UserExpandInfo)session.getAttribute(Globals.USER_EXPAND_INFO);
		
		SolrQueryEntity solrQueryEntity=new SolrQueryEntity();
		solrQueryEntity.setKeyWords(q);
		List<String> queryTypes=new ArrayList<String>();
		queryTypes.add("name");
		queryTypes.add("keyword");
		queryTypes.add("desc");
		solrQueryEntity.setQueryType(queryTypes);
		
		Page page =new Page();
		page.setCurrentPage(num);
		page.setNumPerPage(2); //每页显示数据数
		page.setStartIndex(); //设置起始数量
		
		menuSolrService.queryMenuDoc(solrQueryEntity, user.getId(),page);
		
		mv.addObject("q", q);
		mv.addObject("page", page);
		mv.addObject("ue", ue);
		
		return mv;
		
	}
	
	@RequiresRoles("teacher")
	@RequestMapping(method=RequestMethod.GET, value="/product/{menuid}")
	public ModelAndView getproduct(@PathVariable("menuid") Long menuid) {
		ModelAndView mv=null;
		HttpSession session = ContextHolderUtils.getSession();
		User user =(User)session.getAttribute(Globals.USER_SESSION);
		UserExpandInfo ue=(UserExpandInfo)session.getAttribute(Globals.USER_EXPAND_INFO);
		
		Menu menu=new Menu(1);
		menu.setId(menuid);
		menu.setLevel_(2);
		List<Menu> menus=menuService.getMenuByThis(menu,user.getId());
		if(menus.size()>0){
			mv=new ModelAndView("/pro"+menus.get(0).getUrl_());
		}else{
			mv=new ModelAndView("unauthorized");
		}
		mv.addObject("ue", ue);
		return mv;
		
	} */
}
