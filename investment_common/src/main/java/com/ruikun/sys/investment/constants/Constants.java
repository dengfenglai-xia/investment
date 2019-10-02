package com.ruikun.sys.investment.constants;

import com.google.common.collect.Maps;
import java.util.LinkedHashMap;
import java.util.Map;

public class Constants {

	/**
	 * 用户SESSION KEY
	 */
	public static final String SESSION_USER_KEY = "SESSION_USER_KEY";

	/**
	 * 图片验证码
	 */
	public static final String IMG_CODE="imgCode";

	/**
	 * 短信验证码
	 */
	public static final String VER_CODE="verCode";

	/**
	 * 接收短信的手机号
	 */
	public static final String VER_PHONE="verPhone";

	/**
	 * 操作标识： （true 代表成功，false 代表失败）
	 */
	public static final String SUCCESS = "success";

	/**
	 * 提示信息标识
	 */
	public static final String MSG = "msg";

	/**
	 * 返回数据KEY
	 */
	public static final String RESULT_DATA="data";

	/**
	 * 数据状态 0 正常，1 删除
	 */
	public static final String DEL_FLAG_NORMAL="0";
	public static final String DEL_FLAG_DEL="1";

	/**
	 * 用户类型 1 系统管理员，2 企业管理员，3 企业员工
	 */
	public static final String USER_TYPE_SYSTEM_ADMIN = "1";
	public static final String USER_TYPE_COMPANY_ADMIN = "2";
	public static final String USER_TYPE_COMPANY_STAFF = "3";

	/**
	 * 用户状态 0 正常，1 失效
	 */
	public static final String USER_STATE_NORMAL = "1";
	public static final String USER_STATE_INVALID = "2";
	public static final Map USER_STATE_MAP = Maps.newHashMap();
	static {
		USER_STATE_MAP.put(USER_STATE_NORMAL, "正常");
		USER_STATE_MAP.put(USER_STATE_INVALID, "失效");
	}

	/**
	 * 是否可见 1 可见，2 不可见
	 */
	public static final String LIGHTEN_UP_YES  = "1";
	public static final String LIGHTEN_UP_NO  = "2";

	/**
	 * 账号类型 1 试用，2 正式
	 */
	public static final String ACCOUNT_TYPE_TRY = "1";
	public static final String ACCOUNT_TYPE_FORMAL = "2";
	public static final Map ACCOUNT_TYPE_MAP = Maps.newHashMap();
	static {
		ACCOUNT_TYPE_MAP.put(ACCOUNT_TYPE_TRY, "试用");
		ACCOUNT_TYPE_MAP.put(ACCOUNT_TYPE_FORMAL, "正式");
	}

	/**
	 * 操作标识 1 保存，2 提交
	 */
	public static final String OPER_FLAG_SAVE = "1";
	public static final String OPER_FLAG_COMMIT = "2";


	/**
	 * 房源装修 1 毛坯，2 简装，3 精装
	 */
	public static final String ROOM_DECORATE_NOTHING  = "1";
	public static final String ROOM_DECORATE_SIMPLE  = "2";
	public static final String ROOM_DECORATE_SENIOR  = "3";
	public static final Map ROOM_DECORATE_MAP = Maps.newHashMap();
	static {
		ROOM_DECORATE_MAP.put(ROOM_DECORATE_NOTHING, "毛坯");
		ROOM_DECORATE_MAP.put(ROOM_DECORATE_SIMPLE, "简装");
		ROOM_DECORATE_MAP.put(ROOM_DECORATE_SENIOR, "精装");
	}

	/**
	 * 出租状态 0 不对外招租, 1 在租，2 即将到期，3 空置，4 到期未处理
	 */
	public static final String RENTOUT_STATE_INIT  = "0";
	public static final String RENTOUT_STATE_RENT_ING  = "1";
	public static final String RENTOUT_STATE_SOON_EXPIRE  = "2";
	public static final String RENTOUT_STATE_VACANT_ING  = "3";
	public static final String RENTOUT_STATE_REACH_EXCEED  = "4";
	public static final Map RENTOUT_STATE_MAP = Maps.newHashMap();
	static {
		RENTOUT_STATE_MAP.put(RENTOUT_STATE_INIT, "不对外招租");
		RENTOUT_STATE_MAP.put(RENTOUT_STATE_RENT_ING, "在租");
		RENTOUT_STATE_MAP.put(RENTOUT_STATE_SOON_EXPIRE, "即将到期");
		RENTOUT_STATE_MAP.put(RENTOUT_STATE_VACANT_ING, "空置");
		RENTOUT_STATE_MAP.put(RENTOUT_STATE_REACH_EXCEED , "到期未处理");
	}

