package com.mb.mmdepartment.bean.informationcollaction;

import java.util.List;

/**
 * Created by Administrator on 2015/9/9.
 */
public class Data {
    private List<Description> list ;

    private String count;

    private int totalPages;

    private String totalRows;

    public void setList(List<Description> list){
        this.list = list;
    }
    public List<Description> getList(){
        return this.list;
    }
    public void setCount(String count){
        this.count = count;
    }
    public String getCount(){
        return this.count;
    }
    public void setTotalPages(int totalPages){
        this.totalPages = totalPages;
    }
    public int getTotalPages(){
        return this.totalPages;
    }
    public void setTotalRows(String totalRows){
        this.totalRows = totalRows;
    }
    public String getTotalRows(){
        return this.totalRows;
    }
}
