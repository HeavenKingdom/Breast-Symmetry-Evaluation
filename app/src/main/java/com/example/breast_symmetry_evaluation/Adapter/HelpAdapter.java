package com.example.breast_symmetry_evaluation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.breast_symmetry_evaluation.R;

public class HelpAdapter extends BaseExpandableListAdapter {
    private String[][] childs;
    private String[] groups;
    private Context context;

    public HelpAdapter(Context context, String[] groups, String[][] childs) {
        this.context = context;
        this.groups = groups;
        this.childs = childs;
    }

    @Override
    public int getGroupCount() {
        return groups.length;
    }

    @Override
    public int getChildrenCount(int i) {
        return childs[i].length;
    }

    @Override
    public Object getGroup(int i) {
        return groups[i];
    }

    @Override
    public Object getChild(int i, int i1) {
        return childs[i][i1];
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    /*
     *
     * 指定组视图对象
     *
     * @param i 组位置
     * @param b 该组是展开状态还是伸缩状态，true=展开
     * @param view 重用已有的视图对象
     * @param viewGroup 返回的视图对象始终依附于的视图组
     *
     * */
    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        GroupViewHolder groupViewHolder;
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.expand_group, viewGroup, false);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.parent_textview_id = view.findViewById(R.id.parent_textview_id);
            groupViewHolder.parent_image = view.findViewById(R.id.parent_image);
            view.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) view.getTag();
        }
        groupViewHolder.parent_textview_id.setText(groups[i]);
        //如果是展开状态，
        if (b) {
            groupViewHolder.parent_image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.up_indicator));
        } else {
            groupViewHolder.parent_image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.down_indicator));
        }
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ChildViewHolder childViewHolder;
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.expand_child, viewGroup, false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.chidren_item = (TextView) view.findViewById(R.id.chidren_item);
            view.setTag(childViewHolder);

        } else {
            childViewHolder = (ChildViewHolder) view.getTag();
        }
        childViewHolder.chidren_item.setText(childs[i][i1]);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    static class GroupViewHolder {
        TextView parent_textview_id;
        ImageView parent_image;
    }

    static class ChildViewHolder {
        TextView chidren_item;
    }
}