	/**
	 * 是否对外招租 1 是，2 否
	 */
	public static final String WHETHER_OPEN_YES  = "1";
	public static final String WHETHER_OPEN_NO  = "2";
	public static final Map WHETHER_OPEN_MAP = Maps.newHashMap();
	static {
		WHETHER_OPEN_MAP.put(WHETHER_OPEN_YES, "是");
		WHETHER_OPEN_MAP.put(WHETHER_OPEN_NO, "否");
	}

	/**
	 * 是否临街 1 是，2 否
	 */
	public static final String ROOM_WHETHER_STREET_YES  = "1";
	public static final String ROOM_WHETHER_STREET_NO  = "2";
	public static final Map ROOM_WHETHER_STREET_MAP = Maps.newHashMap();
	static {
		ROOM_WHETHER_STREET_MAP.put(ROOM_WHETHER_STREET_YES, "是");
		ROOM_WHETHER_STREET_MAP.put(ROOM_WHETHER_STREET_NO, "否");
	}

	/**
	 * 客户类型 1 公司，2 个人
	 */
	public static final String CUSTOMER_TYPE_COMPANY  = "1";
	public static final String CUSTOMER_TYPE_PERSONAL  = "2";
	public static final Map CUSTOMER_TYPE_MAP = Maps.newHashMap();
	static {
		CUSTOMER_TYPE_MAP.put(CUSTOMER_TYPE_COMPANY, "公司");
		CUSTOMER_TYPE_MAP.put(CUSTOMER_TYPE_PERSONAL, "个人");
	}

	/**
	 * 客户状态 1 正式，2 历史
	 */
	public static final String CUSTOMER_STATE_NOW  = "1";
	public static final String CUSTOMER_STATE_HISTORY  = "2";
	public static final Map CUSTOMER_STATE_MAP = Maps.newHashMap();
	static {
		CUSTOMER_STATE_MAP.put(CUSTOMER_STATE_NOW, "正式客户");
		CUSTOMER_STATE_MAP.put(CUSTOMER_STATE_HISTORY, "历史客户");
	}

	/**
	 * 合同操作类型-新建
	 */
	public static final String CONTRACT_OPER_TYPE_NEW = "new";
	/**
	 * 合同操作类型-变更
	 */
	public static final String CONTRACT_OPER_TYPE_CHANGE = "change";
	/**
	 * 合同操作类型-退租
	 */
	public static final String CONTRACT_OPER_TYPE_RETURN = "return";
	/**
	 * 合同操作类型-续租
	 */
	public static final String CONTRACT_OPER_TYPE_RENEW = "renew";

	/**
	 * 合同状态（0 初始 1 正式，2 归档 ）
	 */
	public static final String CONTRACT_STATE_INIT = "0";
	public static final String CONTRACT_STATE_FORMAL = "1";
	public static final String CONTRACT_STATE_FILE = "2";

	/**
	 * 正式合同状态（1 待执行，2 执行中，3 已变更，4 已退租，5 已续租，6 已到期）;
	 * 归档合同状态（1 变更归档 ，2 退租归档 ，3 续租归档）
	 */
	public static final String CONTRACT_STATE_TWO_1 = "1";
	public static final String CONTRACT_STATE_TWO_2 = "2";
	public static final String CONTRACT_STATE_TWO_3 = "3";
	public static final String CONTRACT_STATE_TWO_4 = "4";
	public static final String CONTRACT_STATE_TWO_5 = "5";
	public static final String CONTRACT_STATE_TWO_6 = "6";

