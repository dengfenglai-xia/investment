package com.ruikun.sys.investment.entity;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class TransferRecord extends BaseEntity{
	 
	private Long transferId;

	private String contractCode;

	private Integer version;

	private String customerName;

	private String buildingName;

	private String roomNos;
		
	private String costName;
		
	private String startDate;
		
	private String endDate;
		
	private BigDecimal transferAmt;

	private String transferTime;

	/**
	 * 转入类型（1 转入，2 转出）
	 */
	private String transferType;

}
