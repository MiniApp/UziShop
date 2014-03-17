/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.foundation.service.impl;

import com.iskyshop.core.dao.IGenericDAO;
import com.iskyshop.core.query.GenericPageList;
import com.iskyshop.core.query.PageObject;
import com.iskyshop.core.query.support.IPageList;
import com.iskyshop.core.query.support.IQueryObject;
import com.iskyshop.foundation.domain.StoreGrade;
import com.iskyshop.foundation.service.IStoreGradeService;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StoreGradeServiceImpl implements IStoreGradeService {

    @Resource(name = "storeGradeDAO")
    private IGenericDAO<StoreGrade> storeGradeDao;

    public boolean save(StoreGrade storeGrade) {
        try {
            this.storeGradeDao.save(storeGrade);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public StoreGrade getObjById(Long id) {
        StoreGrade storeGrade = (StoreGrade) this.storeGradeDao.get(id);
        if (storeGrade != null) {
            return storeGrade;
        }
        return null;
    }

    public boolean delete(Long id) {
        try {
            this.storeGradeDao.remove(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean batchDelete(List<Serializable> storeGradeIds) {
        for (Serializable id : storeGradeIds) {
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
        GenericPageList pList = new GenericPageList(StoreGrade.class, query, params, this.storeGradeDao);
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

    public boolean update(StoreGrade storeGrade) {
        try {
            this.storeGradeDao.update(storeGrade);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<StoreGrade> query(String query, Map params, int begin, int max) {
        return this.storeGradeDao.query(query, params, begin, max);
    }
}