	/**
	 * 合同到期状态（1 正常，2 即将到期，3 已到期）
	 */
	public static final String CONTRACT_EXPIRE_STATE_NO = "1";
	/**
	 * 合同到期状态（1 正常，2 即将到期，3 已到期）
	 */
	public static final String CONTRACT_EXPIRE_STATE_SOON = "2";
	/**
	 * 合同到期状态（1 正常，2 即将到期，3 已到期）
	 */
	public static final String CONTRACT_EXPIRE_STATE_YES = "3";
	public static final Map CONTRACT_EXPIRE_STATE_MAP = Maps.newHashMap();
	static {
		CONTRACT_EXPIRE_STATE_MAP.put(CONTRACT_EXPIRE_STATE_NO, "正常");
		CONTRACT_EXPIRE_STATE_MAP.put(CONTRACT_EXPIRE_STATE_SOON, "即将到期");
		CONTRACT_EXPIRE_STATE_MAP.put(CONTRACT_EXPIRE_STATE_YES, "已到期");
	}


	/**
	 * 审核状态   0 初始
	 */
	public  static  final String AUDIT_STATE_INIT = "0";
	/**
	 * 审核状态   1 审核中
	 */
	public  static  final String AUDIT_STATE_ING = "1";
	/**
	 * 审核状态   2 通过
	 */
	public  static  final String AUDIT_STATE_PASS = "2";
	/**
	 * 审核状态   3 驳回
	 */
	public  static  final String AUDIT_STATE_NOPASS = "3";

	/**
	 * 租金计价单位（1 元/㎡·天，2 元/㎡·月，3 元/天，4 元/月，5 元/年）
	 */
	public static final String CHARGE_UNIT_1 = "1";
	public static final String CHARGE_UNIT_2 = "2";
	public static final String CHARGE_UNIT_3 = "3";
	public static final String CHARGE_UNIT_4 = "4";
	public static final String CHARGE_UNIT_5 = "5";
	public static final Map CHARGE_UNIT_MAP = Maps.newHashMap();
	static {
		CHARGE_UNIT_MAP.put(CHARGE_UNIT_1, "元/㎡·天");
		CHARGE_UNIT_MAP.put(CHARGE_UNIT_2, "元/㎡·月");
		CHARGE_UNIT_MAP.put(CHARGE_UNIT_3, "元/天");
		CHARGE_UNIT_MAP.put(CHARGE_UNIT_4, "元/月");
		CHARGE_UNIT_MAP.put(CHARGE_UNIT_5, "元/年");
	}

	/**
	 * 工位租金计价单位（1 元/㎡·天，2 元/㎡·月，3 元/天，4 元/月，5 元/年）
	 */
	public static final String WORKPLACE_CHARGE_UNIT_1 = "1";
	public static final String WORKPLACE_CHARGE_UNIT_2 = "2";
	public static final String WORKPLACE_CHARGE_UNIT_3 = "3";
	public static final String WORKPLACE_CHARGE_UNIT_4 = "4";
	public static final String WORKPLACE_CHARGE_UNIT_5 = "5";
	public static final Map WORKPLACE_CHARGE_UNIT_MAP = Maps.newHashMap();
	static {
		WORKPLACE_CHARGE_UNIT_MAP.put(WORKPLACE_CHARGE_UNIT_1, "元/个·天");
		WORKPLACE_CHARGE_UNIT_MAP.put(WORKPLACE_CHARGE_UNIT_2, "元/个·月");
		WORKPLACE_CHARGE_UNIT_MAP.put(WORKPLACE_CHARGE_UNIT_3, "元/天");
		WORKPLACE_CHARGE_UNIT_MAP.put(WORKPLACE_CHARGE_UNIT_4, "元/月");
		WORKPLACE_CHARGE_UNIT_MAP.put(WORKPLACE_CHARGE_UNIT_5, "元/年");
	}

    /**
     * 月租金计算方式（1 按固定租金 ，2 按实际天数）
     */
    public static final String MONTH_RENT_COUNT_TYPE_1 = "1";
    public static final String MONTH_RENT_COUNT_TYPE_2 = "2";

