/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.foundation.service;

import com.iskyshop.core.query.support.IPageList;
import com.iskyshop.core.query.support.IQueryObject;
import com.iskyshop.foundation.domain.Res;
import java.util.List;
import java.util.Map;

public abstract interface IResService {
    public abstract boolean save(Res paramRes);

    public abstract boolean delete(Long paramLong);

    public abstract boolean update(Res paramRes);

    public abstract Res getObjById(Long paramLong);

    public abstract List<Res> query(String paramString, Map paramMap, int paramInt1, int paramInt2);

    public abstract IPageList list(IQueryObject paramIQueryObject);
}