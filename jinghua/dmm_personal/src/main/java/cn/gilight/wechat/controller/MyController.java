package cn.gilight.wechat.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sword.wechat4j.event.EventType;
import org.sword.wechat4j.exception.WeChatException;
import org.sword.wechat4j.menu.Menu;
import org.sword.wechat4j.menu.MenuButton;
import org.sword.wechat4j.menu.MenuManager;
import org.sword.wechat4j.message.CustomerMsg;
import org.sword.wechat4j.response.ArticleResponse;
import org.sword.wechat4j.user.User;
import org.sword.wechat4j.user.UserManager;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.product.EduUtils;
import cn.gilight.personal.juhe.JuheChatDemo;
import cn.gilight.personal.juhe.JuheNewsDemo;
import cn.gilight.personal.juhe.JuheWeatherDemo;
import cn.gilight.personal.student.course.service.CourseService;
import cn.gilight.personal.student.score.dao.StuScoreDao;
import cn.gilight.personal.teacher.teaching.service.TeacherTeachingService;

import com.jhnu.syspermiss.util.SysConfig;

@Controller("myController")
@RequestMapping("/mytest")
public class MyController {
	
	private Logger log = Logger.getLogger(MyController.class);
	
	@Autowired
	private TeacherTeachingService teacherTeachingService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private StuScoreDao stuScoreDao;
	
