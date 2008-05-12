package com.rcc.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Map;
import java.util.Set;

public class RequestLoggingHandlerInterceptor extends HandlerInterceptorAdapter {
    private static final Log log = LogFactory.getLog("HTTP REQUEST");

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler)
        throws Exception
    {
        log.debug("METHOD: " + request.getMethod());
        Map map = request.getParameterMap();
        for (Map.Entry e : (Set<Map.Entry>) map.entrySet()) {
            log.debug("PARAMETER: " + e.getKey() + ": "
                    + StringUtils.arrayToDelimitedString((String[]) e.getValue(), ", "));
        }
        return true;
    }
}
