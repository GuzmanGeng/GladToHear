package com.mb.mmdepartment.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.mb.mmdepartment.R;
import com.mb.mmdepartment.adapter.catlogs.ChildItem;
import com.mb.mmdepartment.adapter.catlogs.GroupItem;
import com.mb.mmdepartment.view.AnimatedExpandableListView;
import java.util.List;

/**
 * Created by Administrator on 2015/8/20.
 */
public class ExpandableAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {
    private LayoutInflater inflater;
    private List<GroupItem> items;
    private CallBack callBack;
    private boolean isSel;
    public ExpandableAdapter(Context context, List<GroupItem> items,CallBack callBack) {
        inflater = LayoutInflater.from(context);
        this.items = items;
        this.callBack=callBack;
    }
    @Override
    public int getGroupCount() {
        return items.size();
    }

    @Override
    public ChildItem getChild(int groupPosition, int childPosition) {
        return items.get(groupPosition).getList().get(childPosition);
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,View convertView, ViewGroup parent) {
        GroupHolder holder;
        GroupItem item = getGroup(groupPosition);
        if (convertView == null) {
            holder=new GroupHolder();
            convertView = inflater.inflate(R.layout.expandable_header, parent, false);
            holder.title=(TextView)convertView.findViewById(R.id.group_title);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }
        holder.title.setText(item.getTitle());
        return convertView;
    }

    @Override
    public View getRealChildView(final int groupPosition, final int childPosition, boolean isLastChild, View view, ViewGroup parent) {
        final ChildHolder holder;
        final ChildItem item=getChild(groupPosition,childPosition);
        if (view == null) {
            holder = new ChildHolder();
            view = inflater.inflate(R.layout.expandable_content, parent, false);
            holder.iv=(ImageView)view.findViewById(R.id.child_item_iv);
            holder.title = (TextView) view.findViewById(R.id.child_item_title);
            view.setTag(holder);
        } else {
            holder = (ChildHolder) view.getTag();
        }
        holder.title.setText(item.getCatlogName());
        holder.iv.setTag(item.getCatlogName());
        if (item.isSel()){
            holder.iv.setImageResource(R.mipmap.marcket_sel);
        }else {
            holder.iv.setImageResource(R.mipmap.market_unsel);
        }
        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.isSel()){
                    holder.iv.setImageResource(R.mipmap.market_unsel);
                    item.setIsSel(false);
                }else {
                    holder.iv.setImageResource(R.mipmap.marcket_sel);
                    item.setIsSel(true);
                }
                notifyDataSetChanged();
                callBack.getView(view,groupPosition,childPosition);
            }
        });
        return view;
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {
        return items.get(groupPosition).getList().size();
    }

    @Override
    public GroupItem getGroup(int groupPosition) {
        return items.get(groupPosition);
    }
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    private static class ChildHolder {
        ImageView iv;
        TextView title;
    }
    private static class GroupHolder {
        TextView title;
    }
    public interface CallBack{
        public void getView(View view, int groupPosition, int childPosition);
    }
}
