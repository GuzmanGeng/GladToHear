package com.mb.mmdepartment.bean.setting;

/**
 * Created by joyone2one on 2015/11/20.
 */
public class LoadPhotoRoot {
    private int status;

    private String error;

    private LoadPhotoData data;

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
    public void setData(LoadPhotoData data){
        this.data = data;
    }
    public LoadPhotoData getData(){
        return this.data;
    }

}
