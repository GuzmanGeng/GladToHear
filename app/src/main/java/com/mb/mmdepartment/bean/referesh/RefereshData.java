package com.mb.mmdepartment.bean.referesh;

/**
 * Created by joyone2one on 2015/11/20.
 */
public class RefereshData {
    private String id;

    private String platform;

    private String v_code;

    private String v_name;

    private String content;

    private String filepath;

    private String size;

    private String filepath_tdc;

    private String status;

    private String ctime;

    private String mtime;

    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
    public void setPlatform(String platform){
        this.platform = platform;
    }
    public String getPlatform(){
        return this.platform;
    }
    public void setV_code(String v_code){
        this.v_code = v_code;
    }
    public String getV_code(){
        return this.v_code;
    }
    public void setV_name(String v_name){
        this.v_name = v_name;
    }
    public String getV_name(){
        return this.v_name;
    }
    public void setContent(String content){
        this.content = content;
    }
    public String getContent(){
        return this.content;
    }
    public void setFilepath(String filepath){
        this.filepath = filepath;
    }
    public String getFilepath(){
        return this.filepath;
    }
    public void setSize(String size){
        this.size = size;
    }
    public String getSize(){
        return this.size;
    }
    public void setFilepath_tdc(String filepath_tdc){
        this.filepath_tdc = filepath_tdc;
    }
    public String getFilepath_tdc(){
        return this.filepath_tdc;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public String getStatus(){
        return this.status;
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
}
