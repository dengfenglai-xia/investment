package com.ruikun.sys.investment.entity;

import lombok.Data;


/**
 * @Description: 用户表属性
 * @author: xiachunyu
 * @date: 2019-06-04
 */
@Data
public class User extends BaseEntity{
	 
	/**
	 * 用户ID
	 */						
	private Integer userId;

	/**
	 * 用户编号
	 */
	private String userCode;
		
	/**
	 * 用户名
	 */						
	private String userName;

	/**
	 * 用户类型
	 */
	private String userType;

	/**
	 * 账号类型
	 */
	private String accountType;

	/**
	 * 密码
	 */						
	private String password;	
		
	/**
	 * 密码盐
	 */						
	private String salt;	
		
	/**
	 * 姓名
	 */						
	private String realName;

	/**
	 * 性别（1 男，2 女）
	 */
	private String sex;

	/**
	 * 部门ID
	 */
	private Integer deptId;

	/**
	 * 部门
	 */
	private String deptName;

	/**
	 * 职务ID
	 */
	private Integer jobId;

	/**
	 * 职务
	 */
	private String jobName;

	/**
	 * 角色ID
	 */
	private Integer roleId;

	/**
	 * 角色
	 */
	private String roleName;

	/**
	 * 联系电话
	 */						
	private String phone;	
		
	/**
	 * 邮箱
	 */						
	private String email;

	/**
	 * 地址
	 */
	private String address;

	/**
	 * 账号状态（1 正常，2 失效）
	 */						
	private String state;

	/**
	 * 截止日期
	 */
	private String endDate;

	/**
	 * 公司编号
	 */
	private String companyCode;

	/**
	 * 公司名称
	 */
	private String company;

	/**
	 * 添加人
	 */
	private String creator;

	/**
	 * 关键字
	 */
	private String keyword;

	public User(){
		super();
	}

	public User(Integer userId){
		this.userId = userId;
	}

}
