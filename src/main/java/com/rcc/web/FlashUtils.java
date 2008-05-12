package com.rcc.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;

public class FlashUtils {
    private static final Log log = LogFactory.getLog(FlashUtils.class);

    public static void flashMessage(String code, HttpServletRequest request, Object... args) {
        Flash flash = (Flash) request.getSession().getAttribute("flash");
        flash.addMessage(RequestUtils.getRequestContext(request).getMessage(code, args, code));
    }

    public static void flashWarning(String code, HttpServletRequest request, Object... args) {
        Flash flash = (Flash) request.getSession().getAttribute("flash");
        flash.addWarning(RequestUtils.getRequestContext(request).getMessage(code, args, code));
    }

    public static void flashError(String code, HttpServletRequest request, Object... args) {
        Flash flash = (Flash) request.getSession().getAttribute("flash");
        flash.addError(RequestUtils.getRequestContext(request).getMessage(code, args, code));
    }
}
