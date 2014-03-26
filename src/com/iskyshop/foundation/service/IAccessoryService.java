package com.iskyshop.foundation.service;

import com.iskyshop.core.query.support.IPageList;
import com.iskyshop.core.query.support.IQueryObject;
import com.iskyshop.foundation.domain.Accessory;
import java.util.List;
import java.util.Map;

public abstract interface IAccessoryService {
    public abstract boolean save(Accessory paramAccessory);

    public abstract boolean delete(Long paramLong);

    public abstract boolean update(Accessory paramAccessory);

    public abstract IPageList list(IQueryObject paramIQueryObject);

    public abstract Accessory getObjById(Long paramLong);

    public abstract Accessory getObjByProperty(String paramString1, String paramString2);

    @SuppressWarnings("rawtypes")
    public abstract List<Accessory> query(String paramString, Map paramMap, int paramInt1, int paramInt2);
}