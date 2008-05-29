package com.rcc.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FlashHandlerInterceptor extends HandlerInterceptorAdapter {
    private static final Log log = LogFactory.getLog(FlashHandlerInterceptor.class);

    public void postHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler, ModelAndView modelAndView)
        throws Exception
    {
        if (modelAndView != null) {
            if (modelAndView.getView() instanceof RedirectView) { return; }

            String viewName = modelAndView.getViewName();
            if (viewName != null && viewName.startsWith("redirect:")) { return; }

            HttpSession session = request.getSession();
            if (session == null) { return; }
            Flash sessionFlash = (Flash) session.getAttribute("flash");
            if (sessionFlash == null) { return; }
            Flash flash = new Flash(sessionFlash);

            modelAndView.addObject("flash", flash);

            sessionFlash.clear();
        }
    }
}
