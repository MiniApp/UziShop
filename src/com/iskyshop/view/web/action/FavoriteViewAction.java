/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.view.web.action;

import com.iskyshop.core.security.support.SecurityUserHolder;
import com.iskyshop.core.tools.CommUtil;
import com.iskyshop.foundation.domain.Favorite;
import com.iskyshop.foundation.domain.Goods;
import com.iskyshop.foundation.domain.Store;
import com.iskyshop.foundation.domain.User;
import com.iskyshop.foundation.service.IFavoriteService;
import com.iskyshop.foundation.service.IGoodsService;
import com.iskyshop.foundation.service.IStoreService;
import com.iskyshop.foundation.service.ISysConfigService;
import com.iskyshop.foundation.service.IUserConfigService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FavoriteViewAction {

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private IUserConfigService userConfigService;

    @Autowired
    private IFavoriteService favoriteService;

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IStoreService storeService;

    @RequestMapping({ "/add_goods_favorite.htm" })
    public void add_goods_favorite(HttpServletResponse response, String id) {
        Map params = new HashMap();
        params.put("user_id", SecurityUserHolder.getCurrentUser().getId());
        params.put("goods_id", CommUtil.null2Long(id));
        List list = this.favoriteService.query(
                "select obj from Favorite obj where obj.user.id=:user_id and obj.goods.id=:goods_id", params, -1, -1);
        int ret = 0;
        if (list.size() == 0) {
            Goods goods = this.goodsService.getObjById(CommUtil.null2Long(id));
            Favorite obj = new Favorite();
            obj.setAddTime(new Date());
            obj.setType(0);
            obj.setUser(SecurityUserHolder.getCurrentUser());
            obj.setGoods(goods);
            this.favoriteService.save(obj);
            goods.setGoods_collect(goods.getGoods_collect() + 1);
            this.goodsService.update(goods);
        } else {
            ret = 1;
        }
        response.setContentType("text/plain");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        try {
            PrintWriter writer = response.getWriter();
            writer.print(ret);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping({ "/add_store_favorite.htm" })
    public void add_store_favorite(HttpServletResponse response, String id) {
        Map params = new HashMap();
        params.put("user_id", SecurityUserHolder.getCurrentUser().getId());
        params.put("store_id", CommUtil.null2Long(id));
        List list = this.favoriteService.query(
                "select obj from Favorite obj where obj.user.id=:user_id and obj.store.id=:store_id", params, -1, -1);
        int ret = 0;
        if (list.size() == 0) {
            Favorite obj = new Favorite();
            obj.setAddTime(new Date());
            obj.setType(1);
            obj.setUser(SecurityUserHolder.getCurrentUser());
            obj.setStore(this.storeService.getObjById(CommUtil.null2Long(id)));
            this.favoriteService.save(obj);
            Store store = obj.getStore();
            store.setFavorite_count(store.getFavorite_count() + 1);
            this.storeService.update(store);
        } else {
            ret = 1;
        }
        response.setContentType("text/plain");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        try {
            PrintWriter writer = response.getWriter();
            writer.print(ret);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}