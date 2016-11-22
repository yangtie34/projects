package com.jhnu.product.four.common.controller;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.jhnu.product.four.award.service.FourAwardService;
import com.jhnu.product.four.book.service.FourBookService;
import com.jhnu.product.four.card.service.FourCardService;
import com.jhnu.product.four.common.entity.FourMethod;
import com.jhnu.product.four.common.entity.ResultBean;
import com.jhnu.product.four.common.service.FourService;
import com.jhnu.product.four.net.service.NetService;
import com.jhnu.product.four.punish.service.FourPunishService;
import com.jhnu.product.four.relation.service.FourRelationService;
import com.jhnu.product.four.score.service.FourScoreService;
import com.jhnu.product.four.wall.service.FourWallService;
import com.jhnu.system.common.condition.Condition;
import com.jhnu.system.common.page.Page;
import com.jhnu.system.permiss.entity.User;
import com.jhnu.system.permiss.entity.UserExpandInfo;
import com.jhnu.system.permiss.service.UserService;
import com.jhnu.util.common.ContextHolderUtils;
import com.jhnu.util.product.Globals;


@Controller
@RequestMapping("/four")
public class FourController {
	@Autowired
	private FourService fourService;
	@Autowired
	private FourBookService fourBookService;
	@Autowired 
	private FourCardService fourCardService;
	@Autowired
	private FourAwardService fourAwardService;
	@Autowired
	private FourScoreService fourScoreService;
	@Autowired
	private FourRelationService fourRelationService;
	@Autowired
	private FourWallService fourWallService;
	@Autowired
	private UserService userService;
	@Autowired
	private FourPunishService fourPunishService;
	@Autowired
	private NetService netService;
	
	
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	public ModelAndView getFour(@PathVariable String id) {
		ModelAndView mv=new ModelAndView("four/four");
		HttpSession session = ContextHolderUtils.getSession();
		User user=(User)session.getAttribute(Globals.USER_SESSION);
		boolean flag=fourService.isFourShared(id);
		boolean isMe=false;
		if(user!=null&&user.getUsername()!=null&&user.getUsername().equals(id)){
			mv.addObject("isMe", true);
			isMe=true;
		}else{
			mv.addObject("isMe", false);
		}
		if(isMe||flag){
			UserExpandInfo ue= userService.getUserExpandInfo(id);
			List<FourMethod> fours=fourService.getFourMethods(id);
			for (int i = 0; i < fours.size(); i++) {
				mv.addObject(fours.get(i).getMethod(), true);
			}
			mv.addObject("id", id);
			mv.addObject("isShare", flag);
			mv.addObject("ue", ue);
		}else{
			mv=new ModelAndView("/login");
		}
		return mv;
	}
	
	
	@RequestMapping(method=RequestMethod.GET, value="/share")
	public ModelAndView shareMe() {
		ModelAndView mv=new ModelAndView("");
		HttpSession session = ContextHolderUtils.getSession();
		User user=(User)session.getAttribute(Globals.USER_SESSION);
		if(user!=null&&user.getUsername()!=null){
			fourService.saveShared(user.getUsername());
			mv=new ModelAndView("redirect:/four/"+user.getUsername());
		}else{
			mv=new ModelAndView("/login");
		}
		return mv;
	}
	
	
	@RequestMapping(method=RequestMethod.GET, value="/share/del")
	public ModelAndView shareDelMe() {
		ModelAndView mv=new ModelAndView("");
		HttpSession session = ContextHolderUtils.getSession();
		User user=(User)session.getAttribute(Globals.USER_SESSION);
		if(user!=null&&user.getUsername()!=null){
			fourService.delFourShared(user.getUsername());
			mv=new ModelAndView("redirect:/four/"+user.getUsername());
		}else{
			mv=new ModelAndView("/login");
		}
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(method=RequestMethod.POST, value="/schoolname/{id}")
	public ResultBean getSchoolName(@PathVariable String id) {
		ResultBean rb = new ResultBean();
		
		HttpSession session = ContextHolderUtils.getSession();
		User user=(User)session.getAttribute(Globals.USER_SESSION);
		if((user!=null&&user.getUsername()!=null&&user.getUsername().equals(id))||fourService.isFourShared(id)){
			rb.setObject(fourService.getSchoolName());
		}
		return rb;	
	}
//	
//	@RequestMapping(method=RequestMethod.GET, value="/view")
	public ModelAndView getViewFour() {
		ModelAndView mv=new ModelAndView("four/viewfour");
		List<FourMethod> fours;
		fours=fourService.getFourMethods();
		for (int i = 0; i < fours.size(); i++) {
			mv.addObject(fours.get(i).getMethod(), true);
		}
		//视图页面本身就是只针对个人开发的。故在此处将ID固定。
		HttpSession session = ContextHolderUtils.getSession();
		User user=(User)session.getAttribute(Globals.USER_SESSION);
		mv.addObject("id", user.getUsername());
		return mv;
	}
	
	
	@RequestMapping(method=RequestMethod.GET, value="/card/detail/{id}")
	public ModelAndView getCardDetail(@PathVariable String id) {
		ModelAndView mv=new ModelAndView("four/card/detail");
		
		HttpSession session = ContextHolderUtils.getSession();
		User user=(User)session.getAttribute(Globals.USER_SESSION);
		if((user!=null&&user.getUsername()!=null&&user.getUsername().equals(id))||fourService.isFourShared(id)){
			mv.addObject("id", id);
		}else{
			mv=new ModelAndView("unauthorized");
		}
		return mv;
	}
	
	
	@RequestMapping(method=RequestMethod.GET, value="/book/detail/{id}")
	public ModelAndView getBookDetail(@PathVariable String id) {
		ModelAndView mv=new ModelAndView("four/book/detail");
		
		HttpSession session = ContextHolderUtils.getSession();
		User user=(User)session.getAttribute(Globals.USER_SESSION);
		if((user!=null&&user.getUsername()!=null&&user.getUsername().equals(id))||fourService.isFourShared(id)){
			mv.addObject("id", id);
		}else{
			mv=new ModelAndView("unauthorized");
		}
		return mv;
	}
	
	
	@RequestMapping(method=RequestMethod.GET, value="/score/detail/{id}")
	public ModelAndView getScoreDetail(@PathVariable String id) {
		ModelAndView mv=new ModelAndView("four/score/detail");
		
		HttpSession session = ContextHolderUtils.getSession();
		User user=(User)session.getAttribute(Globals.USER_SESSION);
		if((user!=null&&user.getUsername()!=null&&user.getUsername().equals(id))||fourService.isFourShared(id)){
			mv.addObject("id", id);
		}else{
			mv=new ModelAndView("unauthorized");
		}
		return mv;
	}
	
	
	
	@ResponseBody
	@RequestMapping(value="/first/book/{id}",method=RequestMethod.POST)
	public ResultBean getFirstBook(@PathVariable String id){
		ResultBean rb=new ResultBean();
		HttpSession session = ContextHolderUtils.getSession();
		User user=(User)session.getAttribute(Globals.USER_SESSION);
		
		if((user!=null&&user.getUsername()!=null&&user.getUsername().equals(id))||fourService.isFourShared(id)){
			rb=fourBookService.getFirstBookLog(id);
		}
		return rb;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/book/borrow/{id}",method=RequestMethod.POST)
	public ResultBean getBookBorrow(@PathVariable String id){
		ResultBean rb=new ResultBean();
		HttpSession session = ContextHolderUtils.getSession();
		User user=(User)session.getAttribute(Globals.USER_SESSION);
		
		if((user!=null&&user.getUsername()!=null&&user.getUsername().equals(id))||fourService.isFourShared(id)){
			rb=fourBookService.getAllBorrowLog(id);
		}
		return rb;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/punish/{id}",method=RequestMethod.POST)
	public ResultBean getPunishMsg(@PathVariable String id){
		ResultBean rb=new ResultBean();
		HttpSession session = ContextHolderUtils.getSession();
		User user=(User)session.getAttribute(Globals.USER_SESSION);
		
		if((user!=null&&user.getUsername()!=null&&user.getUsername().equals(id))||fourService.isFourShared(id)){
			rb=fourPunishService.getStuPunishMsgByID(id);
		}
		return rb;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/book/rke/{id}",method=RequestMethod.POST)
	public ResultBean getBookRKE(@PathVariable String id){
		ResultBean rb=new ResultBean();
		HttpSession session = ContextHolderUtils.getSession();
		User user=(User)session.getAttribute(Globals.USER_SESSION);
		
		if((user!=null&&user.getUsername()!=null&&user.getUsername().equals(id))||fourService.isFourShared(id)){
			rb=fourBookService.getAllRKELog(id);
		}
		return rb;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/wash/{id}",method=RequestMethod.POST)
	public ResultBean getWashMsg(@PathVariable String id){
		ResultBean rb=new ResultBean();
		HttpSession session = ContextHolderUtils.getSession();
		User user=(User)session.getAttribute(Globals.USER_SESSION);
		
		if((user!=null&&user.getUsername()!=null&&user.getUsername().equals(id))||fourService.isFourShared(id)){
			rb=fourCardService.getWashTimes(id);
		}
		return rb; 
	}
	
	
	@ResponseBody
	@RequestMapping(value="/firstpay/msg/{id}",method=RequestMethod.POST)
	public ResultBean getFirstPayMsg(@PathVariable String id){
		ResultBean rb=new ResultBean();
		HttpSession session = ContextHolderUtils.getSession();
		User user=(User)session.getAttribute(Globals.USER_SESSION);
		
		if((user!=null&&user.getUsername()!=null&&user.getUsername().equals(id))||fourService.isFourShared(id)){
			rb=fourCardService.getFirstPayMsg(id);
		}
		return rb; 
		
	}
	
	
	@ResponseBody
	@RequestMapping(value="/sum/moneyAndTimes/{id}",method=RequestMethod.POST)
	public ResultBean getSumMoneyAndTimes(@PathVariable String id){
		ResultBean rb=new ResultBean();
		HttpSession session = ContextHolderUtils.getSession();
		User user=(User)session.getAttribute(Globals.USER_SESSION);
		
		if((user!=null&&user.getUsername()!=null&&user.getUsername().equals(id))||fourService.isFourShared(id)){
			rb=fourCardService.getSumMoneyAndPayTimes(id);
		}
		return rb; 
	}
	
	
	@ResponseBody
	@RequestMapping(value="/card/pay/count/{id}",method=RequestMethod.POST)
	public ResultBean getCardPayCount(@PathVariable String id){
		ResultBean rb=new ResultBean();
		HttpSession session = ContextHolderUtils.getSession();
		User user=(User)session.getAttribute(Globals.USER_SESSION);
		
		if((user!=null&&user.getUsername()!=null&&user.getUsername().equals(id))||fourService.isFourShared(id)){
			rb=fourCardService.getPayCount(id);
		}
		return rb; 
	}
	
	
	@ResponseBody
	@RequestMapping(value="/card/rec/count/{id}",method=RequestMethod.POST)
	public ResultBean getCardRecCount(@PathVariable String id){
		ResultBean rb=new ResultBean();
		HttpSession session = ContextHolderUtils.getSession();
		User user=(User)session.getAttribute(Globals.USER_SESSION);
		
		if((user!=null&&user.getUsername()!=null&&user.getUsername().equals(id))||fourService.isFourShared(id)){
			rb=fourCardService.getRecCount(id);
		}
		return rb; 
	}
	
	
	@ResponseBody
	@RequestMapping(value="/award/times/{id}",method=RequestMethod.POST)
	public ResultBean getAwardTimes(@PathVariable String id){
		ResultBean rb=new ResultBean();
		HttpSession session = ContextHolderUtils.getSession();
		User user=(User)session.getAttribute(Globals.USER_SESSION);
		
		if((user!=null&&user.getUsername()!=null&&user.getUsername().equals(id))||fourService.isFourShared(id)){
			rb=fourAwardService.getAwardTimes(id);
		}
		return rb; 
	}
	
	
	@ResponseBody
	@RequestMapping(value="/first/award/{id}",method=RequestMethod.POST)
	public ResultBean getFirstAwardMsg(@PathVariable String id){
		ResultBean rb=new ResultBean();
		HttpSession session = ContextHolderUtils.getSession();
		User user=(User)session.getAttribute(Globals.USER_SESSION);
		
		if((user!=null&&user.getUsername()!=null&&user.getUsername().equals(id))||fourService.isFourShared(id)){
			rb=fourAwardService.getFirstAwardMsg(id);
		}
		return rb; 
	}
	
	
	@ResponseBody
	@RequestMapping(value="/award/subsidy/{id}",method=RequestMethod.POST)
	public ResultBean getSubsidyMsg(@PathVariable String id){
		ResultBean rb=new ResultBean();
		HttpSession session = ContextHolderUtils.getSession();
		User user=(User)session.getAttribute(Globals.USER_SESSION);
		
		if((user!=null&&user.getUsername()!=null&&user.getUsername().equals(id))||fourService.isFourShared(id)){
			rb=fourAwardService.getSubsidyTimesAndMoney(id);
		}
		return rb; 
	}
	
	
	@ResponseBody
	@RequestMapping(value="/score/avg/{id}",method=RequestMethod.POST)
	public ResultBean getScoreAvg(@PathVariable String id){
		ResultBean rb=new ResultBean();
		HttpSession session = ContextHolderUtils.getSession();
		User user=(User)session.getAttribute(Globals.USER_SESSION);
		
		if((user!=null&&user.getUsername()!=null&&user.getUsername().equals(id))||fourService.isFourShared(id)){
			rb=fourScoreService.getAvgScoreLog(id);
		}
		return rb; 
	}
	
	
	@ResponseBody
	@RequestMapping(value="/score/rank/{id}",method=RequestMethod.POST)
	public ResultBean getScoreRank(@PathVariable String id){
		ResultBean rb=new ResultBean();
		HttpSession session = ContextHolderUtils.getSession();
		User user=(User)session.getAttribute(Globals.USER_SESSION);
		
		if((user!=null&&user.getUsername()!=null&&user.getUsername().equals(id))||fourService.isFourShared(id)){
			rb=fourScoreService.getBestRankLog(id);
		}
		return rb; 
	}
	
	
	@ResponseBody
	@RequestMapping(value="/score/count/{id}",method=RequestMethod.POST)
	public ResultBean getScoreCount(@PathVariable String id){
		ResultBean rb=new ResultBean();
		HttpSession session = ContextHolderUtils.getSession();
		User user=(User)session.getAttribute(Globals.USER_SESSION);
		
		if((user!=null&&user.getUsername()!=null&&user.getUsername().equals(id))||fourService.isFourShared(id)){
			rb=fourScoreService.getScoreCountLog(id);
		}
		return rb; 
	}
	
	
	@ResponseBody
	@RequestMapping(value="/score/down/{id}",method=RequestMethod.POST)
	public ResultBean getScoreDown(@PathVariable String id){
		ResultBean rb=new ResultBean();
		HttpSession session = ContextHolderUtils.getSession();
		User user=(User)session.getAttribute(Globals.USER_SESSION);
		
		if((user!=null&&user.getUsername()!=null&&user.getUsername().equals(id))||fourService.isFourShared(id)){
			rb=fourScoreService.getFirstDownLog(id);
		}
		return rb; 
	}
	
	
	@ResponseBody
	@RequestMapping(value="/relation/room/{id}",method=RequestMethod.POST)
	public ResultBean getRelationRoom(@PathVariable String id){
		ResultBean rb=new ResultBean();
		HttpSession session = ContextHolderUtils.getSession();
		User user=(User)session.getAttribute(Globals.USER_SESSION);
		
		if((user!=null&&user.getUsername()!=null&&user.getUsername().equals(id))||fourService.isFourShared(id)){
			rb=fourRelationService.getRoommateLog(id);
		}
		return rb; 
	}
	
	
	@ResponseBody
	@RequestMapping(value="/relation/rutor/{id}",method=RequestMethod.POST)
	public ResultBean getRelationTutor(@PathVariable String id){
		ResultBean rb=new ResultBean();
		HttpSession session = ContextHolderUtils.getSession();
		User user=(User)session.getAttribute(Globals.USER_SESSION);
		
		if((user!=null&&user.getUsername()!=null&&user.getUsername().equals(id))||fourService.isFourShared(id)){
			rb=fourRelationService.getTutorLog(id);
		}
		return rb; 
	}
	
	
	@ResponseBody
	@RequestMapping(value="/wall/{id}",method=RequestMethod.POST)
	public ResultBean getWall(@PathVariable String id){
		ResultBean rb=new ResultBean();
		HttpSession session = ContextHolderUtils.getSession();
		User user=(User)session.getAttribute(Globals.USER_SESSION);
		
		if((user!=null&&user.getUsername()!=null&&user.getUsername().equals(id))||fourService.isFourShared(id)){
			rb=fourWallService.getWallForLog(id);
		}
		return rb; 
	}

	
	@ResponseBody
	@RequestMapping(value =  "/img/upload" , method = RequestMethod.POST )
	public String petUpgradeTarget(String data) {
		HttpServletRequest request= ContextHolderUtils.getRequest();
		HttpSession session = ContextHolderUtils.getSession();
		User user=(User)session.getAttribute(Globals.USER_SESSION);
		String serverPath = request.getSession().getServletContext().getRealPath("/");
		Base64 base64 = new Base64();
		try {
			//注意点：实际的图片数据是从 data:image/jpeg;base64, 后开始的
			byte[] k = base64.decode(data.substring("data:image/png;base64,".length()));
			InputStream is = new ByteArrayInputStream(k);
			String fileName = user.getUsername();
			String imgFilePath = serverPath + "static\\usertemp\\"+fileName+".png";
            //以下其实可以忽略，将图片压缩处理了一下，可以小一点
			double ratio = 1.0;
			BufferedImage image = ImageIO.read(is);
			int newWidth = (int) (image.getWidth() * ratio);
			int newHeight = (int) (image.getHeight() * ratio);
			Image newimage = image.getScaledInstance(newWidth, newHeight,
			Image.SCALE_SMOOTH);
			BufferedImage tag = new BufferedImage(newWidth, newHeight,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(newimage, 0, 0, null);
			g.dispose();
			ImageIO.write(tag, "png", new File(imgFilePath));
			is.close();
			return fileName;
		} catch (Exception e) {
			return "error";
		}
	}

	@RequestMapping(value =  "/img/down/{imgSrc}" , method = RequestMethod.GET )  
	public void download(HttpServletResponse res,@PathVariable String imgSrc) throws IOException {
		
		HttpServletRequest request= ContextHolderUtils.getRequest();
	    String serverPath = request.getSession().getServletContext().getRealPath("/");
	    String imgFilePath = serverPath + "static\\usertemp\\"+imgSrc+".png";
	    InputStream is=new FileInputStream(new File(imgFilePath));
	    byte[] fileData =FileCopyUtils.copyToByteArray(is);
	    
	    OutputStream os = res.getOutputStream();  
	    try {  
	        res.reset();  
	        res.setHeader("Content-Disposition", "attachment; filename=me.png");  
	        res.setContentType("application/octet-stream; charset=utf-8");  
	        os.write(fileData);  
	        os.flush();  
	    } finally {  
	        if (os != null) {  
	            os.close();  
	        }  
	    }  
	} 
	

	
	@ResponseBody
	@RequestMapping(value = "/card/habit/{id}",method=RequestMethod.POST)
	public ResultBean getCardHabit(@PathVariable String id){
		ResultBean rb = new ResultBean();
		HttpSession session = ContextHolderUtils.getSession();
		User user=(User)session.getAttribute(Globals.USER_SESSION);
		
		if((user!=null&&user.getUsername()!=null&&user.getUsername().equals(id))||fourService.isFourShared(id)){
			rb=fourCardService.getCardHabit(id);
		}
		return rb;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/card/detail/{id}",method=RequestMethod.POST)
	public ResultBean getCardDetailPost(@PathVariable String id,String page,String conditions){
		ResultBean rb = new ResultBean();
		
		HttpSession session = ContextHolderUtils.getSession();
		User user=(User)session.getAttribute(Globals.USER_SESSION);
		if((user!=null&&user.getUsername()!=null&&user.getUsername().equals(id))||fourService.isFourShared(id)){
			Page pageObject=JSONObject.parseObject(page, Page.class);
			List<Condition> list=JSONObject.parseArray(conditions,Condition.class);
			
			pageObject=fourCardService.getCardDetailLog(id, pageObject, list);
			pageObject.setJTemplate(null);
			rb.setObject(pageObject);
		}
		return rb;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/card/detail/group/deal/{id}",method=RequestMethod.POST)
	public ResultBean getCardDetailGroupDeal(@PathVariable String id){
		ResultBean rb = new ResultBean();
		
		HttpSession session = ContextHolderUtils.getSession();
		User user=(User)session.getAttribute(Globals.USER_SESSION);
		if((user!=null&&user.getUsername()!=null&&user.getUsername().equals(id))||fourService.isFourShared(id)){
			rb.setObject(fourCardService.getCardDetailGroupByDeal(id));
		}
		return rb;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/card/detail/group/time/{id}",method=RequestMethod.POST)
	public ResultBean getCardDetailGroupTime(@PathVariable String id){
		ResultBean rb = new ResultBean();
		
		HttpSession session = ContextHolderUtils.getSession();
		User user=(User)session.getAttribute(Globals.USER_SESSION);
		if((user!=null&&user.getUsername()!=null&&user.getUsername().equals(id))||fourService.isFourShared(id)){
			rb.setObject(fourCardService.getCardDetailGroupByTime(id));
		}
		return rb;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/score/detail/{id}",method=RequestMethod.POST)
	public ResultBean getScoreDetailPost(@PathVariable String id,String page,String conditions){
		ResultBean rb = new ResultBean();
		
		HttpSession session = ContextHolderUtils.getSession();
		User user=(User)session.getAttribute(Globals.USER_SESSION);
		if((user!=null&&user.getUsername()!=null&&user.getUsername().equals(id))||fourService.isFourShared(id)){
			Page pageObject=JSONObject.parseObject(page, Page.class);
			List<Condition> list=JSONObject.parseArray(conditions,Condition.class);
			pageObject=fourScoreService.getScoreDetailLog(id, pageObject, list);
			pageObject.setJTemplate(null);
			rb.setObject(pageObject);
		}
		return rb;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/score/detail/group/sy/{id}",method=RequestMethod.POST)
	public ResultBean getScoreDetailGroupDeal(@PathVariable String id){
		ResultBean rb = new ResultBean();
		
		HttpSession session = ContextHolderUtils.getSession();
		User user=(User)session.getAttribute(Globals.USER_SESSION);
		if((user!=null&&user.getUsername()!=null&&user.getUsername().equals(id))||fourService.isFourShared(id)){
			rb.setObject(fourScoreService.getScoreDetailGroupBySY(id));
		}
		return rb;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/book/detail/{id}",method=RequestMethod.POST)
	public ResultBean getBookDetailPost(@PathVariable String id,String page,String conditions){
		ResultBean rb = new ResultBean();
		
		HttpSession session = ContextHolderUtils.getSession();
		User user=(User)session.getAttribute(Globals.USER_SESSION);
		if((user!=null&&user.getUsername()!=null&&user.getUsername().equals(id))||fourService.isFourShared(id)){
			Page pageObject=JSONObject.parseObject(page, Page.class);
			List<Condition> list=JSONObject.parseArray(conditions,Condition.class);
			
			pageObject=fourBookService.getBookDetailLog(id, pageObject, list);
			pageObject.setJTemplate(null);
			rb.setObject(pageObject);
		}
		return rb;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/book/detail/group/deal/{id}",method=RequestMethod.POST)
	public ResultBean getBookDetailGroupDeal(@PathVariable String id){
		ResultBean rb = new ResultBean();
		
		HttpSession session = ContextHolderUtils.getSession();
		User user=(User)session.getAttribute(Globals.USER_SESSION);
		if((user!=null&&user.getUsername()!=null&&user.getUsername().equals(id))||fourService.isFourShared(id)){
			rb.setObject(fourBookService.getBookDetailGroupByDeal(id));
		}
		return rb;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/book/detail/group/time/{id}",method=RequestMethod.POST)
	public ResultBean getBookDetailGroupTime(@PathVariable String id){
		ResultBean rb = new ResultBean();
		
		HttpSession session = ContextHolderUtils.getSession();
		User user=(User)session.getAttribute(Globals.USER_SESSION);
		if((user!=null&&user.getUsername()!=null&&user.getUsername().equals(id))||fourService.isFourShared(id)){
			rb.setObject(fourBookService.getBookDetailGroupByTime(id));
		}
		return rb;
	}
	
	@ResponseBody
	@RequestMapping(value="/net/netMax/{id}",method=RequestMethod.POST)
	public ResultBean getNetMaxt(@PathVariable String id){
		ResultBean rb=new ResultBean();
		HttpSession session = ContextHolderUtils.getSession();
		User user=(User)session.getAttribute(Globals.USER_SESSION);
		
		if((user!=null&&user.getUsername()!=null&&user.getUsername().equals(id))||fourService.isFourShared(id)){
			rb=netService.getNetMaxTime(id);
		}
		return rb; 
	}
	@ResponseBody
	@RequestMapping(value="/net/netBq/{id}",method=RequestMethod.POST)
	public ResultBean getNetBqdj(@PathVariable String id){
		ResultBean rb=new ResultBean();
		HttpSession session = ContextHolderUtils.getSession();
		User user=(User)session.getAttribute(Globals.USER_SESSION);
		
		if((user!=null&&user.getUsername()!=null&&user.getUsername().equals(id))||fourService.isFourShared(id)){
			rb=netService.getNetBq(id);
		}
		return rb; 
	}
	@ResponseBody
	@RequestMapping(value="/net/netSum/{id}",method=RequestMethod.POST)
	public ResultBean getNetSumt(@PathVariable String id){
		ResultBean rb=new ResultBean();
		HttpSession session = ContextHolderUtils.getSession();
		User user=(User)session.getAttribute(Globals.USER_SESSION);
		
		if((user!=null&&user.getUsername()!=null&&user.getUsername().equals(id))||fourService.isFourShared(id)){
			rb=netService.getNetSumTime(id);
		}
		return rb; 
	}
	@ResponseBody
	@RequestMapping(value="/net/netJc/{id}",method=RequestMethod.POST)
	public ResultBean getNetJcOnlinet(@PathVariable String id){
		ResultBean rb=new ResultBean();
		HttpSession session = ContextHolderUtils.getSession();
		User user=(User)session.getAttribute(Globals.USER_SESSION);
		
		if((user!=null&&user.getUsername()!=null&&user.getUsername().equals(id))||fourService.isFourShared(id)){
			rb=netService.getNetJcOnlineTime(id);
		}
		return rb; 
	}
	
	
}
