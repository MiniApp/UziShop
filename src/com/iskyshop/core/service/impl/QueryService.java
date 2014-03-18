/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.core.service.impl;

import com.iskyshop.core.base.GenericEntityDao;
import com.iskyshop.core.service.IQueryService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class QueryService implements IQueryService {

    @Autowired
    @Qualifier("genericEntityDao")
    private GenericEntityDao geDao;

    public GenericEntityDao getGeDao() {
        return this.geDao;
    }

    public void setGeDao(GenericEntityDao geDao) {
        this.geDao = geDao;
    }

    public List query(String scope, Map params, int page, int pageSize) {
        return this.geDao.query(scope, params, page, pageSize);
    }
}