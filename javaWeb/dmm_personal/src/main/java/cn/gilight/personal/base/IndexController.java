package cn.gilight.personal.base;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jhnu.syspermiss.GetCachePermiss;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;

@Controller("indexController")
public class IndexController {
	private Logger log = Logger.getLogger(IndexController.class);
	@Resource
	private BaseDao baseDao;
	@Resource
	private HibernateDao hibernate;
	
	@RequestMapping("/home")
	public void bindWechat(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String basePath = request.getContextPath();
		int port = request.getServerPort();
		String ip = request.getServerName();
		String projectPath = "http://"+ip+":"+port+basePath;
		String username=request.getUserPrincipal().getName();
		GetCachePermiss.init(username,projectPath);
		log.debug(username + ":登陆系统");
		
		//开始判断学生身份
		String countsql = "select * from t_stu t where t.no_ = '"+username+"'";
		int count = baseDao.queryForInt(countsql);
		if(count > 0){
			response.sendRedirect("student/index.jsp");
		}else{
			//开始判断教职工身份
			countsql = "select * from t_tea t where t.tea_no = '"+username+"'";
			count = baseDao.queryForInt(countsql);
			if(count > 0){
				response.sendRedirect("teacher/index.jsp");
			}else{
				response.sendRedirect("home.jsp");
			}
		}
	}
}