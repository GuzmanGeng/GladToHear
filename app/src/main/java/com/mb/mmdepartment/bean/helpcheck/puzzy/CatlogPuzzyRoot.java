package com.mb.mmdepartment.bean.helpcheck.puzzy;

/**
 * Created by joyone2one on 2015/11/23.
 */
public class CatlogPuzzyRoot {
    private int status;

    private String error;

    private CatlogPuzzyData data;

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
    public void setData(CatlogPuzzyData data){
        this.data = data;
    }
    public CatlogPuzzyData getData(){
        return this.data;
    }
}
