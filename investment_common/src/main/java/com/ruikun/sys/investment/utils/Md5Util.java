package com.ruikun.sys.investment.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {
	
    /**利用MD5进行加密
     * @param str  待加密的字符串
     * @return  加密后的字符串
     * @throws NoSuchAlgorithmException  没有这种产生消息摘要的算法
     * @throws UnsupportedEncodingException  
     */
    public String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        //确定计算方法
        MessageDigest md5=MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        String newstr=base64en.encode(md5.digest(str.getBytes("UTF-8")));
        return newstr;
    }
    
	/**
	 * 对密码进行多次md5加密
	 */
	public static String saltMd5(String pw ,String salt) {
		String hashAlgorithmName = ReadConfig.getProperty("hashAlgorithmName");//加密方式  
		String hashIterations = ReadConfig.getProperty("hashIterations"); //加密次数
		Object simpleHash = new SimpleHash(hashAlgorithmName, pw,  
               salt, Integer.parseInt(hashIterations));
		return simpleHash.toString();  
   }

}
