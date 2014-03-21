package com.iskyshop.core.query;

import com.iskyshop.core.dao.IGenericDAO;
import com.iskyshop.core.query.support.IQuery;
import java.util.List;
import java.util.Map;

public class GenericQuery implements IQuery {
    @SuppressWarnings("rawtypes")
    private IGenericDAO dao;

    private int begin;

    private int max;

    @SuppressWarnings("rawtypes")
    private Map params;

    @SuppressWarnings("rawtypes")
    public GenericQuery(IGenericDAO dao) {
        this.dao = dao;
    }

    @SuppressWarnings("rawtypes")
    public List getResult(String condition) {
        return this.dao.find(condition, this.params, this.begin, this.max);
    }

    @SuppressWarnings({ "rawtypes", "unused" })
    public List getResult(String condition, int begin, int max) {
        Object[] params = null;
        return this.dao.find(condition, this.params, begin, max);
    }

    @SuppressWarnings({ "unused", "rawtypes" })
    public int getRows(String condition) {
        int n = condition.toLowerCase().indexOf("order by");
        Object[] params = null;
        if (n > 0) {
            condition = condition.substring(0, n);
        }
        List ret = this.dao.query(condition, this.params, 0, 0);
        if ((ret != null) && (ret.size() > 0)) {
            return ((Long) ret.get(0)).intValue();
        }
        return 0;
    }

    public void setFirstResult(int begin) {
        this.begin = begin;
    }

    public void setMaxResults(int max) {
        this.max = max;
    }

    @SuppressWarnings("rawtypes")
    public void setParaValues(Map params) {
        this.params = params;
    }
}