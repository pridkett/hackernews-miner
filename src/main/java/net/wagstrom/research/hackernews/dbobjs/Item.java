package net.wagstrom.research.hackernews.dbobjs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

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
    private Integer parentId;
    private Item parent;
    private Collection<Item> children;
    private String url;
    private String text;
    private Boolean isHn = true;
    private Boolean isComment = false;
    private Update update;
    private User user;
    private String username;
    private Date createDate;
    private Date hnCreateDate;
    private ArrayList<ItemUpdate> updates;

    public Item() {
        setUpdates(new ArrayList<ItemUpdate>());
    }
    
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
    
    @Column(name="is_hn", nullable=false)
    public Boolean getIsHn() {
        return isHn;
    }
    public void setIsHn(Boolean isHn) {
        this.isHn = isHn;
    }
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="update_id", nullable=false)
    public Update getUpdate() {
        return update;
    }
    public void setUpdate(Update update) {
        this.update = update;
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
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    
    
    /**
     * simple container for the username -- used because we don't do full object
     * joins with JPA in this model, so we need a way to store the username for
     * later goodness
     * 
     * @return
     */
    @Transient
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * simple container for the parent id before we have the object for it
     * @param date
     */
    @Transient
    public Integer getParentId() {
        return parentId;
    }
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
    
    
    public void setHnCreateDate(Date date) {
        hnCreateDate = date;
    }

    @Transient
    public void setHnCreateDate(int since, String units) {
        Date d = new Date();
        long diff = 0;
        if (units.startsWith("hour")) {
            diff = since * 3600 * 1000;
        } else if (units.startsWith("minute")) {
            diff = since * 60 * 1000;
        } else if (units.startsWith("day")) {
            diff = since * 86400 * 1000;
        } else {
            // TODO: shouldd throw an exception here or something....
        }
        hnCreateDate = new Date(d.getTime() - diff);
    }
    
    @Column(name="hn_create_date")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getHnCreateDate() {
        return this.hnCreateDate;
    }
    
    @Column(name="is_comment")
    public Boolean getIsComment() {
        return isComment;
    }
    public void setIsComment(Boolean isComment) {
        this.isComment = isComment;
    }
    
    @Transient
    public Collection<ItemUpdate> getUpdates() {
        return updates;
    }
    public void setUpdates(ArrayList<ItemUpdate> updates) {
        this.updates = updates;
    }
    public void addUpdate(ItemUpdate iu) {
        this.updates.add(iu);
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="parent_id")
    public Item getParent() {
        return parent;
    }

    public void setParent(Item parent) {
        this.parent = parent;
    }

    @OneToMany(mappedBy="parent")
    public Collection<Item> getChildren() {
        return children;
    }

    public void setChildren(Collection<Item> children) {
        this.children = children;
    }
}
