package com.jhnu.util.common;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import com.jhnu.cas.entity.User;

public class PasswordHelperUtil {
	
	
	private static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
	private static String ALGORITHMNAME = "md5";
	private static int HASHITERATIONS = 2;
	
	public static void encryptPassword(User user) {
	
	    user.setSalt(randomNumberGenerator.nextBytes().toHex());
	
	    String newPassword = new SimpleHash(
	            ALGORITHMNAME,
	            user.getPassword(),
	            ByteSource.Util.bytes(user.getCredentialsSalt()),
	            HASHITERATIONS).toHex();
	
	    user.setPassword(newPassword);
	}
	
	public static void encryptPassword(User user,String salt) {
		user.setSalt(salt);
		String newPassword = new SimpleHash(
	            ALGORITHMNAME,
	            user.getPassword(),
	            ByteSource.Util.bytes(user.getCredentialsSalt()),
	            HASHITERATIONS).toHex();
	
	    user.setPassword(newPassword);
	}
	
	public static String simpleEncryptPassword(String salt,String password) {
		String newPassword = new SimpleHash(
	            ALGORITHMNAME,
	            password,
	            ByteSource.Util.bytes(salt),
	            HASHITERATIONS).toHex();
		return newPassword;
		
	}
	
}

