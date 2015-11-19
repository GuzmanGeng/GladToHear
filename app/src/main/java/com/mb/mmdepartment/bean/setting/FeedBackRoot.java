package com.mb.mmdepartment.bean.setting;

/**
 * Created by joyone2one on 2015/11/19.
 */
public class FeedBackRoot {
    private int status;

    private String error;

    private FeedBackData data;

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
    public void setData(FeedBackData data){
        this.data = data;
    }
    public FeedBackData getData(){
        return this.data;
    }
}
