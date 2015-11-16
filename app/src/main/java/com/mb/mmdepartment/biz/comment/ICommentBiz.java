package com.mb.mmdepartment.biz.comment;

import com.mb.mmdepartment.listener.RequestListener;

/**
 * Created by krisi on 2015/10/28.
 */
public interface ICommentBiz {
    void sendcomment(String content_id,String body,String tag,RequestListener listener);
}
