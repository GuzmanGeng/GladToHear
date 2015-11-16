package com.mb.mmdepartment.bean.accumulate;

import java.util.List;

/**
 * Created by Administrator on 2015/9/11 0011.
 */
public class Data {
    private String category;

    private int page;

    private String contion;

    private int perpage;

    private String sort;

    private int offset;

    private List<Product> products ;

    private String count;

    private int totalPages;

    private String totalRows;

    public void setCategory(String category){
        this.category = category;
    }
    public String getCategory(){
        return this.category;
    }
    public void setPage(int page){
        this.page = page;
    }
    public int getPage(){
        return this.page;
    }
    public void setContion(String contion){
        this.contion = contion;
    }
    public String getContion(){
        return this.contion;
    }
    public void setPerpage(int perpage){
        this.perpage = perpage;
    }
    public int getPerpage(){
        return this.perpage;
    }
    public void setSort(String sort){
        this.sort = sort;
    }
    public String getSort(){
        return this.sort;
    }
    public void setOffset(int offset){
        this.offset = offset;
    }
    public int getOffset(){
        return this.offset;
    }
    public void setProducts(List<Product> products){
        this.products = products;
    }
    public List<Product> getProducts(){
        return this.products;
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
