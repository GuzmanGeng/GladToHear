package com.mb.mmdepartment.bean.commodity;
import java.util.List;
public class Type {
    private String category_id;

    private String parent_id;

    private String hid;

    private String title;

    private List<Category> son ;

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
    public void setSon(List<Category> son){
        this.son = son;
    }
    public List<Category> getSon(){
        return this.son;
    }
}
