package com.ruikun.sys.investment.entity;

import lombok.Data;


/**
 * @Description: 事项属性
 * @author: xiachunyu
 * @date: 2019-08-06
 */
@Data
public class Matter extends BaseEntity{
	 
	/**
	 * id
	 */						
	private Integer id;	
		
	/**
	 * 待办类型
	 */						
	private String type;

	/**
	 * 待办业务ID
	 */
	private Long linkId;

	/**
	 * 合同编号
	 */						
	private String contractCode;	
		
	/**
	 * 版本号
	 */						
	private Integer version;	
		
	/**
	 * 客户ID
	 */						
	private Integer customerId;

	/**
	 * 客户名称
	 */
	private String customerName;

	/**
	 * 发件人
	 */						
	private String sender;

	/**
	 * 发件时间
	 */
	private String sendTime;

	/**
	 * 处理人
	 */
	private Integer dealerId;

	/**
	 * 处理状态
	 */
	private String state;

}
