package com.iskyshop.foundation.domain;

import com.iskyshop.core.domain.IdEntity;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "iskyshop_predeposit_cash")
public class PredepositCash extends IdEntity {
    private String cash_sn;

    private String cash_payment;

    @Column(precision = 12, scale = 2)
    private BigDecimal cash_amount;

    @ManyToOne(fetch = FetchType.LAZY)
    private User cash_user;

    private String cash_userName;

    private String cash_bank;

    private String cash_account;

    private int cash_pay_status;

    private int cash_status;

    @Column(columnDefinition = "LongText")
    private String cash_info;

    @ManyToOne(fetch = FetchType.LAZY)
    private User cash_admin;

    @Column(columnDefinition = "LongText")
    private String cash_admin_info;

    public String getCash_payment() {
        return this.cash_payment;
    }

    public void setCash_payment(String cash_payment) {
        this.cash_payment = cash_payment;
    }

    public BigDecimal getCash_amount() {
        return this.cash_amount;
    }

    public void setCash_amount(BigDecimal cash_amount) {
        this.cash_amount = cash_amount;
    }

    public User getCash_user() {
        return this.cash_user;
    }

    public void setCash_user(User cash_user) {
        this.cash_user = cash_user;
    }

    public String getCash_userName() {
        return this.cash_userName;
    }

    public void setCash_userName(String cash_userName) {
        this.cash_userName = cash_userName;
    }

    public String getCash_bank() {
        return this.cash_bank;
    }

    public void setCash_bank(String cash_bank) {
        this.cash_bank = cash_bank;
    }

    public String getCash_account() {
        return this.cash_account;
    }

    public void setCash_account(String cash_account) {
        this.cash_account = cash_account;
    }

    public int getCash_pay_status() {
        return this.cash_pay_status;
    }

    public void setCash_pay_status(int cash_pay_status) {
        this.cash_pay_status = cash_pay_status;
    }

    public int getCash_status() {
        return this.cash_status;
    }

    public void setCash_status(int cash_status) {
        this.cash_status = cash_status;
    }

    public String getCash_sn() {
        return this.cash_sn;
    }

    public void setCash_sn(String cash_sn) {
        this.cash_sn = cash_sn;
    }

    public String getCash_info() {
        return this.cash_info;
    }

    public void setCash_info(String cash_info) {
        this.cash_info = cash_info;
    }

    public User getCash_admin() {
        return this.cash_admin;
    }

    public void setCash_admin(User cash_admin) {
        this.cash_admin = cash_admin;
    }

    public String getCash_admin_info() {
        return this.cash_admin_info;
    }

    public void setCash_admin_info(String cash_admin_info) {
        this.cash_admin_info = cash_admin_info;
    }
}