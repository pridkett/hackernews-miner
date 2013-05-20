package net.wagstrom.research.hackernews.dbobjs;

import java.util.Date;

/**
 * Simple container class for HTML pages fetched. This allows us to
 * retroactively go back and analyze pages in case our scraping
 * or data processing algorithms need to change.
 * 
 * @author pwagstro
 *
 */
public class PageContent {
    private Integer id;
    private Integer itemId;
    private String url;
    private String text;
    private Boolean isHn;
    private Date createDate;
    private Integer updateId;
    
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
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public Integer getUpdateId() {
        return updateId;
    }
    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }
}
