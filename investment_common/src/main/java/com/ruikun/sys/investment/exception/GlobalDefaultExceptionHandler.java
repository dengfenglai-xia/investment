package com.ruikun.sys.investment.exception;


import com.ruikun.sys.investment.utils.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class GlobalDefaultExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView defaultExceptionHandler(HttpServletRequest request, Exception e) {
        ModelAndView model = new ModelAndView();
        if (e instanceof NoHandlerFoundException){
            model.setViewName("404");
        }else{
            model.setViewName("500");
        }
        e.printStackTrace();
        recordError(request,e);
        return model;
    }

    /**
     * 记录错误日志信息
     * @param request
     * @param e
     */
    private void recordError(HttpServletRequest request, Exception e){
        // log记录异常信息
        StringBuffer sb = new StringBuffer();
        sb.append("系统发生异常：");
        sb.append(DateTimeUtil.getFormatDateTime());
        sb.append(" ，来源：");
        sb.append(request.getRemoteAddr());
        sb.append(" ，请求地址：");
        sb.append(request.getRequestURI());
        sb.append(" ，异常信息：");
        sb.append(e.getMessage());
        log.error(sb.toString());
    }
}
