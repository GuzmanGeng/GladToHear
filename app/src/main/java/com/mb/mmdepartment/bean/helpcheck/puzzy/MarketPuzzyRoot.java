package com.mb.mmdepartment.bean.helpcheck.puzzy;

/**
 * Created by joyone2one on 2015/11/23.
 */
public class MarketPuzzyRoot {
    private int status;

    private String error;

    private MarketPuzzyData data;

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
    public void setData(MarketPuzzyData data){
        this.data = data;
    }
    public MarketPuzzyData getData(){
        return this.data;
    }

}
