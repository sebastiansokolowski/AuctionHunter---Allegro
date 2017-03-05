package com.sebastian.sokolowski.auctionhunter.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sebastian.sokolowski.auctionhunter.R;
import com.sebastian.sokolowski.auctionhunter.database.models.Target;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sebastain Sokołowski on 21.02.17.
 */

public class DrawerAdapter extends BaseAdapter {
    private final Context context;
    private List<Target> targetList = new ArrayList<>();
    private OnClickDrawerItemListener mOnClickListener;

    public DrawerAdapter(Context context) {
        this.context = context;
    }

    public void setOnClickListener(OnClickDrawerItemListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    @Override
    public int getCount() {
        return targetList.size();
    }

    @Override
    public Target getItem(int position) {
        return targetList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.list_drawer_target_item, parent, false);
        }
        final Target currentItem = getItem(position);

        TextView targetNameTv = (TextView)
                convertView.findViewById(R.id.target_name_tv);
        TextView targetCountTv = (TextView)
                convertView.findViewById(R.id.target_counter_tv);

        targetNameTv.setText(currentItem.getDrawerName());
        targetCountTv.setText(currentItem.getCountNew() + "/" + currentItem.getCountAll());

        if (mOnClickListener != null) {
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClickListener.onClick(currentItem);
                }
            });
        }

        return convertView;
    }

    public void setItems(List<Target> targetList) {
        this.targetList.clear();
        this.targetList.addAll(targetList);
        notifyDataSetChanged();
    }

    public interface OnClickDrawerItemListener {
        void onClick(Target target);
    }
}
