package com.jhnu.util.login;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.jhnu.cas.entity.LoginResultBean;
import com.jhnu.cas.entity.User;

public class CommonLoginUtil {
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static LoginResultBean checkUserName(String username,JdbcTemplate jdbcTemplate){
		LoginResultBean jrb=new LoginResultBean(username);
		String sql = "select * from t_sys_user where username=?";
        List<User> userList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(User.class), username);
        if(userList.size() == 0 ) {
        	jrb.setErrorMas("用户：'"+username+"'不存在");
        	jrb.setTrue(false);
        }else{
        	jrb.setObject(userList.get(0));
        }
        return jrb;
        
	}
	
	public static LoginResultBean checkIsTrue(User user){
		LoginResultBean jrb=new LoginResultBean(user.getUsername());
		if(user.getIstrue()!=1){
			jrb.setErrorMas("用户'"+user.getUsername()+"'未启用！请联系管理员。");
			jrb.setTrue(false);
    	}
        return jrb;
        
	}
	
}
