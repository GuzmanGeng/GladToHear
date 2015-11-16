package com.mb.mmdepartment.bean.main_search;

/**
 * Created by joyone2one on 2015/11/13.
 */
public class HotRoot {
    private int status;

    private String error;

    private HotData data;

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
    public void setData(HotData data){
        this.data = data;
    }
    public HotData getData(){
        return this.data;
    }
}
