/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.foundation.domain;

import com.iskyshop.core.domain.IdEntity;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "iskyshop_integral_goodscart")
public class IntegralGoodsCart extends IdEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private IntegralGoods goods;

    private int count;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { javax.persistence.CascadeType.REMOVE })
    private IntegralGoodsOrder order;

    @Column(precision = 12, scale = 2)
    private BigDecimal trans_fee;

    private int integral;

    public IntegralGoodsOrder getOrder() {
        return this.order;
    }

    public void setOrder(IntegralGoodsOrder order) {
        this.order = order;
    }

    public BigDecimal getTrans_fee() {
        return this.trans_fee;
    }

    public void setTrans_fee(BigDecimal trans_fee) {
        this.trans_fee = trans_fee;
    }

    public int getIntegral() {
        return this.integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public IntegralGoods getGoods() {
        return this.goods;
    }

    public void setGoods(IntegralGoods goods) {
        this.goods = goods;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}