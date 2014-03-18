/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.core.query.support;

import java.util.List;
import java.util.Map;

public abstract interface IQuery {
    public abstract int getRows(String paramString);

    public abstract List getResult(String paramString);

    public abstract void setFirstResult(int paramInt);

    public abstract void setMaxResults(int paramInt);

    public abstract void setParaValues(Map paramMap);

    public abstract List getResult(String paramString, int paramInt1, int paramInt2);
}