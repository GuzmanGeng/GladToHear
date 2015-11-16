package com.mb.mmdepartment.bean.commodity;

/**
 * Created by Administrator on 2015/9/27 0027.
 */
public class Category {
    private String category_id;

    private String parent_id;

    private String hid;

    private String title;

    private String select;

    public void setCategory_id(String category_id){
        this.category_id = category_id;
    }
    public String getCategory_id(){
        return this.category_id;
    }
    public void setParent_id(String parent_id){
        this.parent_id = parent_id;
    }
    public String getParent_id(){
        return this.parent_id;
    }
    public void setHid(String hid){
        this.hid = hid;
    }
    public String getHid(){
        return this.hid;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setSelect(String select){
        this.select = select;
    }
    public String getSelect(){
        return this.select;
    }
}
