package com.mb.mmdepartment.bean.helpcheck.puzzy;

/**
 * Created by joyone2one on 2015/11/23.
 */
public class PuzzyModel {
    private String keyword;
    private boolean catlog;
    private String shop_name;
    private String search_name;
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public boolean getCatlog() {
        return catlog;
    }

    public void setCatlog(boolean catlog) {
        this.catlog = catlog;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getSearch_name() {
        return search_name;
    }

    public void setSearch_name(String search_name) {
        this.search_name = search_name;
    }

    @Override
    public String toString() {
        return "PuzzyModel{" +
                "keyword='" + keyword + '\'' +
                ", catlog=" + catlog +
                ", shop_name='" + shop_name + '\'' +
                ", search_name='" + search_name + '\'' +
                '}';
    }
}
