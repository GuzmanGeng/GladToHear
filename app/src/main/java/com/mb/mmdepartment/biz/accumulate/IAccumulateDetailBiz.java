package com.mb.mmdepartment.biz.accumulate;
import com.mb.mmdepartment.listener.RequestListener;
public interface IAccumulateDetailBiz {
    void getDetail(String content_id,String tag,RequestListener listener);
}
