package cn.gilight.framework.uitl;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
 
public class PasswordHelperUtil {
	
	
	private static String ALGORITHMNAME = "md5";
	private static int HASHITERATIONS = 2;
	
	public static String simpleEncryptPassword(String salt,String password) {
		String newPassword = new SimpleHash(
	            ALGORITHMNAME,
	            password,
	            ByteSource.Util.bytes(salt),
	            HASHITERATIONS).toHex();
		return newPassword;
	}
}

