package com.mb.mmdepartment.bean.comment;

/**
 * Created by Administrator on 2015/11/22.
 */
public class CommentBackRoot {
    private int status;

    private String error;

    private CommentBackData data;

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
    public void setData(CommentBackData data){
        this.data = data;
    }
    public CommentBackData getData(){
        return this.data;
    }

}
