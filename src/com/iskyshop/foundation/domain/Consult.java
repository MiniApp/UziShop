/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.foundation.domain;

import com.iskyshop.core.domain.IdEntity;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "iskyshop_consult")
public class Consult extends IdEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Goods goods;

    @Column(columnDefinition = "LongText")
    private String consult_content;

    private boolean reply;

    @Column(columnDefinition = "LongText")
    private String consult_reply;

    @ManyToOne(fetch = FetchType.LAZY)
    private User consult_user;

    @ManyToOne(fetch = FetchType.LAZY)
    private User reply_user;

    private Date reply_time;

    private String consult_email;

    public boolean isReply() {
        return this.reply;
    }

    public void setReply(boolean reply) {
        this.reply = reply;
    }

    public String getConsult_email() {
        return this.consult_email;
    }

    public void setConsult_email(String consult_email) {
        this.consult_email = consult_email;
    }

    public Goods getGoods() {
        return this.goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public String getConsult_content() {
        return this.consult_content;
    }

    public void setConsult_content(String consult_content) {
        this.consult_content = consult_content;
    }

    public String getConsult_reply() {
        return this.consult_reply;
    }

    public void setConsult_reply(String consult_reply) {
        this.consult_reply = consult_reply;
    }

    public User getConsult_user() {
        return this.consult_user;
    }

    public void setConsult_user(User consult_user) {
        this.consult_user = consult_user;
    }

    public User getReply_user() {
        return this.reply_user;
    }

    public void setReply_user(User reply_user) {
        this.reply_user = reply_user;
    }

    public Date getReply_time() {
        return this.reply_time;
    }

    public void setReply_time(Date reply_time) {
        this.reply_time = reply_time;
    }
}