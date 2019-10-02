package com.ruikun.sys.investment.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 临时客户
 */
@Data
public class CustomerTemporary extends BaseEntity{

    /**
     * 客户ID
     */
    private Integer temporaryId;

    /**
     * 客户类型（1 公司，2 个人）
     */
    private String customerType;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 管理公司编号
     */
    private String companyCode;

    /**
     * 管理公司
     */
    private String company;

    /**
     * 身份证号
     */
    private String contactsCard;

    /**
     * 客户状态（1 正式客户，2 历史历史）
     */
    private String state;

    /**
     * 联系人
     */
    private String contacts;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 工作单位
     */
    private String workUnit;

    /**
     * 所属行业（基础信息维护）
     */
    private String industry;

    /**
     * 渠道来源（基础信息维护）
     */
    private String channelSource;

    /**
     * 渠道名称
     */
    private String sourceName;

    /**
     * 通讯地址
     */
    private String linkAddress;

    /**
     * 首次接洽时间
     **/
    private String firstMeetTime;

    /**
     * 房间ID
     **/
    private Integer roomId;

    /**
     * 需求房间号集合
     **/
    private String roomNos;

    /**
     *需求结束时间
     **/
    private String demandEndTime;
    /**
     *需求车位最大
     **/
    private Integer demandParkMax;
    /**
     *需求车位最小
     **/
    private Integer demandParkMin;
    /**
     *需求工位最小
     **/
    private Integer demandStationMin;
    /**
     *需求工位最大
     **/
    private Integer demandStationMax;
    /**
     *需求面积最大
     **/
    private BigDecimal demandAreaMax;
    /**
     *需求面积最小
     **/
    private BigDecimal demandAreaMin;
    /**
     *需求开始时间
     **/
    private String demandStartTime;
    /**
     * 总数
     **/
    private int count;
    /**
     * 跟进人
     **/
    private String followUserName;
    /**
     * 查询参数
     **/
    private String param;
    /**
     * 来访开始时间
     **/
    private String startDate;
    /**
     * 来访结束时间
     **/
    private String endDate;
    /**
     * 跟进记录
     **/
    private FollowHistory followHistory;
    /**
     * 跟进提醒
     **/
    private FollowNotice followNotice;
    /**
     * 房间列表
     **/
    private List<CustomerTemporaryRoom> customerTemporaryRoomList;
    /**
     * 跟进记录列表
     **/
    private List<FollowHistory> followHistoryList;
}
