package com.mb.mmdepartment.bean.main_search;

import java.util.List;

/**
 * Created by joyone2one on 2015/11/13.
 */
public class HotData {
    private List<Tags> tags ;

    private List<Market> market ;

    private List<UserKeyword> userkeyword ;

    public void setTags(List<Tags> tags){
        this.tags = tags;
    }
    public List<Tags> getTags(){
        return this.tags;
    }
    public void setMarket(List<Market> market){
        this.market = market;
    }
    public List<Market> getMarket(){
        return this.market;
    }
    public void setUserkeyword(List<UserKeyword> userkeyword){
        this.userkeyword = userkeyword;
    }
    public List<UserKeyword> getUserkeyword(){
        return this.userkeyword;
    }
}
