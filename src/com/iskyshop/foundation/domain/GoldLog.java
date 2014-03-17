/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.foundation.domain;

import com.iskyshop.core.domain.IdEntity;
import java.util.Date;
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
@Table(name = "iskyshop_gold_log")
public class GoldLog extends IdEntity {
    private int gl_type;

    private int gl_money;

    private int gl_count;

    private String gl_payment;

    @Column(columnDefinition = "LongText")
    private String gl_content;

    @ManyToOne(fetch = FetchType.LAZY)
    private User gl_user;

    @ManyToOne(fetch = FetchType.LAZY)
    private User gl_admin;

    private Date gl_admin_time;

    @Column(columnDefinition = "LongText")
    private String gl_admin_content;

    @OneToOne(fetch = FetchType.LAZY)
    private GoldRecord gr;

    public int getGl_type() {
        return this.gl_type;
    }

    public void setGl_type(int gl_type) {
        this.gl_type = gl_type;
    }

    public int getGl_count() {
        return this.gl_count;
    }

    public void setGl_count(int gl_count) {
        this.gl_count = gl_count;
    }

    public String getGl_content() {
        return this.gl_content;
    }

    public void setGl_content(String gl_content) {
        this.gl_content = gl_content;
    }

    public User getGl_user() {
        return this.gl_user;
    }

    public void setGl_user(User gl_user) {
        this.gl_user = gl_user;
    }

    public User getGl_admin() {
        return this.gl_admin;
    }

    public void setGl_admin(User gl_admin) {
        this.gl_admin = gl_admin;
    }

    public Date getGl_admin_time() {
        return this.gl_admin_time;
    }

    public void setGl_admin_time(Date gl_admin_time) {
        this.gl_admin_time = gl_admin_time;
    }

    public String getGl_admin_content() {
        return this.gl_admin_content;
    }

    public void setGl_admin_content(String gl_admin_content) {
        this.gl_admin_content = gl_admin_content;
    }

    public GoldRecord getGr() {
        return this.gr;
    }

    public void setGr(GoldRecord gr) {
        this.gr = gr;
    }

    public String getGl_payment() {
        return this.gl_payment;
    }

    public void setGl_payment(String gl_payment) {
        this.gl_payment = gl_payment;
    }

    public int getGl_money() {
        return this.gl_money;
    }

    public void setGl_money(int gl_money) {
        this.gl_money = gl_money;
    }
}