	@Resource
	private BaseDao baseDao;
	
	
	/**
	 * 接受微信消息 ，并回复消息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/receiveMessage")
	public void reciveMessage(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		//随机字符串
		String echostr = request.getParameter("echostr");
		
		//判断是否是url有效性的验证，如果是有效性验证则进行验证，返回验证随机码
		if(echostr != null){
			//微信加密签名
			String signature = request.getParameter("signature");
			//时间戳
			String timestamp = request.getParameter("timestamp");
			//随机数
			String nonce = request.getParameter("nonce");
			
			PrintWriter pw = response.getWriter();
			// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败  
			if(SignUtil.checkSignature(signature, timestamp, nonce)){
				pw.print(echostr);
			}
			pw.close();
		}else{
			//url有效性已经进行验证，此时需要进行消息解析
			handleMessage(request);
		}
		
		
	}

	/**
	 * 处理接收的消息
	 * @param request
	 */
	private void handleMessage(HttpServletRequest request) {
		try {
			Map<String,String> msg = parseXml(request);
			//获取发送方账号
			String fromUserName = msg.get("FromUserName");
			UserManager userManager = new UserManager();
			//发送方User
			User user = userManager.getUserInfo(fromUserName);
			//消息类型
			String msgType = msg.get("MsgType");
			
			if(msgType.equals("text")){
				String content = msg.get("Content");
				CustomerMsg customerMsg = new CustomerMsg(fromUserName);
				//文字消息
		        if(content.contains("新闻") && !content.contains("+")){
		        	customerMsg.sendText("请发送\"新闻+关键字\"查询新闻");
		        }else if(content.contains("新闻") && StringUtils.hasText(content.substring(content.indexOf("+")))){
		        	String text = content.substring(content.indexOf("+")+1);
		        	List<ArticleResponse> list = JuheNewsDemo.getRequest1(text);
		        	if(list == null || list.size() == 0){
		        		customerMsg.sendText("查询不到相关新闻");
		        	}else{
		        		customerMsg.sendNews(list);
		        	}
		        }else if(content.contains("天气") && !content.contains("+")){
		        	customerMsg.sendText("请发送\"天气+地名\"查询天气");
		        }else if(content.contains("天气") && StringUtils.hasText(content.substring(content.indexOf("+")))){
		        	String text = content.substring(content.indexOf("+")+1);
		        	customerMsg.sendText(JuheWeatherDemo.getRequest1(text));
		        }else if(content.contains("成绩")){
		        	String result = "上学期成绩：";
		        	List<Map<String,Object>> binds = baseDao.queryListInLowerKey("select * from t_wechat_bind where openid = '"+fromUserName+"'");
		        	Map<String,Object> map = null;
		        	if(binds != null && binds.size()>0 ){
		        		map = binds.get(0);
		        	}
		        	int sf = MapUtils.getIntValue(map, "sf");
		        	List<Map<String,Object>> list = null;
		        	if(sf == 2){
		        		String date = DateUtils.getNowDate();
		        		String[] terms = EduUtils.getProSchoolYearTerm(DateUtils.string2Date(date));
		        		Map<String,Object> m  = stuScoreDao.getScore(MapUtils.getString(map, "username"), terms[0], terms[1]);
		        		result = result + "总成绩  "+ MapUtils.getString(m, "total_score") + "，排名  "+MapUtils.getString(m, "rank_")+"\r\n成绩明细：";
		        		list = stuScoreDao.getScoreBySchoolTerm(MapUtils.getString(map, "username"),  terms[0], terms[1]);
		        		for(Map<String,Object> s : list){
		        			result = result +"\r\n" +MapUtils.getString(s, "course_name") + ":"+ MapUtils.getString(s, "credit") + ":"+MapUtils.getString(s, "score");
		        		}
		        		result = result + "\r\n <a href=\""+SysConfig.instance().getServerUrl()+"/student/score/score.jsp\">点击查看更多</a>";
		        		if(m == null){
		        			result = "暂无数据!";
		        		}
		        	}else if(sf == 1){
		        		result = "暂无数据!";
		        	}else{
		        		result = "您还没有绑定个人服务, <a href=\""+SysConfig.instance().getServerUrl()+ "/wechat/redirect\">点击此处进行绑定</a>";
		        	}
		        	customerMsg.sendText(result);
		        }else if(content.contains("课程")){
		        	String result = "今日课程：";
		        	List<Map<String,Object>> binds = baseDao.queryListInLowerKey("select * from t_wechat_bind where openid = '"+fromUserName+"'");
		        	Map<String,Object> map = null;
		        	if(binds != null && binds.size()>0 ){
		        		map = binds.get(0);
		        	}
		        	int sf = MapUtils.getIntValue(map, "sf");
		        	List<Map<String,Object>> list = null;
		        	if(sf == 1){
		        		list = teacherTeachingService.getTodayClass(MapUtils.getString(map, "username"));
		        		for(Map<String,Object> m : list){
		        			result = result +"\r\n"+ MapUtils.getString(m, "TIME_") + ":"+MapUtils.getString(m, "COURSE_NAME") +":"+MapUtils.getString(m, "CLASSROOM_NAME");
		        		}
		        		result = result + "\r\n<a href=\""+SysConfig.instance().getServerUrl()+"/teacher/teaching/index.jsp\">点击查看详情</a>";
						if(list == null || list.size()<1){
							result = "暂无今日课程!";
						}
		        	}else if(sf == 2){
		        		list = courseService.getTodayCourse(MapUtils.getString(map, "username"));
		        		for(Map<String,Object> m : list){
		        			result = result +"\r\n"+ MapUtils.getString(m, "time_") + ":"+MapUtils.getString(m, "course_name")
		        					+ ":" + MapUtils.getString(m, "tea_name")+":"+MapUtils.getString(m, "classroom");
		        		}
		        		result = result + "\r\n <a href=\""+SysConfig.instance().getServerUrl()+"/student/course/course.jsp\">点击查看详情</a>";
		        		if(list == null || list.size()<1){
							result = "暂无今日课程!";
						}
		        	}else{
		        		result = "您还没有绑定个人服务, <a href=\""+SysConfig.instance().getServerUrl()+ "/wechat/redirect\">点击此处进行绑定</a>";
		        	}
		        	customerMsg.sendText(result);
		        }else{
				    Map<String,Object> map = JuheChatDemo.getRequest1(content);
				    String text = MapUtils.getString(map, "text");
				    customerMsg.sendText(text);
		        }
			}else if(msgType.equals("image")){
				//图片消息
				String picUrl = msg.get("PicUrl");//图片url链接
				String mediaId = msg.get("MediaId");//图片消息媒体id，可以调用多媒体文件下载接口拉取数据。
				CustomerMsg cm = new CustomerMsg(fromUserName);
				cm.sendText(user.getNickName()+",发送的图片链接为："+picUrl);
				cm.sendImage(mediaId);
			}else if(msgType.equals("voice")){
				//语音消息
				String mediaId = msg.get("MediaId");//语音消息媒体id，可以调用多媒体文件下载接口拉取数据。
				CustomerMsg cm = new CustomerMsg(fromUserName);
				cm.sendVoice(mediaId);
			}else if(msgType.equals("video")){
				//视频消息
				CustomerMsg cm = new CustomerMsg(fromUserName);
				cm.sendText("您发了个视频");
			}else if(msgType.equals("shortvideo")){
				//小视频消息
				CustomerMsg cm = new CustomerMsg(fromUserName);
				cm.sendText("感谢您发来的小视频");
			}else if(msgType.equals("location")){
				//地理位置
				String location_X = msg.get("Location_X");
				String location_Y = msg.get("Location_Y");
				String scale = msg.get("Scale");
				String label = msg.get("Label");
				CustomerMsg cm = new CustomerMsg(fromUserName);
				cm.sendText("您发送的地理位置信息（维度："+location_X+",经度："+location_Y+",缩放大小："+scale+",位置信息："+label+")");
			}else if(msgType.equals("event")){
				String event = msg.get("Event");
				CustomerMsg cm = new CustomerMsg(fromUserName);
				if(event.equals("subscribe")){
					log.debug(user.getNickName()+"关注了你");
					cm.sendText("欢迎您关注测试账号，您可以回复文字信息进行业务办理，绑定 <a href=\""+SysConfig.instance().getServerUrl()+ "/wechat/redirect\">个人服务</a> 查询个人相关数据，回复“成绩 ”可查询上学期成绩，回复 “课程”可查看今日课程。");
				}else if(event.equals("unsubscribe")){
					log.debug(user.getNickName()+"对你取消了关注");
				}
			}
			else{
				log.info("其他消息类型，暂时不支持");
			}
		} catch (Exception e) {
			log.error("消息解析失败！");
			e.printStackTrace();
		}
	}

