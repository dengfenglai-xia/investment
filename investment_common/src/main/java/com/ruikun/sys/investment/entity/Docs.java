package com.ruikun.sys.investment.entity;

import lombok.Data;


/**
 * @Description: 文档表属性
 * @author: xiachunyu
 * @date: 2019-06-14
 */
@Data
public class Docs extends BaseEntity{
	 
	/**
	 * 文档ID
	 */						
	private Long docId;	
		
	/**
	 * 关联ID（即相关业务表ID）
	 */						
	private Integer linkId;	
		
	/**
	 * 原文档名称
	 */						
	private String docOriginalName;	
		
	/**
	 * 新文档名称
	 */						
	private String docName;	
		
	/**
	 * 文档类型
	 */						
	private String docType;	
		
	/**
	 * 文档路径
	 */						
	private String docPath;	
		
	/**
	 * 文档大小（KB）
	 */						
	private Double docSize;

	/**
	 * 上传人
	 */
	private String uploader;

	public Docs(){
		super();
	}

	public Docs(Integer linkId,String docType){
		this.linkId = linkId;
		this.docType = docType;
	}

}
