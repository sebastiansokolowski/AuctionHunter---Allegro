package com.sebastian.sokolowski.auctionhunter.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sebastian.sokolowski.auctionhunter.R;
import com.sebastian.sokolowski.auctionhunter.database.entity.Target;
import com.sebastian.sokolowski.auctionhunter.main.MainPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sebastain Sokołowski on 21.02.17.
 */

public class DrawerAdapter extends BaseAdapter {
    private final MainPresenter mainPresenter;
    private final Context context;


    private List<Target> targetList = new ArrayList<>();

    {
        Target target1 = new Target();
        target1.setDrawerName("czekolada");
        target1.setCountAll(12);
        Target target2 = new Target();
        target2.setDrawerName("laptop");
        target2.setCountAll(41);
        Target target3 = new Target();
        target3.setDrawerName("samsung s6");
        target3.setCountAll(100);

        targetList.add(target1);
        targetList.add(target2);
        targetList.add(target3);
    }

    public DrawerAdapter(Context context, MainPresenter mainPresenter) {
        this.context = context;
        this.mainPresenter = mainPresenter;
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
        Target currentItem = getItem(position);

        TextView targetNameTv = (TextView)
                convertView.findViewById(R.id.target_name_tv);
        TextView targetCountTv = (TextView)
                convertView.findViewById(R.id.target_counter_tv);

        targetNameTv.setText(currentItem.getDrawerName());
        targetCountTv.setText(currentItem.getCountNow() + "/" + currentItem.getCountAll());

        return convertView;
    }
}
