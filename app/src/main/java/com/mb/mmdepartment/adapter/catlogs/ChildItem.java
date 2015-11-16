package com.mb.mmdepartment.adapter.catlogs;

/**
 * Created by Administrator on 2015/8/20.
 */
public class ChildItem {
    private String catlogName;
    private boolean isSel;

    public boolean isSel() {
        return isSel;
    }

    public void setIsSel(boolean isSel) {
        this.isSel = isSel;
    }

    public String getCatlogName() {
        return catlogName;
    }

    public void setCatlogName(String catlogName) {
        this.catlogName = catlogName;
    }
}
