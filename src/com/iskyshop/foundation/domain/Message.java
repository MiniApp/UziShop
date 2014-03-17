/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.iskyshop.foundation.domain;

import com.iskyshop.core.domain.IdEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "iskyshop_message")
public class Message extends IdEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private User fromUser;

    @ManyToOne(fetch = FetchType.LAZY)
    private User toUser;

    private int status;

    @Column(columnDefinition = "int default 0")
    private int reply_status;

    private String title;

    @Lob
    @Column(columnDefinition = "LongText")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Message parent;

    @OneToMany(mappedBy = "parent", cascade = { javax.persistence.CascadeType.REMOVE })
    List<Message> replys = new ArrayList();

    private int type;

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public User getFromUser() {
        return this.fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public User getToUser() {
        return this.toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Message getParent() {
        return this.parent;
    }

    public void setParent(Message parent) {
        this.parent = parent;
    }

    public List<Message> getReplys() {
        return this.replys;
    }

    public void setReplys(List<Message> replys) {
        this.replys = replys;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReply_status() {
        return this.reply_status;
    }

    public void setReply_status(int reply_status) {
        this.reply_status = reply_status;
    }
}