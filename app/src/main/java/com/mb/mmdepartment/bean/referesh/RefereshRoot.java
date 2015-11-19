package com.mb.mmdepartment.bean.referesh;

import java.util.List;

/**
 * Created by joyone2one on 2015/11/20.
 */
public class RefereshRoot {
    private int status;

    private String error;

    private List<RefereshData> data ;

    public void setStatus(int status){
        this.status = status;
    }
    public int getStatus(){
        return this.status;
    }
    public void setError(String error){
        this.error = error;
    }
    public String getError(){
        return this.error;
    }
    public void setData(List<RefereshData> data){
        this.data = data;
    }
    public List<RefereshData> getData(){
        return this.data;
    }
}
