/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.foundation.domain;

import com.iskyshop.core.domain.IdEntity;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "iskyshop_report")
public class Report extends IdEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Goods goods;

    private int status;

    @ManyToOne(fetch = FetchType.LAZY)
    private ReportSubject subject;

    @OneToOne(fetch = FetchType.LAZY)
    private Accessory acc1;

    @OneToOne(fetch = FetchType.LAZY)
    private Accessory acc2;

    @OneToOne(fetch = FetchType.LAZY)
    private Accessory acc3;

    @Lob
    @Column(columnDefinition = "LongText")
    private String content;

    private int result;

    @Lob
    @Column(columnDefinition = "LongText")
    private String handle_info;

    private Date handle_Time;

    public int getResult() {
        return this.result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public Date getHandle_Time() {
        return this.handle_Time;
    }

    public void setHandle_Time(Date handle_Time) {
        this.handle_Time = handle_Time;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Goods getGoods() {
        return this.goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ReportSubject getSubject() {
        return this.subject;
    }

    public void setSubject(ReportSubject subject) {
        this.subject = subject;
    }

    public Accessory getAcc1() {
        return this.acc1;
    }

    public void setAcc1(Accessory acc1) {
        this.acc1 = acc1;
    }

    public Accessory getAcc2() {
        return this.acc2;
    }

    public void setAcc2(Accessory acc2) {
        this.acc2 = acc2;
    }

    public Accessory getAcc3() {
        return this.acc3;
    }

    public void setAcc3(Accessory acc3) {
        this.acc3 = acc3;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHandle_info() {
        return this.handle_info;
    }

    public void setHandle_info(String handle_info) {
        this.handle_info = handle_info;
    }
}