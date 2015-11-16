package com.mb.mmdepartment.bean.helpcheck.brandsel.list;

/**
 * Created by Administrator on 2015/10/7 0007.
 */
public class SearchRoot {
    private int status;

    private String error;

    private SearchData data;

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
    public void setData(SearchData data){
        this.data = data;
    }
    public SearchData getData(){
        return this.data;
    }
}
