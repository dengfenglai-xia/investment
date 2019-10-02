package com.ruikun.sys.investment.utils;

import org.apache.log4j.Logger;

import java.io.File;

/**
 * 删除文件和目录
 * 
 */
public class DeleteFileUtil {

	private static Logger logger = Logger.getLogger(DeleteFileUtil.class);

	/**
	 * 删除文件，可以是文件或文件夹
	 * 
	 * @param filePath
	 *            要删除的文件所在路径+文件名称
	 * 
	 * @return 删除成功返回true，否则返回false
	 */
	public static boolean delete(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			logger.debug(filePath + " 删除文件失败不存在！");
			return false;
		} else {
			if (file.delete()) {
				logger.debug(filePath + " 文件删除成功！");
				return true;
			} else {
				logger.debug(filePath + " 文件删除失败！");
				return false;
			}
		}
	}

	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}
	
	public static void main(String[] args) {
		String dir = "D:/video/temp/";
		DeleteFileUtil.delAllFile(dir);
	}
}