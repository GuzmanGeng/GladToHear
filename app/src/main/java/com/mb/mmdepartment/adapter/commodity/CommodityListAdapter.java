package com.mb.mmdepartment.adapter.commodity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.bean.commodity.Category;
import com.mb.mmdepartment.bean.commodity.Type;
import com.mb.mmdepartment.listener.CommodityRightItemClickListener;
import com.mb.mmdepartment.overridge.MyGridLayoutManager;
import java.util.List;
public class CommodityListAdapter extends RecyclerView.Adapter<CommodityListAdapter.MyViewHolder> {
    private List<Type> list;
    private RecyclerView.Adapter adapter;
    private CommodityRightItemClickListener rightItemClickListener;
    private Context context;
    private int selWhich;
    public CommodityListAdapter(Context context,List<Type> list,int selWhich,CommodityRightItemClickListener rightItemClickListener) {
        this.context=context;
        this.list = list;
        this.selWhich=selWhich;
        this.rightItemClickListener=rightItemClickListener;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.commodity_recycle_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_title.setText(list.get(position).getTitle());
        List<Category> categories=list.get(position).getSon();
        adapter=new CommodityItemAdapter(categories,rightItemClickListener,position,selWhich);
        MyGridLayoutManager manager = new MyGridLayoutManager(context, 3);
        holder.recyclerView.setLayoutManager(manager);
        holder.recyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title;
        public RecyclerView recyclerView;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.commodity_title);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.commodity_sel_recycle);
        }
    }
}
