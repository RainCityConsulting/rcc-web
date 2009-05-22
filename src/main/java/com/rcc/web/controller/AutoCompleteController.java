package com.rcc.web.controller;

import com.rcc.search.IndexReader;;
import com.rcc.search.SearchResult;
import com.rcc.search.SearchResults;

import org.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AutoCompleteController<T> extends MultiActionController {
    private static final Log log = LogFactory.getLog(AutoCompleteController.class);

    private IndexReader<T> reader;
    private int defaultLimit = 10;
    private boolean escapeQuery = true;
    private boolean autoPrefix = true;
    private int autoPrefixMinLength = 3;

    public void setDefaultLimit(int defaultLimit) { this.defaultLimit = defaultLimit; }
    public void setEscapeQuery(boolean escapeQuery) { this.escapeQuery = escapeQuery; }
    public void setAutoPrefix(boolean autoPrefix) { this.autoPrefix = autoPrefix; }

    public void setAutoPrefixMinLength(int autoPrefixMinLength) {
        this.autoPrefixMinLength = autoPrefixMinLength;
    }

    public void setIndexReader(IndexReader<T> reader) {
        this.reader = reader;
    }

    public ModelAndView search(HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        String query = this.cleanQuery(request.getParameter("q"));
        if (log.isDebugEnabled()) {
            log.debug(String.format("Query after cleaning: [%s]", query));
        }
        int limit = ControllerUtils.getIntParam(request, "limit", this.defaultLimit);

        SearchResults<T> results = this.reader.parseAndSearch(
                query, 0, limit, this.escapeQuery, this.autoPrefix, this.autoPrefixMinLength);
        if (log.isDebugEnabled()) {
            log.debug(String.format("Found %d results", results.getHitCount()));
        }

        ServletOutputStream os = null;
        try {
            os = response.getOutputStream();
            for (SearchResult<T> result : results.getResults()) {
                T t = result.getResult();
                JSONObject jobj = new JSONObject(t);
                os.println(jobj.toString());
            }
        } finally {
            if (os != null) { os.close(); }
        }

        return null;
    }

    private String cleanQuery(String q) {
        if (q == null) { return ""; }

        if (q.indexOf('[') >= 0) {
            q = q.substring(0, q.indexOf('['));
        }

        if (q.indexOf(']') >= 0) {
            q = q.substring(0, q.indexOf(']'));
        }

        return q;
    }
}
