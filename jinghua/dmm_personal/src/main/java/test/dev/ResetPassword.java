package test.dev;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.gilight.framework.uitl.common.MapUtils;

import com.jhnu.syspermiss.util.PasswordHelperUtil;

public class ResetPassword {
	public static void main(String[] args) {
		List<Map<String, Object>> list =  DB.queryListBySql("select t.username,t.salt from t_sys_user t ");
		List<String> sqls = new ArrayList<String>();
		for (Map<String, Object> user : list) {
			String username = MapUtils.getString(user, "USERNAME");
			String salt = MapUtils.getString(user, "SALT");
			String password = getSaltPassword(username+salt, "123456");
			sqls.add("UPDATE T_SYS_USER T SET T.PASSWORD = '"+password+"' WHERE T.USERNAME = '"+username+"'");
			//DB.update("UPDATE T_SYS_USER T SET T.PASSWORD = '"+password+"' WHERE T.USERNAME = '"+username+"'");
			System.out.println("+++++++ " +username+ "的密码已经更新");
		}
		DB.batchUpdate(sqls);
		
		System.out.println("密码更新完成，全部更新为123456，共计更新" + list.size() + "个用户！");
	}
	private static String getSaltPassword(String UserNameSalt,String password) {
		return PasswordHelperUtil.simpleEncryptPassword(UserNameSalt,password);
	}

}
