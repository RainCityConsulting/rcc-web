package com.rcc.web.servlet.listener;

import javax.servlet.ServletRequestListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpServletRequest;

public class SessionCreateListener implements ServletRequestListener {
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        // Create the session, if necessary
        // The SessionListener will take care of initializing the session
        request.getSession(true);
    }

    public void requestDestroyed(ServletRequestEvent sre) { ; }
}
