package com.iskyshop.foundation.domain;

import com.iskyshop.core.domain.IdEntity;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "iskyshop_goods_returnlog")
public class GoodsReturnLog extends IdEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private OrderForm of;

    @ManyToOne(fetch = FetchType.LAZY)
    private GoodsReturn gr;

    @ManyToOne(fetch = FetchType.LAZY)
    private User return_user;

    public OrderForm getOf() {
        return this.of;
    }

    public void setOf(OrderForm of) {
        this.of = of;
    }

    public GoodsReturn getGr() {
        return this.gr;
    }

    public void setGr(GoodsReturn gr) {
        this.gr = gr;
    }

    public User getReturn_user() {
        return this.return_user;
    }

    public void setReturn_user(User return_user) {
        this.return_user = return_user;
    }
}