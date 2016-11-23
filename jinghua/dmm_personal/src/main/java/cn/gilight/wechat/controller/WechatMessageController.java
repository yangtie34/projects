package cn.gilight.wechat.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sword.wechat4j.WechatSupport;
import org.sword.wechat4j.common.Config;
import org.sword.wechat4j.response.ArticleResponse;

import cn.gilight.framework.base.dao.HibernateDao;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.personal.juhe.JuheChatDemo;
import cn.gilight.personal.juhe.JuheNewsDemo;
import cn.gilight.personal.juhe.JuheWeatherDemo;

/**   
* @Description: 微信消息解析案例 实现
* @author Sunwg  
* @date 2016年3月9日 下午12:28:17   
*/
@Controller("wechatMessageController")
@RequestMapping("/wechat")
public class WechatMessageController extends WechatSupport   {
	private Logger log = Logger.getLogger(WechatMessageController.class);

	@Autowired
	private static JdbcTemplate jdbc;
	
	public JdbcTemplate getJdbc() {
		return jdbc;
	}

	public void setJdbc(JdbcTemplate jdbc) {
		WechatMessageController.jdbc = jdbc;
	}

	public WechatMessageController() {
		super(null);
	}
	
	public WechatMessageController(HttpServletRequest request) {
		super(request);
	}
	
	
	public WechatMessageController(HttpServletRequest request, Logger log,
			HibernateDao hibernate) {
		super(request);
	}

	@RequestMapping("/receiveMessage")
	public void receiveMessage(HttpServletRequest request, HttpServletResponse response) throws IOException{
		WechatMessageController messageController = new WechatMessageController(request);
        String result = messageController.execute();
        response.getOutputStream().write(result.getBytes());
	}
	
	@RequestMapping("/signature")
	@ResponseBody
	public Map<String, Object> getjsapi(String url) {
		Map<String, Object> result = SignUtil.getJsConfig(url);
		return result;
	}
	
	@Override
	protected void onText() {
        String content = this.wechatRequest.getContent();
        
        if(content.contains("新闻") && !content.contains("+")){
        	responseText("请发送\"新闻+关键字\"查询新闻");
        }else if(content.contains("新闻") && StringUtils.hasText(content.substring(content.indexOf("+")))){
        	String text = content.substring(content.indexOf("+")+1);
        	List<ArticleResponse> list = JuheNewsDemo.getRequest1(text);
        	if(list == null || list.size() == 0){
        		responseText("查询不到相关新闻");
        	}else{
        		responseNews(list);
        	}
        }else if(content.contains("天气") && !content.contains("+")){
        	responseText("请发送\"天气+地名\"查询天气");
        }else if(content.contains("天气") && StringUtils.hasText(content.substring(content.indexOf("+")))){
        	String text = content.substring(content.indexOf("+")+1);
        	responseText(JuheWeatherDemo.getRequest1(text));
        }else{
		    Map<String,Object> map = JuheChatDemo.getRequest1(content);
		    String text = MapUtils.getString(map, "text");
		    responseText(text);
        }
        
	}

	@Override
	protected void onImage() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void onVoice() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void onVideo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onShortVideo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onLocation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onLink() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onUnknown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void click() {
		// TODO Auto-generated method stub
		String eventKey = this.wechatRequest.getEventKey();
		if("V1001_HELLO".equals(eventKey)){
			responseText("你点击了："+this.wechatRequest.getEvent());
		}
		
	}
	
	@Override
	protected void subscribe() {
		log.info(this.wechatRequest.getFromUserName() +" 关注了您！");
		String content =  Config.instance().getSubscribeMessage();
		responseText(content);
	}

	/* 
	 * 取消关注，删除在系统中的用户信息绑定
	 */
	@Override
	protected void unSubscribe() {
		String openid = this.wechatRequest.getFromUserName();
		jdbc.execute("DELETE FROM T_WECHAT_BIND T WHERE T.OPENID = '" + openid+"'" );
		log.info(this.wechatRequest.getFromUserName() +" 对你取消关注！");
	}

	@Override
	protected void scan() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void location() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void view() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void templateMsgCallback() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void scanCodePush() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void scanCodeWaitMsg() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void picSysPhoto() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void picPhotoOrAlbum() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void picWeixin() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void locationSelect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void kfCreateSession() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void kfCloseSession() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void kfSwitchSession() {
		
	}
}