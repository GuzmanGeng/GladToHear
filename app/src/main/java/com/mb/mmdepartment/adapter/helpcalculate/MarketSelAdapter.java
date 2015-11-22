package com.mb.mmdepartment.adapter.helpcalculate;
import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import com.mb.mmdepartment.biz.calculate.AreasGetBiz;
import com.mb.mmdepartment.constans.BaseConsts;
import com.mb.mmdepartment.tools.sp.SPCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.bean.market_address.Description;
import com.mb.mmdepartment.listener.OnRecycItemClickListener;
import java.util.List;

/**
 * Created by Administrator on 2015/8/19.
 */
public class MarketSelAdapter extends RecyclerView.Adapter<MarketSelAdapter.ViewHolder>{
    private List<Description> list;
    private OnRecycItemClickListener onItemClickListener;
    private boolean[] isSel;
    public MarketSelAdapter(List<Description> list,OnRecycItemClickListener onItemClickListener,boolean[] isSel) {
        this.list = list;
        this.onItemClickListener=onItemClickListener;
        this.isSel=isSel;
    }
    @Override
    public MarketSelAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.help_calculate_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final String url= BaseConsts.BASE_IMAGE_URL+list.get(position).getShop_logo();
        final String name=list.get(position).getShop_name();
        ImageLoader.getInstance().displayImage(url,holder.market_name_iv);
        holder.market_name_tv.setText(name);
        holder.sel_address_liner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog(v.getContext(),list.get(position).getShop_id(),list.get(position).getShop_name());
            }
        });
    }

    /**
     * 帮你算dialog
     */
    private void alertDialog(Context context,String shop_id,String shop_name) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setContentView(R.layout.help_calculate_address_sel_dialog);

        final TextView market_name=(TextView)window.findViewById(R.id.market_name);
        market_name.setText(shop_name);

        final TextView location_state=(TextView)window.findViewById(R.id.location_state);
        final RecyclerView address_recycle= (RecyclerView) window.findViewById(R.id.address_recycle);
        final Spinner address_spi = (Spinner) window.findViewById(R.id.address_spi);

        String location_address = SPCache.getString(BaseConsts.SharePreference.MAP_LOCATION, "");
        if (TextUtils.isEmpty(location_address)) {
            location_state.setText("请先打开定位...");
        } else {
            location_state.setText(location_address);
        }
        getAreas(address_spi,context,address_recycle,shop_id);
    }
    private void getAreas(Spinner spinner,Context context,RecyclerView recyclerView,String shop_id) {
        AreasGetBiz biz = new AreasGetBiz(spinner, context,recyclerView,shop_id);
        biz.getAreas(SPCache.getString("provience", "上海"));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView market_name_tv,market_name_address;
        public ImageView market_sel_state;
        public ImageView market_name_iv;
        public View sel_address_liner;
        public ViewHolder(View v) {
            super(v);
            market_name_tv = (TextView) v.findViewById(R.id.market_name_tv);
            market_name_address = (TextView) v.findViewById(R.id.market_name_address);
            market_sel_state = (ImageView) v.findViewById(R.id.market_sel_state);
            market_name_iv = (ImageView) v.findViewById(R.id.market_name_iv);
            sel_address_liner = v.findViewById(R.id.sel_address_liner);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
