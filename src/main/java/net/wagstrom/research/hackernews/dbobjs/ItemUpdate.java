package net.wagstrom.research.hackernews.dbobjs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="hn_item_update")
public class ItemUpdate {
    private Integer id;
    private Integer updateId;
    private Integer itemId;
    private Integer numComments;
    private Integer points;
    
    @Id
    @GeneratedValue
    @Column(name="id")
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    
    @Column(name="update_id")
    public Integer getUpdateId() {
        return updateId;
    }
    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }
    
    @Column(name="item_id")
    public Integer getItemId() {
        return itemId;
    }
    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }
    
    @Column(name="comments")
    public Integer getNumComments() {
        return numComments;
    }
    public void setNumComments(Integer numComments) {
        this.numComments = numComments;
    }
    
    @Column(name="points")
    public Integer getPoints() {
        return points;
    }
    public void setPoints(Integer points) {
        this.points = points;
    }
}
