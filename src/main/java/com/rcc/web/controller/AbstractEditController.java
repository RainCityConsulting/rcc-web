package com.rcc.web.controller;

import com.rcc.beans.Identifiable;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Map;

public abstract class AbstractEditController extends AbstractFormController {
    private String createContent;
    private String editContent;

    public void setCreateContent(String createContent) {
        this.createContent = createContent;
    }

    public String getCreateContent() {
        return this.createContent;
    }

    public void setEditContent(String editContent) {
        this.editContent = editContent;
    }

    public String getEditContent() {
        return this.editContent;
    }

    protected final Object formBackingObject(HttpServletRequest request) throws Exception {
        int id = ControllerUtils.getIntParam(request, "id", 0);
        if (id == 0) {
            // Create
            return this.formNewBackingObject(request);
        } else {
            // Update
            return this.formExistingBackingObject(id);
        }
    }

    /**
     * This implementation calls formNewBackingObject(). Implementations can override this method
     * if the HttpServletRequest is required.
     */
    protected Identifiable formNewBackingObject(HttpServletRequest request) throws Exception {
        return this.formNewBackingObject();
    }

    /**
     * Returns null.
     */
    protected Identifiable formNewBackingObject() throws Exception {
        return null;
    }

    protected abstract Identifiable formExistingBackingObject(int id) throws Exception;

    protected final void referenceData(Map map, HttpServletRequest request, Object command,
            Errors errors)
        throws Exception
    {
        Identifiable id = (Identifiable) command;

        if (id.getId() == 0) {
            // Create
            map.put("content", this.getCreateContent());
        } else {
            // Update
            map.put("content", this.getEditContent());
        }

        this.internalReferenceData(map, request, command, errors);
    }

    protected void internalReferenceData(Map map, HttpServletRequest request, Object command,
            Errors errors)
        throws Exception
    {
        this.referenceData(map);
    }

    protected ModelAndView internalProcessFormSubmission(HttpServletRequest request,
            HttpServletResponse response, Object command, BindException errors)
        throws Exception
    {
        Identifiable id = (Identifiable) command;

        if (id.getId() == 0) {
            // Create
            return this.processCreateFormSubmission(request, response, id, errors);
        } else {
            // Update
            return this.processUpdateFormSubmission(request, response, id, errors);
        }
    }

    protected abstract ModelAndView processCreateFormSubmission(HttpServletRequest request,
            HttpServletResponse response, Identifiable id, BindException errors)
        throws Exception;

    protected abstract ModelAndView processUpdateFormSubmission(HttpServletRequest request,
            HttpServletResponse response,  Identifiable id, BindException errors)
        throws Exception;
}
