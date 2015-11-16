package com.mb.mmdepartment.adapter.catlogs;

import java.util.List;

/**
 * Created by Administrator on 2015/8/20.
 */
public class GroupItem {
    private String title;
    private List<ChildItem> list;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ChildItem> getList() {
        return list;
    }

    public void setList(List<ChildItem> list) {
        this.list = list;
    }
}
