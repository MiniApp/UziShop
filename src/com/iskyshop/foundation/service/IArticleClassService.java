/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.foundation.service;

import com.iskyshop.core.query.support.IPageList;
import com.iskyshop.core.query.support.IQueryObject;
import com.iskyshop.foundation.domain.ArticleClass;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract interface IArticleClassService {
    public abstract boolean save(ArticleClass paramArticleClass);

    public abstract ArticleClass getObjById(Long paramLong);

    public abstract boolean delete(Long paramLong);

    public abstract boolean batchDelete(List<Serializable> paramList);

    public abstract IPageList list(IQueryObject paramIQueryObject);

    public abstract boolean update(ArticleClass paramArticleClass);

    public abstract List<ArticleClass> query(String paramString, Map paramMap, int paramInt1, int paramInt2);

    public abstract ArticleClass getObjByPropertyName(String paramString, Object paramObject);
}