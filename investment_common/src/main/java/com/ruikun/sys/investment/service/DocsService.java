package com.ruikun.sys.investment.service;

import java.util.List;
import com.ruikun.sys.investment.entity.Docs;

/**
 * @Description: 文档表相关操作
 * @author: xiachunyu
 * @date: 2019-06-14
 */
public interface DocsService {
	 	
	/**
	 * @Description: 查询文档表信息列表
	 * @author: xiachunyu
	 * @date: 2019-06-14
	 */
	public List<Docs> queryDocsList(Docs docs);
	
	/**
	 * @Description: 通过主键查询文档表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-14
	 */
	public Docs queryDocsDetailByPrimarykey(Long docId);
		
	/**
	 * @Description: 查询文档表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-14
	 */
	public Docs queryDocsDetail(Docs docs);
	
	/**
	 * @Description: 新增文档表信息
	 * @author: xiachunyu
	 * @date: 2019-06-14
	 */
	public void insertDocs(Docs docs) ;
	
	/**
	 * @Description: 修改文档表信息
	 * @author: xiachunyu
	 * @date: 2019-06-14
	 */
	public void updateDocs(Docs docs) ;
	
	/**
	 * @Description: 删除文档表信息
	 * @author: xiachunyu
	 * @date: 2019-06-14
	 */
	public void deleteDocsByPrimarykey(Long docId) ;
	
}
