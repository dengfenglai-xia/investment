package com.ruikun.sys.investment.utils;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

public class UploadUtils {
	/**
	 * 提取上传文件
	 */
	public static List<MultipartFile> getFile()
			throws Exception {
		// 解析器解析request的上下文
		HttpServletRequest request = com.ruikun.sys.investment.utils.CommonUtil.getRequest();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 先判断request中是否包涵multipart类型的数据
		List<MultipartFile> multipartFiles = new ArrayList<MultipartFile>();
		if (multipartResolver.isMultipart(request)) {

			StandardServletMultipartResolver resolver = new StandardServletMultipartResolver();
			MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
			Iterator iter = multipartRequest.getFileNames();
			while (iter.hasNext()) {
				multipartFiles.add(multipartRequest.getFile((String) iter.next()));
			}
		}
		return multipartFiles;
	}

	/**
	 * 获得随机UUID文件名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String generateRandonFileName(String fileName) {
		// 首相获得扩展名，然后生成一个UUID码作为名称，然后加上扩展名
		String ext = fileName.substring(fileName.lastIndexOf("."));
		return UUID.randomUUID().toString().replace("-", "") + ext;
	}

	/**
	 * 获得文件目录
	 * 
	 * @param uuidFileName
	 * @return
	 */
	public static String generateRandomDir(String uuidFileName) {
		int hashCode = uuidFileName.hashCode();// 得到它的hashcode编码
		// 一级目录
		int d1 = hashCode & 0xf;
		// 二级目录
		int d2 = (hashCode >> 4) & 0xf;
		return "/" + d1 + "/" + d2;
	}

	/**
	 * 上传文件
	 * @throws Exception
	 */
	public static Map uploadFileAndGetPath(MultipartFile multipartFile) {
		Map map = Maps.newHashMap();
		String filePath = "";
		String fileName = "";
		try {
			// 保存文件根路径
			String fileRootPath = ReadConfig.getProperty("FILE_ROOT_PATH");
			// 原文件名称
			String originalImgName = multipartFile.getOriginalFilename();
			// 获取文件后缀名
			String suffix = originalImgName.substring(originalImgName.lastIndexOf("."));
			if (!StringUtils.isEmpty(originalImgName)) {
				// 重命名，防止重名
				fileName = CommonUtil.getUniqueCode()+ suffix;
				// 根据hash散列算法生成多级目录
				String hashDir = generateRandomDir(fileName);
				String savePath = fileRootPath + "/" + hashDir;
				File file = new File(savePath);
				if (!file.exists()) file.mkdirs();
				filePath = hashDir + "/" + fileName;
				savePath = fileRootPath + filePath;
				file = new File(savePath);
				// 保存文件
				multipartFile.transferTo(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("filePath",filePath);
		map.put("fileName",fileName);
		return map;
	}


	/**
	 * 上传文件
	 * @throws Exception
	 */
	public static void uploadFileRootPath(MultipartFile multipartFile,String fileName) {
		String filePath = "";
		try {
			// 保存文件根路径
			String fileRootPath = ReadConfig.getProperty("FILE_ROOT_PATH");
			// 原文件名称
			String originalImgName = multipartFile.getOriginalFilename();
			// 获取文件后缀名
			String suffix = originalImgName.substring(originalImgName.lastIndexOf("."));
			if (!StringUtils.isEmpty(originalImgName)) {
				// 重命名，防止重名
				fileName = fileName+ suffix;
				File file = new File(fileRootPath + "/company/" + fileName);
				// 保存文件
				multipartFile.transferTo(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
