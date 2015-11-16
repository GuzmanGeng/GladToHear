package com.mb.mmdepartment.bean.userspace.listrecord.getwin;

import java.util.List;

/**
 * Created by jack on 2015/10/19.
 */
public class Root {
    private static int status;
    private String error;
    private Data data;

    public static int getStatus() {
        return status;
    }

    public static void setStatus(int status) {
        Root.status = status;
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
