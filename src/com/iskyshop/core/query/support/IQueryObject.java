package com.iskyshop.core.query.support;

import com.iskyshop.core.domain.virtual.SysMap;
import com.iskyshop.core.query.PageObject;
import java.util.Map;

public abstract interface IQueryObject {
    public abstract String getQuery();

    @SuppressWarnings("rawtypes")
    public abstract Map getParameters();

    public abstract PageObject getPageObj();

    public abstract IQueryObject addQuery(String paramString, @SuppressWarnings("rawtypes")
    Map paramMap);

    public abstract IQueryObject addQuery(String paramString1, SysMap paramSysMap, String paramString2);

    public abstract IQueryObject addQuery(String paramString1, Object paramObject, String paramString2,
            String paramString3);
}