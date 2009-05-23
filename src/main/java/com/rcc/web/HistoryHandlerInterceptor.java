package com.rcc.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

public class HistoryHandlerInterceptor extends HandlerInterceptorAdapter {
    private static final Log log = LogFactory.getLog(HistoryHandlerInterceptor.class);

    public static final String attrName = HistoryHandlerInterceptor.class.getName() + ".history";

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler)
        throws Exception
    {
        HttpSession session = request.getSession();
        if (session == null) { return true; }
        List<View> history = (List<View>) session.getAttribute(attrName);
        if (history == null) {
            history = new ArrayList<View>();
            session.setAttribute(attrName, history);
        }
        history.add(new View(request));
        return true;
    }

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
            List<View> history = (List<View>) session.getAttribute(attrName);
            if (history == null || history.isEmpty()) { return; }

            int idx = history.size() - 1;
            if (idx < 0) { idx = 0; }
            modelAndView.addObject("h", idx);
        }
    }
}
