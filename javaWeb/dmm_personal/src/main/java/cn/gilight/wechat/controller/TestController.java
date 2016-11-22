package cn.gilight.wechat.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sword.wechat4j.message.CustomerMsg;
import org.sword.wechat4j.response.ArticleResponse;
import org.sword.wechat4j.templateMessage.TemplateMessage;
import org.sword.wechat4j.templateMessage.TemplateMessageUtil;
import org.sword.wechat4j.user.User;
import org.sword.wechat4j.user.UserManager;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.common.StringUtils;
import cn.gilight.framework.uitl.product.EduUtils;
import cn.gilight.personal.juhe.JuheChatDemo;
import cn.gilight.personal.student.book.service.BookService;
import cn.gilight.personal.student.card.service.CardService;
import cn.gilight.personal.student.colorPage.service.ColorPageService;
import cn.gilight.personal.student.course.service.CourseService;
import cn.gilight.personal.student.score.dao.StuScoreDao;
import cn.gilight.personal.student.score.service.StuScoreService;
import cn.gilight.personal.teacher.classes.service.TeacherClassService;
import cn.gilight.personal.teacher.dailylife.service.DailyLifeService;
import cn.gilight.personal.teacher.score.service.ScoreService;
import cn.gilight.personal.teacher.teaching.service.TeacherTeachingService;
import cn.gilight.personal.teacher.warning.service.TeacherWarningService;
import cn.gilight.wechat.service.WordQueryService;

import com.alibaba.fastjson.JSONObject;
import com.jhnu.syspermiss.util.SysConfig;

/**   
* @Description:微信消息解析案例 实现
* @author Sunwg  
* @date 2016年1月14日 下午5:18:36   
*/
@Controller("testController")
@RequestMapping("/")
public class TestController {
	private Logger log = Logger.getLogger(TestController.class);
	
	@Autowired
	private TeacherTeachingService teacherTeachingService;
	@Autowired
	private DailyLifeService dailyLifeService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private BookService bookService;
	@Autowired
	private StuScoreDao stuScoreDao;
	@Autowired
	private ColorPageService colorPageService;
	@Autowired
	private StuScoreService stuScoreService;
	@Autowired
	private TeacherWarningService warningService;
	@Autowired
	private CardService cardService;
	@Autowired
	private WordQueryService wordQueryService;
	@Autowired
	private TeacherClassService teacherClassService;
	@Autowired
	private ScoreService scoreService;
	
	@Resource
	private BaseDao baseDao;
	
	/** 
	* @Title: reciveMessage 
	* @Description:接收微信消息，并回复消息
	* @param request
	* @param response
	* @throws IOException
	*/
	@RequestMapping("test/reciveMessage")
	public void reciveMessage(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		//该段代码的作用是用来验证接受消息的url的可用性
		// 随机字符串
		String echostr = request.getParameter("echostr");
		//判断是否是url有效性的验证，如果是有效性验证则进行验证，返回验证随机码
		if(echostr != null){
			// 微信加密签名
			String signature = request.getParameter("signature");
			// 时间戳
			String timestamp = request.getParameter("timestamp");
			// 随机数
			String nonce = request.getParameter("nonce");
			PrintWriter out = response.getWriter();  
		    // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败  
		    if (SignUtil.checkSignature(signature, timestamp, nonce)) {  
		        out.print(echostr);  
		    }  
		    out.close();  
		}else{
			//url有效性已经进行验证，此时需要进行消息解析
			handleMessage(request);
		}
	}
	
