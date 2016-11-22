package com.jhnu.edu.service.impl;


import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Service;

import com.jhnu.edu.entity.User;
import com.jhnu.edu.service.PasswordHelper;

@Service("passwordHelper")
public class PasswordHelperImpl implements PasswordHelper{

    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
    private String algorithmName = "md5";
    private int hashIterations = 2;

    public void encryptPassword(User user) {

        user.setSalt(randomNumberGenerator.nextBytes().toHex());

        String newPassword = new SimpleHash(
                algorithmName,
                user.getPassword(),
                ByteSource.Util.bytes(user.gotCredentialsSalt()),
                hashIterations).toHex();

        user.setPassword(newPassword);
    }

	@Override
	public void encryptPassword(User user,String salt) {
		user.setSalt(salt);
		String newPassword = new SimpleHash(
                algorithmName,
                user.getPassword(),
                ByteSource.Util.bytes(user.gotCredentialsSalt()),
                hashIterations).toHex();

        user.setPassword(newPassword);
	}

	@Override
	public String simpleEncryptPassword(String salt,String password) {
		String newPassword = new SimpleHash(
                algorithmName,
                password,
                ByteSource.Util.bytes(salt),
                hashIterations).toHex();
		return newPassword;
		
	}
}
