package com.ruikun.sys.investment.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.ContractRoom;
import com.ruikun.sys.investment.service.ContractRoomService;
import com.ruikun.sys.investment.utils.PagingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Description: 合同_房间表相关操作
 * @author: xiachunyu
 * @date: 2019-06-04
 */
@Controller
public class ContractRoomController {

    @Autowired
    private ContractRoomService contractRoomService;

    /**
     * @Description: 查询合同_房间表信息列表
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("queryContractRoomList")
    @ResponseBody
    public Map queryContractRoomList(HttpServletRequest request, ContractRoom contractRoom) {
        Map map = Maps.newHashMap();
        PagingUtil.setPageParam(request);
        List<ContractRoom> list = contractRoomService.queryContractRoomList(contractRoom);
        map.put(Constants.RESULT_DATA, new PageInfo<ContractRoom>(list));
        return map;
    }

    /**
     * @Description: 通过主键查询合同_房间表信息详情
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("queryContractRoomDetailByPrimarykey/{id}")
    public String queryContractRoomDetailByPrimarykey(@PathVariable("id") Integer id, Model model) {
        ContractRoom contractRoom = contractRoomService.queryContractRoomDetailByPrimarykey(id);
        model.addAttribute("contractRoom", contractRoom);
        return "contractRoomDetail";
    }

    /**
     * @Description: 查询合同_房间表信息详情
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("queryContractRoomDetail}")
    public String queryContractRoomDetail(ContractRoom contractRoom, Model model) {
        contractRoom = contractRoomService.queryContractRoomDetail(contractRoom);
        model.addAttribute("contractRoom", contractRoom);
        return "contractRoomDetail";
    }

    /**
     * @Description: 查询合同_房间表信息详情(JSON)
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("checkRoom")
    @ResponseBody
    public Map checkRoom(ContractRoom contractRoom) {
        Map map = Maps.newHashMap();
        try {
            contractRoom = contractRoomService.queryContractRoomDetail(contractRoom);
            if (contractRoom == null) {
                map.put(Constants.MSG, "允许签约");
                map.put(Constants.SUCCESS, true);
            } else {
                String stateOne = contractRoom.getStateOne();
                String stateTwo = contractRoom.getStateTwo();
                String auditState = contractRoom.getAuditState();
                if (stateOne.equals(Constants.CONTRACT_STATE_FORMAL)) {
                    map.put(Constants.MSG, contractRoom.getBuildingName() + ":" + contractRoom.getRoomNo() + "房间，" + "已被签约");
                    map.put(Constants.SUCCESS, false);
                }
                if(stateOne.equals(Constants.CONTRACT_STATE_INIT)
                        && stateTwo.equals(Constants.CONTRACT_STATE_INIT)){
                    // 草稿状态允许签约
                    if(auditState.equals(Constants.AUDIT_STATE_INIT)){
                        map.put(Constants.MSG, "允许签约");
                        map.put(Constants.SUCCESS, true);
                    }else{
                        map.put(Constants.MSG, contractRoom.getBuildingName() + ":" + contractRoom.getRoomNo() + "房间，" + "正在审核中,暂不能重复签约");
                        map.put(Constants.SUCCESS, false);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put(Constants.SUCCESS, false);
            map.put(Constants.MSG, "系统异常");
        }
        return map;
    }


    /**
     * @Description: 查询合同_房间表信息详情(JSON)
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("queryContractRoomDetailByPrimarykey")
    @ResponseBody
    public ContractRoom queryContractRoomDetailByPrimarykey(Integer id) {
        ContractRoom contractRoom = contractRoomService.queryContractRoomDetailByPrimarykey(id);
        return contractRoom;
    }

    /**
     * @Description: 新增合同_房间表信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("insertContractRoom")
    @ResponseBody
    public Map insertContractRoom(ContractRoom contractRoom) {
        Map map = Maps.newHashMap();
        try {
            contractRoomService.insertContractRoom(contractRoom);
            map.put(Constants.MSG, "添加成功");
            map.put(Constants.SUCCESS, true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put(Constants.SUCCESS, false);
            map.put(Constants.MSG, "系统异常");
        }
        return map;
    }

    /**
     * @Description: 修改合同_房间表信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("updateContractRoom")
    @ResponseBody
    public Map updateContractRoom(ContractRoom contractRoom) {
        Map map = Maps.newHashMap();
        try {
            contractRoomService.updateContractRoom(contractRoom);
            map.put(Constants.MSG, "更新成功");
            map.put(Constants.SUCCESS, true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put(Constants.SUCCESS, false);
            map.put(Constants.MSG, "系统异常");
        }
        return map;
    }

    /**
     * @Description: 删除合同_房间表信息
     * @author: xiachunyu
     * @date: 2019-06-04
     */
    @RequestMapping("deleteContractRoomByPrimarykey")
    @ResponseBody
    public Map deleteContractRoomByPrimarykey(Integer id) {
        Map map = Maps.newHashMap();
        try {
            contractRoomService.deleteContractRoomByPrimarykey(id);
            map.put(Constants.MSG, "删除成功");
            map.put(Constants.SUCCESS, true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put(Constants.SUCCESS, false);
            map.put(Constants.MSG, "系统异常");
        }
        return map;
    }

}
