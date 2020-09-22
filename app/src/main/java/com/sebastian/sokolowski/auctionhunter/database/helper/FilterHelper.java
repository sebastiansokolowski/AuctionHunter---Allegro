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

import com.google.gson.internal.LinkedTreeMap;
import com.sebastian.sokolowski.auctionhunter.R;
import com.sebastian.sokolowski.auctionhunter.database.models.FilterModel;
import com.sebastian.sokolowski.auctionhunter.database.models.FilterValueModel;
import com.sebastian.sokolowski.auctionhunter.database.models.RealmString;
import com.sebastian.sokolowski.auctionhunter.database.models.TargetItem;
import com.sebastian.sokolowski.auctionhunter.rest.response.CategoryParameter;
import com.sebastian.sokolowski.auctionhunter.rest.response.CategoryParameterDictionary;
import com.sebastian.sokolowski.auctionhunter.rest.response.CategoryParameterType;
import com.sebastian.sokolowski.auctionhunter.rest.response.Listing;
import com.sebastian.sokolowski.auctionhunter.rest.response.ListingOffer;
import com.sebastian.sokolowski.auctionhunter.rest.response.OfferImages;
import com.sebastian.sokolowski.auctionhunter.rest.response.SellingModeFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Sebastian Sokołowski on 05.03.17.
 */

public class FilterHelper {
    private static List<String> filtersIdToSkip = Arrays.asList();

    public static List<TargetItem> createTargetItems(Listing itemList) {
        List<TargetItem> targetItems = new ArrayList<>();

        List<ListingOffer> offers = new ArrayList<>();
        offers.addAll(itemList.getItems().getPromoted());
        offers.addAll(itemList.getItems().getRegular());

        for (ListingOffer item : offers
        ) {
            TargetItem targetItem = new TargetItem();
            targetItem.setId(item.getId());
            targetItem.setName(item.getName());
            targetItem.setSellingModeFormat(item.getSellingMode().getFormat());

            for (OfferImages offerImages : item.getImages()
            ) {
                targetItem.setImageUrl(offerImages.getUrl());
            }

            if (item.getSellingMode().getFormat() == SellingModeFormat.AUCTION) {
                targetItem.setPriceBid(Float.parseFloat(item.getSellingMode().getPrice().getAmount()));
                if (item.getSellingMode().getFixedPrice() != null) {
                    targetItem.setPrice(Float.parseFloat(item.getSellingMode().getFixedPrice().getAmount()));
                }
            }
            float fullPrice = Float.parseFloat(item.getSellingMode().getPrice().getAmount()) +
                    Float.parseFloat(item.getDelivery().getLowestPrice().getAmount());
            targetItem.setPriceFull(fullPrice);

            targetItems.add(targetItem);
        }

        return targetItems;
    }

