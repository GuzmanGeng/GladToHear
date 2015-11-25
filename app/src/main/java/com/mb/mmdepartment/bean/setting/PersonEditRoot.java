package com.mb.mmdepartment.bean.setting;

/**
 * Created by joyone2one on 2015/11/25.
 */
public class PersonEditRoot {
    private int status;

    private String error;

    private PersonEditData data;

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
    public void setData(PersonEditData data){
        this.data = data;
    }
    public PersonEditData getData(){
        return this.data;
    }
}
