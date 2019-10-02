package com.ruikun.sys.investment.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

public class DownLoadFileUtil {

	/**
	 * 检查文件是否存在
	 * @param filePath（文件散列路径。例如：/0/1/test.doc）
	 */
	public static Boolean checkFileIsExists(String filePath){
		filePath = ReadConfig.getProperty("FILE_ROOT_PATH")+ filePath;
		File file = new File(filePath);
		if (file.exists()) {
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 文件下载
	 * @param filePath（文件散列路径。例如：/0/1/test.doc）
	 * @param fileName
	 * @throws Exception
	 */
	public static void downloadFile(String filePath,String fileName){
		HttpServletResponse response = CommonUtil.getResponse();
		FileInputStream in = null;
		OutputStream out = null;
		try{
			filePath = ReadConfig.getProperty("FILE_ROOT_PATH")+ filePath;
			File file = new File(filePath);
			in = new FileInputStream(file);
			out = response.getOutputStream();
			fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			if (file.exists()) {
				response.setCharacterEncoding("UTF-8");
				response.setHeader("content-disposition", "attachment;filename="+ fileName);
				byte buffer[] = new byte[1024];
				int len = 0;
				while ((len = in.read(buffer)) > 0) {
					out.write(buffer, 0, len);
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			try {
				if(in != null) in.close();
				if(out != null) out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 根据基础路径和文件名下载文件
	 * @param response
	 * @param filePath
	 * @param fileName
	 * @throws Exception
	 */
	public static void downloadFile(HttpServletRequest request,HttpServletResponse response,String filePath,String fileName) throws Exception{

		File file = new File(filePath);
		FileInputStream in = new FileInputStream(file);
		OutputStream out = response.getOutputStream();
		//防止在火狐浏览器中下载的文件名称中文乱码
		final String userAgent = request.getHeader("USER-AGENT");
		if(userAgent.contains("Mozilla")){//google,火狐浏览器
			fileName = new String(fileName.getBytes(), "ISO8859-1");
		}else{
			fileName = URLEncoder.encode(fileName,"UTF-8");//其他浏览器
		}

		if (file.exists()) {
			response.setCharacterEncoding("utf-8");
			response.setHeader("content-disposition", "attachment;filename="+ fileName);
			byte buffer[] = new byte[1024];
			int len = 0;
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
			in.close();
			out.close();
		}
	}
}
