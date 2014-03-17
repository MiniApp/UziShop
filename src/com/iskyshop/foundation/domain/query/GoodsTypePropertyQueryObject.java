/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.foundation.domain.query;

import com.iskyshop.core.query.QueryObject;
import org.springframework.web.servlet.ModelAndView;

public class GoodsTypePropertyQueryObject extends QueryObject {
    public GoodsTypePropertyQueryObject(String currentPage, ModelAndView mv, String orderBy, String orderType) {
        super(currentPage, mv, orderBy, orderType);
    }

    public GoodsTypePropertyQueryObject() {
    }
}