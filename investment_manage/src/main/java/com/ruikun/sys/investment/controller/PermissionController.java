package com.ruikun.sys.investment.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.entity.Permission;
import com.ruikun.sys.investment.entity.PermissionTree;
import com.ruikun.sys.investment.entity.Role;
import com.ruikun.sys.investment.entity.RolePermission;
import com.ruikun.sys.investment.service.PermissionService;
import com.ruikun.sys.investment.service.PermissionService;
import com.ruikun.sys.investment.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@SuppressWarnings("all")
public class PermissionController {
	
    @Autowired
    private PermissionService permissionService;
    
	@Autowired
	private RoleService roleService;


	/**
	 *  查询菜单列表
	 */
	@RequestMapping(value="/permissionBusinessList")
    public String permissionList(HttpServletRequest request,Model model ){
		List<Permission> permissionList = permissionService.findPermissionList(new Permission());
		List<Permission> permissionList_sort = Lists.newArrayList();
		sortList(permissionList,permissionList_sort,0);
		model.addAttribute("permissionList", permissionList_sort);
		return "permissionBusiness/permissionBusinessList";
    }
	
	/**
	 * 递归将菜单按照层级排序
	 */
	private void sortList(List<Permission> permissionList, List<Permission> permissionList_sort, Integer parentid){
		boolean bool = false;
		for (int i=0; i<permissionList.size(); i++){
			Permission per = permissionList.get(i);
			if (per.getParentid().equals(parentid)){	
				permissionList_sort.add(per);
				Integer permissionid = per.getPermissionid();
				// 判断是否还有子节点, 有则继续获取子节点
				for (int j=0; j<permissionList.size(); j++){
					Permission per_child = permissionList.get(j);
					if (per_child.getParentid().equals(permissionid)){
						bool = true;
						break;
					}
				}
				if(bool){
					sortList(permissionList, permissionList_sort, permissionid);
				}
			}
		}
	}

	
	/**
	 * 跳转到添加菜单页面
	 */
	@RequestMapping(value="/toAddPermission/{permissionid}")
    public String toAddPermission(Model model,@PathVariable("permissionid") Integer permissionid){
		Permission permission=permissionService.findPermissionDetail(permissionid);
		model.addAttribute("permission", permission);
		return "permissionBusiness/permissionBusinessAdd";
    }
	
	/**
	 * 跳转到编辑菜单页面
	 */
	@RequestMapping(value="/toEditPermission/{permissionid}")
    public String toEditPermission(Model model,@PathVariable("permissionid") Integer permissionid){
		Permission permission=permissionService.findPermissionDetail(permissionid);
		String url=permission.getUrl();
		String templetId="";
		if(!StringUtils.isEmpty(url) && url.startsWith("templetDataList")){
			templetId=url.substring(url.indexOf("/")+1);
		}
		model.addAttribute("permission", permission);
		model.addAttribute("templetId", templetId);
		return "permissionBusiness/permissionBusinessEdit";
    }
	/**
	 *  添加菜单资源
	 */
	@RequestMapping("addPermission")
	@ResponseBody
	public Map addPermission(Permission permission){
		Map map = Maps.newHashMap();
		try{
			//判断菜单名称是否已存在
			List<Permission> list=permissionService.findPermissionList(permission);
			if(list.size()>0){
				map.put(Constants.SUCCESS, false);
		        map.put(Constants.MSG, "此菜单已存在");
		        return map;
			}
			permissionService.insertPermission(permission);
	        map.put(Constants.SUCCESS, true);
	        map.put(Constants.MSG, "菜单添加成功");
		}catch (Exception e) {
	        map.put(Constants.SUCCESS, false);
	        map.put(Constants.MSG, "系统异常,请重试");
		}
		return map;
	}
    
	
	/**
	 * 修改菜单
	 */
	@RequestMapping("updatePermission")
	@ResponseBody
	public Map updatePermission(Permission permission){
		Map map = Maps.newHashMap();
		try{
			//判断菜单名称是否已存在
			List<Permission> list=permissionService.findPermissionList(permission);
			if(list.size()>0){
				for(Permission p:list){
					if(!permission.getPermissionid().equals(p.getPermissionid())){
						map.put(Constants.SUCCESS, false);
						map.put(Constants.MSG, "此菜单已存在");
						return map;
					}
				}
			}
			permissionService.updatePermission(permission);
	        map.put(Constants.SUCCESS, true);
	        map.put(Constants.MSG, "修改成功");
		}catch (Exception e) {
	        map.put(Constants.SUCCESS, false);
	        map.put(Constants.MSG, "系统异常,请重试");
		}
		return map;
	}
	
	
	/**
	 *  删除菜单
	 */
	@RequestMapping("deletePermission/{permissionid}")
	@ResponseBody
	public Map deletePermission(@PathVariable("permissionid")Integer permissionid){
		Map map = Maps.newHashMap();
		try{
			//如果此菜单被角色使用，则不能删除
			List<RolePermission> list=permissionService.findRolePermissionByPerId(permissionid);
			if(list.size()>0){
				map.put(Constants.SUCCESS, false);
		        map.put(Constants.MSG, "此菜单正在使用中，不能删除");
		        return map;
			}
			permissionService.deletePermission(permissionid);
	        map.put(Constants.SUCCESS, true);
	        map.put(Constants.MSG, "删除成功");
		}catch (Exception e) {
	        map.put(Constants.SUCCESS, false);
	        map.put(Constants.MSG, "系统异常,请重试");
		}
		return map;
	}
	
	/**
	 * 跳转到分配权限列表页面
	 */
	@RequestMapping(value="/permissionBusinessTreeList/{roleId}")
    public String permissionTreeList(@PathVariable("roleId")Integer roleId,Model model){
		String ids = "";
		List<PermissionTree> treeList = permissionService.findPermissionTreeList(); //查询菜单(全部)树
		Role role = roleService.queryRoleDetailByPrimarykey(roleId);
		List<RolePermission> rpList = permissionService.findPermissionByRoleId(roleId); //查询所选角色下的权限菜单
		for(RolePermission rp:rpList){
			ids += rp.getPermissionid() + ",";
		}
		model.addAttribute("ids", ids);
		model.addAttribute("zNodes", JSON.toJSONString(treeList));
		model.addAttribute("roleId", roleId);
		if(role != null){
			model.addAttribute("roleName", role.getRoleName());
		}else{
			model.addAttribute("roleName", "");
		}
		return "role/permissionTreeList";
    }
	
	/**
	 *  分配角色菜单权限
	 */
	@RequestMapping(value="/saveRolePermission")
	@ResponseBody
	public Map saveRolePermission(Integer roleId,String ids){
		Map map = Maps.newHashMap();
		try{
			permissionService.insertRolePermission(roleId,ids);
	        map.put(Constants.SUCCESS, true);
	        map.put(Constants.MSG, "保存成功");
		}catch (Exception e) {
	        map.put(Constants.SUCCESS, false);
	        map.put(Constants.MSG, "保存失败,请重试");
		}
		return map;
    }
	

}
