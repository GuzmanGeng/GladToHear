package com.mb.mmdepartment.bean.helpcheck.brandsel;
import java.util.List;
import com.mb.mmdepartment.bean.marcketseldetail.Lists;
/**
 * Created by Administrator on 2015/9/21 0021.
 */
public class Data {
    private List<Type> types ;

    private List<Lists> lists ;
    public void setTypes(List<Type> types){
        this.types = types;
    }
    public List<Type> getTypes(){
        return this.types;
    }
    public void setLists(List<Lists> lists){
        this.lists = lists;
    }
    public List<Lists> getLists(){
        return this.lists;
    }

}
