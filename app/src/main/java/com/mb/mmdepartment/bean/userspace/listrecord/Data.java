package com.mb.mmdepartment.bean.userspace.listrecord;
import java.util.List;
public class Data {
    private String count;

    private List<Orders> orders ;

    public void setCount(String count){
        this.count = count;
    }
    public String getCount(){
        return this.count;
    }
    public void setOrders(List<Orders> orders){
        this.orders = orders;
    }
    public List<Orders> getOrders(){
        return this.orders;
    }
}
