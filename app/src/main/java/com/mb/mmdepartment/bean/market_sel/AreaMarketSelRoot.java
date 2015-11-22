package com.mb.mmdepartment.bean.market_sel;

import java.util.List;

/**
 * Created by Administrator on 2015/11/22.
 */
public class AreaMarketSelRoot {

    private int status;

    private String error;

    private List<AreaMarketSelData> data ;

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
    public void setData(List<AreaMarketSelData> data){
        this.data = data;
    }
    public List<AreaMarketSelData> getData(){
        return this.data;
    }
}
