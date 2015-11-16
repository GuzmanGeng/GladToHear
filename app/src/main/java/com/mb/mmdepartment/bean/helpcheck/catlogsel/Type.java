package com.mb.mmdepartment.bean.helpcheck.catlogsel;

/**
 * Created by Administrator on 2015/9/21 0021.
 */
public class Type {
    private String category_id;

    private String parent_id;

    private String hid;

    private String title;

    private String description;

    private String ctime;

    private String mtime;

    private String sort_order;

    private String number;

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
    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
        return this.description;
    }
    public void setCtime(String ctime){
        this.ctime = ctime;
    }
    public String getCtime(){
        return this.ctime;
    }
    public void setMtime(String mtime){
        this.mtime = mtime;
    }
    public String getMtime(){
        return this.mtime;
    }
    public void setSort_order(String sort_order){
        this.sort_order = sort_order;
    }
    public String getSort_order(){
        return this.sort_order;
    }
    public void setNumber(String number){
        this.number = number;
    }
    public String getNumber(){
        return this.number;
    }
}
