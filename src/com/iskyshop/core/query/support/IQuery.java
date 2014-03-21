package com.iskyshop.core.query.support;

import java.util.List;
import java.util.Map;

public abstract interface IQuery {
    public abstract int getRows(String paramString);

    @SuppressWarnings("rawtypes")
    public abstract List getResult(String paramString);

    public abstract void setFirstResult(int paramInt);

    public abstract void setMaxResults(int paramInt);

    @SuppressWarnings("rawtypes")
    public abstract void setParaValues(Map paramMap);

    @SuppressWarnings("rawtypes")
    public abstract List getResult(String paramString, int paramInt1, int paramInt2);
}