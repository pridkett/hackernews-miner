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

@Entity
@Table(name="hn_user")
public class User {
    public static final long serialVersionUID = 201306042028L;
    private Integer id;
    private String name;
    private Date hnCreateDate;
    private String hnCreateDateText;
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
    
    @Column(name="name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    @Column(name="hn_create_date")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getHnCreateDate() {
        return hnCreateDate;
    }
    public void setHnCreateDate(Date hnCreateDate) {
        this.hnCreateDate = hnCreateDate;
    }
    
    @Column(name="hn_create_date_text")
    public String getHnCreateDateText() {
        return hnCreateDateText;
    }
    public void setHnCreateDateText(String hnCreateText) {
        this.hnCreateDateText = hnCreateText;
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
