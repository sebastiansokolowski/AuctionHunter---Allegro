package com.sebastian.sokolowski.auctionhunter.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sebastian.sokolowski.auctionhunter.R;
import com.sebastian.sokolowski.auctionhunter.database.models.TargetItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Sebastain Sokołowski on 21.02.17.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.TargetViewHolder> {
    private final Context context;
    private final List<TargetItem> targetItems;

    public MainAdapter(Context context, List<TargetItem> targetItems) {
        this.context = context;
        this.targetItems = targetItems;

        addValues();
    }

    private void addValues() {
        TargetItem targetItem = new TargetItem();
        targetItem.setName("czekolada");
        targetItem.setOffertype(TargetItem.Offertype.BOTH);
        targetItem.setPrice("12.11");
        targetItem.setPriceBid("1.11");
        targetItem.setPriceFull("5.11");
        targetItem.setWhen("2017-01-01");

        TargetItem targetItem2 = new TargetItem();
        targetItem2.setName("BMX");
        targetItem2.setOffertype(TargetItem.Offertype.BUY_NOW);
        targetItem2.setPrice("1113.12");
        targetItem2.setPriceFull("1222.12");
        targetItem2.setWhen("2017-03-04");

        targetItems.add(targetItem);
        targetItems.add(targetItem2);

        notifyDataSetChanged();
    }

    @Override
    public TargetViewHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_target_item, parent, false);
        TargetViewHolder vh = new TargetViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(TargetViewHolder holder, int position) {
        TargetItem item = targetItems.get(position);

        holder.name.setText(item.getName());
        holder.when.setText(item.getWhen());
        holder.priceFull.setText(item.getPriceFull());

        if (item.getOffertype() == TargetItem.Offertype.BOTH ||
                item.getOffertype() == TargetItem.Offertype.BUY_NOW) {
            holder.price.setVisibility(View.VISIBLE);
            holder.price.setText(item.getPrice());
        }

        if (item.getOffertype() == TargetItem.Offertype.BOTH ||
                item.getOffertype() == TargetItem.Offertype.AUCTION) {
            holder.priceBid.setVisibility(View.VISIBLE);
            holder.priceBid.setText(item.getPrice());
        }

        if (item.getImageUrl() != null && !item.getImageUrl().isEmpty()) {
            Picasso.with(context).load(item.getImageUrl()).into(holder.mItemImage);
        }
    }

    @Override
    public int getItemCount() {
        return targetItems.size();
    }

    public void onItemDismiss(int position) {
        targetItems.remove(position);
        notifyItemRemoved(position);
    }

    public static class TargetViewHolder extends RecyclerView.ViewHolder {
        public View background;
        public View container;

        public TextView name;
        public TextView when;
        public TextView priceFull;
        public TextView price;
        public TextView priceBid;
        public ImageView mItemImage;

        public TargetViewHolder(View view) {
            super(view);
            background = view.findViewById(R.id.background);
            container = view.findViewById(R.id.container);

            name = (TextView) view.findViewById(R.id.tv_item_name);
            when = (TextView) view.findViewById(R.id.tv_item_when);
            priceFull = (TextView) view.findViewById(R.id.tv_item_price_full);
            price = (TextView) view.findViewById(R.id.tv_item_price);
            priceBid = (TextView) view.findViewById(R.id.tv_item_price_bid);
            mItemImage = (ImageView) view.findViewById(R.id.iv_item_image);
        }
    }
}
