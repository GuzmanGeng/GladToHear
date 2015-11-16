package com.mb.mmdepartment.bean.getcities.serachcity;

import com.mb.mmdepartment.bean.getcities.Description;

import java.util.List;

/**
 * Created by krisi on 2015/10/21.
 */
public class Root {
    private int status;
    private String error;
    private List<Description> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<Description> getData() {
        return data;
    }

    public void setData(List<Description> data) {
        this.data = data;
    }
}