	/**
	 * 保证金计价单位（1 元，2 月）
	 */
	public static final String DEPOSIT_CHARGE_UNIT_1 = "1";
	public static final String DEPOSIT_CHARGE_UNIT_2 = "2";
	public static final Map DEPOSIT_CHARGE_UNIT_MAP = Maps.newHashMap();
	static {
		DEPOSIT_CHARGE_UNIT_MAP.put(DEPOSIT_CHARGE_UNIT_1, "元");
		DEPOSIT_CHARGE_UNIT_MAP.put(DEPOSIT_CHARGE_UNIT_2, "月");
	}

	/**
	 * 提前付款日类型（1 自然日，2 工作日 ，3 固定日期，4 自然月）
	 */
	public static final String ADVANCE_PAY_TYPE_NATURAL = "1";
	public static final String ADVANCE_PAY_TYPE_WORK = "2";
	public static final String ADVANCE_PAY_TYPE_FIXED = "3";
	public static final String ADVANCE_PAY_TYPE_MONTH = "4";
	public static final Map ADVANCE_PAY_TYPE_MAP = Maps.newHashMap();
	static {
		ADVANCE_PAY_TYPE_MAP.put(ADVANCE_PAY_TYPE_NATURAL, "自然日");
		ADVANCE_PAY_TYPE_MAP.put(ADVANCE_PAY_TYPE_WORK, "工作日");
		ADVANCE_PAY_TYPE_MAP.put(ADVANCE_PAY_TYPE_FIXED, "固定日期/号");
		ADVANCE_PAY_TYPE_MAP.put(ADVANCE_PAY_TYPE_MONTH, "自然月");
	}

	/**
	 * 合同是否生效（1 未生效，2 已生效）
	 */
	public static final String CONTRACT_EFFECTIVE_STATE_NO = "1";
	public static final String CONTRACT_EFFECTIVE_STATE_YES = "2";
	public static final Map CONTRACT_EFFECTIVE_STATE_MAP = Maps.newHashMap();
	static {
		CONTRACT_EFFECTIVE_STATE_MAP.put(CONTRACT_EFFECTIVE_STATE_NO, "未生效");
		CONTRACT_EFFECTIVE_STATE_MAP.put(CONTRACT_EFFECTIVE_STATE_YES, "已生效");
	}

	/**
	 * 计划账单是否已出账单（1 未出，2 已出）
	 */
	public static final String BILL_WHETHER_OUT_NO = "1";
	public static final String BILL_WHETHER_OUT_YES = "2";
	public static final Map BILL_WHETHER_OUT_MAP = Maps.newHashMap();
	static {
		BILL_WHETHER_OUT_MAP.put(BILL_WHETHER_OUT_NO, "未出");
		BILL_WHETHER_OUT_MAP.put(BILL_WHETHER_OUT_YES, "已出");
	}

	/**
	 * 账单类型（1 收款，2 付款）
	 */
	public static final String BILL_TYPE_RECEIVABLES = "1";
	public static final String BILL_TYPE_PAYMENT = "2";
	public static final Map BILL_TYPE_MAP = Maps.newHashMap();
	static {
		BILL_TYPE_MAP.put(BILL_TYPE_RECEIVABLES, "收款");
		BILL_TYPE_MAP.put(BILL_TYPE_PAYMENT, "付款");
	}

	/**
	 * 账单确认状态（1 未确认，2 已确认）
	 */
	public static final String BILL_CONFIRM_STATE_NO = "1";
	public static final String BILL_CONFIRM_STATE_OK = "2";
	public static final Map BILL_CONFIRM_STATE_MAP = Maps.newHashMap();
	static {
		BILL_CONFIRM_STATE_MAP.put(BILL_CONFIRM_STATE_NO, "未确认");
		BILL_CONFIRM_STATE_MAP.put(BILL_CONFIRM_STATE_OK, "已确认");
	}

	/**
	 * 账单处理状态（1 未收/付，2 部分收/付，3 已收/付
	 */
	public static final String BILL_DEAL_STATE_NO = "1";
	public static final String BILL_DEAL_STATE_PART = "2";
	public static final String BILL_DEAL_STATE_YES = "3";
	public static final Map BILL_DEAL_STATE_MAP = Maps.newHashMap();
	static {
		BILL_DEAL_STATE_MAP.put(BILL_DEAL_STATE_NO, "未收/付");
		BILL_DEAL_STATE_MAP.put(BILL_DEAL_STATE_PART, "部分收/付");
		BILL_DEAL_STATE_MAP.put(BILL_DEAL_STATE_YES, "已收/付");
	}

