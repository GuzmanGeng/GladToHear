package com.mb.mmdepartment.adapter.informationcollection;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.bean.informationcollaction.commentlist.Comment;
import com.mb.mmdepartment.bean.informationcollaction.commentlist.User;
import com.mb.mmdepartment.view.CircleImageView;
import java.util.List;

/**
 * Created by Administrator on 2015/9/25 0025.
 */
public class InformationCommentAdapter extends RecyclerView.Adapter<InformationCommentAdapter.MyViewHolder> {
    private List<Comment> list;
    private boolean isShowAll;
    public InformationCommentAdapter(List<Comment> list,boolean isShowAll) {
        this.list=list;
        this.isShowAll=isShowAll;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.informatin_detail_chat_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Comment comment=list.get(position);
        String url=comment.getUser().getAvatar();
        if (TextUtils.isEmpty(url)) {
            holder.imageView.setImageResource(R.mipmap.ic_launcher);
        }else {
            ImageLoader.getInstance().displayImage(url, holder.imageView, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {
                    ((ImageView) view).setImageResource(R.mipmap.ic_launcher);
                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {
                    ((ImageView) view).setImageResource(R.mipmap.ic_launcher);
                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                    ((ImageView) view).setImageBitmap(bitmap);
                }

                @Override
                public void onLoadingCancelled(String s, View view) {

                }
            });
        }
        holder.title.setText(comment.getNickname());
        holder.content.setText(comment.getBody());
        holder.isCai.setText(String.valueOf(comment.getIs_audit()));
        holder.isZan.setText(String.valueOf(comment.getIs_favorite()));
    }

    @Override
    public int getItemCount() {
        if (isShowAll){
            return list.size();
        }
        if (list.size()==1){
            return 1;
        }
        return 2;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView imageView;
        public TextView title,content,isCai,isZan;
        private ImageView cai,zan;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (CircleImageView) itemView.findViewById(R.id.information_detail_item_comment_iv);
            title = (TextView) itemView.findViewById(R.id.information_detail_item_title_tv);
            content = (TextView) itemView.findViewById(R.id.information_detail_item_content_tv);
            isCai=(TextView)itemView.findViewById(R.id.information_detail_item_cai_count_tv);
            isZan=(TextView)itemView.findViewById(R.id.information_detail_zan_count_tv);
            cai=(ImageView)itemView.findViewById(R.id.information_detail_item_cai_iv);
            zan=(ImageView)itemView.findViewById(R.id.information_detail_item_zan_iv);
        }
    }
}
