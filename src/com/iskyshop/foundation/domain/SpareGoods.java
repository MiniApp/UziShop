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
@Table(name = "iskyshop_spare_goods")
public class SpareGoods extends IdEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    private Accessory main_img;

    @OneToOne(fetch = FetchType.LAZY)
    private Accessory img1;

    @OneToOne(fetch = FetchType.LAZY)
    private Accessory img2;

    @OneToOne(fetch = FetchType.LAZY)
    private Accessory img3;

    @OneToOne(fetch = FetchType.LAZY)
    private Accessory img4;

    @OneToOne(fetch = FetchType.LAZY)
    private Accessory img5;

    @Column(columnDefinition = "bit default false")
    private boolean recommend;

    @ManyToOne(fetch = FetchType.LAZY)
    private SpareGoodsFloor sgf;

    @Column(columnDefinition = "bit default false")
    private boolean viewInFloor;

    @Column(columnDefinition = "int default 0")
    private int status;

    @Column(columnDefinition = "int default 0")
    private int down;

    @Column(columnDefinition = "int default 0")
    private String errorMessage;

    private String title;

    @Column(columnDefinition = "int default 0")
    private int goods_old_price;

    @Column(columnDefinition = "int default 0")
    private int goods_price;

    @OneToOne(fetch = FetchType.LAZY)
    private SpareGoodsClass spareGoodsClass;

    @Column(columnDefinition = "int default 0")
    private int oldAndnew;

    private String phone;

    private String QQ;

    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    private Area area;

    @Column(columnDefinition = "LongText")
    private String content;

    public int getDown() {
        return this.down;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public boolean isRecommend() {
        return this.recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public String getQQ() {
        return this.QQ;
    }

    public void setQQ(String qq) {
        this.QQ = qq;
    }

    public boolean isViewInFloor() {
        return this.viewInFloor;
    }

    public void setViewInFloor(boolean viewInFloor) {
        this.viewInFloor = viewInFloor;
    }

    public SpareGoodsFloor getSgf() {
        return this.sgf;
    }

    public void setSgf(SpareGoodsFloor sgf) {
        this.sgf = sgf;
    }

    public Accessory getImg5() {
        return this.img5;
    }

    public void setImg5(Accessory img5) {
        this.img5 = img5;
    }

    public Accessory getMain_img() {
        return this.main_img;
    }

    public void setMain_img(Accessory main_img) {
        this.main_img = main_img;
    }

    public Accessory getImg1() {
        return this.img1;
    }

    public void setImg1(Accessory img1) {
        this.img1 = img1;
    }

    public Accessory getImg2() {
        return this.img2;
    }

    public void setImg2(Accessory img2) {
        this.img2 = img2;
    }

    public Accessory getImg3() {
        return this.img3;
    }

    public void setImg3(Accessory img3) {
        this.img3 = img3;
    }

    public Accessory getImg4() {
        return this.img4;
    }

    public void setImg4(Accessory img4) {
        this.img4 = img4;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getGoods_old_price() {
        return this.goods_old_price;
    }

    public void setGoods_old_price(int goods_old_price) {
        this.goods_old_price = goods_old_price;
    }

    public int getGoods_price() {
        return this.goods_price;
    }

    public void setGoods_price(int goods_price) {
        this.goods_price = goods_price;
    }

    public SpareGoodsClass getSpareGoodsClass() {
        return this.spareGoodsClass;
    }

    public void setSpareGoodsClass(SpareGoodsClass spareGoodsClass) {
        this.spareGoodsClass = spareGoodsClass;
    }

    public int getOldAndnew() {
        return this.oldAndnew;
    }

    public void setOldAndnew(int oldAndnew) {
        this.oldAndnew = oldAndnew;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Area getArea() {
        return this.area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}