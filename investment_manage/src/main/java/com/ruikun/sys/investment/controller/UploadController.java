package com.ruikun.sys.investment.controller;

import com.google.common.collect.Maps;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.Docs;
import com.ruikun.sys.investment.entity.Upload;
import com.ruikun.sys.investment.entity.User;
import com.ruikun.sys.investment.service.DocsService;
import com.ruikun.sys.investment.utils.CacheUtils;
import com.ruikun.sys.investment.utils.DateTimeUtil;
import com.ruikun.sys.investment.utils.ReadConfig;
import com.ruikun.sys.investment.utils.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @description 富文本图片/文件上传
 * @author chunyu.xia
 * @date 2019/3/6
 */
@Controller
public class UploadController {

    @Autowired
    private DocsService docsService;

    /**
     * @description 文件上传
     * @author chunyu.xia
     * @date 2019/3/6
     */
    @RequestMapping(value="/fileUpload")
    @ResponseBody
    public Map fileUpload(Docs docs,MultipartFile file) {
        Map map = Maps.newHashMap();
        try {
            User user = CacheUtils.getUser();
            String originalName = file.getOriginalFilename(); //原文件名称
            double docSize = (double)file.getSize()/1024; //文件大小 KB
            map = UploadUtils.uploadFileAndGetPath(file);

            String docPath = (String) map.get("filePath"); //文件路径
            String docName = (String) map.get("fileName"); //文件名称（新）
            docs.setDocOriginalName(originalName);
            docs.setDocName(docName);
            docs.setDocSize(docSize);
            docs.setDocPath(docPath);
            docs.setCreateUserId(user.getUserId());
            docs.setUpdateUserId(user.getUserId());
            docsService.insertDocs(docs);

            map.put("originalName", originalName);
            map.put("filePath", docPath);
            map.put(Constants.MSG, "上传成功");
            map.put(Constants.SUCCESS, true);
            map.put("fileId",docs.getDocId());
            map.put("uploader",user.getRealName());
            map.put("uploadTime",DateTimeUtil.getFormatDate());
        } catch (Exception e) {
            e.printStackTrace();
            map.put(Constants.MSG, "上传失败");
            map.put(Constants.SUCCESS, false);
        }
        return map;
    }

    /**
     * @description 图片上传
     * @author chunyu.xia
     * @date 2019/3/6
     */
    @RequestMapping(value="/imgUpload")
    @ResponseBody
    public Upload imgUpload(MultipartFile file) {
        // 映射路径
        String mappingPath = ReadConfig.getProperty("FILE_MAPPING_PATH");
        //原文件名称
        String originalImgName = file.getOriginalFilename();
        Upload upload = new Upload();
        // 获取上传路径
        Map resMap = UploadUtils.uploadFileAndGetPath(file);
        String url = (String) resMap.get("filePath");
        upload.setUrl(mappingPath + url);
        upload.setState("SUCCESS");
        upload.setOriginal(originalImgName);
        upload.setTitle(originalImgName);
        return upload;
    }

}
