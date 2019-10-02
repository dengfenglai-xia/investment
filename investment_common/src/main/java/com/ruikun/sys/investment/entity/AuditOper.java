package com.ruikun.sys.investment.entity;

import lombok.Data;

import java.util.Date;


/**
 * @Description: 属性
 * @author: xiachunyu
 * @date: 2019-07-09
 */
@Data
public class AuditOper{
	 
	/**
	 * 主键ID
	 */						
	private Long id;

	/**
	 * 代办事项ID
	 */
	private Integer matterId;

	/**
	 * 审核项
	 */						
	private String sign;

	/**
	 * 类型
	 */
	private String operType;

	/**
	 * 业务编号
	 */						
	private String code;	
		
	/**
	 * 版本号
	 */						
	private Integer version;	
		
	/**
	 * 审核人ID
	 */						
	private Integer userId;

	/**
	 * 审核人
	 */
	private String auditor;

	/**
	 * 审核人职位
	 */
	private String jobName;

	/**
	 * 审核顺序
	 */						
	private Integer sort;	
		
	/**
	 * 审核意见
	 */						
	private String auditOpinion;	
		
	/**
	 * 审核时间
	 */						
	private String auditDate;	
		
	/**
	 * 审核状态（1 待审核 ，2 通过 ，3 驳回）
	 */						
	private String auditState;	
		
	/**
	 * 是否点亮（1 未点亮，2 点亮）
	 */						
	private String lightenUp;

	/**
	 * 客户名称
	 */
	private String customerName;

	/**
	 * 提交人
	 */
	private String submitter;

	/**
	 * 提交时间
	 */
	private Date subtime;

	/**
	 * 提交时间
	 */
	private String subtimeStr;

	/**
	 * 处理状态（1 待处理，2 已处理）
	 */
	private String dealState;

}
