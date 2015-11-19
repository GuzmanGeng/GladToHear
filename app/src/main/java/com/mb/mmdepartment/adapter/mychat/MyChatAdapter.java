package com.mb.mmdepartment.adapter.mychat;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mb.mmdepartment.R;
import com.mb.mmdepartment.bean.mychat.Comment;
import com.mb.mmdepartment.view.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

import java.util.List;

/**
 * Created by joyone2one on 2015/11/19.
 */
public class MyChatAdapter extends RecyclerView.Adapter<MyChatAdapter.MyViewHolder> {
    private List<Comment> list;
    public MyChatAdapter(List<Comment> list) {
        this.list = list;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_chat_recycle_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String nick_name = list.get(position).getUser().getUsername();
        String title = list.get(position).getTitle();
        String content = list.get(position).getBody();
        holder.tv_nickname.setText(nick_name);
        holder.tv_title.setText(title);
        holder.tv_content.setText(content);
        String iv_star = list.get(position).getUser().getAvatar();
        if (TextUtils.isEmpty(iv_star)) {
            holder.imageView.setImageResource(R.mipmap.iv_hearder_default);
        } else {
            ImageLoader.getInstance().displayImage(iv_star, holder.imageView, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {

                }
                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {
                    ((ImageView)view).setImageResource(R.mipmap.iv_hearder_default);
                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                    ((ImageView)view).setImageBitmap(bitmap);
                }

                @Override
                public void onLoadingCancelled(String s, View view) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView imageView;
        private TextView tv_nickname;
        private TextView tv_content;
        private TextView tv_title;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_content = (TextView) itemView.findViewById(R.id.content_chat);
            tv_title=(TextView)itemView.findViewById(R.id.title_chat);
            tv_nickname=(TextView)itemView.findViewById(R.id.nick_name_chat);
            imageView=(CircleImageView)itemView.findViewById(R.id.image_iv_chat);
        }
    }
}
