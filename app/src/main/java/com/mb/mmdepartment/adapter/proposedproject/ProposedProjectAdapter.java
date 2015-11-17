package com.mb.mmdepartment.adapter.proposedproject;
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
public class ProposedProjectAdapter extends RecyclerView.Adapter<ProposedProjectAdapter.MyViewHolder> implements ProposedProjectInnerAdapter.AllSel{
    private List<DataList> lists;
    private int which;
    private Context context;
    private boolean is_sel;
    public ProposedProjectAdapter(List<DataList> lists,int which,Context context){
        this.lists=lists;
        this.which=which;
        this.context=context;
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

        final ProposedProjectInnerAdapter adapter = new ProposedProjectInnerAdapter(list, which,holder.image_sel,holder.check_all,this);
        MyGridLayoutManager manager = new MyGridLayoutManager(context,1);
        holder.inner_recycle.setLayoutManager(manager);
        holder.inner_recycle.setAdapter(adapter);
//        holder.image_sel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (is_sel) {
//                    for (int i = 0; i < list.size(); i++) {
//                        String id = list.get(i).getId();
//                        TApplication.ids.remove(id);
//                    }
//                    holder.image_sel.setImageResource(R.mipmap.market_unsel);
//                    notifyItemChanged(position);
//                } else {
//                    for (int i=0;i<list.size();i++) {
//                        String id = list.get(i).getId();
//                        if (!TApplication.ids.contains(id)) {
//                            TApplication.ids.add(id);
//                        }
//                    }
//                    adapter.notifyDataSetChanged();
//                    holder.image_sel.setImageResource(R.mipmap.marcket_sel);
//                }
//            }
//        });
    }
    @Override
    public int getItemCount() {
        return lists.size();
    }

    @Override
    public void selAll(boolean sel_all) {
        is_sel=sel_all;
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
