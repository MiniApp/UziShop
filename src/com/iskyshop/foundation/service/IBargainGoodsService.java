/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.foundation.service;

import com.iskyshop.core.query.support.IPageList;
import com.iskyshop.core.query.support.IQueryObject;
import com.iskyshop.foundation.domain.BargainGoods;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract interface IBargainGoodsService {
    public abstract boolean save(BargainGoods paramBargainGoods);

    public abstract BargainGoods getObjById(Long paramLong);

    public abstract boolean delete(Long paramLong);

    public abstract boolean batchDelete(List<Serializable> paramList);

    public abstract IPageList list(IQueryObject paramIQueryObject);

    public abstract boolean update(BargainGoods paramBargainGoods);

    public abstract List<BargainGoods> query(String paramString, Map paramMap, int paramInt1, int paramInt2);
}