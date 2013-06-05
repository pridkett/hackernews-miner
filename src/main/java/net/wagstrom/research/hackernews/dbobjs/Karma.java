package net.wagstrom.research.hackernews.dbobjs;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="hn_karma")
public class Karma implements Serializable {
    public static final long serialVersionUID = 201306042032L;
    
    private Integer id;
    private Integer userId;
    private Integer updateId;
    private Integer karma;
    private Date createDate;
    
    @Id
    @GeneratedValue
    @Column(name="id")
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    
    @Column(name="user_id")
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    @Column(name="update_id")
    public Integer getUpdateId() {
        return updateId;
    }
    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }
    
    @Column(name="karma")
    public Integer getKarma() {
        return karma;
    }
    public void setKarma(Integer karma) {
        this.karma = karma;
    }
    
    @Column(name="create_date", updatable=false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    @PrePersist
    protected void onUpdate() {
        this.createDate = new Date();
    }
}
