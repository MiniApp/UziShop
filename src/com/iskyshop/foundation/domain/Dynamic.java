/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
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
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "iskyshop_dynamic")
public class Dynamic extends IdEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Goods goods;

    @ManyToOne(fetch = FetchType.LAZY)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(columnDefinition = "bit default false")
    private boolean locked;

    @OneToOne(fetch = FetchType.LAZY)
    private Accessory img;

    @Column(columnDefinition = "LongText")
    private String content;

    @Column(columnDefinition = "int default 0")
    private int turnNum;

    @Column(columnDefinition = "int default 0")
    private int discussNum;

    @Column(columnDefinition = "int default 0")
    private int praiseNum;

    @ManyToOne(fetch = FetchType.LAZY)
    private Dynamic dissParent;

    @ManyToOne(fetch = FetchType.LAZY)
    private Dynamic turnParent;

    @OneToMany(mappedBy = "dissParent", cascade = { javax.persistence.CascadeType.REMOVE })
    List<Dynamic> childs = new ArrayList();

    @Column(columnDefinition = "bit default true")
    private boolean display;

    public boolean isDisplay() {
        return this.display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public boolean isLocked() {
        return this.locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public Accessory getImg() {
        return this.img;
    }

    public void setImg(Accessory img) {
        this.img = img;
    }

    public Store getStore() {
        return this.store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public List<Dynamic> getChilds() {
        return this.childs;
    }

    public void setChilds(List<Dynamic> childs) {
        this.childs = childs;
    }

    public Goods getGoods() {
        return this.goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getTurnNum() {
        return this.turnNum;
    }

    public void setTurnNum(int turnNum) {
        this.turnNum = turnNum;
    }

    public int getDiscussNum() {
        return this.discussNum;
    }

    public void setDiscussNum(int discussNum) {
        this.discussNum = discussNum;
    }

    public int getPraiseNum() {
        return this.praiseNum;
    }

    public void setPraiseNum(int praiseNum) {
        this.praiseNum = praiseNum;
    }

    public Dynamic getDissParent() {
        return this.dissParent;
    }

    public void setDissParent(Dynamic dissParent) {
        this.dissParent = dissParent;
    }

    public Dynamic getTurnParent() {
        return this.turnParent;
    }

    public void setTurnParent(Dynamic turnParent) {
        this.turnParent = turnParent;
    }
}