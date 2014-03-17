/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.foundation.domain;

import com.iskyshop.core.domain.IdEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "iskyshop_complaint_goods")
public class ComplaintGoods extends IdEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Goods goods;

    @Column(columnDefinition = "LongText")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Complaint complaint;

    public Goods getGoods() {
        return this.goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Complaint getComplaint() {
        return this.complaint;
    }

    public void setComplaint(Complaint complaint) {
        this.complaint = complaint;
    }
}