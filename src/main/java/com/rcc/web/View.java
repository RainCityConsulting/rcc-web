package com.rcc.web;

import javax.servlet.http.HttpServletRequest;

public class View {
    private String context;
    private String path;
    private String query;

    public View(HttpServletRequest request) {
        this.context = request.getContextPath(); // starts with '/'
        this.path = request.getServletPath(); // starts with '/'
        this.query = request.getQueryString(); // does NOT start with '?'
    }

    public String getPath() {
        if (this.query != null) {
            return this.context + this.path + '?' + this.query;
        }
        return this.context + this.path;
    }

    public String getRedirectPath() {
        if (this.query != null) {
            return this.path + '?' + this.query;
        }
        return this.path;
    }

    public boolean hasQuery() {
        return this.query != null;
    }

    public String toString() {
        return this.getPath();
    }
}
