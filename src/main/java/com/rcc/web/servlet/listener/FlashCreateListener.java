package com.rcc.web.servlet.listener;

import com.rcc.web.Flash;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSession;
     
public class FlashCreateListener implements HttpSessionListener {
    private static final Log log = LogFactory.getLog(FlashCreateListener.class);

    public void sessionCreated(HttpSessionEvent se) {
        if (log.isDebugEnabled()) { log.debug("Adding flash to session"); }
        HttpSession session = se.getSession();
        session.setAttribute("flash", new Flash());
    }

    public void sessionDestroyed(HttpSessionEvent se) { ; }
}
