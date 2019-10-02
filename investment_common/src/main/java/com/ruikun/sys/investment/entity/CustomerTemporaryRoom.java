package com.ruikun.sys.investment.entity;

import lombok.Data;


/**
 * @Description: 临时客户_房间表属性
 */
@Data
public class CustomerTemporaryRoom extends BaseEntity{

	/**
	 * 房间ID
	 */
	private Integer id;

	/**
	 * 临时客户id
	 **/
	private Integer temporaryId;

	/**
	 * 签约房间ID
	 */
	private Integer roomId;

	/**
	 * 签约房间号
	 */
	private String roomNo;

	/**
	 * 签约房间状态
	 */
	private String roomState;

	/**
	 * 签约房间面积
	 */
	private Double buildArea;

	/**
	 * 所在楼层ID
	 */
	private Integer floorId;

	/**
	 * 所在楼层
	 */
	private String floorNo;

	/**
	 * 所在楼宇ID
	 */
	private Integer buildingId;

	/**
	 * 所在楼宇
	 */
	private String buildingName;

	/**
	 * 项目ID
	 */
	private Integer projectId;

	/**
	 * 项目名称
	 */
	private String projectName;


}