    public static HashMap<FilterModel, View> createDefaultFiltersViews(Context context) {
        List<CategoryParameter> categoryParameters = new ArrayList<>();
        //Price
        LinkedTreeMap<String, Object> priceRestrictions = new LinkedTreeMap<>();
        priceRestrictions.put("range", true);
        CategoryParameter priceCategoryParameter = new CategoryParameter(
                "price",
                "Cena",
                CategoryParameterType.FLOAT,
                false,
                false,
                "zł",
                priceRestrictions,
                null
        );
        categoryParameters.add(priceCategoryParameter);
        //SellingMode
        LinkedTreeMap<String, Object> sellingModeRestrictions = new LinkedTreeMap<>();
        sellingModeRestrictions.put("multipleChoices", true);
        List<CategoryParameterDictionary> sellingModes = new ArrayList<>();
        sellingModes.add(new CategoryParameterDictionary("BUY_NOW", "Kup teraz", new ArrayList<>()));
        sellingModes.add(new CategoryParameterDictionary("AUCTION", "Aukcja", new ArrayList<>()));
        sellingModes.add(new CategoryParameterDictionary("ADVERTISEMENT", "Ogłoszenie", new ArrayList<>()));
        CategoryParameter sellingModeCategoryParameter = new CategoryParameter(
                "sellingMode.format",
                "Rodzaj oferty",
                CategoryParameterType.DICTIONARY,
                false,
                false,
                "",
                sellingModeRestrictions,
                sellingModes
        );
        categoryParameters.add(sellingModeCategoryParameter);
        //SearchMode
        LinkedTreeMap<String, Object> searchModeRestrictions = new LinkedTreeMap<>();
        List<CategoryParameterDictionary> searchModes = new ArrayList<>();
        searchModes.add(new CategoryParameterDictionary("REGULAR", "W tytule", new ArrayList<>()));
        searchModes.add(new CategoryParameterDictionary("DESCRIPTIONS", "W tytule i opisach", new ArrayList<>()));
        searchModes.add(new CategoryParameterDictionary("CLOSED", "W tytule ofert zamkniętych", new ArrayList<>()));
        CategoryParameter searchModeCategoryParameter = new CategoryParameter(
                "searchMode",
                "Wyszukiwanie",
                CategoryParameterType.DICTIONARY,
                false,
                false,
                "",
                searchModeRestrictions,
                searchModes
        );
        categoryParameters.add(searchModeCategoryParameter);
        //Location
        LinkedTreeMap<String, Object> locationRestrictions = new LinkedTreeMap<>();
        CategoryParameter locationCategoryParameter = new CategoryParameter(
                "location.city",
                "Lokalizacja",
                CategoryParameterType.STRING,
                false,
                false,
                "",
                locationRestrictions,
                null
        );
        categoryParameters.add(locationCategoryParameter);
        //Options
        LinkedTreeMap<String, Object> optionsRestrictions = new LinkedTreeMap<>();
        optionsRestrictions.put("multipleChoices", true);
        List<CategoryParameterDictionary> options = new ArrayList<>();
        options.add(new CategoryParameterDictionary("FREE_SHIPPING", "Darmowa wysyłka", new ArrayList<>()));
        options.add(new CategoryParameterDictionary("FREE_RETURN", "Darmowy zwrot", new ArrayList<>()));
        options.add(new CategoryParameterDictionary("VAT_INVOICE", "Faktura VAT", new ArrayList<>()));
        options.add(new CategoryParameterDictionary("COINS", "Monety Allegro", new ArrayList<>()));
        options.add(new CategoryParameterDictionary("BRAND_ZONE", "Strefa Marek", new ArrayList<>()));
        options.add(new CategoryParameterDictionary("SUPERSELLER", "Super Sprzedawca", new ArrayList<>()));
        options.add(new CategoryParameterDictionary("CHARITY", "Oferta charytatywna", new ArrayList<>()));
        options.add(new CategoryParameterDictionary("SMART", "Allegro Smart", new ArrayList<>()));
        CategoryParameter optionCategoryParameter = new CategoryParameter(
                "option",
                "Opcje",
                CategoryParameterType.DICTIONARY,
                false,
                false,
                null,
                optionsRestrictions,
                options
        );
        categoryParameters.add(optionCategoryParameter);

        return createFiltersViews(context, categoryParameters, false);
    }

    public static HashMap<FilterModel, View> createFiltersViews(Context context, List<CategoryParameter> filterItemList, boolean parameter) {
        HashMap<FilterModel, View> filterViewHashMap = new HashMap<>();

        for (final CategoryParameter categoryParameter : filterItemList
        ) {
            if (filtersIdToSkip.contains(categoryParameter.getId())) {
                continue;
            }

            final FilterModel filterModel = new FilterModel();
            filterModel.setFilterId(categoryParameter.getId());
            filterModel.setFilterName(categoryParameter.getName());
            filterModel.setParameter(parameter);
            filterModel.setDataTypeEnum(categoryParameter.getType());
            switch (categoryParameter.getType()) {
                case INTEGER:
                case FLOAT:
                    boolean range = (boolean) categoryParameter.getRestrictions().getOrDefault("range", false);
                    filterModel.setRange(range);
                    break;
                case DICTIONARY:
                    boolean multipleChoices = (boolean) categoryParameter.getRestrictions().getOrDefault("multipleChoices", false);
                    filterModel.setMultipleChoices(multipleChoices);
                    break;
            }

            if (categoryParameter.getDictionary() != null) {
                for (CategoryParameterDictionary categoryParameterDictionary : categoryParameter.getDictionary()
                ) {
                    FilterValueModel filterValueModel = new FilterValueModel();
                    filterValueModel.setFilterValueId(categoryParameterDictionary.getId());
                    filterValueModel.setFilterValueName(categoryParameterDictionary.getValue());

                    filterModel.addFilterValue(filterValueModel);
                }
            }

            //set view

            LinearLayout view = new LinearLayout(context);
            view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            view.setOrientation(LinearLayout.VERTICAL);

            TextView textView = new TextView(context);
            textView.setTypeface(Typeface.DEFAULT_BOLD);
            textView.setTextAppearance(context, android.R.style.TextAppearance_Medium);
            textView.setText(filterModel.getFilterName());
            view.addView(textView);

            switch (filterModel.getDataTypeEnum()) {
                case DICTIONARY:
                    if (filterModel.isMultipleChoices()) {
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
                    } else {
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
                    }
                    break;
                case INTEGER:
                case FLOAT:
                case STRING:
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

    private static EditText getEditTextView(Context context, CategoryParameterType dataTypeEnum) {
        EditText editText = new EditText(context);
        switch (dataTypeEnum) {
            case STRING:
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
                editText.setMaxLines(1);
                break;
            case FLOAT:
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case INTEGER:
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                editText.setMaxEms(9);
                break;
        }

        return editText;
    }
}
