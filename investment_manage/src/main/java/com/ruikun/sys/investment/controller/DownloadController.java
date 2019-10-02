package com.ruikun.sys.investment.controller;

import com.google.common.collect.Maps;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.utils.DownLoadFileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @className: DownloadFileController
 * @description: 文件下载
 * @author: chunyu.xia
 * @date: 2019/03/26 13:42
 */
@Controller
public class DownloadController {

    /**
     * @description 检查文件是否存在
     * @author chunyu.xia
     * @date 2019/3/26
     * @param filePath 文件散列路径
     */
    @RequestMapping("/checkFileIsExists")
    @ResponseBody
    public Map checkFileIsExists(String filePath){
        Map map = Maps.newHashMap();
        boolean isExists = DownLoadFileUtil.checkFileIsExists(filePath);
        if(isExists){
            map.put(Constants.MSG, "下载资源存在");
            map.put(Constants.SUCCESS, true);
        }else{
            map.put(Constants.MSG, "下载资源不存在");
            map.put(Constants.SUCCESS, false);
        }
        return map;
    }

    /**
     * @description 文件下载
     * @author chunyu.xia
     * @date 2019/3/26
     * @param filePath 文件散列路径
     *        fileName 文件中文名称
     */
    @RequestMapping("/downloadFile")
    public void downloadFile(String filePath, String fileName){
        try {
            DownLoadFileUtil.downloadFile(filePath, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
