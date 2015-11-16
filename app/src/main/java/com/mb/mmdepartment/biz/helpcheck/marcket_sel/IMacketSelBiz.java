package com.mb.mmdepartment.biz.helpcheck.marcket_sel;

import com.mb.mmdepartment.listener.RequestListener;

/**
 * Created by Administrator on 2015/9/16 0016.
 */
public interface IMacketSelBiz {
    void getMacketList(int parentId,String tag,RequestListener listener);
}
