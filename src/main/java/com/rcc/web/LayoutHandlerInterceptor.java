package com.rcc.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LayoutHandlerInterceptor extends HandlerInterceptorAdapter {
    private static final Log log = LogFactory.getLog(LayoutHandlerInterceptor.class);
    public static final String IGNORE = LayoutHandlerInterceptor.class.getName() + ".IGNORE";

    private String defaultViewName;

    public void setDefaultViewName(String defaultViewName) {
        this.defaultViewName = defaultViewName;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler, ModelAndView mav)
        throws Exception
    {
        if (mav == null) { return; }

        if (mav.getView() instanceof RedirectView) { return; }

        if (mav.getModel().containsKey(IGNORE)) { return; }

        String viewName = mav.getViewName();
        if (viewName != null && viewName.startsWith("redirect:")) { return; }

        if (log.isDebugEnabled()) {
            log.debug("Setting view to default: " + this.defaultViewName);
        }
        mav.setViewName(this.defaultViewName);
    }
}
