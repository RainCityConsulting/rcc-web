package com.rcc.web.controller;

import com.rcc.web.FlashUtils;
import com.rcc.web.View;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractWizardFormController
    extends org.springframework.web.servlet.mvc.AbstractWizardFormController
{
    private static final Log log = LogFactory.getLog(AbstractWizardFormController.class);

    private String[] contents;

    public void setContents(String[] contents) {
        this.contents = contents;
    }

    /**
     * This implementation was copied from the spring code. Everything is the same except
     * for the return 0 at the end.
     */
    protected int getCurrentPage(HttpServletRequest request) {
        // Check for overriding attribute in request.
        String pageAttrName = getPageSessionAttributeName(request);
        Integer pageAttr = (Integer) request.getAttribute(pageAttrName);
        if (pageAttr != null) {
            return pageAttr.intValue();
        }
        // Check for explicit request parameter.
        String pageParam = request.getParameter(PARAM_PAGE);
        if (pageParam != null) {
            return Integer.parseInt(pageParam);
        }
        // Check for original attribute in session.
        if (isSessionForm()) {
            pageAttr = (Integer) request.getSession().getAttribute(pageAttrName);
            if (pageAttr != null) {
                return pageAttr.intValue();
            }
        }

        return 0;
    }

    protected int getPageCount(HttpServletRequest request, Object command) {
        return this.contents.length;
    }

    protected String getViewName(HttpServletRequest request, Object command, int page) {
        // This will be taken care of by the layout interceptor
        return null;
    }

    protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response,
            BindException errors)
        throws Exception
    {
        return this.showForm(request, errors, null);
    }

    protected Map referenceData(HttpServletRequest request, Object command, Errors errors, int page)
        throws Exception
    {
        Map map = new HashMap();
        map.put("content", this.contents[page]);
        this.referenceData(map, request, command, errors, page);
        return map;
    }

    protected void referenceData(Map map, HttpServletRequest request, Object command, Errors errors,
            int page)
        throws Exception
    {
        this.referenceData(map, page);
    }

    protected void referenceData(Map map, int page) throws Exception { ; }
}
