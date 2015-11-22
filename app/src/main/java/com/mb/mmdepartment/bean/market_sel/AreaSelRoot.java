package com.mb.mmdepartment.bean.market_sel;

import java.util.List;

/**
 * Created by Administrator on 2015/11/22.
 */
public class AreaSelRoot {
    private int status;

    private String error;

    private List<AreaSelData> data ;

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
    public void setData(List<AreaSelData> data){
        this.data = data;
    }
    public List<AreaSelData> getData(){
        return this.data;
    }
}