	/**
	 * 解析消息转换成Map {MsgId=6239569947902712341, FromUserName=owN2uwyJrP9foLqDz8Ejc3KJQ8pg, CreateTime=1452763087, Content=你好, ToUserName=gh_5a2dc45617ea, MsgType=text}
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	private Map<String, String> parseXml(HttpServletRequest request) throws Exception {
		//将解析的结果存到Map中
		Map<String,String> map = new HashMap<String,String>();
		//从request中获取输入流
		InputStream in = request.getInputStream();
		//读取输入流
		SAXReader reader = new SAXReader();
		Document doc = reader.read(in);
		//得到xml的根元素
		Element root = doc.getRootElement();
		//得到根元素全部子节点
		List<Element> elementList =  root.elements();
		//遍历全部子节点
		for(Element e : elementList){
			map.put(e.getName(), e.getText());
		}
		in.close();
		in = null;
		return map;
	}
	
	/**
	 * 群发消息
	 * @param message
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sendMessage")
	@ResponseBody
	public boolean sendMessage(String message) throws UnsupportedEncodingException{
		UserManager um = new UserManager();
		List<String> userList = um.subscriberList().getData().getOpenid();
		for(String openid : userList){
			User user = um.getUserInfo(openid);
			String nickname = user.getNickName();
			CustomerMsg cm = new CustomerMsg(openid);
			cm.sendText(message+","+nickname+",这是群发消息！");
		}
		return true;
	}
	
	/**
	 * 发送给指定人
	 * @param message
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sendMessageToUsers")
	@ResponseBody
	public boolean sendMessageToUsers(String message,String userList) throws UnsupportedEncodingException{
		String[] users = userList.split(",");
		UserManager um = new UserManager();
		for(int i=0;i<users.length;i++){
			User user = um.getUserInfo(users[i]);
			String nickname = user.getNickName();
			CustomerMsg cm = new CustomerMsg(users[i]);
			cm.sendText(message+","+nickname+",这是发送给指定人的消息！");
		}
		return true;
	}
	
	/**
	 * 给某个关注者发送消息
	 * @param message
	 * @param openId
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sendMessageToUser")
	@ResponseBody
	public Map<String,Object> sendMessageToUser(String message,String openId) throws UnsupportedEncodingException{
		Map<String,Object> map = new HashMap<String,Object>();
		UserManager um = new UserManager();
		User user = um.getUserInfo(openId);
		String nickname = user.getNickName();
		map.put("name",nickname);
		CustomerMsg cm = new CustomerMsg(openId);
		cm.sendText(message+","+nickname+",这不是群发消息！");
		return map;
	}
	
	/**
	 * 群发一条新闻
	 * @param title
	 * @param content
	 * @param imgUrl
	 * @param url
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sendNew")
	@ResponseBody
	public boolean sendNew(String title,String content,String imgUrl,String url) throws UnsupportedEncodingException{
		UserManager um = new UserManager();
		List<String> userList = um.subscriberList().getData().getOpenid();
		for(String openid : userList){
			User user = um.getUserInfo(openid);
			String nickname = user.getNickName();
			nickname = new String(nickname.getBytes("ISO8859-1"),"UTF-8"); 
			CustomerMsg cm = new CustomerMsg(openid);
			cm.sendText("群发新闻："+nickname);
			cm.sendNew(title, content, imgUrl, url);
		}
		return true;
	}
	
	/**
	 * 群发多条新闻
	 * @param title
	 * @param content
	 * @param imgUrl
	 * @param url
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/sendNews")
	@ResponseBody
	public boolean sendnews(String title,String content,String imgUrl,String url) throws UnsupportedEncodingException{
		UserManager um = new UserManager();
		List<String> userList = um.subscriberList().getData().getOpenid();
		for(String openid : userList){
			User user = um.getUserInfo(openid);
			String nickname = user.getNickName();
			nickname = new String(nickname.getBytes("ISO8859-1"),"UTF-8"); 
			CustomerMsg cm = new CustomerMsg(openid);
			List<ArticleResponse> news = new ArrayList<ArticleResponse>();
			for(int j=0;j<4;j++){
				ArticleResponse response = new ArticleResponse();
				response.setTitle(title+j);
				response.setDescription(content+j);
				response.setPicUrl(user.getHeadimgUrl());
				response.setUrl(url);
				news.add(response);
			}
			cm.sendText("群发多条新闻："+nickname);
			cm.sendNews(news);
		}
		return true;
	}
	
	/**
	 * 获取关注者列表
	 * @return
	 */
	@RequestMapping("/userList")
	@ResponseBody
	public List<User> getUserList(){
		List<User> userList = new ArrayList<User>();
		UserManager um = new UserManager();
		List<String> openIds = um.allSubscriber();
		for(String openid : openIds){
			User user = um.getUserInfo(openid);
			userList.add(user);
		}
		return userList;
	}
	
	/**
	 * 生成微信菜单
	 * @throws WeChatException
	 */
	@RequestMapping("/createMenu")
	public void createMenuExample() throws WeChatException{
		MenuManager mm = new MenuManager();
		Menu menu = new Menu();
		List<MenuButton> btns = new ArrayList<MenuButton>();
		
		MenuButton btn1 = new MenuButton();
		btn1.setName("个人服务");
		btn1.setType(EventType.view);
		btn1.setUrl(SysConfig.instance().getServerUrl()+ "/wechat/redirect");
		
		MenuButton btn2 = new MenuButton();
		btn2.setName("聊天机器人");
		btn2.setType(EventType.click);
		btn2.setKey("V1001_HELLO");
		
		
		MenuButton btn3 = new MenuButton();
		btn3.setName("微信JS-SDK");
		btn3.setType(EventType.view);
		btn3.setUrl("http://203.195.235.76/jssdk");
		
		btns.add(btn1);
		btns.add(btn2);
		btns.add(btn3);
		menu.setButton(btns);
		mm.create(menu);
	}
	
}
