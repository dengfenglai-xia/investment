package com.ruikun.sys.investment.controller;

import com.google.common.collect.Maps;
import com.ruikun.sys.investment.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Map;

@Controller
@Slf4j
public class LoginController {

	/**
	 * 登录界面
	 */
	@RequestMapping(value = "/login")
	public String login(){
		return "login";
	}

	/**
	 * 登录
	 */
	@RequestMapping("loginCommit")
    @ResponseBody
    public Map loginCommit(String username,String password,boolean rememberMe) {
        Map map = Maps.newHashMap();
    	boolean result = true;
    	String msg = "登录成功";
        // 获取当前实体信息
        Subject subject = SecurityUtils.getSubject();  
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(rememberMe);
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            result = false;
            msg = "用户名或者密码不正确";
        } catch (IncorrectCredentialsException e) {
            result = false;
            msg = "用户名或者密码不正确";
        } catch (AuthenticationException e) {
            result = false;
            msg = "23点 至 次日凌晨1点为系统维护时间，请您谅解";
        }
    	map.put(Constants.SUCCESS, result);
        map.put(Constants.MSG, msg);
        return map;
    }
}
