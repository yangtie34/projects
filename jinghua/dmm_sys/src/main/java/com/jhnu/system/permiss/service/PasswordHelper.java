package com.jhnu.system.permiss.service;

import com.jhnu.system.permiss.entity.User;

public interface PasswordHelper {

	/**
	 * 生成随机加密密码
	 * @param user
	 */
	
    public void encryptPassword(User user);
    
    /**
     * 通过现有的安全码，生成加密密码
     * @param user
     * @param salt
     */
    public void encryptPassword(User user,String salt);
    
    /**
     * 通过密码和一个变量生成简单的加密编码
     * @param password
     * @param salt
     */
    public String simpleEncryptPassword(String salt,String password);
    
}
