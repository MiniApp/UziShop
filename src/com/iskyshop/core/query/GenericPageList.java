/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.core.query;

import com.iskyshop.core.dao.IGenericDAO;
import com.iskyshop.core.query.support.IQuery;
import com.iskyshop.core.query.support.IQueryObject;
import java.util.Map;

public class GenericPageList extends PageList {
    private static final long serialVersionUID = 6730593239674387757L;

    protected String scope;

    protected Class cls;

    public GenericPageList(Class cls, IQueryObject queryObject, IGenericDAO dao) {
        this(cls, queryObject.getQuery(), queryObject.getParameters(), dao);
    }

    public GenericPageList(Class cls, String scope, Map paras, IGenericDAO dao) {
        this.cls = cls;
        this.scope = scope;
        IQuery query = new GenericQuery(dao);
        query.setParaValues(paras);
        setQuery(query);
    }

    public void doList(int currentPage, int pageSize) {
        String totalSql = "select COUNT(obj) from " + this.cls.getName() + " obj where " + this.scope;
        super.doList(pageSize, currentPage, totalSql, this.scope);
    }
}