	/**
	 * 账单性质（1 正式，2 非正式）
	 */
	public static final String BILL_PROPERTY_FORMAL = "1";
	public static final String BILL_PROPERTY_TEMP = "2";

	/**
	 * 支付方式（1 现金，2 银行转账（对公），3 微信/支付宝，4 其他，5 银行转账（对私））
	 */
	public static final String SETTLE_TYPE_CASH = "1";
	public static final String SETTLE_TYPE_REMIT = "2 ";
	public static final String SETTLE_TYPE_AWPAY = "3";
	public static final String SETTLE_TYPE_OTHER = "4";
	public static final String SETTLE_TYPE_REMIT_PRIVATE = "5";

	/**
	 * 基础数据-项目业态
	 */
	public static final String DATA_TYPE_BUSINESSMODE = "businessMode";
	/**
	 * 基础数据-渠道来源
	 */
	public static final String DATA_TYPE_CHANNELSOURCE = "channelSource";
	/**
	 * 基础数据-所属行业
	 */
	public static final String DATA_TYPE_INDUSTRY = "industry";
	/**
	 * 基础数据-租期
	 */
	public static final String DATA_TYPE_LEASETERM = "leaseTerm";
	/**
	 * 基础数据-楼宇朝向
	 */
	public static final String DATA_TYPE_ORIENTATION = "orientation";
	/**
	 * 基础数据-配套设施
	 */
	public static final String DATA_TYPE_FACILITIES = "facilities";
	/**
	 * 基础数据-房源标签
	 */
	public static final String DATA_TYPE_TAGS = "tags";
	/**
	 * 基础数据-跟进方式
	 */
	public static final String DATA_TYPE_FOLLOW_TYPE = "followType";


	/**
	 * 编号前缀
	 */
	public static final String ROOM_CONTRACT_CODE = "FY";
	public static final String CAR_PLACE_CONTRACT_CODE = "CW";
	public static final String WORK_PLACE_CONTRACT_CODE = "GW";
	public static final String RECEIPT_BILL_SK_CODE = "SK";
	public static final String RECEIPT_BILL_FK_CODE = "FK";
	public static final String COMPANY_CODE = "GS";

	/**
	 * 文档类型-项目
	 */
	public static final String DOC_TYPE_PROJECT = "project";
	/**
	 * 文档类型-楼宇
	 */
	public static final String DOC_TYPE_BUILDING = "building";
	/**
	 * 文档类型-房源
	 */
	public static final String DOC_TYPE_ROOM = "room";
	/**
	 * 文档类型-工位
	 */
	public static final String DOC_TYPE_WORKPLACE = "workplace";
	/**
	 * 文档类型-客户
	 */
	public static final String DOC_TYPE_CUSTOMER = "customer";
	/**
	 * 文档类型-合同
	 */
	public static final String DOC_TYPE_CONTRACT = "contract";
	/**
	 * 文档类型-渠道
	 */
	public static final String DOC_TYPE_CHANNEL = "channel";
	/**
	 * 文档类型-临时客户
	 */
	public static final String DOC_TYPE_TEMPORARY = "temporary";

	/**
	 * 费用名称-租金
	 */
	public static final String COST_NAME_RENT = "租金";

	/**
	 * 费用名称-租金保证金
	 */
	public static final String COST_NAME_DEPOSIT = "租赁押金";
	/**
	 * 费用名称-物业费押金
	 */
	public static final String COST_NAME_WY_DEPOSIT = "物业押金";
	/**
	 * 费用名称-履约保证金
	 */
	public static final String COST_NAME_BREACHAMT = "履约保证金";
	/**
	 * 费用名称-租房协议押金
	 */
	public static final String COST_NAME_AGREEMENT_PLEDGE = "租房协议押金";


