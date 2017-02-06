package cn.gilight.dmm.xg.interceptor;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * 
 * @author xuebl
 * @date 2016年5月11日 下午2:42:44
 */
public class ParameterInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
		Map map = arg0.getParameterMap();
		Set<Map.Entry<String, Object[]>> set = map.entrySet();
		for(Map.Entry<String, Object[]> entry : set){
			Object[] val = entry.getValue();
			if(val.length==0 || val[0] == null || "".equals(val[0])){
				entry.setValue(new Object[]{null});
//				entry.setValue(new Object[]{1});
			}
		}
		return true;
	}

}
