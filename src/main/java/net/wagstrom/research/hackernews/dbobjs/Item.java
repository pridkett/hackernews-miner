package net.wagstrom.research.hackernews.dbobjs;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * An item represents a single entity in the hackernews
 * database.
 * 
 * @author pwagstro
 *
 */
@Entity
@Table(name="hn_item")
public class Item implements Serializable {
    public static final long serialVersionUID = 201306042026L;

    private Integer id;
    private Integer itemId;
    private String url;
    private String text;
    private Boolean isHn;
    private Integer updateId;
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
    
    @Column(name="text")
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
    
    @Column(name="update_id")
    public Integer getUpdateId() {
        return updateId;
    }
    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }
    
    @Column(name="create_date")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
}
