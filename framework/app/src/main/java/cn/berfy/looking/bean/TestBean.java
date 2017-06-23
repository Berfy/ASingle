package cn.berfy.looking.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/19.
 */

public class TestBean {

    private List<ContentBean> list;
    private String title;
    private ContentBean data;

    public List<ContentBean> getList() {
        return list;
    }

    public void setList(List<ContentBean> list) {
        this.list = list;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ContentBean getData() {
        return data;
    }

    public void setData(ContentBean data) {
        this.data = data;
    }
}
