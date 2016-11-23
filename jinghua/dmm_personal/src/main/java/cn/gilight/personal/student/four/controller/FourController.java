package cn.gilight.personal.student.four.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jhnu.syspermiss.util.ContextHolderUtils;

import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.personal.student.four.service.FourAwardService;
import cn.gilight.personal.student.four.service.FourBookService;
import cn.gilight.personal.student.four.service.FourCardService;
import cn.gilight.personal.student.four.service.FourFirstService;
import cn.gilight.personal.student.four.service.FourRelationService;
import cn.gilight.personal.student.four.service.FourScoreService;
import cn.gilight.personal.student.main.controller.StudentMainController;

@Controller("fourController")
@RequestMapping("/student/four")
public class FourController {
	private Logger log = Logger.getLogger(StudentMainController.class);
	
	@Autowired
	private FourRelationService fourRelationService;
	@Autowired
	private FourAwardService fourAwardService;
	@Autowired
	private FourCardService fourCardService;
	@Autowired
	private FourBookService fourBookService;
	@Autowired
	private FourScoreService fourScoreService;
	@Autowired
	private FourFirstService fourFirstService;
	
	@RequestMapping("/fdys")
	@ResponseBody
	public List<Map<String,Object>> getFdys(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return fourRelationService.getFdys(username);
	}
	
	@RequestMapping("/mySchool")
	@ResponseBody
	public Map<String,Object> getMySchool(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return fourRelationService.mySchool(username);
	}
	
	@RequestMapping("/teas")
	@ResponseBody
	public List<Map<String,Object>> queryTea(String xn,String xq){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return fourRelationService.queryTea(xn,xq,username);
	}
	
	@RequestMapping("/award")
	@ResponseBody
	public Map<String,Object> getAward(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return fourAwardService.getAward(username);
	}
	
	@RequestMapping("/punish")
	@ResponseBody
	public Map<String,Object> getPunish(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return fourAwardService.getPunish(username);
	}
	//查询username借书
	@RequestMapping("/borrowbooks")
	@ResponseBody
	public Map<String, Object> borrowBook(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal=(AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username=principal.getName();
		log.debug(username);
		return fourBookService.borrowBook(username);
	}
	
	@RequestMapping("/wall")
	@ResponseBody
	public Map<String,Object> getWall(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		Map<String,Object> resultMap = new HashMap<String,Object>();
		Map<String,Object> map = fourScoreService.getScoreMap(username);
		String scorePro = MapUtils.getString(map, "passpro");
		scorePro = scorePro.substring(0, scorePro.length()-1);
		double sd = Double.parseDouble(scorePro);
		String score = "";
		if(sd >= 80){
			score = "学霸";
		}else if(sd < 80 && sd > 30){
			score = "成绩平平";
		}else{
			score = "学渣";
		}
		
		Map<String,Object> cardMap = fourCardService.getCardMap(username);
		String passNumPro = MapUtils.getString(cardMap, "passNumPro");
		passNumPro = passNumPro.substring(0, passNumPro.length()-1);
		double cd = Double.parseDouble(passNumPro);
		String card = "";
		if(cd >= 80){
			card = "土豪";
		}else if(cd < 80 && cd > 30){
			card = "小资";
		}else{
			card = "小康";
		}
		
		Map<String,Object> bookMap = fourBookService.borrowBook(username);
		String numLess = MapUtils.getString(bookMap, "numLess");
		numLess = numLess.substring(0, numLess.length() - 1);
		double bd = Double.parseDouble(numLess);
		String book = "";
		if(bd >= 80){
			book = "手不释卷";
		}else if(bd < 80 && bd >30){
			book = "读书平平";
		}else{
			book = "寡闻";
		}
	
		resultMap.put("score", score);
		resultMap.put("card", card);
		resultMap.put("book", book);
		
		return resultMap;
	}
	
	//查询username每学期借书与同届人均相比
	@RequestMapping("/myBorrow")
	@ResponseBody
	public List<Map<String,Object>> myBorrow(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal=(AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username=principal.getName();
		log.debug(username);
		return fourBookService.myBorrow(username);
	}
	//查询username每学期进出图书馆与同届人均相比
	@RequestMapping("/inOutLibr")
	@ResponseBody
	public Map<String, Object> inOutLibr(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return fourBookService.inOutLibr(username);
	}
	//进出图书馆比较
	@RequestMapping("/myLibrs")
	@ResponseBody
	public List<Map<String, Object>> inOutLibrs(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return fourBookService.myLibrs(username);
	}
	
	@RequestMapping("/card")
	@ResponseBody
	public Map<String,Object> getCardMap(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return fourCardService.getCardMap(username);
	}
	
	@RequestMapping("/cardChart")
	@ResponseBody
	public List<Map<String,Object>> getCardChart(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return fourCardService.getCardChart(username);
	}
	
	@RequestMapping("/cardPie")
	@ResponseBody
	public Map<String,Object> getCardPie(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return fourCardService.getCardPie(username);
	}
	
	@RequestMapping("/cardMeal")
	@ResponseBody
	public Map<String,Object> getCardMeal(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return fourCardService.getCardMeal(username);
	}
	
	@RequestMapping("/cardMealPayAvg")
	@ResponseBody
	public Map<String,Object> getCardMealPayAvg(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return fourCardService.getCardMealPayAvg(username);
	}
	
	@RequestMapping("/cardWin")
	@ResponseBody
	public List<Map<String,Object>> getCardWin(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return fourCardService.getCardWins(username);
	}
	
	@RequestMapping("/shopCard")
	@ResponseBody
	public Map<String,Object> getShopCard(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return fourCardService.getShopCard(username);
	}
	
	@RequestMapping("/washCard")
	@ResponseBody
	public Map<String,Object> getWashCard(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return fourCardService.getWashCard(username);
	}
	
	@RequestMapping("/scoreMap")
	@ResponseBody
	public Map<String,Object> getScoreMap(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return fourScoreService.getScoreMap(username);
	}
	
	@RequestMapping("/scoreChart")
	@ResponseBody
	public List<Map<String,Object>> getScoreChart(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return fourScoreService.getScoreChart(username);
	}
	
	@RequestMapping("/goodScore")
	@ResponseBody
	public Map<String,Object> getGoodScore(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return fourScoreService.getGoodScore(username);
	}
	
	@RequestMapping("/scoreCourse")
	@ResponseBody
	public Map<String,Object> getScoreCourse(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return fourScoreService.scoreCourseMap(username);
	}
	
	@RequestMapping("/first")
	@ResponseBody
	public Map<String,Object> getFirst(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		Map<String,Object> cardMap = fourFirstService.getFirstCard(username);
		resultMap.put("cardMap", cardMap);
		
		Map<String,Object> bookMap = fourFirstService.getFirstBook(username);
		resultMap.put("bookMap", bookMap);
		
		Map<String,Object> notPassMap = fourFirstService.getFirstNotPass(username);
		resultMap.put("notPassMap", notPassMap);
		
		Map<String,Object> punishMap = fourFirstService.getFirstPunish(username);
		resultMap.put("punishMap", punishMap);
		
		Map<String,Object> awardMap = fourFirstService.getFirstAward(username);
		resultMap.put("awardMap", awardMap);
		
		Map<String,Object> subsidyMap = fourFirstService.getFirstSubsidy(username);
		resultMap.put("subsidyMap", subsidyMap);
		
		Map<String,Object> bookRkeMap = fourFirstService.getFirstBookRke(username);
		resultMap.put("bookRkeMap", bookRkeMap);
		
		return resultMap;
	}
	
}