	/** 
	* @Title: handleMessage 
	* @Description:处理接收的消息
	* @param @param request
	*/
	private void handleMessage(HttpServletRequest request){
		try {
			Map<String, String> msg = parseXml(request);
			String fromUser = msg.get("FromUserName");
			UserManager ma = new UserManager();
			User user = ma.getUserInfo(fromUser);
			String msgType = msg.get("MsgType");
			
			//得到该微信号所绑定的人员及其身份
			List<Map<String,Object>> binds = baseDao.queryListInLowerKey("select * from t_wechat_bind where openid = '"+fromUser+"'");
        	Map<String,Object> map = null;
        	if(binds != null && binds.size()>0 ){
        		map = binds.get(0);
        	}
        	int sf = MapUtils.getIntValue(map, "sf");
        	
			//文字消息
			if (msgType.equals("text")) {
				log.debug(user.getNickName() +"发送文字消息：" + msg.get("Content"));
				String content = msg.get("Content");
				CustomerMsg customerMsg = new CustomerMsg(fromUser);
				String username = MapUtils.getString(map, "username");
				String result = "";
				if(content.contains("书") && content.contains("推荐")){
					if(sf == 2){
						List<Map<String,Object>> recommentBook = bookService.getRecommendBook(username);
						if(recommentBook != null && recommentBook.size()>0){
							result = "根据你的喜好为你推荐以下图书：";
							for(Map<String,Object> m : recommentBook){
								result = result + "\r\n"+MapUtils.getString(m, "name_");
							}
						}else{
							result = "没有找到主人喜爱的图书！";
						}
					}else if(sf != 1){
						result = "您还未绑定学生服务，或未绑定成功！";
					}
				}else if(content.contains("还书")){
					if(sf == 2){
						List<Map<String,Object>> list = wordQueryService.queryStuInBorrow(username);
						if(list != null && list.size()>0){
							result = "小主人还有以下图书没有还，请注意时间：";
							for(Map<String,Object> b : list){
								result = result + "\r\n"+MapUtils.getString(b, "book_name")+",借阅时间 "+MapUtils.getString(b, "borrow_time")+",应还时间 "+MapUtils.getString(b, "should_return_time");
							}
						}else{
							result = "小主人没有未还的书。";
						}
					}else if(sf != 1){
						result = "您还未绑定学生服务，或未绑定成功！";
					}
				}else if(content.contains("土豪")){
					if(sf == 2){
						String d = DateUtils.getLastMonth().substring(0, 7);
						Map<String,Object> m = wordQueryService.queryTyrant(username, d);
						if(m != null){
							result = "上月班级的土豪是  → "+ MapUtils.getString(m, "name");
						}else{
							result = "没有找到上月班级的土豪！";
						}
					}else if(sf != 1){
						result = "您还未绑定学生服务，或未绑定成功！";
					}
				}else if(content.contains("辅导员")){
					String[] terms = EduUtils.getSchoolYearTerm(new Date());
					if(sf == 2){
						Map<String,Object> m = wordQueryService.queryInstructor(terms[0],terms[1],username);
						if(StringUtils.hasText(MapUtils.getString(m, "instructor_"))){
							result = "本班辅导员为 ："+MapUtils.getString(m, "instructor_")+",联系电话："+MapUtils.getString(m, "instructor_tel");
						}else{
							result = "辅导员信息未维护！";
						}
					}else if(sf != 1){
						result = "您还未绑定学生服务，或未绑定成功！";
					}
				}else if(content.contains("学霸")){
					if(sf == 2){
						List<Map<String,Object>> list = wordQueryService.getMajorSmart(username);
						if(list != null && list.size()>0){
							result = "本专业学霸为这些人：\r\n"+MapUtils.getString(list.get(0), "name")+":"+MapUtils.getString(list.get(0), "sex");
							for(int i=1;i<list.size();i++){
								result = result + ","+MapUtils.getString(list.get(i), "name")+":"+ MapUtils.getString(list.get(i), "sex");
							}
						}else{
							result = "未找到本专业学霸信息！";
						}
					}else if(sf != 1){
						result = "您还未绑定学生服务，或未绑定成功！";
					}
				}else if(content.contains("学分")){
					if(sf == 2){
						Map<String,Object> xf = stuScoreService.getCredit(username);
						if(StringUtils.hasText(MapUtils.getString(xf, "total_credit")) && !"0.0".equals(MapUtils.getString(xf, "total_credit")) && "0".equals(MapUtils.getString(xf, "total_credit"))){
							result = "主人所需修总学分："+MapUtils.getString(xf, "total_credit")+",已修学分："+MapUtils.getString(xf, "my_credit");
						}else{
							result = "学分信息未维护！";
						}
					}else if(sf != 1){
						result = "您还未绑定学生服务，或未绑定成功！";
					}
				}else if(content.contains("挂科")){
					String[] terms = EduUtils.getProSchoolYearTerm(new Date());
					if(sf == 2){
						List<Map<String,Object>> list = wordQueryService.getStuFlunk(username, terms[0], terms[1]);
						if(list != null && list.size()>0){
							result = "上学期挂科科目：";
							for(Map<String,Object> gk : list){
								String score = MapUtils.getString(gk, "centesimal_score");
								if(StringUtils.hasText(MapUtils.getString(gk, "score"))){
									score = MapUtils.getString(gk, "score");
								}
								result = result + "\r\n"+MapUtils.getString(gk, "course_name")+":"+score;
							}
						}else{
							result = "小主人上学期没有挂科呦~ 棒棒哒！";
						}
					}else if(sf != 1){
						result = "您还未绑定学生服务，或未绑定成功！";
					}
				}else if(content.contains("男") && content.contains("最多") && content.contains("专业")){
					if(sf == 2){
						Map<String,Object> major = colorPageService.QueryMajorSex(username);
						Map<String, Object> boyMajor = MapUtils.getMap(major, "boyMajor");
						if(StringUtils.hasText(MapUtils.getString(boyMajor, "major"))){
							result = "男生最多的专业是："+MapUtils.getString(boyMajor, "major");
						}
					}else if(sf != 1){
						result = "您还未绑定学生服务，或未绑定成功！";
					}
				}else if(content.contains("专业") && content.contains("女") && content.contains("最多")){
					if(sf == 2){
						Map<String,Object> major = colorPageService.QueryMajorSex(username);
						Map<String, Object> grilMajor = MapUtils.getMap(major, "grilMajor");
						if(StringUtils.hasText(MapUtils.getString(grilMajor, "major"))){
							result = "女生最多的专业是："+MapUtils.getString(grilMajor, "major");
						}
					}else if(sf != 1){
						result = "您还未绑定学生服务，或未绑定成功！";
					}
				}else if(content.contains("餐厅") && (content.contains("好吃")||content.contains("受欢迎"))){
					if(sf == 2){
						List<Map<String,Object>> list = wordQueryService.getCardDeptMonth();
						if(list != null && list.size()>0){
							result = "餐厅窗口TOP3：";
							for(Map<String,Object> ct : list){
								result = result + "\r\n"+MapUtils.getString(ct, "eat_time") +":"+MapUtils.getString(ct, "dept_name")+":TOP"+MapUtils.getString(ct, "rn");
							}
						}
					}else if(sf != 1){
						result = "您还未绑定学生服务，或未绑定成功！";
					}
				}else if(content.contains("室友")){
					if(sf == 2){
						List<Map<String,Object>> list = wordQueryService.queryRoomie(username);
						if(list!=null && list.size()>0){
							result = "小主人，你的室友是这些人：";
							for(Map<String,Object> sy : list){
								result = result + "\r\n"+MapUtils.getString(sy, "name_") + " → "+MapUtils.getString(sy, "origin_name");
							}
						}else{
							result = "室友信息未维护！";
						}
					}else if(sf != 1){
						result = "您还未绑定学生服务，或未绑定成功！";
					}
				}else if(content.contains("老乡")){
					if(sf == 2){
						List<Map<String,Object>> list = wordQueryService.queryStuPaisan(username);
						if(list != null && list.size()>0){
							result = "小主人在本专业有"+list.size()+"个老乡：";
							for(Map<String,Object> lx : list){
								result = result +"\r\n"+MapUtils.getString(lx, "stu_name")+":"+MapUtils.getString(lx, "sex")+":"+
										MapUtils.getString(lx, "enroll_grade")+":"+MapUtils.getString(lx, "tel");
							}
						}else{
							result = "小主人在本专业没有老乡。";
						}
					}else if(sf != 1){
						result = "您还未绑定学生服务，或未绑定成功！";
					}
				}
				if(!StringUtils.hasText(result)){
					Map<String,Object> m = JuheChatDemo.getRequest1(content);
				    result = MapUtils.getString(m, "text");
				}
			    customerMsg.sendText(result);
			}
			//事件消息
			else if(msgType.equals("event")){
				String event = msg.get("Event");
				//订阅事件
				if (event.equals("subscribe")) {
					log.debug(user.getNickName() +"关注了您！ ");
					CustomerMsg customerMsg = new CustomerMsg(fromUser);
					String html = "";
					customerMsg.sendText("欢迎您关注测试账号\r\n→学生←绑定过个人服务后可以发送以下内容查询：\r\n发送“辅导员”查询辅导员\r\n发送“挂科 ”查询上学期挂科\r\n发送“学分”查询所修学分"
							+ "\r\n发送“学霸”查询本专业学霸\r\n发送“土豪 ”查询本班上月土豪\r\n发送“室友”查询室友信息\r\n发送“老乡”查询本专业老乡\r\n发送“还书”查询未还图书"
							+ "\r\n发送“最受欢迎的餐厅窗口”\r\n发送“男(女)生最多的专业”"
							+ "\r\n发送“图书推荐”\r\n\r\n  ☞ 你还可以和图灵机器人聊天☺，发送“你是谁”就知道谁是图灵机器人了！");
				}
				//取消订阅
				else if (event.equals("unsubscribe")) {
					log.warn(user.getNickName() +"取消关注了您！");
				}
				//点击事件
				else if(event.equals("CLICK")){
					/*String eventKey = msg.get("EventKey");
					CustomerMsg customerMsg = new CustomerMsg(fromUser);
					TemplateMessage message = new TemplateMessage();
					boolean b = false;
					JSONObject data = new JSONObject();
					message.setTouser(fromUser);
					//学生服务---今日课程
					if(eventKey.equals("student_today_course")){
			        	if(sf == 2){
			        		message.setTemplate_id("AoY7RuEKCQnvYFZd4sKjKNuzgPHVshF8VGcKj-vponQ");
			        		List<Map<String,Object>> list = courseService.getTodayCourse(MapUtils.getString(map, "username"));
			        		String value = "";
			        		for(Map<String,Object> m : list){
			        			value = value + MapUtils.getString(m, "time_") + ":"+MapUtils.getString(m, "course_name")
			        					+ ":" + MapUtils.getString(m, "tea_name")+":"+MapUtils.getString(m, "classroom")+"\n";
			        		}
			        		Map<String,Object> course = new HashMap<String,Object>();
			        		course.put("value", value);
			        		course.put("color", "#40E0D0");
			        		data.put("course", course);
			        		if(list == null || list.size()<1){
			        			customerMsg.sendText("暂无今日课程!");
							}else{
								b = true;
							}
			        	}else if(sf == 1){
			        		customerMsg.sendText("你绑定的身份为教师，请选择教师服务！");
			        	}else{
			        		customerMsg.sendText("您还没有绑定学生服务, <a href=\""+SysConfig.instance().getServerUrl()+ "/wechat/redirect\">点击此处进行绑定</a>");
			        	}
					}//学生服务---上学期成绩
					if(eventKey.equals("student_score")){
			        	if(sf == 2){
			        		message.setTemplate_id("5rA_2Mv6hWKnZ8qXJvWupubn_kWvABScJgC1OFBHEcU");
			        		Map<String,Object> school_year = new HashMap<String, Object>();
			        		Map<String,Object> all = new HashMap<String, Object>();
			        		Map<String,Object> rank = new HashMap<String, Object>();
			        		Map<String,Object> score = new HashMap<String, Object>();
			        		
			        		String value = "";
			        		String date = DateUtils.getNowDate();
			        		String[] terms = EduUtils.getProSchoolYearTerm(DateUtils.string2Date(date));
			        		Map<String,Object> m  = stuScoreDao.getScore(MapUtils.getString(map, "username"), terms[0], terms[1]);
			        		
			        		school_year.put("value", terms[0]+"学年第"+terms[1]+"学期");
			        		school_year.put("color", "#7B68EE");
			        		all.put("value", MapUtils.getString(m, "total_score"));
			        		all.put("color", "#fc58a1");
			        		rank.put("value", MapUtils.getString(m, "rank_"));
			        		rank.put("color", "#fc58a1");
			        		List<Map<String,Object>> list = stuScoreDao.getScoreBySchoolTerm(MapUtils.getString(map, "username"),   terms[0], terms[1]);
			        		if(list != null && list.size()>0){
			        			b = true;
			        		}
			        		for(Map<String,Object> s : list){
			        			value = value + MapUtils.getString(s, "course_name") + " : "+ MapUtils.getString(s, "credit") + "学分 : "+MapUtils.getString(s, "score")+"\n";
			        		}
			        		score.put("value", value);
			        		score.put("color", "#7D7D7D");
			        		if(m == null){
			        			customerMsg.sendText("暂无上学期成绩数据!");
			        		}
			        		data.put("schoolYear", school_year);
			        		data.put("all", all);
			        		data.put("rank", rank);
			        		data.put("score", score);
			        	}else if(sf == 1){
			        		customerMsg.sendText("你绑定的身份为教师，请选择教师服务！");
			        	}else{
			        		customerMsg.sendText("您还没有绑定学生服务, <a href=\""+SysConfig.instance().getServerUrl()+ "/wechat/redirect\">点击此处进行绑定</a>");
			        	}
					}
					//学生服务---昨日消费
					if(eventKey.equals("student_card")){
						if(sf == 2){
							message.setTemplate_id("wz7BZ1i7VXqjjQYu7hh7Un1wKnq2d_Nx9PesaJWXHM4");
			        		Map<String,Object> yesterday = new HashMap<String, Object>();
			        		Map<String,Object> card = new HashMap<String, Object>();
			        		yesterday.put("value", DateUtils.getYesterday());
			        		yesterday.put("color", "#7B68EE");
			        		String value = "";
							List<Map<String,Object>> ls = cardService.queryConsumeOfDay(MapUtils.getString(map, "username"),DateUtils.getYesterday());
							if(ls != null && ls.size()>0){
								b = true;
								for(Map<String,Object> m : ls){
									value = value + MapUtils.getString(m, "xfsj") + "  "+MapUtils.getString(m, "dept") +"  "+MapUtils.getString(m, "xfje")+"元\n"; 
								}
								card.put("value", value);
								card.put("color", "#458B00");
								data.put("yesterday", yesterday);
								data.put("card", card);
							}else{
								customerMsg.sendText("暂无昨日消费数据！");
							}
			        	}else if(sf == 1){
			        		customerMsg.sendText("你绑定的身份为教师，请选择教师服务！");
			        	}else{
			        		customerMsg.sendText("您还没有绑定学生服务, <a href=\""+SysConfig.instance().getServerUrl()+ "/wechat/redirect\">点击此处进行绑定</a>");
			        	}
					}
					//查空教室
					if(eventKey.equals("classroom")){
						if(sf == 2){
							message.setTemplate_id("gDrOVwXnfwGiW2hi64luXeZtIE-nFG16SgwD_uU3_qI");
							b=true;
							Map<String,Object> room1 = new HashMap<String, Object>();
							Map<String,Object> room2 = new HashMap<String, Object>();
							Map<String,Object> room3 = new HashMap<String, Object>();
							Map<String,Object> room4 = new HashMap<String, Object>();
							Map<String,Object> room5 = new HashMap<String, Object>();
							
			        		List<Map<String,Object>> ls1 = courseService.getEmptyClassroom(DateUtils.getNowDate(), 1, 2);
			        		List<Map<String,Object>> ls2 = courseService.getEmptyClassroom(DateUtils.getNowDate(), 3, 4);
			        		List<Map<String,Object>> ls3 = courseService.getEmptyClassroom(DateUtils.getNowDate(), 5, 6);
			        		List<Map<String,Object>> ls4 = courseService.getEmptyClassroom(DateUtils.getNowDate(), 7, 8);
			        		List<Map<String,Object>> ls5 = courseService.getEmptyClassroom(DateUtils.getNowDate(), 9, 10);
			        		
			        		String r1 = "";
			        		String r2 = "";
			        		String r3 = "";
			        		String r4 = "";
			        		String r5 = "";
			        		if(ls1 != null && ls1.size()>0){
			        			r1 = r1 + MapUtils.getString(ls1.get(0),"name_");
			        			for(int i=1;i<ls1.size();i++){
			        				r1 = r1+"," +MapUtils.getString(ls1.get(i),"name_");
			        			}
			        		}else{
			        			r1 = "第1-2节没有找到空教室。";
			        		}
			        		if(ls2 != null && ls2.size()>0){
			        			r2 = r2 + MapUtils.getString(ls2.get(0),"name_");
			        			for(int i=1;i<ls2.size();i++){
			        				r2 = r2 +","+MapUtils.getString(ls2.get(i),"name_");
			        			}
			        		}else{
			        			r2 = "第3-4节没有找到空教室。";
			        		}
			        		if(ls3 != null && ls3.size()>0){
			        			r3 = r3 + MapUtils.getString(ls3.get(0),"name_");
			        			for(int i=1;i<ls3.size();i++){
			        				r3 = r3 +","+MapUtils.getString(ls3.get(i),"name_");
			        			}
			        		}else{
			        			r3 = "第5-6节没有找到空教室。";
			        		}
			        		if(ls4 != null && ls4.size()>0){
			        			r4 = r4 + MapUtils.getString(ls4.get(0),"name_");
			        			for(int i=1;i<ls4.size();i++){
			        				r4 = r4 +","+MapUtils.getString(ls4.get(i),"name_");
			        			}
			        		}else{
			        			r4 = "第7-8节没有找到空教室。";
			        		}
			        		if(ls5 != null && ls5.size()>0){
			        			r5 = r5 + MapUtils.getString(ls5.get(0),"name_");
			        			for(int i=1;i<ls5.size();i++){
			        				r5 = r5 + "," + MapUtils.getString(ls5.get(i),"name_");
			        			}
			        		}else{
			        			r5 = "第9-10节没有找到空教室。";
			        		}
			        		room1.put("value", r1);
			        		room1.put("color", "#8F8F8F");
			        		room2.put("value", r2);
			        		room2.put("color", "#8F8F8F");
			        		room3.put("value", r3);
			        		room3.put("color", "#8F8F8F");
			        		room4.put("value", r4);
			        		room4.put("color", "#8F8F8F");
			        		room5.put("value", r5);
			        		room5.put("color", "#8F8F8F");
			        		data.put("room1", room1);
			        		data.put("room2", room2);
			        		data.put("room3", room3);
			        		data.put("room4", room4);
			        		data.put("room5", room5);
			        	}else if(sf == 1){
			        		customerMsg.sendText("你绑定的身份为教师，请选择教师服务！");
			        	}else{
			        		customerMsg.sendText("您还没有绑定学生服务, <a href=\""+SysConfig.instance().getServerUrl()+ "/wechat/redirect\">点击此处进行绑定</a>");
			        	}
					}
					//教师服务--今日课程
					if(eventKey.equals("teacher_today_course")){
			        	if(sf == 1){
			        		message.setTemplate_id("AoY7RuEKCQnvYFZd4sKjKNuzgPHVshF8VGcKj-vponQ");
			        		List<Map<String,Object>> list = teacherTeachingService.getTodayClass(MapUtils.getString(map, "username"));
			        		String value = "";
			        		for(Map<String,Object> m : list){
			        			value = value + MapUtils.getString(m, "TIME_") + " : "+MapUtils.getString(m, "COURSE_NAME") +" : "+MapUtils.getString(m, "CLASSROOM_NAME")+"\n";
			        		}
			        		
			        		Map<String,Object> course = new HashMap<String,Object>();
			        		course.put("value", value);
			        		course.put("color", "#40E0D0");
			        		data.put("course", course);
			        		
							if(list == null || list.size()<1){
								customerMsg.sendText("暂无今日课程!");
							}else{
								b = true;
							}
			        	}else if(sf == 2){
			        		customerMsg.sendText("你绑定的身份为学生，请选择学生服务！");
			        	}else{
			        		customerMsg.sendText("您还没有绑定教职工服务, <a href=\""+SysConfig.instance().getServerUrl()+ "/wechat/redirect\">点击此处进行绑定</a>");
			        	}
					}
					//教师服务--预警消息
					if(eventKey.equals("teacher_warning")){
						if(sf == 1){
							message.setTemplate_id("klLL4wKivwjGJh3l5ExF3IPygak0LhYuvup1yN8fio4");
							Map<String,Object> card = new HashMap<String,Object>();
							Map<String,Object> stay = new HashMap<String,Object>();
							Map<String,Object> study = new HashMap<String,Object>();
							
			        		Map<String,Object> cardWarn = warningService.queryConsumeNums(MapUtils.getString(map, "username"), 1);
			        		Map<String,Object> stayWarn =  warningService.queryStayNums(MapUtils.getString(map, "username"),DateUtils.getYesterday());
			        		String[] term = EduUtils.getProSchoolYearTerm(new Date());
			        		List<Map<String,Object>> studyWarn = warningService.queryStudyNums(MapUtils.getString(map, "username"), term[0], term[1]);
			        		String ca = "";
			        		if(MapUtils.getInteger(cardWarn, "high") != 0 || MapUtils.getInteger(cardWarn, "low") != 0){
			        			ca = "高消费异常人数："+ MapUtils.getIntValue(cardWarn, "high") +" ，低消费人数："+MapUtils.getIntValue(cardWarn, "low");
			        		}else{
			        			ca = "无消费异常！";
			        		}
			        		card.put("value", ca);
			        		card.put("color", "#16bf7e");
			        		String sty = "";
			        		if(MapUtils.getInteger(stayWarn, "late") != 0 || MapUtils.getInteger(stayWarn, "out") != 0){
			        			sty = "晚归人数为："+ MapUtils.getIntValue(stayWarn, "late") +"，意思不在校人数为："+MapUtils.getIntValue(stayWarn, "out");
			        		}else{
			        			sty = "无住宿异常！";
			        		}
			        		stay.put("value", sty);
			        		stay.put("color", "#7B68EE");
			        		String value = "";
			        		if(studyWarn != null && studyWarn.size()>0){
			        			for(Map<String,Object> m : studyWarn){
			        				value = value + MapUtils.getString(m, "bj")+ "  挂科人数    "+MapUtils.getIntValue(m, "gkrs")+"\n";
			        			}
			        		}else{
			        			value = "无学业异常！";
			        		}
			        		study.put("value", value);
		        			study.put("color", "#fc58a1");
			        		data.put("card", card);
			        		data.put("stay", stay);
			        		data.put("study", study);
			        		if(cardWarn == null && stayWarn == null && studyWarn == null){
			        			customerMsg.sendText("暂无学生异常数据！");
			        		}else{
			        			b = true;
			        		}
			        	}else if(sf == 2){
			        		customerMsg.sendText("你绑定的身份为学生，请选择学生服务！");
			        	}else{
			        		customerMsg.sendText("您还没有绑定教职工服务, <a href=\""+SysConfig.instance().getServerUrl()+ "/wechat/redirect\">点击此处进行绑定</a>");
			        	}
					}
					if(b){
						message.setData(data);
						TemplateMessageUtil.sendTemplateMessage(message);
					}*/
					String eventKey = msg.get("EventKey");
					CustomerMsg customerMsg = new CustomerMsg(fromUser);
					String result = "";
		        	List<Map<String,Object>> list = null;
					//学生服务---今日课程
					if(eventKey.equals("student_today_course")){
			        	if(sf == 2){
			        		list = courseService.getTodayCourse(MapUtils.getString(map, "username"));
			        		for(Map<String,Object> m : list){
			        			result = result + MapUtils.getString(m, "time_") + ":"+MapUtils.getString(m, "course_name")
			        					+ ":" + MapUtils.getString(m, "tea_name")+":"+MapUtils.getString(m, "classroom")+"\r\n";
			        		}
			        		if(list == null || list.size()<1){
			        			customerMsg.sendText("暂无今日课程!");
							}else{
								customerMsg.sendNew("今日课程", result, "", SysConfig.instance().getServerUrl()+"/student/course/course.jsp");
							}
			        	}else if(sf == 1){
			        		customerMsg.sendText("你绑定的身份为教师，请选择教师服务！");
			        	}else{
			        		customerMsg.sendText("您还没有绑定学生服务, <a href=\""+SysConfig.instance().getServerUrl()+ "/wechat/redirect\">点击此处进行绑定</a>");
			        	}
					}
					//学生服务---上学期成绩
					if(eventKey.equals("student_score")){
			        	if(sf == 2){
			        		String date = DateUtils.getNowDate();
			        		String[] terms = EduUtils.getProSchoolYearTerm(DateUtils.string2Date(date));
			        		Map<String,Object> m  = stuScoreDao.getScore(MapUtils.getString(map, "username"), terms[0], terms[1]);
			        		result = result + "总成绩  "+ MapUtils.getString(m, "total_score") + "，排名  "+MapUtils.getString(m, "rank_")+"\r\n成绩明细：";
			        		list = stuScoreDao.getScoreBySchoolTerm(MapUtils.getString(map, "username"),  terms[0], terms[1]);
			        		for(Map<String,Object> s : list){
			        			result = result +"\r\n" +MapUtils.getString(s, "course_name") + ":"+ MapUtils.getString(s, "credit") + ":"+MapUtils.getString(s, "score");
			        		}
			        		if(m == null){
			        			customerMsg.sendText("暂上学期成绩数据!");
			        		}else{
			        			customerMsg.sendNew("上学期成绩", result, "", SysConfig.instance().getServerUrl()+"/student/score/score.jsp");
			        		}
			        	}else if(sf == 1){
			        		customerMsg.sendText("你绑定的身份为教师，请选择教师服务！");
			        	}else{
			        		customerMsg.sendText("您还没有绑定学生服务, <a href=\""+SysConfig.instance().getServerUrl()+ "/wechat/redirect\">点击此处进行绑定</a>");
			        	}
					}
					//学生服务---昨日消费
					if(eventKey.equals("student_card")){
						if(sf == 2){
							List<Map<String,Object>> ls = cardService.queryConsumeOfDay(MapUtils.getString(map, "username"),DateUtils.getYesterday());
							if(ls != null && ls.size()>0){
								for(Map<String,Object> m : ls){
									result = result + MapUtils.getString(m, "xfsj") + "  "+MapUtils.getString(m, "dept") +"  "+MapUtils.getString(m, "xfje")+"\r\n"; 
								}
								customerMsg.sendNew("昨日消费明细", result, "", SysConfig.instance().getServerUrl()+"/student/card/card.jsp");
							}else{
								customerMsg.sendText("暂无昨日消费数据！");
							}
			        	}else if(sf == 1){
			        		customerMsg.sendText("你绑定的身份为教师，请选择教师服务！");
			        	}else{
			        		customerMsg.sendText("您还没有绑定学生服务, <a href=\""+SysConfig.instance().getServerUrl()+ "/wechat/redirect\">点击此处进行绑定</a>");
			        	}
					}
					//学生服务---空教室
					if(eventKey.equals("classroom")){
						if(sf == 2){
			        		List<Map<String,Object>> ls1 = courseService.getEmptyClassroom(DateUtils.getNowDate(), 1, 2);
			        		List<Map<String,Object>> ls2 = courseService.getEmptyClassroom(DateUtils.getNowDate(), 3, 4);
			        		List<Map<String,Object>> ls3 = courseService.getEmptyClassroom(DateUtils.getNowDate(), 5, 6);
			        		List<Map<String,Object>> ls4 = courseService.getEmptyClassroom(DateUtils.getNowDate(), 7, 8);
			        		List<Map<String,Object>> ls5 = courseService.getEmptyClassroom(DateUtils.getNowDate(), 9, 10);
			        		if(ls1 != null && ls1.size()>0){
			        			result = result + "第1-2节空教室："+MapUtils.getString(ls1.get(0),"name_");
			        			for(int i=1;i<ls1.size();i++){
			        				result = result +","+MapUtils.getString(ls1.get(i),"name_");
			        			}
			        		}else{
			        			result = result + "第1-2节没有找到空教室。";
			        		}
			        		if(ls2 != null && ls2.size()>0){
			        			result = result + "\r\n \r\n第3-4节空教室："+MapUtils.getString(ls2.get(0),"name_");
			        			for(int i=1;i<ls2.size();i++){
			        				result = result +","+MapUtils.getString(ls2.get(i),"name_");
			        			}
			        		}else{
			        			result = result + "\r\n \r\n第3-4节没有找到空教室。";
			        		}
			        		if(ls3 != null && ls3.size()>0){
			        			result = result + "\r\n \r\n第5-6节空教室："+MapUtils.getString(ls3.get(0),"name_");
			        			for(int i=1;i<ls3.size();i++){
			        				result = result +","+MapUtils.getString(ls3.get(i),"name_");
			        			}
			        		}else{
			        			result = result + "\r\n \r\n第5-6节没有找到空教室。";
			        		}
			        		if(ls4 != null && ls4.size()>0){
			        			result = result + "\r\n \r\n第7-8节空教室："+MapUtils.getString(ls4.get(0),"name_");
			        			for(int i=1;i<ls4.size();i++){
			        				result = result +","+MapUtils.getString(ls4.get(i),"name_");
			        			}
			        		}else{
			        			result = result + "\r\n \r\n第7-8节没有找到空教室。";
			        		}
			        		if(ls5 != null && ls5.size()>0){
			        			result = result + "\r\n \r\n第9-10节空教室："+MapUtils.getString(ls5.get(0),"name_");
			        			for(int i=1;i<ls5.size();i++){
			        				result = result +","+MapUtils.getString(ls5.get(i),"name_");
			        			}
			        		}else{
			        			result = result + "\r\n \r\n第9-10节没有找到空教室。";
			        		}
			        		customerMsg.sendNew("今日空教室", result, "", "");
			        	}else if(sf == 1){
			        		customerMsg.sendText("你绑定的身份为教师，请选择教师服务！");
			        	}else{
			        		customerMsg.sendText("您还没有绑定学生服务, <a href=\""+SysConfig.instance().getServerUrl()+ "/wechat/redirect\">点击此处进行绑定</a>");
			        	}
					}
					//教师服务---今日课程
					if(eventKey.equals("teacher_today_course")){
			        	if(sf == 1){
			        		list = teacherTeachingService.getTodayClass(MapUtils.getString(map, "username"));
			        		for(Map<String,Object> m : list){
			        			result = result + MapUtils.getString(m, "TIME_") + ":"+MapUtils.getString(m, "COURSE_NAME") +":"+MapUtils.getString(m, "CLASSROOM_NAME")+"\r\n";
			        		}
							if(list == null || list.size()<1){
								customerMsg.sendText("暂无今日课程!");
							}else{
								customerMsg.sendNew("今日课程", result, "", SysConfig.instance().getServerUrl()+"/teacher/teaching/index.jsp");
							}
			        	}else if(sf == 2){
			        		customerMsg.sendText("你绑定的身份为学生，请选择学生服务！");
			        	}else{
			        		customerMsg.sendText("您还没有绑定教职工服务, <a href=\""+SysConfig.instance().getServerUrl()+ "/wechat/redirect\">点击此处进行绑定</a>");
			        	}
					}
					//教师服务---预警信息
					if(eventKey.equals("teacher_warning")){
						if(sf == 1){
			        		Map<String,Object> cardWarn = warningService.queryConsumeNums(MapUtils.getString(map, "username"), 1);
			        		Map<String,Object> stayWarn =  warningService.queryStayNums(MapUtils.getString(map, "username"),DateUtils.getYesterday());
			        		String[] term = EduUtils.getProSchoolYearTerm(new Date());
			        		List<Map<String,Object>> studyWarn = warningService.queryStudyNums(MapUtils.getString(map, "username"), term[0], term[1]);
			        		if(MapUtils.getInteger(cardWarn, "high") != 0 || MapUtils.getInteger(cardWarn, "low") != 0){
			        			result = result + "近一个月消费异常 \r\n 高消费人数为："+MapUtils.getIntValue(cardWarn, "high") +",低消费人数为："+MapUtils.getIntValue(cardWarn, "low")+"\r\n";
			        		}
			        		if(MapUtils.getInteger(stayWarn, "late") != 0 || MapUtils.getInteger(stayWarn, "out") != 0){
			        			result = result + "昨日住宿异常 \r\n 晚归人数为："+MapUtils.getIntValue(stayWarn, "late") +",疑似不在校人数为："+MapUtils.getIntValue(stayWarn, "out")+"\r\n";
			        		}
			        		if(studyWarn != null && studyWarn.size()>0){
			        			result = result + "上学期学业异常\r\n";
			        			for(Map<String,Object> m : studyWarn){
			        				result = result + MapUtils.getString(m, "bj")+ "  挂科人数    "+MapUtils.getIntValue(m, "gkrs")+"\r\n";
			        			}
			        		}
			        		if(!StringUtils.hasText(result)){
			        			customerMsg.sendText("暂无学生异常数据！");
			        		}else{
			        			customerMsg.sendNew("学生异常", result, "", SysConfig.instance().getServerUrl()+ "/teacher/warning/warning.jsp");
			        		}
			        	}else if(sf == 2){
			        		customerMsg.sendText("你绑定的身份为学生，请选择学生服务！");
			        	}else{
			        		customerMsg.sendText("您还没有绑定教职工服务, <a href=\""+SysConfig.instance().getServerUrl()+ "/wechat/redirect\">点击此处进行绑定</a>");
			        	}
					}
					
				}
			}
			//图片消息
			else if(msgType.equals("iamge")){
				System.out.println(msg);
				log.info("您收到一条图片消息！");
				
			}
			//位置消息
			else if(msgType.equals("location")){
			}
			//声音消息
			else if(msgType.equals("voice")){
				String msgContent = msg.get("Content");
				log.debug(user.getNickName() +" 发来消息 ： "+msgContent);
				CustomerMsg customerMsg = new CustomerMsg(fromUser);
				customerMsg.sendText(msgContent);
			}
			//其他消息
			else{
				log.info("其他消息类型，暂时不支持");
			}
		} catch (Exception e) {
			log.error("消息解析失败！");
			e.printStackTrace();
		}
	}
	
	
	/** 
	* @Title: parseXml 
	* @Description:解析消息转换成Map {MsgId=6239569947902712341, FromUserName=owN2uwyJrP9foLqDz8Ejc3KJQ8pg, CreateTime=1452763087, Content=你好, ToUserName=gh_5a2dc45617ea, MsgType=text}
	* @param request
	* @throws Exception
	* @return Map<String,String>
	*/
	public  Map<String, String> parseXml(HttpServletRequest request) throws Exception {  
	        // 将解析结果存储在HashMap中  
	        Map<String, String> map = new HashMap<String, String>();  
	        // 从request中取得输入流  
	        InputStream inputStream = request.getInputStream();  
	       // 读取输入流  
	        SAXReader reader = new SAXReader();
	        Document document = reader.read(inputStream); 
	        // 得到xml根元素  
	        Element root = document.getRootElement();  
	        // 得到根元素的全部子节点  
	        @SuppressWarnings("unchecked")
			List<Element> elementList = root.elements();  
	        // 遍历全部子节点  
	        for (Element e : elementList)  {
	            map.put(e.getName(), e.getText()); 
	         }
	        // 释放资源  
	        inputStream.close();  
	        inputStream = null;  
	        return map;  
	    }  
	 
