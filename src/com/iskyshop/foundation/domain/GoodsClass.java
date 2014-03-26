package com.iskyshop.foundation.domain;

import com.iskyshop.core.domain.IdEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "iskyshop_goodsclass")
public class GoodsClass extends IdEntity {
    private String className;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @OneToMany(mappedBy = "parent")
    @OrderBy("sequence asc")
    private List<GoodsClass> childs = new ArrayList();

    @ManyToOne(fetch = FetchType.LAZY)
    private GoodsClass parent;

    private int sequence;

    private int level;

    private boolean display;

    private boolean recommend;

    @ManyToOne(fetch = FetchType.LAZY)
    private GoodsType goodsType;

    @Column(columnDefinition = "LongText")
    private String seo_keywords;

    @Column(columnDefinition = "LongText")
    private String seo_description;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @OneToMany(mappedBy = "gc")
    private List<Goods> goods_list = new ArrayList();

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @OneToMany(mappedBy = "gc")
    private List<GoodsClassStaple> gcss = new ArrayList();

    @Column(columnDefinition = "int default 0")
    private int icon_type;

    private String icon_sys;

    @OneToOne
    private Accessory icon_acc;

    public int getIcon_type() {
        return this.icon_type;
    }

    public void setIcon_type(int icon_type) {
        this.icon_type = icon_type;
    }

    public String getIcon_sys() {
        return this.icon_sys;
    }

    public void setIcon_sys(String icon_sys) {
        this.icon_sys = icon_sys;
    }

    public Accessory getIcon_acc() {
        return this.icon_acc;
    }

    public void setIcon_acc(Accessory icon_acc) {
        this.icon_acc = icon_acc;
    }

    public List<Goods> getGoods_list() {
        return this.goods_list;
    }

    public void setGoods_list(List<Goods> goods_list) {
        this.goods_list = goods_list;
    }

    public List<GoodsClassStaple> getGcss() {
        return this.gcss;
    }

    public void setGcss(List<GoodsClassStaple> gcss) {
        this.gcss = gcss;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getSequence() {
        return this.sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isDisplay() {
        return this.display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public boolean isRecommend() {
        return this.recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public GoodsType getGoodsType() {
        return this.goodsType;
    }

    public void setGoodsType(GoodsType goodsType) {
        this.goodsType = goodsType;
    }

    public List<GoodsClass> getChilds() {
        return this.childs;
    }

    public void setChilds(List<GoodsClass> childs) {
        this.childs = childs;
    }

    public GoodsClass getParent() {
        return this.parent;
    }

    public void setParent(GoodsClass parent) {
        this.parent = parent;
    }

    public String getSeo_keywords() {
        return this.seo_keywords;
    }

    public void setSeo_keywords(String seo_keywords) {
        this.seo_keywords = seo_keywords;
    }

    public String getSeo_description() {
        return this.seo_description;
    }

    public void setSeo_description(String seo_description) {
        this.seo_description = seo_description;
    }
}