	/**
	 * 周期性费用名称-物业费
	 */
	public static final String COST_NAME_PROPERTY = "物业费";
	/**
	 * 周期性费用名称-清洁费
	 */
	public static final String COST_NAME_CLEAR = "清洁费";
	/**
	 * 周期性费用名称-车位费
	 */
	public static final String COST_NAME_CARPLACE = "车位费";
	/**
	 * 周期性费用名称-能源费
	 */
	public static final String COST_NAME_ENERGY = "能源费";
	/**
	 * 周期性费用名称MAP
	 */
	public static final Map CYCLE_COST_NAME_MAP = Maps.newHashMap();
	static {
		CYCLE_COST_NAME_MAP.put(COST_NAME_RENT, "租金");
		CYCLE_COST_NAME_MAP.put(COST_NAME_PROPERTY, "物业费");
		CYCLE_COST_NAME_MAP.put(COST_NAME_CLEAR, "清洁费");
		CYCLE_COST_NAME_MAP.put(COST_NAME_CARPLACE, "车位费");
		CYCLE_COST_NAME_MAP.put(COST_NAME_ENERGY, "能源费");
	}




	/**
	 * 费用名称-类别
	 */
	public static final String COST_NAME_PLEDGE = "押金";

	/**
	 * 合同类型-房源合同
	 */
	public static final String CONTRACT_TYPE_ROOM = "房源合同";
	/**
	 * 合同类型-工位合同
	 */
	public static final String CONTRACT_TYPE_WORKPLACE = "工位合同";
	/**
	 * 合同类型-房源协议
	 */
	public static final String CONTRACT_TYPE_ROOM_AGREEMENT = "房源协议";

	/**
	 * 1 初次接触 2 潜在客户 3 意向客户 4 成交客户 5 流失客户
	 **/
	public static final String CUSTOMER_STATE_FISRT = "1";
	/**
	 * 1 初次接触 2 潜在客户 3 意向客户 4 成交客户 5 流失客户
	 **/
	public static final String CUSTOMER_STATE_POTENTIAL = "2";
	/**
	 * 1 初次接触 2 潜在客户 3 意向客户 4 成交客户 5 流失客户
	 **/
	public static final String CUSTOMER_STATE_INTENTION = "3";
	/**
	 * 1 初次接触 2 潜在客户 3 意向客户 4 成交客户 5 流失客户
	 **/
	public static final String CUSTOMER_STATE_DEAL = "4";
	/**
	 * 1 初次接触 2 潜在客户 3 意向客户 4 成交客户 5 流失客户
	 **/
	public static final String CUSTOMER_STATE_LOSS = "5";
	/**
	 * 临时
	 * 客户状态
	 **/
	public static Map<String,String> MAP_CUSTOMER_STATE = Maps.newHashMap();
	static{
		MAP_CUSTOMER_STATE.put(CUSTOMER_STATE_FISRT,"初次接触");
		MAP_CUSTOMER_STATE.put(CUSTOMER_STATE_POTENTIAL,"潜在客户");
		MAP_CUSTOMER_STATE.put(CUSTOMER_STATE_INTENTION,"意向客户");
		MAP_CUSTOMER_STATE.put(CUSTOMER_STATE_DEAL,"成交客户");
		MAP_CUSTOMER_STATE.put(CUSTOMER_STATE_LOSS,"流失客户");
	}


