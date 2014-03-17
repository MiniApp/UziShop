/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.foundation.service;

import com.iskyshop.core.query.support.IPageList;
import com.iskyshop.core.query.support.IQueryObject;
import com.iskyshop.foundation.domain.StoreCart;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract interface IStoreCartService {
    public abstract boolean save(StoreCart paramStoreCart);

    public abstract StoreCart getObjById(Long paramLong);

    public abstract boolean delete(Long paramLong);

    public abstract boolean batchDelete(List<Serializable> paramList);

    public abstract IPageList list(IQueryObject paramIQueryObject);

    public abstract boolean update(StoreCart paramStoreCart);

    public abstract List<StoreCart> query(String paramString, Map paramMap, int paramInt1, int paramInt2);
}