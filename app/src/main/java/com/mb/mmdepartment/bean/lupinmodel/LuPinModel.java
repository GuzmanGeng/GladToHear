package com.mb.mmdepartment.bean.lupinmodel;

/**
 * Created by krisi on 2015/11/1.
 */
public class LuPinModel {

    private String name;

    private String type;

    private String state;

    private String operationtime;

    private String end_time;
    private String select_shop_id;
    private String source;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSelect_shop_id() {
        return select_shop_id;
    }

    public void setSelect_shop_id(String select_shop_id) {
        this.select_shop_id = select_shop_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOperationtime() {
        return operationtime;
    }

    public void setOperationtime(String operationtime) {
        this.operationtime = operationtime;
    }

    public String getEndtime() {
        return end_time;
    }

    public void setEndtime(String end_time) {
        this.end_time = end_time;
    }
}
