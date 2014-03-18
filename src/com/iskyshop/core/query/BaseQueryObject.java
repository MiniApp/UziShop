/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.core.query;

import org.springframework.web.servlet.ModelAndView;

public class BaseQueryObject extends QueryObject {
    public BaseQueryObject(String currentPage, ModelAndView mv, String orderBy, String orderType) {
        super(currentPage, mv, orderBy, orderType);
    }

    public String getQuery() {
        return super.getQuery();
    }
}