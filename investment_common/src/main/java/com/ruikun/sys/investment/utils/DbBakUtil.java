package com.ruikun.sys.investment.utils;

import org.apache.log4j.Logger;

import java.io.*;

public class DbBakUtil {
	private static Logger logger = Logger.getLogger(DbBakUtil.class);
	
	private static String userName = ReadConfig.getProperty("db.username");
	private static String password = ReadConfig.getProperty("db.password");
	private static String dbPath = ReadConfig.getProperty("db.path");
	private static String backUpPath = ReadConfig.getProperty("db.backUpPath");
	private static String dbName = ReadConfig.getProperty("db.name");
	private static String dbHost = ReadConfig.getProperty("db.host");
	private static String separator = File.separator;
	public static String backup(String fileName) throws IOException {
		String filePath = null;
		try {
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec(dbPath + separator + "mysqldump -u"+userName + " -p" + password + " " + dbName );
			InputStream inputStream = process.getInputStream();//得到输入流，写成.sql文件
			InputStreamReader reader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader br = new BufferedReader(reader);
			String s = null;
			StringBuffer sb = new StringBuffer();
			while((s = br.readLine()) != null){
				sb.append(s+"\r\n");
			}
			s = sb.toString();
			File file = new File(backUpPath + File.separator + fileName + ".sql" );
			file.getParentFile().mkdirs();
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			fileOutputStream.write(s.getBytes());
			fileOutputStream.close();
			br.close();
			reader.close();
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filePath;
	}
	
	
	public static void restore(String fileName) throws IOException {
		try {
			Runtime runtime = Runtime.getRuntime();
			//Process process = runtime.exec("C:\\Program Files\\MySQL\\MySQL Server 5.7\\bin\\mysql -u root -p123456 --default-character-set=utf8 huihangjinrong");
			Process process = runtime.exec(dbPath + separator + "mysql -u"+userName + " -p" + password + " --default-character-set=utf8 " + dbName );
			OutputStream outputStream = process.getOutputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(backUpPath + File.separator + fileName + ".sql" )));
			String str = null;
			StringBuffer sb = new StringBuffer();
			while((str = br.readLine()) != null){
				sb.append(str+"\r\n");
			}
			str = sb.toString();
			OutputStreamWriter writer = new OutputStreamWriter(outputStream,"utf-8");
			writer.write(str);
			writer.flush();
			outputStream.close();
			br.close();
			writer.close();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]){
		try {
//			backup("test2");
			restore("test2");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
