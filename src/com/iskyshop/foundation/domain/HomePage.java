/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.foundation.domain;

import com.iskyshop.core.domain.IdEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "iskyshop_homepage")
public class HomePage extends IdEntity {

    @OneToOne(fetch = FetchType.LAZY)
    private User owner;

    @OneToMany(mappedBy = "homepage", cascade = { javax.persistence.CascadeType.REMOVE })
    private List<Visit> customers = new ArrayList();

    public User getOwner() {
        return this.owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Visit> getCustomers() {
        return this.customers;
    }

    public void setCustomers(List<Visit> customers) {
        this.customers = customers;
    }
}