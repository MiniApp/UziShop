/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.foundation.domain;

import com.iskyshop.core.domain.IdEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "iskyshop_spare_goodsFloor")
public class SpareGoodsFloor extends IdEntity {
    private String title;

    @Column(columnDefinition = "int default 0")
    private int sequence;

    @OneToOne(fetch = FetchType.LAZY)
    private SpareGoodsClass sgc;

    @Column(columnDefinition = "bit default true")
    private boolean display;

    @OneToMany(mappedBy = "sgf")
    private List<SpareGoods> sgs = new ArrayList();

    @Column(columnDefinition = "int default 0")
    private int adver_type;

    @OneToOne(fetch = FetchType.LAZY)
    private AdvertPosition adp;

    @OneToOne(fetch = FetchType.LAZY)
    private Accessory advert_img;

    private String advert_url;

    public boolean isDisplay() {
        return this.display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public int getAdver_type() {
        return this.adver_type;
    }

    public void setAdver_type(int adver_type) {
        this.adver_type = adver_type;
    }

    public AdvertPosition getAdp() {
        return this.adp;
    }

    public void setAdp(AdvertPosition adp) {
        this.adp = adp;
    }

    public Accessory getAdvert_img() {
        return this.advert_img;
    }

    public void setAdvert_img(Accessory advert_img) {
        this.advert_img = advert_img;
    }

    public String getAdvert_url() {
        return this.advert_url;
    }

    public void setAdvert_url(String advert_url) {
        this.advert_url = advert_url;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSequence() {
        return this.sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public SpareGoodsClass getSgc() {
        return this.sgc;
    }

    public void setSgc(SpareGoodsClass sgc) {
        this.sgc = sgc;
    }

    public List<SpareGoods> getSgs() {
        return this.sgs;
    }

    public void setSgs(List<SpareGoods> sgs) {
        this.sgs = sgs;
    }
}