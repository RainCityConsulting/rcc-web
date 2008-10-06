package com.rcc.web.controller;

import javax.servlet.http.HttpServletRequest;

public class ControllerUtils {
    public static boolean hasParam(HttpServletRequest request, String param) {
        String v = request.getParameter(param);
        return v != null && v.length() > 0;
    }

    public static String getParam(HttpServletRequest request, String param, String fallback) {
        if (hasParam(request, param)) {
            return request.getParameter(param);
        }
        return fallback;
    }

    public static int getIntParam(HttpServletRequest request, String param, int fallback) {
        if (hasParam(request, param)) {
            try {
                return Integer.parseInt(request.getParameter(param));
            } catch (NumberFormatException e) {
                return fallback;
            }
        }
        return fallback;
    }
}
