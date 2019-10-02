package com.ruikun.sys.investment.entity;

import lombok.Data;


/**
 * @Description: 基础数据表属性
 * @author: xiachunyu
 * @date: 2019-06-04
 */
@Data
public class BasicData extends BaseEntity{

	/**
	 * 数据ID
	 */						
	private Integer bdId;	
		
	/**
	 * 数据名称
	 */						
	private String bdName;	
		
	/**
	 * 数据类型
	 */						
	private String bdType;

	public BasicData(){
		super();
	}

	public BasicData(String bdType){
		this.bdType = bdType;
	}

	public BasicData(String bdName,String bdType){
		this.bdName = bdName;
		this.bdType = bdType;
	}
}
