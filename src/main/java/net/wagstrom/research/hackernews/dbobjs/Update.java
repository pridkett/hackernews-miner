package net.wagstrom.research.hackernews.dbobjs;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="hn_update")
public class Update {
    public static final long serialVersionUID = 201306042026L;
    private Integer id;
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
    
    @Basic(optional=false)
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
