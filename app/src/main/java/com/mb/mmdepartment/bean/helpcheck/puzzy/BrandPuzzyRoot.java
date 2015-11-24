package com.mb.mmdepartment.bean.helpcheck.puzzy;

/**
 * Created by joyone2one on 2015/11/23.
 */
public class BrandPuzzyRoot {
    private int status;

    private String error;

    private BrandPuzzyData data;

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
    public void setData(BrandPuzzyData data){
        this.data = data;
    }
    public BrandPuzzyData getData(){
        return this.data;
    }
}
