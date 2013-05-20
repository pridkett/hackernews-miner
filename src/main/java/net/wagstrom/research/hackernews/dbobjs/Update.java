package net.wagstrom.research.hackernews.dbobjs;

import java.util.Date;

public class Update {
    private Integer id;
    private Date createDate;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
}
