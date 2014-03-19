/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.manage.seller.action;

import com.iskyshop.core.annotation.SecurityMapping;
import com.iskyshop.core.domain.virtual.SysMap;
import com.iskyshop.core.mv.JModelAndView;
import com.iskyshop.core.query.support.IPageList;
import com.iskyshop.core.security.support.SecurityUserHolder;
import com.iskyshop.core.tools.CommUtil;
import com.iskyshop.foundation.domain.GoodsReturn;
import com.iskyshop.foundation.domain.User;
import com.iskyshop.foundation.domain.query.GoodsReturnQueryObject;
import com.iskyshop.foundation.service.IGoodsReturnItemService;
import com.iskyshop.foundation.service.IGoodsReturnService;
import com.iskyshop.foundation.service.ISysConfigService;
import com.iskyshop.foundation.service.IUserConfigService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GoodsReturnSellerAction {

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private IUserConfigService userConfigService;

    @Autowired
    private IGoodsReturnService goodsReturnService;

    @Autowired
    private IGoodsReturnItemService goodsReturnItemService;

    @SecurityMapping(title = "卖家退货列表", value = "/seller/goods_return.htm*", rtype = "seller", rname = "退货管理", rcode = "goods_return_seller", rgroup = "客户服务")
    @RequestMapping({ "/seller/goods_return.htm" })
    public ModelAndView refund(HttpServletRequest request, HttpServletResponse response, String id, String currentPage,
            String data_type, String data, String beginTime, String endTime) {
        ModelAndView mv = new JModelAndView("user/default/usercenter/goods_return.html",
                this.configService.getSysConfig(), this.userConfigService.getUserConfig(), 0, request, response);
        GoodsReturnQueryObject qo = new GoodsReturnQueryObject(currentPage, mv, "addTime", "desc");
        qo.setPageSize(Integer.valueOf(30));
        if (!(CommUtil.null2String(data).equals(""))) {
            if (CommUtil.null2String(data_type).equals("order_id")) {
                qo.addQuery("obj.of.order_id", new SysMap("order_id", data), "=");
            }
            if (CommUtil.null2String(data_type).equals("buyer_name")) {
                qo.addQuery("obj.of.user.userName", new SysMap("userName", data), "=");
            }
        }
        if (!(CommUtil.null2String(beginTime).equals(""))) {
            qo.addQuery("obj.addTime", new SysMap("beginTime", CommUtil.formatDate(beginTime)), ">=");
        }
        if (!(CommUtil.null2String(endTime).equals(""))) {
            qo.addQuery("obj.addTime", new SysMap("endTime", CommUtil.formatDate(endTime)), "<=");
        }
        qo.addQuery("obj.user.id", new SysMap("user_id", SecurityUserHolder.getCurrentUser().getId()), "=");
        IPageList pList = this.goodsReturnService.list(qo);
        CommUtil.saveIPageList2ModelAndView("", "", "", pList, mv);
        mv.addObject("data_type", data_type);
        mv.addObject("data", data);
        mv.addObject("beginTime", beginTime);
        mv.addObject("endTime", endTime);
        return mv;
    }

    @SecurityMapping(title = "卖家退款列表", value = "/seller/return_view.htm*", rtype = "seller", rname = "退货管理", rcode = "goods_return_seller", rgroup = "客户服务")
    @RequestMapping({ "/seller/return_view.htm" })
    public ModelAndView return_view(HttpServletRequest request, HttpServletResponse response, String id) {
        ModelAndView mv = new JModelAndView("user/default/usercenter/return_view.html",
                this.configService.getSysConfig(), this.userConfigService.getUserConfig(), 0, request, response);
        GoodsReturn obj = this.goodsReturnService.getObjById(CommUtil.null2Long(id));
        mv.addObject("obj", obj);
        return mv;
    }
}