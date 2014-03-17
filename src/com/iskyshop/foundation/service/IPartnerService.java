/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.foundation.service;

import com.iskyshop.core.query.support.IPageList;
import com.iskyshop.core.query.support.IQueryObject;
import com.iskyshop.foundation.domain.Partner;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract interface IPartnerService {
    public abstract boolean save(Partner paramPartner);

    public abstract Partner getObjById(Long paramLong);

    public abstract boolean delete(Long paramLong);

    public abstract boolean batchDelete(List<Serializable> paramList);

    public abstract IPageList list(IQueryObject paramIQueryObject);

    public abstract boolean update(Partner paramPartner);

    public abstract List<Partner> query(String paramString, Map paramMap, int paramInt1, int paramInt2);
}