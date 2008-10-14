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
    private IndexReader<T> reader;

    public ModelAndView search(HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        String query = request.getParameter("q");
        int limit = ControllerUtils.getIntParam(request, "limit", 10);

        SearchResults<T> results = this.reader.parseAndSearch(
                query, 0, limit, true, true, 3);

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
}
