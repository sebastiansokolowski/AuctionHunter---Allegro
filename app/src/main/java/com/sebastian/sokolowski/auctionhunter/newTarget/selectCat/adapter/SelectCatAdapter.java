package com.sebastian.sokolowski.auctionhunter.newTarget.selectCat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sebastian.sokolowski.auctionhunter.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sebastian Sokołowski on 04.03.17.
 */

public class SelectCatAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<CatAdapterItem> mCatList = new ArrayList<>();
    private OnClickListenerCatItem mCategoryListener;

    public SelectCatAdapter(Context context) {
        mContext = context;
    }

    public void setCategoryListener(OnClickListenerCatItem categoryListener) {
        mCategoryListener = categoryListener;
    }

    @Override
    public int getCount() {
        return mCatList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCatList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).
                    inflate(R.layout.fragment_select_cat_item, parent, false);
        }

        final CatAdapterItem item = mCatList.get(position);

        TextView catName = (TextView) convertView.findViewById(R.id.tv_category_name);
        View catChild = convertView.findViewById(R.id.tv_category_child);

        catName.setText(item.getName());
        catChild.setVisibility(item.isHasChild() ? View.VISIBLE : View.INVISIBLE);

        if (mCategoryListener != null) {
            catName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCategoryListener.onCategoryClicked(item.getId());
                }
            });

            catChild.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCategoryListener.onCategoryChildrenClicked(item.getId());
                }
            });
        }

        return convertView;
    }

    public void addItems(List<CatAdapterItem> catAdapterItems) {
        mCatList.addAll(catAdapterItems);
        notifyDataSetChanged();
    }

    public void clear() {
        mCatList.clear();
        notifyDataSetChanged();
    }

    public interface OnClickListenerCatItem {
        void onCategoryChildrenClicked(String categoryId);

        void onCategoryClicked(String categoryId);
    }
}
