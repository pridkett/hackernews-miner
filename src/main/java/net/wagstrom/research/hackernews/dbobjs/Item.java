package net.wagstrom.research.hackernews.dbobjs;

import java.util.Date;

public class Item {
    private Integer id;
    private Integer itemId;
    private String url;
    private String text;
    private Boolean isHn;
    private Integer updateId;
    private Date createDate;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getItemId() {
        return itemId;
    }
    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public Boolean getIsHn() {
        return isHn;
    }
    public void setIsHn(Boolean isHn) {
        this.isHn = isHn;
    }
    public Integer getUpdateId() {
        return updateId;
    }
    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
}
