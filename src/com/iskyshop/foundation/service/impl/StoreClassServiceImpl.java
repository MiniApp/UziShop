/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.foundation.service.impl;

import com.iskyshop.core.dao.IGenericDAO;
import com.iskyshop.core.query.GenericPageList;
import com.iskyshop.core.query.PageObject;
import com.iskyshop.core.query.support.IPageList;
import com.iskyshop.core.query.support.IQueryObject;
import com.iskyshop.foundation.domain.StoreClass;
import com.iskyshop.foundation.service.IStoreClassService;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StoreClassServiceImpl implements IStoreClassService {

    @Resource(name = "storeClassDAO")
    private IGenericDAO<StoreClass> storeClassDao;

    public boolean save(StoreClass storeClass) {
        try {
            this.storeClassDao.save(storeClass);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public StoreClass getObjById(Long id) {
        StoreClass storeClass = (StoreClass) this.storeClassDao.get(id);
        if (storeClass != null) {
            return storeClass;
        }
        return null;
    }

    public boolean delete(Long id) {
        try {
            this.storeClassDao.remove(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean batchDelete(List<Serializable> storeClassIds) {
        for (Serializable id : storeClassIds) {
            delete((Long) id);
        }
        return true;
    }

    public IPageList list(IQueryObject properties) {
        if (properties == null) {
            return null;
        }
        String query = properties.getQuery();
        Map params = properties.getParameters();
        GenericPageList pList = new GenericPageList(StoreClass.class, query, params, this.storeClassDao);
        if (properties != null) {
            PageObject pageObj = properties.getPageObj();
            if (pageObj != null)
                pList.doList((pageObj.getCurrentPage() == null) ? 0 : pageObj.getCurrentPage().intValue(),
                        (pageObj.getPageSize() == null) ? 0 : pageObj.getPageSize().intValue());
        } else {
            pList.doList(0, -1);
        }
        return pList;
    }

    public boolean update(StoreClass storeClass) {
        try {
            this.storeClassDao.update(storeClass);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<StoreClass> query(String query, Map params, int begin, int max) {
        return this.storeClassDao.query(query, params, begin, max);
    }
}