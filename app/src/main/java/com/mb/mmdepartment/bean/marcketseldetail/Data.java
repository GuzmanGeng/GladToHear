package com.mb.mmdepartment.bean.marcketseldetail;
import java.util.List;
public class Data {
    private String count;

    private List<Lists> list ;

    public void setCount(String count){
        this.count = count;
    }
    public String getCount(){
        return this.count;
    }
    public void setList(List<Lists> list){
        this.list = list;
    }
    public List<Lists> getList(){
        return this.list;
    }
}
