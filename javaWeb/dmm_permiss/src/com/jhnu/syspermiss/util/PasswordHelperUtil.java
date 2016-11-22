package com.jhnu.syspermiss.util;



import com.jhnu.syspermiss.permiss.entity.User;
import com.jhnu.syspermiss.util.shiro.crypto.RandomNumberGenerator;
import com.jhnu.syspermiss.util.shiro.crypto.SecureRandomNumberGenerator;
import com.jhnu.syspermiss.util.shiro.crypto.hash.SimpleHash;
import com.jhnu.syspermiss.util.shiro.util.ByteSource;

//@Service("passwordHelper")
public class PasswordHelperUtil{

    private static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
    private static String algorithmName = "md5";
    private static int hashIterations = 2;

    public static void encryptPassword(User user) {

        user.setSalt(randomNumberGenerator.nextBytes().toHex());

        String newPassword = new SimpleHash(
                algorithmName,
                user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                hashIterations).toHex();

        user.setPassword(newPassword);
    }

	public static void encryptPassword(User user,String salt) {
		user.setSalt(salt);
		String newPassword = new SimpleHash(
                algorithmName,
                user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                hashIterations).toHex();
        user.setPassword(newPassword);
	}

	public static String simpleEncryptPassword(String salt,String password) {
		String newPassword = new SimpleHash(
                algorithmName,
                password,
                ByteSource.Util.bytes(salt),
                hashIterations).toHex();
		return newPassword;
		
	}
}
