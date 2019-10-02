package com.ruikun.sys.investment.entity;

import lombok.Data;

import java.math.BigDecimal;


/**
 * @Description: 渠道属性
 * @author: xiachunyu
 * @date: 2019-08-01
 */
@Data
public class Channel extends BaseEntity{
	 
	/**
	 * channel_id
	 */						
	private Integer channelId;

	/**
	 * 公司编号
	 */
	private String companyCode;

	/**
	 * 渠道来源ID
	 */						
	private Integer channelSourceId;	
		
	/**
	 * 渠道来源
	 */						
	private String channelSource;	
		
	/**
	 * 渠道名称
	 */						
	private String channelName;	
		
	/**
	 * 联系人
	 */						
	private String contacts;	
		
	/**
	 * 联系电话
	 */						
	private String phone;	
		
	/**
	 * 省
	 */						
	private String provName;	
		
	/**
	 * 市
	 */						
	private String cityName;	
		
	/**
	 * 区
	 */						
	private String areaName;	
		
	/**
	 * 地址
	 */						
	private String address;	
		
	/**
	 * 邮箱
	 */						
	private String email;

	/**
	 * 起始面积（㎡）
	 */
	private Double startArea;

	/**
	 * 结束面积（㎡）
	 */
	private Double endArea;

	private BigDecimal startAmt;

	private BigDecimal endAmt;

	private int orderNum;

	private int placeNum;

	private BigDecimal totalAmt;

	private Double totalArea;

}
