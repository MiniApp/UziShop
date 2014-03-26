package com.iskyshop.foundation.domain;

import com.iskyshop.core.domain.IdEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "iskyshop_order_log")
public class OrderFormLog extends IdEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private OrderForm of;

    private String log_info;

    @ManyToOne(fetch = FetchType.LAZY)
    private User log_user;

    @Lob
    @Column(columnDefinition = "LongText")
    private String state_info;

    public String getState_info() {
        return this.state_info;
    }

    public void setState_info(String state_info) {
        this.state_info = state_info;
    }

    public OrderForm getOf() {
        return this.of;
    }

    public void setOf(OrderForm of) {
        this.of = of;
    }

    public String getLog_info() {
        return this.log_info;
    }

    public void setLog_info(String log_info) {
        this.log_info = log_info;
    }

    public User getLog_user() {
        return this.log_user;
    }

    public void setLog_user(User log_user) {
        this.log_user = log_user;
    }
}