package com.sebastian.sokolowski.auctionhunter.main.views;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;

import com.sebastian.sokolowski.auctionhunter.main.adapter.MainAdapter;

/**
 * Created by Sebastian Sokołowski on 02.03.17.
 */

public class SliderRecyclerView extends RecyclerView {
    private static final String TAG = SliderRecyclerView.class.getSimpleName();

    public SliderRecyclerView(Context context) {
        super(context);
        initSwipe();
    }

    public SliderRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initSwipe();
    }

    public SliderRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initSwipe();
    }

    private void initSwipe() {
        ItemTouchHelper.Callback simpleCallback = new ItemTouchHelper.Callback() {

            @Override
            public boolean isItemViewSwipeEnabled() {
                return true;
            }

            @Override
            public int getMovementFlags(RecyclerView recyclerView, ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, ViewHolder viewHolder, ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(ViewHolder viewHolder, int direction) {
                if (getAdapter() != null) {
                    MainAdapter mainAdapter = (MainAdapter) getAdapter();
                    mainAdapter.onItemDismiss(viewHolder.getAdapterPosition());
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView,
                                    RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                    int actionState, boolean isCurrentlyActive) {

                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    ((MainAdapter.TargetViewHolder) viewHolder).container.setTranslationX(dX);

                } else {
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY,
                            actionState, isCurrentlyActive);
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(this);
    }
}
