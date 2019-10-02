package com.ruikun.sys.investment.controller;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.*;
import com.ruikun.sys.investment.service.*;
import com.ruikun.sys.investment.utils.CacheUtils;
import com.ruikun.sys.investment.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @Description: 主页
 * @author: xiachunyu
 * @date: 2019-06-04
 */
@Controller
public class HomeController {
    @Autowired
    private BillsService billsService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private WorkplaceService workplaceService;
    @Autowired
    private CarplaceService carplaceService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private CustomerTemporaryService customerTemporaryService;
    @Autowired
    private FollowNoticeService followNoticeService;

    @RequestMapping("home")
    public String home(Model model) {
        User user = CacheUtils.getUser();
        String companyCode = user.getCompanyCode();
        CustomerTemporary customerTemporary = new CustomerTemporary();
        customerTemporary.setCompanyCode(companyCode);
        int customerTemporaryNum = customerTemporaryService.queryCustomerTemporaryNum(customerTemporary);
        Bills bills = new Bills();
        bills.setCompanyCode(companyCode);
        bills.setBillType(Constants.BILL_TYPE_RECEIVABLES);
        bills.setConfirmState(Constants.BILL_CONFIRM_STATE_OK);
        // 获取未收金额
        BigDecimal unReceivablesAmt = billsService.queryResidualAmt(bills);
        bills.setBillType(Constants.BILL_TYPE_PAYMENT);
        // 获取未付金额
        BigDecimal unPayAmt = billsService.queryResidualAmt(bills);
        // 获取即将到期房源数
        Room room = new Room();
        String[] states = {Constants.RENTOUT_STATE_SOON_EXPIRE};
        room.setStates(states);
        room.setCompanyCode(companyCode);
        int roomCount = roomService.queryRoomCount(room);
        // 获取即将到期工位数
        Workplace workplace = new Workplace();
        workplace.setStates(states);
        workplace.setCompanyCode(companyCode);
        int workplaceCount = workplaceService.queryWorkplaceCount(workplace);
        // 获取即将车位数
        Carplace carplace = new Carplace();
        carplace.setStates(states);
        carplace.setCompanyCode(companyCode);
        int carplaceCount = carplaceService.queryCarplaceCount(carplace);
        // 到期合同列表
        Contract contract = new Contract();
        contract.setCompanyCode(companyCode);
        contract.setExpireState(Constants.CONTRACT_EXPIRE_STATE_SOON);
        List<Contract> warnContractList = contractService.queryContractList(contract);

        String today = DateTimeUtil.getFormatDate();
        FollowNotice followNotice = new FollowNotice();
        followNotice.setCreateUserId(user.getUserId());
        followNotice.setToday(today);
        String dates = followNoticeService.queryFollowDateList(followNotice);
        model.addAttribute("customerTemporaryNum", customerTemporaryNum);
        model.addAttribute("contractNum", warnContractList.size());
        model.addAttribute("unReceivablesAmt", unReceivablesAmt);
        model.addAttribute("unPayAmt", unPayAmt);
        model.addAttribute("roomCount", roomCount);
        model.addAttribute("workplaceCount", workplaceCount);
        model.addAttribute("carplaceCount", carplaceCount);
        model.addAttribute("warnContractList", warnContractList);
        model.addAttribute("cerrentDate", DateTimeUtil.getFormatDate());
        model.addAttribute("dates", dates);
        return "home";
    }

    @RequestMapping("settleListStatistics")
    @ResponseBody
    public Map settleListStatistics() {
        String date = DateTimeUtil.getFormatDate();
        date = date.substring(0, 8);
        Map map = Maps.newHashMap();
        User user = CacheUtils.getUser();
        Bills bills = new Bills();
        bills.setConfirmState(Constants.BILL_CONFIRM_STATE_OK); // 已确认
        bills.setCompanyCode(user.getCompanyCode());
        List<BillsSum> billsSums = billsService.queryFinanceBillsList(bills);
        Calendar c = Calendar.getInstance();
        int n = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        List<Cap> dayList = Lists.newArrayList();
        for (int i = 0; i < n; i++) {
            Cap cap = new Cap();
            int month = i + 1;
            cap.setMonth(month);
            int cnum = 0;
            int pnum = 0;
            for (int j = 0; j < billsSums.size(); j++) {
                BillsSum bs = billsSums.get(j);
                String shouldDealDate = bs.getShouldDealDate();
                BigDecimal finishAmt = bs.getFinishAmt();
                BigDecimal residualAmt = bs.getResidualAmt();
                String billType = bs.getBillType();
                String tempDate = "";
                if (String.valueOf(month).length() == 1) {
                    tempDate = date + "0" + month;
                } else {
                    tempDate = date + month;
                }
                if (tempDate.equals(shouldDealDate) &&
                        (finishAmt.compareTo(BigDecimal.ZERO) == 0
                                || (finishAmt.compareTo(BigDecimal.ZERO) == 1 && residualAmt.compareTo(BigDecimal.ZERO) == 1))) {
                    if (billType.equals(Constants.BILL_TYPE_RECEIVABLES)) {
                        cnum++;
                    } else {
                        pnum++;
                    }
                }
            }
            cap.setCnum(cnum);
            cap.setPnum(pnum);
            dayList.add(cap);
        }
        map.put("dayList", dayList);
        return map;
    }

    /**
     * 获取日历安排
     *
     * @param date
     * @return
     */
    @RequestMapping("getDates")
    @ResponseBody
    public String getDates(String date) {
        User user = CacheUtils.getUser();
        FollowNotice followNotice = new FollowNotice();
        followNotice.setCreateUserId(user.getUserId());
        followNotice.setToday(date);
        String dates = followNoticeService.queryFollowDateList(followNotice);
        return dates;
    }

    @RequestMapping({"index", ""})
    public String index(Model model) {
        User user = CacheUtils.getUser();
        model.addAttribute("user", user);
        return "index";
    }

}
