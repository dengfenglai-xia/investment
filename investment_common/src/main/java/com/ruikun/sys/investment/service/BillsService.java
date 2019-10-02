package com.ruikun.sys.investment.service;

import com.ruikun.sys.investment.entity.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Description: 账单表相关操作
 * @author: xiachunyu
 * @date: 2019-07-03
 */
public interface BillsService {

	/**
	 * @Description: 生成账单数据
	 * @author: xiachunyu
	 * @date: 2019-07-03
	 */
	public void generateBills(Bills bills);

	/**
	 * @Description: 获取结算账单汇总数据
	 * @author: xiachunyu
	 * @date: 2019-07-05
	 */
	public List<BillsSum> queryBillsSumList(Bills bills);

	/**
	 * @Description: 获取结算账单汇总数据详情
	 * @author: xiachunyu
	 * @date: 2019-07-05
	 */
	public BillsSum queryBillsSumDetail(Bills bills);

	/**
	 * @Description: 获取结算账单汇总数据
	 * @author: xiachunyu
	 * @date: 2019-07-05
	 */
	public List<BillsSum> queryFinanceBillsList(Bills bills);

	/**
	 * @Description: 查询账单表信息列表
	 * @author: xiachunyu
	 * @date: 2019-07-03
	 */
	public List<Bills> queryBillsList(Bills bills);

	/**
	 * @Description: 查询可结转账单
	 * @author: xiachunyu
	 * @date: 2019-07-03
	 */
	public List<Bills> queryTransferBillsList(Bills bills);

	/**
	 * @Description: 确认账单
	 * @author: xiachunyu
	 * @date: 2019-07-03
	 */
	public void confirmBills(BillsSum billsSum);

	/**
	 * @Description: 账单结算
	 * @author: xiachunyu
	 * @date: 2019-07-03
	 */
	public void settleBills(SettelSum settelSum);

	/**
	 * @Description: 账单结转
	 * @author: xiachunyu
	 * @date: 2019-07-03
	 */
	public void transferBills(Transfer transfer);

	/**
	 * @Description: 通过主键查询账单表信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-03
	 */
	public Bills queryBillsDetailByPrimarykey(Integer billId);
		
	/**
	 * @Description: 查询账单表信息详情
	 * @author: xiachunyu
	 * @date: 2019-07-03
	 */
	public Bills queryBillsDetail(Bills bills);
	
	/**
	 * @Description: 新增账单表信息
	 * @author: xiachunyu
	 * @date: 2019-07-03
	 */
	public void insertBills(Bills bills) ;

	/**
	 * @Description: 修改账单表信息
	 * @author: xiachunyu
	 * @date: 2019-07-03
	 */
	public void updateBills(Bills bills) ;

	/**
	 * @Description: 批量确认账单
	 * @author: xiachunyu
	 * @date: 2019-07-03
	 */
	public void updateConfirmBillsBatch(Bills bills) ;

	/**
	 * @Description: 批量结算账单
	 * @author: xiachunyu
	 * @date: 2019-07-03
	 */
	public void updateSettleBillsBatch(Bills bills) ;

	/**
	 * @Description: 批量生成通知单
	 * @author: xiachunyu
	 * @date: 2019-07-03
	 */
	public String generateBillsNoticeBatch(String[] billCodeArr) throws Exception;

	/**
	 * @Description: 删除账单表信息
	 * @author: xiachunyu
	 * @date: 2019-07-03
	 */
	public void deleteBills(Bills bills) ;

	public int queryLoseCustomer(Bills bills) ;

	public BigDecimal queryResidualAmt(Bills bills) ;

	public List<Deposit> queryDepositBillsInfo(Deposit deposit) ;

}
