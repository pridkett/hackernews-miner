package net.wagstrom.research.hackernews.dbobjs;

import java.util.Date;

public class User {
    private Integer id;
    private String name;
    private Date hnCreateDate;
    private String hnCreateText;
    private Date createDate;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getHnCreateDate() {
        return hnCreateDate;
    }
    public void setHnCreateDate(Date hnCreateDate) {
        this.hnCreateDate = hnCreateDate;
    }
    public String getHnCreateText() {
        return hnCreateText;
    }
    public void setHnCreateText(String hnCreateText) {
        this.hnCreateText = hnCreateText;
    }
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
