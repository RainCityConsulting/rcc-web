package com.rcc.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;

public class FlashUtils {
    private static final Log log = LogFactory.getLog(FlashUtils.class);

    public static void messageCode(String code, HttpServletRequest request, Object... args) {
        Flash flash = (Flash) request.getSession().getAttribute("flash");
        flash.addMessage(RequestUtils.getRequestContext(request).getMessage(code, args, code));
    }

    public static void warningCode(String code, HttpServletRequest request, Object... args) {
        Flash flash = (Flash) request.getSession().getAttribute("flash");
        flash.addWarning(RequestUtils.getRequestContext(request).getMessage(code, args, code));
    }

    public static void errorCode(String code, HttpServletRequest request, Object... args) {
        Flash flash = (Flash) request.getSession().getAttribute("flash");
        flash.addError(RequestUtils.getRequestContext(request).getMessage(code, args, code));
    }

    public static void message(String msg, HttpServletRequest request, Object... args) {
        Flash flash = (Flash) request.getSession().getAttribute("flash");
        flash.addMessage(RequestUtils.getRequestContext(request).getMessage(msg, args, msg));
    }

    public static void warning(String msg, HttpServletRequest request, Object... args) {
        Flash flash = (Flash) request.getSession().getAttribute("flash");
        flash.addWarning(RequestUtils.getRequestContext(request).getMessage(msg, args, msg));
    }

    public static void error(String msg, HttpServletRequest request, Object... args) {
        Flash flash = (Flash) request.getSession().getAttribute("flash");
        flash.addError(RequestUtils.getRequestContext(request).getMessage(msg, args, msg));
    }
}
