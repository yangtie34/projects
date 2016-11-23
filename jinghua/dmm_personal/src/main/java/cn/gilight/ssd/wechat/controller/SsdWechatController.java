package cn.gilight.ssd.wechat.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jhnu.syspermiss.util.SysConfig;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;
import cn.gilight.personal.po.TSysUser;

/**   
* @Description: 陕师大获取用户名接口
* @author Sunwg  
* @date 2016年5月23日 下午4:28:44   
*/
@Controller("ssdWechatController")
@RequestMapping("/ssd/wechat")
public class SsdWechatController {
	private Logger log = Logger.getLogger(SsdWechatController.class);
	@Resource
	private BaseDao baseDao;
	@Resource
	private HibernateDao hibernate;
	
	@RequestMapping(method=RequestMethod.GET, value="/redirect")
	@Transactional
	public ModelAndView bindWechat(HttpServletRequest request,String timestamp,String signature) {
		ModelAndView mv = new ModelAndView("wechat/ssd/check");
		String userParam="",token="";
		boolean success = false;
		String message = "";
		try{
			userParam = getProperty("ssd.userParam");
			token = getProperty("ssd.token");
		}catch(Exception e ){
			log.error("读取微信配置文件中的属性时出错，请确认陕师大微信配置文件无误！（/ssd/config.properties）");
			e.printStackTrace();
		}
		String uid = request.getParameter(userParam);
		if(checkSignature(uid,timestamp,token,signature)){
			Long timestampVal = new Long(timestamp);
			//比较时间戳大小，验证URL是否过时
			if(timestampVal > getCurrentTimestamp()){
				//验证通过，开始登陆并跳转
				success = true;
				String username = uid;
				TSysUser userTemp = new TSysUser();
				userTemp.setUsername(username);
				List<TSysUser> userlist = hibernate.findByExample(userTemp);
				TSysUser user = userlist.get(0);
				String password = user.getPassword() + "_external";
				/* 
				 * 调用系统方法登录username 
				 **/
				SysConfig sys = SysConfig.instance();
				String url = sys.getCasServerLoginUrl();
				mv.addObject("auto", true);
				mv.addObject("username", username);
				mv.addObject("password", password);
				int sf = checkUserIsTeaOrStu(username);
				switch (sf) {
					case 1:
						mv.addObject("service", sys.getServerUrl()+"/teacher/index.jsp");
						mv.setViewName("redirect:"+url);
						break;
					case 2:
						mv.addObject("service", sys.getServerUrl()+"/student/index.jsp");
						mv.setViewName("redirect:"+url);
						break;
					default:
						mv.setViewName("index");
						break;
				}
			}else{
				message = "链接失效，请返回上一层重新查询";
			}
		}else{
			message = "请求校验失败";
		}
		mv.addObject("message", message);
		mv.addObject("success", success);
		return mv;
	}
	
	
	
	
	/** 
	* @Description: 验证用户是学生还是教师 ，1：教师，2：学生
	* @Return: int
	*/
	private int checkUserIsTeaOrStu(String username){
		int result = 0;
		String teaSql = "SELECT T.TEA_NO,T.NAME_ FROM T_TEA T WHERE T.TEA_NO='"+username+"'";
		int teaNum = baseDao.queryForInt(teaSql);
		if(teaNum > 0){
			return 1;
		}else{
			String stuSql = "SELECT NO_,NAME_ FROM T_STU WHERE NO_='"+username+"'";
			int stuNum  = baseDao.queryForInt(stuSql);
			if (stuNum > 0) {
				return 2;
			}
		}
		return result;
	}
	
	private boolean checkSignature(String uid,String timestamp,String token,String signature){
		boolean result = false;
		//组装数组
		String[] tempArray={uid,timestamp,token};
		//排序数组
		Arrays.sort(tempArray);
		//拼接数组
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < tempArray.length; i++){
			sb.append(tempArray[i]);
		}
		//数组通过sha1加密
		String tempString=SHA1Encode(sb.toString());
		//验证signature ，确认来自陕师大微信的请求
		if(tempString.equals(signature)){
			result = true;
		}
		return result;
	}

	private Long getCurrentTimestamp(){
		Long currentTime = System.currentTimeMillis();
		return new Long((currentTime+"").substring(0, 10));
	}
	
	private String getProperty(String propertyName) throws UnsupportedEncodingException,IOException{
		Properties p = new Properties();
		InputStream inStream = this.getClass().getResourceAsStream("/ssd/config.properties");
		if(inStream == null){
			log.error("根目录下找不到sysConfig.properties文件");
			return null;
		}
		p.load(new InputStreamReader(inStream, "UTF-8"));
		return p.getProperty(propertyName).trim();
	}
	
	private String SHA1Encode(String decript){
		try {
			MessageDigest digest = java.security.MessageDigest
					.getInstance("SHA-1");
			digest.update(decript.getBytes());
			byte messageDigest[] = digest.digest();
			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			// 字节数组转换为 十六进制 数
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
}