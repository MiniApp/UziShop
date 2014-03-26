package com.iskyshop.foundation.domain;

import com.iskyshop.core.annotation.Lock;
import com.iskyshop.core.domain.IdEntity;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "iskyshop_advert")
public class Advert extends IdEntity {
    private String ad_title;

    @Lock
    @Temporal(TemporalType.DATE)
    private Date ad_begin_time;

    @Lock
    @Temporal(TemporalType.DATE)
    private Date ad_end_time;

    @ManyToOne(fetch = FetchType.LAZY)
    private AdvertPosition ad_ap;

    @Lock
    private int ad_status;

    @OneToOne(cascade = { javax.persistence.CascadeType.REMOVE }, fetch = FetchType.LAZY)
    private Accessory ad_acc;

    private String ad_text;

    private int ad_slide_sequence;

    @ManyToOne(fetch = FetchType.LAZY)
    private User ad_user;

    private String ad_url;

    private int ad_click_num;

    private int ad_gold;

    public int getAd_gold() {
        return this.ad_gold;
    }

    public void setAd_gold(int ad_gold) {
        this.ad_gold = ad_gold;
    }

    public int getAd_status() {
        return this.ad_status;
    }

    public void setAd_status(int ad_status) {
        this.ad_status = ad_status;
    }

    public String getAd_title() {
        return this.ad_title;
    }

    public void setAd_title(String ad_title) {
        this.ad_title = ad_title;
    }

    public Date getAd_begin_time() {
        return this.ad_begin_time;
    }

    public void setAd_begin_time(Date ad_begin_time) {
        this.ad_begin_time = ad_begin_time;
    }

    public Date getAd_end_time() {
        return this.ad_end_time;
    }

    public void setAd_end_time(Date ad_end_time) {
        this.ad_end_time = ad_end_time;
    }

    public AdvertPosition getAd_ap() {
        return this.ad_ap;
    }

    public void setAd_ap(AdvertPosition ad_ap) {
        this.ad_ap = ad_ap;
    }

    public int getAd_click_num() {
        return this.ad_click_num;
    }

    public void setAd_click_num(int ad_click_num) {
        this.ad_click_num = ad_click_num;
    }

    public Accessory getAd_acc() {
        return this.ad_acc;
    }

    public void setAd_acc(Accessory ad_acc) {
        this.ad_acc = ad_acc;
    }

    public User getAd_user() {
        return this.ad_user;
    }

    public void setAd_user(User ad_user) {
        this.ad_user = ad_user;
    }

    public String getAd_text() {
        return this.ad_text;
    }

    public void setAd_text(String ad_text) {
        this.ad_text = ad_text;
    }

    public String getAd_url() {
        return this.ad_url;
    }

    public void setAd_url(String ad_url) {
        this.ad_url = ad_url;
    }

    public int getAd_slide_sequence() {
        return this.ad_slide_sequence;
    }

    public void setAd_slide_sequence(int ad_slide_sequence) {
        this.ad_slide_sequence = ad_slide_sequence;
    }
}