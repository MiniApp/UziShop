/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.foundation.domain;

import com.iskyshop.core.domain.IdEntity;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "iskyshop_group_price_range")
public class GroupPriceRange extends IdEntity {
    private String gpr_name;

    private int gpr_begin;

    private int gpr_end;

    public String getGpr_name() {
        return this.gpr_name;
    }

    public void setGpr_name(String gpr_name) {
        this.gpr_name = gpr_name;
    }

    public int getGpr_begin() {
        return this.gpr_begin;
    }

    public void setGpr_begin(int gpr_begin) {
        this.gpr_begin = gpr_begin;
    }

    public int getGpr_end() {
        return this.gpr_end;
    }

    public void setGpr_end(int gpr_end) {
        this.gpr_end = gpr_end;
    }
}