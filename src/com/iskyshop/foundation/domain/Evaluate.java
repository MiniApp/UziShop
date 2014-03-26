package com.iskyshop.foundation.domain;

import com.iskyshop.core.domain.IdEntity;
import java.math.BigDecimal;
import java.util.Date;
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
@Table(name = "iskyshop_evaluate")
public class Evaluate extends IdEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Goods evaluate_goods;

    @Lob
    @Column(columnDefinition = "LongText")
    private String goods_spec;

    @ManyToOne(fetch = FetchType.LAZY)
    private OrderForm of;

    private String evaluate_type;

    private int evaluate_buyer_val;

    @Column(precision = 12, scale = 2)
    private BigDecimal description_evaluate;

    @Column(precision = 12, scale = 2)
    private BigDecimal service_evaluate;

    @Column(precision = 12, scale = 2)
    private BigDecimal ship_evaluate;

    private int evaluate_seller_val;

    @Column(columnDefinition = "LongText")
    private String evaluate_info;

    @ManyToOne(fetch = FetchType.LAZY)
    private User evaluate_user;

    @ManyToOne(fetch = FetchType.LAZY)
    private User evaluate_seller_user;

    private Date evaluate_seller_time;

    @Column(columnDefinition = "LongText")
    private String evaluate_seller_info;

    private int evaluate_status;

    @Column(columnDefinition = "LongText")
    private String evaluate_admin_info;

    public int getEvaluate_status() {
        return this.evaluate_status;
    }

    public void setEvaluate_status(int evaluate_status) {
        this.evaluate_status = evaluate_status;
    }

    public Date getEvaluate_seller_time() {
        return this.evaluate_seller_time;
    }

    public void setEvaluate_seller_time(Date evaluate_seller_time) {
        this.evaluate_seller_time = evaluate_seller_time;
    }

    public User getEvaluate_user() {
        return this.evaluate_user;
    }

    public void setEvaluate_user(User evaluate_user) {
        this.evaluate_user = evaluate_user;
    }

    public Goods getEvaluate_goods() {
        return this.evaluate_goods;
    }

    public void setEvaluate_goods(Goods evaluate_goods) {
        this.evaluate_goods = evaluate_goods;
    }

    public OrderForm getOf() {
        return this.of;
    }

    public void setOf(OrderForm of) {
        this.of = of;
    }

    public String getEvaluate_info() {
        return this.evaluate_info;
    }

    public void setEvaluate_info(String evaluate_info) {
        this.evaluate_info = evaluate_info;
    }

    public String getEvaluate_type() {
        return this.evaluate_type;
    }

    public void setEvaluate_type(String evaluate_type) {
        this.evaluate_type = evaluate_type;
    }

    public String getGoods_spec() {
        return this.goods_spec;
    }

    public void setGoods_spec(String goods_spec) {
        this.goods_spec = goods_spec;
    }

    public int getEvaluate_buyer_val() {
        return this.evaluate_buyer_val;
    }

    public void setEvaluate_buyer_val(int evaluate_buyer_val) {
        this.evaluate_buyer_val = evaluate_buyer_val;
    }

    public int getEvaluate_seller_val() {
        return this.evaluate_seller_val;
    }

    public void setEvaluate_seller_val(int evaluate_seller_val) {
        this.evaluate_seller_val = evaluate_seller_val;
    }

    public User getEvaluate_seller_user() {
        return this.evaluate_seller_user;
    }

    public void setEvaluate_seller_user(User evaluate_seller_user) {
        this.evaluate_seller_user = evaluate_seller_user;
    }

    public String getEvaluate_seller_info() {
        return this.evaluate_seller_info;
    }

    public void setEvaluate_seller_info(String evaluate_seller_info) {
        this.evaluate_seller_info = evaluate_seller_info;
    }

    public String getEvaluate_admin_info() {
        return this.evaluate_admin_info;
    }

    public void setEvaluate_admin_info(String evaluate_admin_info) {
        this.evaluate_admin_info = evaluate_admin_info;
    }

    public BigDecimal getDescription_evaluate() {
        return this.description_evaluate;
    }

    public void setDescription_evaluate(BigDecimal description_evaluate) {
        this.description_evaluate = description_evaluate;
    }

    public BigDecimal getService_evaluate() {
        return this.service_evaluate;
    }

    public void setService_evaluate(BigDecimal service_evaluate) {
        this.service_evaluate = service_evaluate;
    }

    public BigDecimal getShip_evaluate() {
        return this.ship_evaluate;
    }

    public void setShip_evaluate(BigDecimal ship_evaluate) {
        this.ship_evaluate = ship_evaluate;
    }
}