package com.ruikun.sys.investment.shiroConfig;

import com.google.common.collect.Lists;
import com.ruikun.sys.investment.entity.Permission;
import com.ruikun.sys.investment.entity.User;
import com.ruikun.sys.investment.service.PermissionService;
import com.ruikun.sys.investment.service.UserService;
import com.ruikun.sys.investment.utils.DateTimeUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;
    @Autowired
    PermissionService permissionService;

    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String curremtTime = DateTimeUtil.getFormatDateTime();
        String curremtDate = curremtTime.substring(0, 10);
        String startDate = curremtDate + " 23:00:00";
        String endDate = DateTimeUtil.getAboutDay(curremtDate, 1, "yyyy-MM-dd") + " 01:00:00";
        if (DateTimeUtil.compare_date(startDate, curremtTime, "yyyy-MM-dd HH:mm:ss") == 1
                && DateTimeUtil.compare_date(curremtTime, endDate, "yyyy-MM-dd HH:mm:ss") == 1) {
            throw new AuthenticationException();
        }
        UsernamePasswordToken userToken = (UsernamePasswordToken) authenticationToken;
        String userName = (String) userToken.getPrincipal();  // 获取用户身份信息
        User user = new User();
        user.setUserName(userName);
        user = userService.queryUserDetail(user);
        //查询数据库获取用户信息
        if (user == null) {
            throw new UnknownAccountException(); //没找到帐号
        }
        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                user, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), this.getName());
        return simpleAuthenticationInfo;
    }

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User) principalCollection.getPrimaryPrincipal();
        List<Permission> permissionList = Lists.newArrayList();
        permissionList = permissionService.findPermissionListByUserid(user.getUserId());
        //创建权限集合对象
        List<String> permissions = new ArrayList<String>();
        if(permissionList!=null){
            for(Permission permission:permissionList){
                //将数据库中的权限标签 符放入集合
                permissions.add(permission.getPercode());
            }
        }
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }
}