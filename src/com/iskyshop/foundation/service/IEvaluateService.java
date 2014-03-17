/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.foundation.service;

import com.iskyshop.core.query.support.IPageList;
import com.iskyshop.core.query.support.IQueryObject;
import com.iskyshop.foundation.domain.Evaluate;
import com.iskyshop.foundation.domain.Goods;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract interface IEvaluateService {
    public abstract boolean save(Evaluate paramEvaluate);

    public abstract Evaluate getObjById(Long paramLong);

    public abstract boolean delete(Long paramLong);

    public abstract boolean batchDelete(List<Serializable> paramList);

    public abstract IPageList list(IQueryObject paramIQueryObject);

    public abstract boolean update(Evaluate paramEvaluate);

    public abstract List<Evaluate> query(String paramString, Map paramMap, int paramInt1, int paramInt2);

    public abstract List<Goods> query_goods(String paramString, Map paramMap, int paramInt1, int paramInt2);
}