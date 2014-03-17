/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.foundation.service.impl;

import com.iskyshop.core.dao.IGenericDAO;
import com.iskyshop.core.query.GenericPageList;
import com.iskyshop.core.query.PageObject;
import com.iskyshop.core.query.support.IPageList;
import com.iskyshop.core.query.support.IQueryObject;
import com.iskyshop.foundation.domain.Bargain;
import com.iskyshop.foundation.service.IBargainService;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BargainServiceImpl implements IBargainService {

    @Resource(name = "bargainDAO")
    private IGenericDAO<Bargain> bargainDao;

    public boolean save(Bargain bargain) {
        try {
            this.bargainDao.save(bargain);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Bargain getObjById(Long id) {
        Bargain bargain = (Bargain) this.bargainDao.get(id);
        if (bargain != null) {
            return bargain;
        }
        return null;
    }

    public boolean delete(Long id) {
        try {
            this.bargainDao.remove(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean batchDelete(List<Serializable> bargainIds) {
        for (Serializable id : bargainIds) {
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
        GenericPageList pList = new GenericPageList(Bargain.class, query, params, this.bargainDao);
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

    public boolean update(Bargain bargain) {
        try {
            this.bargainDao.update(bargain);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Bargain> query(String query, Map params, int begin, int max) {
        return this.bargainDao.query(query, params, begin, max);
    }
}