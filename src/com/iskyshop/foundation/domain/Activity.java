package com.iskyshop.foundation.domain;

import com.iskyshop.core.domain.IdEntity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "iskyshop_activity")
public class Activity extends IdEntity {
    private String ac_title;

    @Temporal(TemporalType.DATE)
    private Date ac_begin_time;

    @Temporal(TemporalType.DATE)
    private Date ac_end_time;

    @OneToOne(cascade = { javax.persistence.CascadeType.REMOVE }, fetch = FetchType.LAZY)
    private Accessory ac_acc;

    private int ac_sequence;

    private int ac_status;

    @Column(columnDefinition = "LongText")
    private String ac_content;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @OneToMany(mappedBy = "act", cascade = { javax.persistence.CascadeType.REMOVE })
    private List<ActivityGoods> ags = new ArrayList();

    @Column(precision = 3, scale = 2)
    private BigDecimal ac_rebate;

    public List<ActivityGoods> getAgs() {
        return this.ags;
    }

    public void setAgs(List<ActivityGoods> ags) {
        this.ags = ags;
    }

    public String getAc_title() {
        return this.ac_title;
    }

    public void setAc_title(String ac_title) {
        this.ac_title = ac_title;
    }

    public Date getAc_begin_time() {
        return this.ac_begin_time;
    }

    public void setAc_begin_time(Date ac_begin_time) {
        this.ac_begin_time = ac_begin_time;
    }

    public Date getAc_end_time() {
        return this.ac_end_time;
    }

    public void setAc_end_time(Date ac_end_time) {
        this.ac_end_time = ac_end_time;
    }

    public Accessory getAc_acc() {
        return this.ac_acc;
    }

    public void setAc_acc(Accessory ac_acc) {
        this.ac_acc = ac_acc;
    }

    public int getAc_sequence() {
        return this.ac_sequence;
    }

    public void setAc_sequence(int ac_sequence) {
        this.ac_sequence = ac_sequence;
    }

    public int getAc_status() {
        return this.ac_status;
    }

    public void setAc_status(int ac_status) {
        this.ac_status = ac_status;
    }

    public String getAc_content() {
        return this.ac_content;
    }

    public void setAc_content(String ac_content) {
        this.ac_content = ac_content;
    }

    public BigDecimal getAc_rebate() {
        return this.ac_rebate;
    }

    public void setAc_rebate(BigDecimal ac_rebate) {
        this.ac_rebate = ac_rebate;
    }
}