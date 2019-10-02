package com.ruikun.sys.investment.utils;

import com.ruikun.sys.investment.constants.Constants;
import org.apache.commons.io.IOUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * IO流工具类
 */
public class IOUtil {

	/**
	 * 将输入流转换为字符串
	 * 
	 *            待转换为字符串的输入流
	 * @return 由输入流转换String的字符串
	 * @throws IOException
	 */
	public static String inputStreamToString(InputStream inputStream, String encoding) throws IOException {
		if(StringUtils.isEmpty(encoding)) {
			encoding = "UTF-8";
		}
		return IOUtils.toString(inputStream, encoding);
	}

	/**
	 * 
	 * 将字符串转换为输入流
	 * @throws IOException
	 */
	public static InputStream toInputStream(String inputStr, String encoding) throws IOException {
		if (StringUtils.isEmpty(inputStr)) {
			return null;
		}
		if(StringUtils.isEmpty(encoding)) {
			encoding = "UTF-8";
		}
		return IOUtils.toInputStream(inputStr, encoding);
	}
	
	/**
	 * 编码
	 * @param source
	 * @param encode
	 * @return
	 */
	public static String urlEncode(String source, String encode) {
		String result = source;
		try {
			result = URLEncoder.encode(source, encode);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 将输入流转换字节数组
	 * @param inputStream
	 * @return byte[]
	 * @throws IOException
	 */
	public static byte[] inputStreamToByteArray(InputStream inputStream) throws IOException {
		return IOUtils.toByteArray(inputStream);
	}
	
}
