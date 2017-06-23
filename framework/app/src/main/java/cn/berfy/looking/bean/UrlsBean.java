package cn.berfy.looking.bean;

/**
 * Created by Berfy on 2017/4/7.
 * 关键字bean
 */
public class UrlsBean {

    private long id;//ID
    private String url;//网址
    private String updateTime;//更新时间
    private boolean isCheck;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
