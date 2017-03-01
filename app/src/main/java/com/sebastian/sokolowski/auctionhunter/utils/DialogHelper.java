package com.sebastian.sokolowski.auctionhunter.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.sebastian.sokolowski.auctionhunter.R;
import com.sebastian.sokolowski.auctionhunter.soap.request.SortOptionsType;
import com.sebastian.sokolowski.auctionhunter.soap.request.SortOrderEnum;
import com.sebastian.sokolowski.auctionhunter.soap.request.SortTypeEnum;

/**
 * Created by Sebastian Sokołowski on 27.02.17.
 */

public class DialogHelper {

    public static void deleteTargetDialog(Context context, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.dialog_message_remove_target)
                .setTitle(R.string.dialog_title_remove_target);
        builder.setPositiveButton(R.string.dialog_ok_button, listener);
        builder.setNegativeButton(R.string.dialog_cancel_button, listener);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public interface OnChangeSortType {
        void onChangeSortType(SortTypeEnum sortTypeEnum, SortOrderEnum sortOrderEnum);
    }

    public static void changeSortTypeDialog(Context context, final OnChangeSortType onChangeSortType) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.dialog_sort_type, null);
        builder.setView(view);

        final ListView listViewSortType = (ListView) view.findViewById(R.id.lv_sort_type);
        ArrayAdapter<String> adapterSortType = new ArrayAdapter<>(context, android.R.layout.simple_list_item_single_choice,
                context.getResources().getStringArray(R.array.sort_type));
        listViewSortType.setAdapter(adapterSortType);

        final ListView listViewSortOrder = (ListView) view.findViewById(R.id.lv_sort_order);
        ArrayAdapter<String> adapterSortOrder = new ArrayAdapter<>(context, android.R.layout.simple_list_item_single_choice,
                context.getResources().getStringArray(R.array.sort_order));
        listViewSortOrder.setAdapter(adapterSortOrder);


        builder.setPositiveButton(R.string.dialog_ok_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SortTypeEnum sortTypeEnum;
                SortOrderEnum sortOrderEnum;

                sortTypeEnum = SortTypeEnum.values()[listViewSortType.getCheckedItemPosition()];
                sortOrderEnum = SortOrderEnum.values()[listViewSortOrder.getCheckedItemPosition()];

                onChangeSortType.onChangeSortType(sortTypeEnum, sortOrderEnum);
            }
        });
        builder.setNegativeButton(R.string.dialog_cancel_button, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
