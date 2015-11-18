package com.mb.mmdepartment.adapter.proposedproject;
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.base.TApplication;
import com.mb.mmdepartment.bean.buyplan.byprice.DataList;
import com.mb.mmdepartment.bean.marcketseldetail.Lists;
import com.mb.mmdepartment.overridge.MyGridLayoutManager;

import java.util.List;

/**
 * Created by joyone2one on 2015/11/16.
 */
public class ProposedProjectAdapter extends RecyclerView.Adapter<ProposedProjectAdapter.MyViewHolder> implements ProposedProjectInnerAdapter.SizeCallBack{
    private List<DataList> lists;
    private int which;
    private Context context;
    private boolean is_sel;
    private TextView textView;
    private Activity activity;
    public ProposedProjectAdapter(List<DataList> lists,int which,Context context,TextView textView,Activity activity){
        this.lists=lists;
        this.which=which;
        this.context=context;
        this.textView=textView;
        this.activity=activity;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.proposed_project_reccycle_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        String name = lists.get(position).getName();
        holder.name.setText(name);
        final List<Lists> list = lists.get(position).getList();

        final ProposedProjectInnerAdapter adapter = new ProposedProjectInnerAdapter(list, which,holder.image_sel,holder.check_all,this,activity);
        MyGridLayoutManager manager = new MyGridLayoutManager(context,1);
        holder.inner_recycle.setLayoutManager(manager);
        holder.inner_recycle.setAdapter(adapter);
    }
    @Override
    public int getItemCount() {
        return lists.size();
    }

    @Override
    public void getSize() {
        textView.setText(TApplication.ids.size()+"");
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout check_all;
        public ImageView image_sel;
        public TextView name;
        public RecyclerView inner_recycle;
        public MyViewHolder(View itemView) {
            super(itemView);
            check_all = (LinearLayout) itemView.findViewById(R.id.check_all);
            image_sel = (ImageView) itemView.findViewById(R.id.image_sel);
            name = (TextView) itemView.findViewById(R.id.name);
            inner_recycle = (RecyclerView) itemView.findViewById(R.id.inner_recycle);
        }
    }
}
