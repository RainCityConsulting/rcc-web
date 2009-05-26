package com.rcc.web;

import com.rcc.web.controller.ControllerUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HistoryRedirectHandlerInterceptor extends HandlerInterceptorAdapter {
    private static final Log log = LogFactory.getLog(HistoryRedirectHandlerInterceptor.class);

    private String paramName;

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler, ModelAndView mav)
        throws Exception
    {
        int last = ControllerUtils.getIntParam(request, this.paramName, -1);
        if (last >= 0) {
            String redirectPath = HistoryUtils.redirectPath(request, last);
            mav.clear();
            mav.setViewName("redirect:" + redirectPath);
        }
    }
}
