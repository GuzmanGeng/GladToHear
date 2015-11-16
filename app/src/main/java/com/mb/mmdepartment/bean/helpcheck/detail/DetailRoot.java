package com.mb.mmdepartment.bean.helpcheck.detail;

/**
 * Created by Administrator on 2015/10/7 0007.
 */
public class DetailRoot {
    private int status;

    private String error;

    private DetailData data;

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
    public void setData(DetailData data){
        this.data = data;
    }
    public DetailData getData(){
        return this.data;
    }

}
