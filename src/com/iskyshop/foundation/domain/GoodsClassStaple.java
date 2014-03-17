/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
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
@Table(name = "iskyshop_goodsclassstaple")
public class GoodsClassStaple extends IdEntity {
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private GoodsClass gc;

    @ManyToOne(fetch = FetchType.LAZY)
    private Store store;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GoodsClass getGc() {
        return this.gc;
    }

    public void setGc(GoodsClass gc) {
        this.gc = gc;
    }

    public Store getStore() {
        return this.store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}