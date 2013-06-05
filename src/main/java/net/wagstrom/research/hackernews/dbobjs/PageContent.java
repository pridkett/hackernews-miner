package net.wagstrom.research.hackernews.dbobjs;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Simple container class for HTML pages fetched. This allows us to
 * retroactively go back and analyze pages in case our scraping
 * or data processing algorithms need to change.
 * 
 * @author pwagstro
 *
 */
@Entity
@Table(name="hn_pagecontent")
public class PageContent {
    private Integer id;
    private Integer itemId;
    private String url;
    private String text;
    private Boolean isHn;
    private Date createDate;
    private Integer updateId;
    
    @Id
    @GeneratedValue
    @Column(name="id")
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    
    @Column(name="hn_id")
    public Integer getItemId() {
        return itemId;
    }
    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }
    
    @Column(name="url")
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    
    @Column(name="test")
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    
    @Column(name="is_hn")
    public Boolean getIsHn() {
        return isHn;
    }
    public void setIsHn(Boolean isHn) {
        this.isHn = isHn;
    }
    
    @Column(name="create_date", updatable=false, columnDefinition="TIMESTAMPE DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    @Column(name="update_id")
    public Integer getUpdateId() {
        return updateId;
    }
    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }
    
    @PrePersist
    protected void onUpdate() {
        this.createDate = new Date();
    }
}
