package com.mb.mmdepartment.bean.buyplan;


import com.mb.mmdepartment.bean.buyplan.byprice.Data;

/**
 * Created by krisi on 2015/10/22.
 */
public class Root {
    private int status;
    private String error;
    private Data data;


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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