	/** 
	* @Title: sendMessage 
	* @Description:批量发送消息，即每日推送
	* @param request
	* @param response
	* @throws IOException
	*/
	@RequestMapping("test/sendMessage")
	@ResponseBody
	public boolean sendMessage(HttpServletRequest request,String message) throws IOException {
		// 主动发送客服消息
		UserManager userManager = new UserManager();
		List<String> userList = userManager.subscriberList().getData().getOpenid();
		for (int i = 0; i < userList.size(); i++) {
			String toUserOpenId = userList.get(i);
			CustomerMsg customerMsg = new CustomerMsg(toUserOpenId);
			customerMsg.sendText(message);
		}
		return true;
	}
	
	@RequestMapping("test/sendMessageToUser")
	@ResponseBody
	public Map<String, Object> sendMessageToUser(String message,String toUserOpenId) throws IOException {
		// 主动发送客服消息
		CustomerMsg customerMsg = new CustomerMsg(toUserOpenId);
		customerMsg.sendText(message);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("name", "sunweiguang");
		result.put("gender", "boy");
		return result;
	}
	
	/** 
	* @Title: sendNews
	* @Description:发送新闻
	* @param request
	* @throws IOException
	*/
	@RequestMapping("test/sendNew")
	@ResponseBody
	public boolean sendNew(HttpServletRequest request,String title,String content,String imageUrl,String url) throws IOException {
		// 主动发送客服消息
		// 获得关注者列表，发送给每个人
		UserManager userManager = new UserManager();
		List<String> userList = userManager.subscriberList().getData().getOpenid();
		for (int i = 0; i < userList.size(); i++) {
			String toUserOpenId = userList.get(i);
			User user = userManager.getUserInfo(toUserOpenId);
			CustomerMsg customerMsg = new CustomerMsg(toUserOpenId);
			customerMsg.sendNew(title,content,user.getHeadimgUrl(),url);
		}
		return true;
	}
	
