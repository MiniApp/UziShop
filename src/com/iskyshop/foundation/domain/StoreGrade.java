package com.iskyshop.foundation.domain;

import com.iskyshop.core.domain.IdEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "iskyshop_storegrade")
public class StoreGrade extends IdEntity {
    private String gradeName;

    private boolean sysGrade;

    private boolean audit;

    @Column(columnDefinition = "int default 50")
    private int goodsCount;

    @Column(columnDefinition = "int default 0")
    private int sequence;

    private float spaceSize;

    @Lob
    @Column(columnDefinition = "LongText")
    private String content;

    private String price;

    private String add_funciton;

    private String templates;

    @Column(columnDefinition = "int default 0")
    private int gradeLevel;

    @Column(columnDefinition = "int default 0")
    private int acount_num;

    public int getAcount_num() {
        return this.acount_num;
    }

    public void setAcount_num(int acount_num) {
        this.acount_num = acount_num;
    }

    public String getGradeName() {
        return this.gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public boolean isAudit() {
        return this.audit;
    }

    public void setAudit(boolean audit) {
        this.audit = audit;
    }

    public int getGoodsCount() {
        return this.goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAdd_funciton() {
        return this.add_funciton;
    }

    public void setAdd_funciton(String add_funciton) {
        this.add_funciton = add_funciton;
    }

    public String getTemplates() {
        return this.templates;
    }

    public void setTemplates(String templates) {
        this.templates = templates;
    }

    public boolean isSysGrade() {
        return this.sysGrade;
    }

    public void setSysGrade(boolean sysGrade) {
        this.sysGrade = sysGrade;
    }

    public int getSequence() {
        return this.sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public float getSpaceSize() {
        return this.spaceSize;
    }

    public void setSpaceSize(float spaceSize) {
        this.spaceSize = spaceSize;
    }

    public int getGradeLevel() {
        return this.gradeLevel;
    }

    public void setGradeLevel(int gradeLevel) {
        this.gradeLevel = gradeLevel;
    }
}