	/**
	 * 合同配置项
	 */
	public static LinkedHashMap CONTRACT_FIELD_MAP = Maps.newLinkedHashMap();
	static{
		CONTRACT_FIELD_MAP.put("${companyName}","公司名称（甲方）");
		CONTRACT_FIELD_MAP.put("${buildingName}","楼宇名称");
		CONTRACT_FIELD_MAP.put("${followPerson}","跟进人（甲方）");

		CONTRACT_FIELD_MAP.put("${customerName}","客户名称（乙方）");
		CONTRACT_FIELD_MAP.put("${customerType}","客户名称类型");
		CONTRACT_FIELD_MAP.put("${contacts}","客户联系人（乙方）");
		CONTRACT_FIELD_MAP.put("${phone}","客户联系电话（乙方）");

		CONTRACT_FIELD_MAP.put("${contractType}","合同类别");
		CONTRACT_FIELD_MAP.put("${contractCode}","合同编号");
		CONTRACT_FIELD_MAP.put("${signDate}","签订日期");
		CONTRACT_FIELD_MAP.put("${startDate}","生效时间");
		CONTRACT_FIELD_MAP.put("${endDate}","截止时间");
		CONTRACT_FIELD_MAP.put("${roomNos}","签约房间号");
		CONTRACT_FIELD_MAP.put("${totalArea}","签约面积");
		CONTRACT_FIELD_MAP.put("${leaseUnitPrice}","租金-单价");
		CONTRACT_FIELD_MAP.put("${leaseDayPrice}","租金-日租金");
		CONTRACT_FIELD_MAP.put("${leaseMonthPrice}","租金-月租金");
		CONTRACT_FIELD_MAP.put("${leaseAdvancePayDays}","租金-提前付款日");
		CONTRACT_FIELD_MAP.put("${leasePayCycle}","租金-付款周期");
		CONTRACT_FIELD_MAP.put("${leaseFeeLateRatio}","租金-滞纳金比例");
		CONTRACT_FIELD_MAP.put("${deposit}","租赁押金");
		CONTRACT_FIELD_MAP.put("${wyDeposit}","物业押金");
		CONTRACT_FIELD_MAP.put("${breachAmt}","履约保证金");
		CONTRACT_FIELD_MAP.put("${increaseRate}","递增率");
		CONTRACT_FIELD_MAP.put("${freeTime}","免租期");

		CONTRACT_FIELD_MAP.put("${wyRentDateRange}","物业费-付费周期");
		CONTRACT_FIELD_MAP.put("${wyUnitPrice}","物业费-单价");
		CONTRACT_FIELD_MAP.put("${wyDayPrice}","物业费-日租金");
		CONTRACT_FIELD_MAP.put("${wyMonthPrice}","物业费-月租金");
		CONTRACT_FIELD_MAP.put("${wyAdvancePayDays}","物业费-提前付款日");
		CONTRACT_FIELD_MAP.put("${wyPayCycle}","物业费-付款周期");
		CONTRACT_FIELD_MAP.put("${wyFeeLateRatio}","物业费-滞纳金比例");

		CONTRACT_FIELD_MAP.put("${clearRentDateRange}","清洁费-付费周期");
		CONTRACT_FIELD_MAP.put("${clearUnitPrice}","清洁费-单价");
		CONTRACT_FIELD_MAP.put("${clearDayPrice}","清洁费-日租金");
		CONTRACT_FIELD_MAP.put("${clearMonthPrice}","清洁费-月租金");
		CONTRACT_FIELD_MAP.put("${clearAdvancePayDays}","清洁费-提前付款日");
		CONTRACT_FIELD_MAP.put("${clearPayCycle}","清洁费-付款周期");
		CONTRACT_FIELD_MAP.put("${clearFeeLateRatio}","清洁费-滞纳金比例");

		CONTRACT_FIELD_MAP.put("${carRentDateRange}","车位费-付费周期");
		CONTRACT_FIELD_MAP.put("${carUnitPrice}","车位费-单价");
		CONTRACT_FIELD_MAP.put("${carDayPrice}","车位费-日租金");
		CONTRACT_FIELD_MAP.put("${carMonthPrice}","车位费-月租金");
		CONTRACT_FIELD_MAP.put("${carAdvancePayDays}","车位费-提前付款日");
		CONTRACT_FIELD_MAP.put("${carPayCycle}","车位费-付款周期");
		CONTRACT_FIELD_MAP.put("${carFeeLateRatio}","车位费-滞纳金比例");
		CONTRACT_FIELD_MAP.put("${carRentDateRange}","车位费-付费周期");

		CONTRACT_FIELD_MAP.put("${energyRentDateRange}","能源费-付费周期");
		CONTRACT_FIELD_MAP.put("${energyUnitPrice}","能源费-单价");
		CONTRACT_FIELD_MAP.put("${energyDayPrice}","能源费-日租金");
		CONTRACT_FIELD_MAP.put("${energyMonthPrice}","能源费-月租金");
		CONTRACT_FIELD_MAP.put("${energyAdvancePayDays}","能源费-提前付款日");
		CONTRACT_FIELD_MAP.put("${energyPayCycle}","能源费-付款周期");
		CONTRACT_FIELD_MAP.put("${energyFeeLateRatio}","能源费-滞纳金比例");
	}
}


