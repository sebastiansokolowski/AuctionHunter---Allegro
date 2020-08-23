package com.sebastian.sokolowski.auctionhunter.main.adapter;

import android.content.Context;
import android.os.Build;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sebastian.sokolowski.auctionhunter.R;
import com.sebastian.sokolowski.auctionhunter.database.models.TargetItem;
import com.sebastian.sokolowski.auctionhunter.utils.MyUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sebastain Sokołowski on 21.02.17.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.TargetViewHolder> {
    private final Context context;
    private OnClickDrawerItemListener mOnClickListener;
    private final List<TargetItem> targetItems = new ArrayList<>();

    public MainAdapter(Context context) {
        this.context = context;
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
    public void onBindViewHolder(final TargetViewHolder holder, int position) {
        final TargetItem item = targetItems.get(position);

        holder.name.setText(item.getName());
        holder.when.setText(item.getWhen());

        holder.priceFull.setText(MyUtils.getPrice(context, item.getPriceFull()));

        if (item.getOffertype() == TargetItem.Offertype.BOTH ||
                item.getOffertype() == TargetItem.Offertype.BUY_NOW) {
            holder.price.setVisibility(View.VISIBLE);
            holder.price.setText(MyUtils.getPrice(context, item.getPrice()));
        }else{
            holder.price.setVisibility(View.GONE);
        }

        if (item.getOffertype() == TargetItem.Offertype.BOTH ||
                item.getOffertype() == TargetItem.Offertype.AUCTION) {
            holder.priceBid.setVisibility(View.VISIBLE);
            holder.priceBid.setText(MyUtils.getPrice(context, item.getPriceBid()));
        }else{
            holder.priceBid.setVisibility(View.GONE);
        }

        if (item.getImageUrl() != null && !item.getImageUrl().isEmpty()) {
            holder.mItemImage.startAnimation(MyUtils.createRotateAnimation(context));
            Picasso.with(context).load(item.getImageUrl()).placeholder(R.drawable.ic_loading).into(holder.mItemImage, new Callback() {
                @Override
                public void onSuccess() {
                    holder.mItemImage.clearAnimation();
                }

                @Override
                public void onError() {
                    if (Build.VERSION.SDK_INT >= 21) {
                        holder.mItemImage.setImageDrawable(context.getDrawable(R.drawable.item_no_image));
                    } else {
                        holder.mItemImage.setImageDrawable(context.getResources().getDrawable(R.drawable.item_no_image));
                    }
                }
            });
        }

        if (mOnClickListener != null) {
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClickListener.onClick(item);
                }
            });
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

    public void setOnClickListener(OnClickDrawerItemListener onClickListener) {
        mOnClickListener = onClickListener;
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

    public void setItems(List<TargetItem> targetList) {
        targetItems.clear();
        targetItems.addAll(targetList);
        notifyDataSetChanged();
    }

    public interface OnClickDrawerItemListener {
        void onClick(TargetItem targetItem);
    }
}
