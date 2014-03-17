/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.foundation.domain;

import com.iskyshop.core.domain.IdEntity;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "iskyshop_navigation")
public class Navigation extends IdEntity {
    private String title;

    private int location;

    private String url;

    private String original_url;

    private int sequence;

    private boolean display;

    private int new_win;

    private String type;

    private Long type_id;

    private boolean sysNav;

    public boolean isSysNav() {
        return this.sysNav;
    }

    public void setSysNav(boolean sysNav) {
        this.sysNav = sysNav;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLocation() {
        return this.location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSequence() {
        return this.sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getType_id() {
        return this.type_id;
    }

    public void setType_id(Long type_id) {
        this.type_id = type_id;
    }

    public int getNew_win() {
        return this.new_win;
    }

    public void setNew_win(int new_win) {
        this.new_win = new_win;
    }

    public boolean isDisplay() {
        return this.display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public String getOriginal_url() {
        return this.original_url;
    }

    public void setOriginal_url(String original_url) {
        this.original_url = original_url;
    }
}