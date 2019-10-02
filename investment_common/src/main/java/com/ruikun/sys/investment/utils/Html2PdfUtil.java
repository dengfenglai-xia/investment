package com.ruikun.sys.investment.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;


public class Html2PdfUtil {
	
	private static String TEMP_PDF_PATH="D:\\tempPdf\\";
	private static String TEMP_HTML_PATH="D:\\tempHtml\\";
	
	/**
	 * @author djg
	 * 将传入的字符串写入到html文件中
	 */
    public static String stringToHtml(String content){
    	String path=null;
        if(content != null){
        	path=TEMP_HTML_PATH+UUID.randomUUID().toString().replace("-", "")+".html";
            try {
                File file = new File(path);
                if(!file.exists()){
                    File dir = new File(file.getParent());
                    dir.mkdirs();
                    file.createNewFile();
                }
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                OutputStreamWriter osw = new OutputStreamWriter(fileOutputStream,"utf-8");

                osw.write(content);
                osw.flush();
                osw.close();
                fileOutputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return path;
    }
	
	/**
     *wkhtmltopdf在硬盘的安装路径
     */
    //我这里是使用配置文件读取的
    private static final String WK_HTML_TO_PDF ="D:/wkhtmltopdf/bin/wkhtmltopdf.exe";

    public static String convert(Object srcPath){
    	String tempPath=TEMP_PDF_PATH+UUID.randomUUID().toString().replace("-", "")+".pdf";
        File file = new File(tempPath);
        File parent = file.getParentFile();

        if(!parent.exists()){
            parent.mkdirs();
        }

       StringBuilder cmd = new StringBuilder();
        cmd.append(WK_HTML_TO_PDF);
        cmd.append(" ");
        cmd.append(" --page-size A4 ");
        cmd.append(" ");
        cmd.append(" --footer-spacing 5 ");
        cmd.append(" ");
        cmd.append(" --no-background ");
        cmd.append(" ");
        cmd.append(srcPath);
        cmd.append(" ");
        cmd.append(tempPath);

        try {
            Runtime.getRuntime().exec(cmd.toString());
        }catch (Exception e){
        	tempPath = null;
        }
        return tempPath;
    }
    //cmd.append("--page-size A4");
    //cmd.append(" ");
    /**
     * 下载
     * @param path   需要下载的文件的路径
     * @param response
     */
    public static void download(String path, String fileName, HttpServletResponse response){
        try{
            File file = new File(path);
            //以流的形式下载文件
            InputStream is = new BufferedInputStream(new FileInputStream(file));
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            is.close();
            //清空response
            response.reset();
            //设置response的Header
            fileName= new String(fileName.getBytes(),"ISO-8859-1");
            response.addHeader("Content-Disposition", "attachment;filename=" +fileName);
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(bytes);
            toClient.flush();
            toClient.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    } 
    
}
