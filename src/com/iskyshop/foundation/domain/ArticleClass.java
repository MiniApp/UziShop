/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.foundation.domain;

import com.iskyshop.core.domain.IdEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "iskyshop_articleclass")
public class ArticleClass extends IdEntity {
    private String className;

    private int sequence;

    private int level;

    private String mark;

    private boolean sysClass;

    @ManyToOne(fetch = FetchType.LAZY)
    private ArticleClass parent;

    @OneToMany(mappedBy = "parent", cascade = { javax.persistence.CascadeType.REMOVE })
    private List<ArticleClass> childs = new ArrayList();

    @OneToMany(mappedBy = "articleClass", cascade = { javax.persistence.CascadeType.REMOVE })
    @OrderBy("addTime desc")
    private List<Article> articles = new ArrayList();

    public int getSequence() {
        return this.sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isSysClass() {
        return this.sysClass;
    }

    public void setSysClass(boolean sysClass) {
        this.sysClass = sysClass;
    }

    public ArticleClass getParent() {
        return this.parent;
    }

    public void setParent(ArticleClass parent) {
        this.parent = parent;
    }

    public List<ArticleClass> getChilds() {
        return this.childs;
    }

    public void setChilds(List<ArticleClass> childs) {
        this.childs = childs;
    }

    public String getMark() {
        return this.mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public List<Article> getArticles() {
        return this.articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}