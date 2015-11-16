package com.mb.mmdepartment.biz.helpcheck.marcket_sel.main_new;

import com.mb.mmdepartment.listener.RequestListener;

/**
 * Created by Administrator on 2015/9/17 0017.
 */
public interface INewBrandBiz {
    /**
     *
     * @param page 第几页
     * @param userid  用户id不登陆传0登陆传id获取的结果不一样
     * @param city_id 城市id,根据获取的城市来选择
     * @param keyword 关键字搜索
     * @param zixun_category 关键词
     * @param listener 监听接口
     */
    void getMacketList(int page,int userid,int city_id,String keyword,String zixun_category,RequestListener listener);
}
