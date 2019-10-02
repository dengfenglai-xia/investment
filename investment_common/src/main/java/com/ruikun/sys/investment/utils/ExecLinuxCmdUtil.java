package com.ruikun.sys.investment.utils;

import java.io.InputStreamReader;
import java.io.LineNumberReader;

/**
 *  java在linux环境下执行linux命令，然后返回命令返回值。 
 */
public class ExecLinuxCmdUtil {
	
	public static Object exec(String cmd) {
		StringBuffer sb = new StringBuffer();
		try {
			String[] cmdA = { "/bin/sh", "-c", cmd };
			Process process = Runtime.getRuntime().exec(cmdA);
			LineNumberReader br = new LineNumberReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		String pwdString = exec("pwd").toString();
		String netsString = exec("netstat -nat|grep -i \"80\"|wc -l").toString();
		System.out.println("==========获得值=============");
		System.out.println(pwdString);
		System.out.println(netsString);
	}	
}
