package com.mb.mmdepartment.bean.getcities;


import java.util.List;

/**
 * Created by jack on 2015/10/19.
 */
public class Root {
    private int status;

    private String error;

    private List<Description> data ;

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
