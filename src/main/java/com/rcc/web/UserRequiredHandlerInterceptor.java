package com.rcc.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserRequiredHandlerInterceptor extends HandlerInterceptorAdapter {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler)
    {
        String userAttributeName = System.getProperty("com.rcc.web.userAttributeName", "user");
        Object user = request.getSession().getAttribute(userAttributeName);
        if (user == null) {
            throw new UserRequiredException();
        }
        return true;
    }
}
