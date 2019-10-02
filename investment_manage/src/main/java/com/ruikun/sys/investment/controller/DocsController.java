package com.ruikun.sys.investment.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.Docs;
import com.ruikun.sys.investment.service.DocsService;
import com.ruikun.sys.investment.utils.CacheUtils;
import com.ruikun.sys.investment.utils.PagingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
 
/**
 * @Description: 文档表相关操作
 * @author: xiachunyu
 * @date: 2019-06-14
 */
@Controller
@RequestMapping("docs")
public class DocsController {
	
	@Autowired
    private DocsService docsService;  
		
	/**
	 * @Description: 查询文档表信息列表
	 * @author: xiachunyu
	 * @date: 2019-06-14
	 */
	@RequestMapping("queryDocsList")
	@ResponseBody
	public Map queryDocsList(HttpServletRequest request,Docs docs){
		Map map = Maps.newHashMap();
		PagingUtil.setPageParam(request);
		List<Docs> list = docsService.queryDocsList(docs);
		map.put(Constants.RESULT_DATA,new PageInfo<Docs>(list));
        return map;
	}
	
	/**
	 * @Description: 通过主键查询文档表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-14
	 */
	@RequestMapping("queryDocsDetailByPrimarykey/{docId}")
	public String queryDocsDetailByPrimarykey(@PathVariable("docId")Long docId,Model model){
		Docs docs = docsService.queryDocsDetailByPrimarykey(docId);
		model.addAttribute("docs", docs);
		return "docsDetail";
	}

	/**
	 * @Description: 查询文档表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-14
	 */
	@RequestMapping("queryDocsDetail}")
	public String queryDocsDetail(Docs docs,Model model){
		docs = docsService.queryDocsDetail(docs);
		model.addAttribute("docs", docs);
		return "docsDetail";
	}

	/**
	 * @Description: 查询文档表信息详情(JSON)
	 * @author: xiachunyu
	 * @date: 2019-06-14
	 */
	@RequestMapping("queryDocsDetailByPrimarykey")
	@ResponseBody
	public Docs queryDocsDetailByPrimarykey(Long docId){
		Docs docs = docsService.queryDocsDetailByPrimarykey(docId);
		return docs;
	}
	
	/**
	 * @Description: 新增文档表信息
	 * @author: xiachunyu
	 * @date: 2019-06-14
	 */
	@RequestMapping("insertDocs")
	@ResponseBody
	public Map insertDocs(Docs docs){
		Map map = Maps.newHashMap();
		try {		
			docsService.insertDocs(docs);
			map.put(Constants.MSG, "添加成功");
			map.put(Constants.SUCCESS, true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put(Constants.SUCCESS, false);
			map.put(Constants.MSG, "系统异常");
		}
        return map;
	}
	
	/**
	 * @Description: 修改文档表信息
	 * @author: xiachunyu
	 * @date: 2019-06-14
	 */
	@RequestMapping("updateDocs")
	@ResponseBody
	public Map updateDocs(Docs docs){
		Map map = Maps.newHashMap();
		try {		
			docsService.updateDocs(docs);
			map.put(Constants.MSG, "更新成功");
			map.put(Constants.SUCCESS, true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put(Constants.SUCCESS, false);
			map.put(Constants.MSG, "系统异常");
		}
        return map;
	}
	
	/**
	 * @Description: 删除文档表信息
	 * @author: xiachunyu
	 * @date: 2019-06-14
	 */
	@RequestMapping("deleteDocs")
	@ResponseBody
	public Map deleteDocs(Long docId){
		Map map = Maps.newHashMap();
		try {
			Docs docs = new Docs();
			Integer userId = CacheUtils.getUser().getUserId();
			docs.setDocId(docId);
			docs.setUpdateUserId(userId);
			docs.setDelFlag(Constants.DEL_FLAG_DEL);
			docsService.updateDocs(docs);
			map.put(Constants.MSG, "删除成功");
			map.put(Constants.SUCCESS, true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put(Constants.SUCCESS, false);
			map.put(Constants.MSG, "系统异常");
		}
        return map;
	}

}
