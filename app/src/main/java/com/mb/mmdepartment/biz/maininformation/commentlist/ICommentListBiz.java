package com.mb.mmdepartment.biz.maininformation.commentlist;
import com.mb.mmdepartment.listener.MakeCommentListener;
public interface ICommentListBiz {
    void getCommentList(String content_id,String userid,int page,String tag,MakeCommentListener listener);
}
