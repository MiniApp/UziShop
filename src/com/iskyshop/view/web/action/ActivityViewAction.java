/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.view.web.action;

import com.iskyshop.core.domain.virtual.SysMap;
import com.iskyshop.core.mv.JModelAndView;
import com.iskyshop.core.query.support.IPageList;
import com.iskyshop.core.tools.CommUtil;
import com.iskyshop.foundation.domain.Activity;
import com.iskyshop.foundation.domain.query.ActivityGoodsQueryObject;
import com.iskyshop.foundation.service.IActivityGoodsService;
import com.iskyshop.foundation.service.IActivityService;
import com.iskyshop.foundation.service.ISysConfigService;
import com.iskyshop.foundation.service.IUserConfigService;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ActivityViewAction {

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private IUserConfigService userConfigService;

    @Autowired
    private IActivityService activityService;

    @Autowired
    private IActivityGoodsService activityGoodsService;

    @RequestMapping({ "/activity.htm" })
    public ModelAndView activity(HttpServletRequest request, HttpServletResponse response, String id, String currentPage) {
        ModelAndView mv = new JModelAndView("activity.html", this.configService.getSysConfig(),
                this.userConfigService.getUserConfig(), 1, request, response);
        Activity act = this.activityService.getObjById(CommUtil.null2Long(id));
        ActivityGoodsQueryObject qo = new ActivityGoodsQueryObject(currentPage, mv, "addTime", "desc");
        qo.setPageSize(Integer.valueOf(24));
        qo.addQuery("obj.ag_status", new SysMap("ag_status", Integer.valueOf(1)), "=");
        qo.addQuery("obj.act.id", new SysMap("act_id", act.getId()), "=");
        qo.addQuery("obj.act.ac_status", new SysMap("ac_status", Integer.valueOf(1)), "=");
        qo.addQuery("obj.act.ac_begin_time", new SysMap("ac_begin_time", new Date()), "<=");
        qo.addQuery("obj.act.ac_end_time", new SysMap("ac_end_time", new Date()), ">=");
        qo.addQuery("obj.ag_goods.goods_status", new SysMap("goods_status", Integer.valueOf(0)), "=");
        IPageList pList = this.activityGoodsService.list(qo);
        CommUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
        mv.addObject("act", act);
        return mv;
    }
}