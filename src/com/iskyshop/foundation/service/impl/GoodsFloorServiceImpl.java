/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.foundation.service.impl;

import com.iskyshop.core.dao.IGenericDAO;
import com.iskyshop.core.query.GenericPageList;
import com.iskyshop.core.query.PageObject;
import com.iskyshop.core.query.support.IPageList;
import com.iskyshop.core.query.support.IQueryObject;
import com.iskyshop.foundation.domain.GoodsFloor;
import com.iskyshop.foundation.service.IGoodsFloorService;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GoodsFloorServiceImpl implements IGoodsFloorService {

    @Resource(name = "goodsFloorDAO")
    private IGenericDAO<GoodsFloor> goodsFloorDao;

    public boolean save(GoodsFloor goodsFloor) {
        try {
            this.goodsFloorDao.save(goodsFloor);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public GoodsFloor getObjById(Long id) {
        GoodsFloor goodsFloor = (GoodsFloor) this.goodsFloorDao.get(id);
        if (goodsFloor != null) {
            return goodsFloor;
        }
        return null;
    }

    public boolean delete(Long id) {
        try {
            this.goodsFloorDao.remove(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean batchDelete(List<Serializable> goodsFloorIds) {
        for (Serializable id : goodsFloorIds) {
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
        GenericPageList pList = new GenericPageList(GoodsFloor.class, query, params, this.goodsFloorDao);
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

    public boolean update(GoodsFloor goodsFloor) {
        try {
            this.goodsFloorDao.update(goodsFloor);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<GoodsFloor> query(String query, Map params, int begin, int max) {
        return this.goodsFloorDao.query(query, params, begin, max);
    }
}