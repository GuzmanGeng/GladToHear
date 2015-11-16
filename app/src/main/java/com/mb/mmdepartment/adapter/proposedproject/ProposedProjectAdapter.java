package com.mb.mmdepartment.adapter.proposedproject;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.bean.buyplan.byprice.DataList;
import java.util.List;

/**
 * Created by joyone2one on 2015/11/16.
 */
public class ProposedProjectAdapter extends RecyclerView.Adapter<ProposedProjectAdapter.MyViewHolder> {
    private List<DataList> lists;
    public ProposedProjectAdapter(List<DataList> lists){
        this.lists=lists;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return null;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView marcket_sel_detail_item_standard_tv;
        public TextView marcket_sel_detail_item_title_tv;
        public TextView marcket_sel_detail_item_from_tv;
        public TextView marcket_sel_detail_item_new_price_tv;
        public TextView marcket_sel_detail_item_del_price_tv;
        public TextView marcket_sel_detail_item_example_tv;
        public TextView marcket_sel_detail_item_count_tv;
        public ImageView marcket_sel_detail_item_iv;
        public LinearLayout marcket_sel_detail_liner;
        public MyViewHolder(View itemView) {
            super(itemView);
            marcket_sel_detail_item_standard_tv=(TextView)itemView.findViewById(R.id.marcket_sel_detail_item_standard_tv);
            marcket_sel_detail_item_title_tv=(TextView)itemView.findViewById(R.id.marcket_sel_detail_item_title_tv);
            marcket_sel_detail_item_from_tv=(TextView)itemView.findViewById(R.id.marcket_sel_detail_item_from_tv);
            marcket_sel_detail_item_new_price_tv=(TextView)itemView.findViewById(R.id.marcket_sel_detail_item_new_price_tv);
            marcket_sel_detail_item_del_price_tv=(TextView)itemView.findViewById(R.id.marcket_sel_detail_item_del_price_tv);
            marcket_sel_detail_item_example_tv=(TextView)itemView.findViewById(R.id.marcket_sel_detail_item_example_tv);
            marcket_sel_detail_item_count_tv=(TextView)itemView.findViewById(R.id.marcket_sel_detail_item_count_tv);
            marcket_sel_detail_item_iv=(ImageView)itemView.findViewById(R.id.marcket_sel_detail_item_iv);
            marcket_sel_detail_liner = (LinearLayout) itemView.findViewById(R.id.marcket_sel_detail_liner);
        }
    }
}
