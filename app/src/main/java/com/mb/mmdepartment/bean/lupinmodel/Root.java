package com.mb.mmdepartment.bean.lupinmodel;

import java.util.List;

/**
 * Created by krisi on 2015/11/2.
 */
public class Root {
    private String error;
    private int status;
    private List<String> Data;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<String> getData() {
        return Data;
    }

    public void setData(List<String> data) {
        Data = data;
    }
}
