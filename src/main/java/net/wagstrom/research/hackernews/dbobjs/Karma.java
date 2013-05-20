package net.wagstrom.research.hackernews.dbobjs;

public class Karma {
    private Integer id;
    private Integer userId;
    private Integer updateId;
    private Integer karma;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getUpdateId() {
        return updateId;
    }
    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }
    public Integer getKarma() {
        return karma;
    }
    public void setKarma(Integer karma) {
        this.karma = karma;
    }
}
