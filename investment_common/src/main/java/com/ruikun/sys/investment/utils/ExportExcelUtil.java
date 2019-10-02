package com.ruikun.sys.investment.utils;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

/**
 * 导出Excel工具类
 * @author Administrator
 *
 */
public class ExportExcelUtil {

	public static void outputFileData(HttpServletResponse response,byte[] fileBuff,String fileName) throws Exception{
		response.setContentType("application/octet-stream;charset=GBK");
		response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"),"iso-8859-1"));
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			out.write(fileBuff);
			out.flush();
		} catch (IOException e) {
		}finally{
			if (out!=null)
				out.close();
		}
	}
	
	/**
	 * 根据参数生成excel文件的二进制数据
	 * @param tempPath	excel模板路径
	 * @param params	模板中相关参数的Map
	 * @return			生成文件的byte数组
	 */
	public static byte[] genSingleExcelFileData(String tempPath,Map<String,Object> params){
		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(new File(tempPath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		XLSTransformer trans = new XLSTransformer();
		Workbook workbook;
		try {
			workbook = trans.transformXLS(fileInputStream, params);
		} catch (ParsePropertyException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidFormatException e) {
			e.printStackTrace();
			return null;
		}
		ByteArrayOutputStream buff = new ByteArrayOutputStream();
		try {
			workbook.write(buff);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return buff.toByteArray();
	}
}
