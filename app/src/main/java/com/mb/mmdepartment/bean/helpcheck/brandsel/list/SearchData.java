package com.mb.mmdepartment.bean.helpcheck.brandsel.list;

import java.util.List;

/**
 * Created by Administrator on 2015/10/7 0007.
 */
public class SearchData {
    private String count;

    private List<SearchList> list ;

    public void setCount(String count){
        this.count = count;
    }
    public String getCount(){
        return this.count;
    }
    public void setList(List<SearchList> list){
        this.list = list;
    }
    public List<SearchList> getList(){
        return this.list;
    }

}
