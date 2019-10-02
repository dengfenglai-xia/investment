package com.ruikun.sys.investment.service.impl;

import com.ruikun.sys.investment.entity.Docs;
import com.ruikun.sys.investment.mapper.DocsMapper;
import com.ruikun.sys.investment.service.DocsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
 @Service
public class DocsServiceImpl implements DocsService {
	
	@Autowired
	private DocsMapper docsMapper;
	
	/**
	 * @Description: 查询文档表信息
	 * @author: xiachunyu
	 * @date: 2019-06-14
	 */
	public List<Docs> queryDocsList(Docs docs){
		return docsMapper.queryDocsList(docs);
	}
	
	/**
	 * @Description: 通过主键查询文档表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-14
	 */
	public Docs queryDocsDetailByPrimarykey(Long docId){
		return docsMapper.queryDocsDetailByPrimarykey(docId);
	}
	
	/**
	 * @Description: 查询文档表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-14
	 */
	public Docs queryDocsDetail(Docs docs){
		return docsMapper.queryDocsDetail(docs);
	}
	
	/**
	 * @Description: 新增文档表信息
	 * @author: xiachunyu
	 * @date: 2019-06-14
	 */
	public void insertDocs(Docs docs) {
		Date date = new Date(); // 当前时间
		docs.setCreateTime(date); // 创建时间
		docs.setUpdateTime(date); // 更新时间
		docsMapper.insertDocs(docs);
	}
	
	/**
	 * @Description: 修改文档表信息
	 * @author: xiachunyu
	 * @date: 2019-06-14
	 */
	public void updateDocs(Docs docs) {
		docs.setUpdateTime(new Date()); //更新时间
		docsMapper.updateDocs(docs);
	}
	
	/**
	 * @Description: 删除文档表信息
	 * @author: xiachunyu
	 * @date: 2019-06-14
	 */
	public void deleteDocsByPrimarykey(Long docId) {
		docsMapper.deleteDocsByPrimarykey(docId);
	}
	
}
