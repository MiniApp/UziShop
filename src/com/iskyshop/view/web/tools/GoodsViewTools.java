/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.view.web.tools;

import com.iskyshop.core.security.support.SecurityUserHolder;
import com.iskyshop.core.tools.CommUtil;
import com.iskyshop.foundation.domain.Goods;
import com.iskyshop.foundation.domain.GoodsClass;
import com.iskyshop.foundation.domain.GoodsSpecProperty;
import com.iskyshop.foundation.domain.GoodsSpecification;
import com.iskyshop.foundation.domain.User;
import com.iskyshop.foundation.domain.UserGoodsClass;
import com.iskyshop.foundation.service.IGoodsClassService;
import com.iskyshop.foundation.service.IGoodsService;
import com.iskyshop.foundation.service.IUserGoodsClassService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GoodsViewTools {

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IGoodsClassService goodsClassService;

    @Autowired
    private IUserGoodsClassService userGoodsClassService;

    public List<GoodsSpecification> generic_spec(String id) {
        List specs = new ArrayList();
        if ((id != null) && (!(id.equals("")))) {
            Goods goods = this.goodsService.getObjById(Long.valueOf(Long.parseLong(id)));
            for (GoodsSpecProperty gsp : goods.getGoods_specs()) {
                GoodsSpecification spec = gsp.getSpec();
                if (!(specs.contains(spec))) {
                    specs.add(spec);
                }
            }
        }
        Collections.sort(specs, new Comparator() {
            public int compare(GoodsSpecification gs1, GoodsSpecification gs2) {
                return (gs1.getSequence() - gs2.getSequence());
            }

            @Override
            public int compare(Object o1, Object o2) {
                return 0;
            }
        });
        return specs;
    }

    public List<UserGoodsClass> query_user_class(String pid) {
        List list = new ArrayList();
        if ((pid == null) || (pid.equals(""))) {
            Map map = new HashMap();
            map.put("uid", SecurityUserHolder.getCurrentUser().getId());
            list = this.userGoodsClassService
                    .query("select obj from UserGoodsClass obj where obj.parent.id is null and obj.user.id = :uid order by obj.sequence asc",
                            map, -1, -1);
        } else {
            Map params = new HashMap();
            params.put("pid", Long.valueOf(Long.parseLong(pid)));
            params.put("uid", SecurityUserHolder.getCurrentUser().getId());
            list = this.userGoodsClassService
                    .query("select obj from UserGoodsClass obj where obj.parent.id=:pid and obj.user.id = :uid order by obj.sequence asc",
                            params, -1, -1);
        }
        return list;
    }

    public List<Goods> query_with_gc(String gc_id, int count) {
        List list = new ArrayList();
        GoodsClass gc = this.goodsClassService.getObjById(CommUtil.null2Long(gc_id));
        if (gc != null) {
            Set ids = genericIds(gc);
            Map params = new HashMap();
            params.put("ids", ids);
            params.put("goods_status", Integer.valueOf(0));
            list = this.goodsService
                    .query("select obj from Goods obj where obj.gc.id in (:ids) and obj.goods_status=:goods_status order by obj.goods_click desc",
                            params, 0, count);
        }
        return list;
    }

    private Set<Long> genericIds(GoodsClass gc) {
        Set ids = new HashSet();
        ids.add(gc.getId());
        for (GoodsClass child : gc.getChilds()) {
            Set<Long> cids = genericIds(child);
            for (Long cid : cids) {
                ids.add(cid);
            }
            ids.add(child.getId());
        }
        return ids;
    }

    public List<Goods> sort_sale_goods(String store_id, int count) {
        List list = new ArrayList();
        Map params = new HashMap();
        params.put("store_id", CommUtil.null2Long(store_id));
        params.put("goods_status", Integer.valueOf(0));
        list = this.goodsService
                .query("select obj from Goods obj where obj.goods_store.id=:store_id and obj.goods_status=:goods_status order by obj.goods_salenum desc",
                        params, 0, count);
        return list;
    }

    public List<Goods> sort_collect_goods(String store_id, int count) {
        List list = new ArrayList();
        Map params = new HashMap();
        params.put("store_id", CommUtil.null2Long(store_id));
        params.put("goods_status", Integer.valueOf(0));
        list = this.goodsService
                .query("select obj from Goods obj where obj.goods_store.id=:store_id and obj.goods_status=:goods_status order by obj.goods_collect desc",
                        params, 0, count);
        return list;
    }

    public List<Goods> query_combin_goods(String id) {
        return this.goodsService.getObjById(CommUtil.null2Long(id)).getCombin_goods();
    }
}