	/** 
	* @Title: sendNews
	* @Description:发送新闻
	* @param request
	* @throws IOException
	*/
	@RequestMapping("test/sendNews")
	@ResponseBody
	public boolean sendNews(HttpServletRequest request,String title,String content,String imageUrl,String url) throws IOException {
		// 主动发送客服消息
		// 获得关注者列表，发送给每个人
		UserManager userManager = new UserManager();
		List<String> userList = userManager.subscriberList().getData().getOpenid();
		for (int i = 0; i < userList.size(); i++) {
			String toUserOpenId = userList.get(i);
			User user = userManager.getUserInfo(toUserOpenId);
			CustomerMsg customerMsg = new CustomerMsg(toUserOpenId);
			List<ArticleResponse> news = new ArrayList<ArticleResponse>();
			for (int j = 0; j < 4; j++) {
				ArticleResponse response = new ArticleResponse();
				response.setUrl(url);
				response.setTitle(title);
				response.setPicUrl(user.getHeadimgUrl());
				response.setDescription(content);
				news.add(response);
			}
			customerMsg.sendNews(news);
		}
		return true;
	}
	
	/** 
	 * @Title: sendNews
	 * @Description: 发送新闻
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping("test/getUserInfo")
	@ResponseBody
	public List<User> getUserInfo(HttpServletRequest request) throws IOException {
		List<User> result = new ArrayList<User>();
		// 主动发送客服消息
		// 获得关注者列表，发送给每个人
		UserManager userManager = new UserManager();
		List<String> userList = userManager.subscriberList().getData().getOpenid();
		for (int i = 0; i < userList.size(); i++) {
			String toUserOpenId = userList.get(i);
			User user = userManager.getUserInfo(toUserOpenId);
			result.add(user);
		}
		return result;
	}
}