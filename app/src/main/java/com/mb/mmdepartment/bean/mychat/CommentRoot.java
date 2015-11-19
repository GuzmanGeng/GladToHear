package com.mb.mmdepartment.bean.mychat;
/**
 * Created by joyone2one on 2015/11/19.
 */
public class CommentRoot {
    private int status;

    private String error;

    private CommentList data;

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
    public void setData(CommentList data){
        this.data = data;
    }
    public CommentList getData(){
        return this.data;
    }
}
