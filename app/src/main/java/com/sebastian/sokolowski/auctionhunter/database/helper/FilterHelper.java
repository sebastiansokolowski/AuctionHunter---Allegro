package com.sebastian.sokolowski.auctionhunter.database.helper;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.sebastian.sokolowski.auctionhunter.R;
import com.sebastian.sokolowski.auctionhunter.database.models.FilterModel;
import com.sebastian.sokolowski.auctionhunter.database.models.FilterValueModel;
import com.sebastian.sokolowski.auctionhunter.database.models.RealmString;
import com.sebastian.sokolowski.auctionhunter.database.models.TargetItem;
import com.sebastian.sokolowski.auctionhunter.soap.response.doGetItemsListResponse.FilterItem;
import com.sebastian.sokolowski.auctionhunter.soap.response.doGetItemsListResponse.FilterValuesItem;
import com.sebastian.sokolowski.auctionhunter.soap.response.doGetItemsListResponse.Item;
import com.sebastian.sokolowski.auctionhunter.soap.response.doGetItemsListResponse.PhotoInfoType;
import com.sebastian.sokolowski.auctionhunter.soap.response.doGetItemsListResponse.PriceInfoType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Sebastian Sokołowski on 05.03.17.
 */

public class FilterHelper {
    private static List<String> filtersIdToSkip = Arrays.asList("search", "category", "startingTime");

    public static List<TargetItem> createTargetItems(List<Item> itemList) {
        List<TargetItem> targetItems = new ArrayList<>();

        for (Item item : itemList
                ) {
            TargetItem targetItem = new TargetItem();
            targetItem.setId(item.getItemId());
            targetItem.setName(item.getItemTitle());

            if(item.getPhotosInfo() != null){
                for (PhotoInfoType photoInfoType : item.getPhotosInfo()
                        ) {
                    switch (photoInfoType.getPhotoSize()) {
                        case PHOTO_TYPE_LARGE:
                            targetItem.setImageUrl(photoInfoType.getPhotoUrl());
                            break;
                    }
                }
            }

            for (PriceInfoType priceInfoType : item.getPriceInfo()
                    ) {
                switch (priceInfoType.getPriceType()) {
                    case PRICE_TYPE_BIDDING:
                        if (targetItem.getOffertype() == null) {
                            targetItem.setOffertype(TargetItem.Offertype.AUCTION);
                        } else {
                            targetItem.setOffertype(TargetItem.Offertype.BOTH);
                        }
                        targetItem.setPriceBid(priceInfoType.getPriceValue());
                        break;
                    case PRICE_TYPE_BUY_NOW:
                        if (targetItem.getOffertype() == null) {
                            targetItem.setOffertype(TargetItem.Offertype.BUY_NOW);
                        } else {
                            targetItem.setOffertype(TargetItem.Offertype.BOTH);
                        }
                        targetItem.setPrice(priceInfoType.getPriceValue());
                        break;
                    case PRICE_TYPE_WITH_DELIVERY:
                        targetItem.setPriceFull(priceInfoType.getPriceValue());
                        break;
                }
            }
            targetItems.add(targetItem);
        }

        return targetItems;
    }

    public static HashMap<FilterModel, View> createFiltersViews(Context context, List<FilterItem> filterItemList) {
        HashMap<FilterModel, View> filterViewHashMap = new HashMap<>();

        for (final FilterItem filterItem : filterItemList
                ) {
            if (filterItem.getFilterId() == null ||
                    filterItem.getFilterName() == null ||
                    filterItem.getFilterType() == null ||
                    filterItem.getFilterControlType() == null ||
                    filterItem.getFilterDataType() == null ||
                    filterItem.getFilterIsRange() == null
                    ) {
                continue;
            }
            if (filtersIdToSkip.contains(filterItem.getFilterId())) {
                continue;
            }

            final FilterModel filterModel = new FilterModel();
            filterModel.setFilterId(filterItem.getFilterId());
            filterModel.setFilterName(filterItem.getFilterName());
            filterModel.setControlTypeEnum(filterItem.getFilterControlType());
            filterModel.setDataTypeEnum(filterItem.getFilterDataType());
            filterModel.setRange(filterItem.getFilterIsRange());

            if (filterItem.getFilterValuesList() != null) {
                for (FilterValuesItem filterValuesItem : filterItem.getFilterValuesList()
                        ) {
                    FilterValueModel filterValueModel = new FilterValueModel();
                    if (filterValuesItem.getFilterValueName() != null) {
                        filterValueModel.setFilterValueName(filterValuesItem.getFilterValueName());
                    }
                    if (filterValuesItem.getFilterValueId() != null) {
                        filterValueModel.setFilterValueId(filterValuesItem.getFilterValueId());
                    }
                    if (filterValuesItem.getFilterValueCount() != null) {
                        filterValueModel.setFilterValueCount(filterValuesItem.getFilterValueCount());
                    }

                    filterModel.addFilterValue(filterValueModel);
                }
            }

            LinearLayout view = new LinearLayout(context);
            view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            view.setOrientation(LinearLayout.VERTICAL);

            TextView textView = new TextView(context);
            textView.setTypeface(Typeface.DEFAULT_BOLD);
            textView.setTextAppearance(context, android.R.style.TextAppearance_Medium);
            textView.setText(filterModel.getFilterName());
            view.addView(textView);

            switch (filterModel.getControlTypeEnum()) {
                case CONTROL_TYPE_CHECKBOX:
                    for (final FilterValueModel filterValueModel : filterModel.getFilterValueModels()
                            ) {
                        CheckBox checkBox = new CheckBox(context);
                        checkBox.setText(filterValueModel.getFilterValueName());
                        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                RealmString realmString = new RealmString(filterValueModel.getFilterValueId());
                                if (isChecked) {
                                    filterModel.addFilterValueId(realmString);
                                } else {
                                    filterModel.removeFilterValueId(realmString);
                                }
                            }
                        });
                        view.addView(checkBox);
                    }
                    break;
                case CONTROL_TYPE_COMBOBOX:
                    List<String> list = new ArrayList<>();

