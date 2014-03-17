/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.foundation.domain;

import com.iskyshop.core.domain.IdEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "iskyshop_chatting")
public class Chatting extends IdEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private User user1;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user2;

    @Column(columnDefinition = "int default 0")
    private int type;

    @OneToMany(mappedBy = "chatting", cascade = { javax.persistence.CascadeType.REMOVE })
    private List<ChattingLog> logs = new ArrayList();

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public User getUser1() {
        return this.user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return this.user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public List<ChattingLog> getLogs() {
        return this.logs;
    }

    public void setLogs(List<ChattingLog> logs) {
        this.logs = logs;
    }
}