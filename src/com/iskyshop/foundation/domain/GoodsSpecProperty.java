/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.foundation.domain;

import com.iskyshop.core.domain.IdEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "iskyshop_goodsspecproperty")
public class GoodsSpecProperty extends IdEntity {
    private int sequence;

    @Column(columnDefinition = "LongText")
    private String value;

    @OneToOne(fetch = FetchType.LAZY, cascade = { javax.persistence.CascadeType.REMOVE })
    private Accessory specImage;

    @ManyToOne(fetch = FetchType.LAZY)
    private GoodsSpecification spec;

    public int getSequence() {
        return this.sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Accessory getSpecImage() {
        return this.specImage;
    }

    public void setSpecImage(Accessory specImage) {
        this.specImage = specImage;
    }

    public GoodsSpecification getSpec() {
        return this.spec;
    }

    public void setSpec(GoodsSpecification spec) {
        this.spec = spec;
    }
}