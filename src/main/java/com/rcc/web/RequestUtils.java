package com.rcc.web;

import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {
    public static RequestContext getRequestContext(HttpServletRequest request) {
        RequestContext ctx = (RequestContext) request.getAttribute("springRequestContext");
        if (ctx == null) {
            ctx = new RequestContext(request);
            request.setAttribute("springRequestContext", ctx);
        }
        return ctx;
    }
}
