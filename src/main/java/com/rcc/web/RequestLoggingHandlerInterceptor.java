package com.rcc.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

public class RequestLoggingHandlerInterceptor extends HandlerInterceptorAdapter {
    private static final Log log = LogFactory.getLog("HTTP_REQUEST");

    private boolean logHttpMethod;
    private boolean logRequestParams;
    private boolean logCookies;
    private boolean logHttpHeaders;

    public void setLogHttpMethod(boolean l) { this.logHttpMethod = l; }
    public void setLogRequestParams(boolean l) { this.logRequestParams = l; }
    public void setLogCookies(boolean l) { this.logCookies = l; }
    public void setLogHttpHeaders(boolean l) { this.logHttpHeaders = l; }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler)
        throws Exception
    {
        if (this.logHttpMethod) {
            log.debug("HTTP METHOD: " + request.getMethod());
        }

        if (this.logRequestParams) {
            Map map = request.getParameterMap();
            for (Map.Entry e : (Set<Map.Entry>) map.entrySet()) {
                log.debug("HTTP REQUEST PARAMETER: " + e.getKey() + ": "
                        + StringUtils.arrayToDelimitedString((String[]) e.getValue(), ", "));
            }
        }

        if (this.logCookies) {
            if (request.getCookies() != null) {
                for (Cookie c : request.getCookies()) {
                    log.debug(String.format("COOKIE: (%s) %s [%s]",
                            c.getPath(), c.getName(), c.getValue()));
                }
            } else {
                log.debug("HTTP COOKIE: NONE");
            }
        }

        if (this.logHttpHeaders) {
            Enumeration<String> headerNames = (Enumeration<String>) request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                String val = StringUtils.arrayToCommaDelimitedString(
                        StringUtils.toStringArray(request.getHeaders(name)));
                log.debug(String.format("HTTP HEADER: %s: %s", name, val));
            }
        }

        return true;
    }
}
