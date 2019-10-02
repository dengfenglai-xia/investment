package com.ruikun.sys.investment.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpReqUtil {

	// 默认连接时间
	private static int DEFAULT_CONNTIME = 5000;
	// 默认超时时间
	private static int DEFAULT_READTIME = 5000;

	private static Logger logger = LoggerFactory.getLogger(HttpReqUtil.class);

	public static String httpRequest(String requestUrl, String method,String outputStr) {
		logger.debug("*********通讯开始********");
		logger.debug("请求地址: " + requestUrl + "，发送报文: " + outputStr);
		String result = "";
		try {
			URL url = new URL(requestUrl);
			//根据url的协议选择对应的请求方式
			HttpURLConnection conn = getConnection(method, url);
			if ("GET".equalsIgnoreCase(method))conn.connect();
			if (outputStr != null && !"".equals(outputStr)) {
				OutputStream output = conn.getOutputStream();
				output.write(outputStr.getBytes("UTF-8"));
				output.flush();
			}
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream inputStream = conn.getInputStream();
				result = IOUtils.toString(inputStream, "UTF-8");
				conn.disconnect();
			}
		} catch (ConnectException ce) {
			logger.debug("*********请求超时*********");
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("*********请求异常*********");
		}
		return result;
	}

	/**
	 * 根据url的协议选择对应的请求方式
	 * @param method 请求的方法(POST、GET)
	 * @throws IOException
	 */
	public static HttpURLConnection getConnection(String method, URL url) throws Exception {
		HttpURLConnection conn = null;
		/*if ("https".equals(url.getProtocol())) {
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new SecureRandom());
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);
			conn = httpUrlConn;
		} else {*/
			conn = (HttpURLConnection) url.openConnection();
		/*}*/
		conn.setRequestMethod(method);
		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setConnectTimeout(DEFAULT_CONNTIME);
		conn.setReadTimeout(DEFAULT_READTIME);
		return conn;
	}
	
	/**
	 * post请求
	 * 
	 * @param url
	 *            功能和操作
	 * @param body
	 *            要post的数据
	 * @return
	 * @throws IOException
	 */
	public static String post(String url, String body)
	{
		String result = "";
		try
		{
			OutputStreamWriter out = null;
			BufferedReader in = null;
			URL realUrl = new URL(url);
			URLConnection conn = realUrl.openConnection();

			// 设置连接参数
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(20000);
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			// 提交数据
			out = new OutputStreamWriter(conn.getOutputStream(), "gb2312");
			out.write(body);
			out.flush();

			// 读取返回数据
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line = "";
			boolean firstLine = true; // 读第一行不加换行符
			while ((line = in.readLine()) != null)
			{
				if (firstLine)
				{
					firstLine = false;
				} else
				{
					result += System.lineSeparator();
				}
				result += line;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}

}
