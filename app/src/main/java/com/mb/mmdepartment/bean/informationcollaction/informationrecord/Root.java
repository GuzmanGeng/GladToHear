package com.mb.mmdepartment.bean.informationcollaction.informationrecord;

/**
 * Created by krisi on 2015/11/3.
 */
public class Root {
    private int status;

    private String error;

    private Data data ;

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
    public void setData (Data data ){
        this. data = data ;
    }
    public Data getData (){
        return this. data ;
    }
}
