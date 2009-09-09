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
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler, ModelAndView modelAndView)
        throws Exception
    {
        if (modelAndView != null) {
            if (modelAndView.getView() instanceof RedirectView) {
                if (log.isDebugEnabled()) {
                    log.debug("View is RedirectView - will not add history: "
                            + modelAndView.getView());
                }
                return;
            }

            String viewName = modelAndView.getViewName();
            if (viewName != null && viewName.startsWith("redirect:")) {
                if (log.isDebugEnabled()) {
                    log.debug("View name begins with 'redirect:' - will not add history: "
                            + viewName);
                }
                return;
            }

            HttpSession session = request.getSession();
            if (session == null) {
                if (log.isDebugEnabled()) {
                    log.debug("Session is null - will not add history: " + modelAndView);
                }
                return;
            }

            List<View> history = (List<View>) session.getAttribute(attrName);
            if (history == null) {
                history = new ArrayList<View>();
                session.setAttribute(attrName, history);
            }

            history.add(new View(request));

            int idx = history.size() - 1;
            if (idx < 0) { idx = 0; }
            modelAndView.addObject(attrName, idx);
        } else {
            if (log.isDebugEnabled()) { log.debug("ModelAndView is null - will not add history"); }
        }
    }
}
