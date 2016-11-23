package cn.gilight.wechat.util;

import java.security.MessageDigest;
/**   
* @Description: SHA加密
* @author Sunwg  
* @date 2016年3月16日 上午11:55:16   
*/
public class SHAUtil {
    /*** 
     * SHA加密 生成40位SHA码
     * @param 待加密字符串
     * @return 返回40位SHA码
     */
    public static String shaEncode(String inStr) throws Exception {
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance("SHA");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        byte[] byteArray = inStr.getBytes("UTF-8");
        byte[] md5Bytes = sha.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) { 
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
    
    public static void main(String[] args) throws Exception {
    	String random = String.valueOf(Math.random());
    	System.out.println(random);
    	String encode = shaEncode(random);
    	String password = "password_external_"+random+"_random_" + encode;
    	
    	
    	
    	if (password.contains("_external_") && password.length() > 20) {
    		String rs = password.split("_external_")[1];
    		if (encode.equals(shaEncode(rs.split("_random_")[0]))) {
				System.out.println("验证通过");
				System.out.println(rs.split("_random_")[1]);
				System.out.println(password.split("_external_")[0]);
			}
		}
	}
}