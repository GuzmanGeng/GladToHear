package com.mb.mmdepartment.bean.getversion;

import com.mb.mmdepartment.bean.getversion.Data;

import java.util.List;

/**
 * Created by krisi on 2015/11/2.
 */
public class GetVersion {
    private int status;

    private String error;

    private List<Data> data ;

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
    public void setData(List<Data> data){
        this.data = data;
    }
    public List<Data> getData(){
        return this.data;
    }
}
