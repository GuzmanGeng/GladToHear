package com.mb.mmdepartment.bean.setting;

/**
 * Created by joyone2one on 2015/11/20.
 */
public class UserPassChangeRoot {
    private int status;

    private String error;

    private UserPassChangeData data;

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
    public void setData(UserPassChangeData data){
        this.data = data;
    }
    public UserPassChangeData getData(){
        return this.data;
    }

}