                    // add empty item which mean that user don't set filter
                    list.add("");
                    for (FilterValueModel filterValueModel : filterModel.getFilterValueModels()
                            ) {
                        list.add(filterValueModel.getFilterValueName());
                    }

                    Spinner spinner = new Spinner(context);
                    final ArrayAdapter<CharSequence> adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, list);
                    spinner.setAdapter(adapter);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            filterModel.removeAllFilterValueId();

                            if (position != 0) {
                                RealmString realmString = new RealmString(filterModel.getFilterValueModels().get(position - 1).getFilterValueId());
                                filterModel.addFilterValueId(realmString);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            filterModel.removeAllFilterValueId();
                        }
                    });

                    view.addView(spinner);
                    break;
                case CONTROL_TYPE_TEXTBOX:
                    if (filterModel.isRange()) {
                        LinearLayout linearLayoutHorizontal = new LinearLayout(context);
                        linearLayoutHorizontal.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        linearLayoutHorizontal.setOrientation(LinearLayout.HORIZONTAL);

                        // min value
                        final EditText editTextMin = getEditTextView(context, filterModel.getDataTypeEnum());
                        editTextMin.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 4));
                        editTextMin.setHint(context.getString(R.string.new_target_target_min));
                        editTextMin.setGravity(Gravity.CENTER);
                        editTextMin.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                filterModel.getFilterValueIdRange().setRangeValueMin(editTextMin.getText().toString());
                            }
                        });
                        linearLayoutHorizontal.addView(editTextMin);

                        // divider
                        TextView textDivider = new TextView(context);
                        textDivider.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
                        textDivider.setText("-");
                        textDivider.setTextSize(20);
                        textDivider.setGravity(Gravity.CENTER);
                        linearLayoutHorizontal.addView(textDivider);

                        //max value
                        final EditText editTextMax = getEditTextView(context, filterModel.getDataTypeEnum());
                        editTextMax.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 4));
                        editTextMax.setHint(context.getString(R.string.new_target_target_max));
                        editTextMax.setGravity(Gravity.CENTER);
                        editTextMax.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                filterModel.getFilterValueIdRange().setRangeValueMax(editTextMax.getText().toString());
                            }
                        });
                        linearLayoutHorizontal.addView(editTextMax);

                        view.addView(linearLayoutHorizontal);
                    } else {
                        final EditText editText = getEditTextView(context, filterModel.getDataTypeEnum());
                        editText.setMaxLines(1);
                        editText.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                filterModel.removeAllFilterValueId();

                                RealmString realmString = new RealmString(editText.getText().toString());
                                filterModel.getFilterValueIdList().add(realmString);
                            }
                        });
                        view.addView(editText);
                    }
                    break;
                default:
                    continue;
            }

            filterViewHashMap.put(filterModel, view);
        }

        return filterViewHashMap;
    }

    private static EditText getEditTextView(Context context, FilterModel.DataTypeEnum dataTypeEnum) {
        EditText editText = new EditText(context);
        switch (dataTypeEnum) {
            case DATA_TYPE_STRING:
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
                editText.setMaxLines(1);
                break;
            case DATA_TYPE_DATETIME:
                editText.setInputType(InputType.TYPE_CLASS_DATETIME);
                break;
            case DATA_TYPE_FLOAT:
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case DATA_TYPE_INT:
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                editText.setMaxEms(9);
                break;
            case DATA_TYPE_LONG:
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                editText.setMaxEms(18);
                break;
        }

        return editText;
    }
}
