package com.ruikun.sys.investment.utils;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 操作文件
 */
public class FileUtil {

    /**
     * 文件夹内文件删除
     */
    public static boolean deleteDir(String path) {
        File dir = new File(path);
        if (!dir.exists())
            return false;
        if (dir.isDirectory()) {
            String[] childrens = dir.list();
            // 递归删除目录中的子目录下
            for (String child : childrens) {
                System.out.println(child);
                File file1 = new File(dir, child);
                file1.delete();
            }
        }
        return true;
    }

    /**
     * 删除目下文件和文件夹
     * @param folderPath
     */
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            File myFilePath = new File(filePath);
            myFilePath.delete(); //删除空文件夹
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
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 定时将指定文件夹下的所有文件压缩
     * @param file
     * @param baseDir
     * @param zos
     */
    public static void compress(File file, String baseDir, ZipOutputStream zos) {
        if (!file.exists()) {
            System.out.println("待压缩的文件目录或文件" + file.getName() + "不存在");
            return;
        }
        File[] files = file.listFiles();
        BufferedInputStream bis = null;
        //ZipOutputStream zos = null;
        byte[] bufs = new byte[1024 * 10];
        FileInputStream fis = null;
        try {
            for (int i = 0; i < files.length; i++) {
                String fName = files[i].getName();
                System.out.println("压缩：" + baseDir + fName);
                if (files[i].isFile()) {
                    ZipEntry zipEntry = new ZipEntry(baseDir + fName);//
                    zos.putNextEntry(zipEntry);
                    //读取待压缩的文件并写进压缩包里
                    fis = new FileInputStream(files[i]);
                    bis = new BufferedInputStream(fis, 1024 * 10);
                    int read = 0;
                    while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {
                        zos.write(bufs, 0, read);
                    }
                    //删除源文件，则执行下面2句
                    //fis.close();
                    //files[i].delete();
                } else if (files[i].isDirectory()) {
                    compress(files[i], baseDir + fName + "/", zos);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭流
            try {
                if (null != bis)
                    bis.close();
                if (null != fis)
                    fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void fileZip(String filePath, String baseDir) {
        File sourceDir = new File(filePath);
        File zipFile = new File(filePath + ".zip");
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(new FileOutputStream(zipFile));
            compress(sourceDir, baseDir, zos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (zos != null)
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    public static void main(String[] args) {
        String filePath = "D:/temp/out/GS-20190709-569890";
//        String baseDir = "缴费通知单/";
//        fileZip(filePath, baseDir);
        delFolder(filePath);
    }
}