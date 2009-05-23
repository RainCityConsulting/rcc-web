package com.rcc.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

public class HistoryUtils {
    private static final Log log = LogFactory.getLog(HistoryUtils.class);

    public static String redirectPath(HttpServletRequest request, int idx) {
        List<View> history = (List<View>) request.getSession().getAttribute(
                HistoryHandlerInterceptor.attrName);
        if (history == null) { return null; }
        if (history.size() <= idx) { return null; }
        return history.get(idx).getRedirectPath();
    }
}
