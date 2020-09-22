package com.sebastian.sokolowski.auctionhunter.database.models;

import com.sebastian.sokolowski.auctionhunter.rest.response.CategoryParameterType;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Sebastian Sokołowski on 06.03.17.
 */

public class FilterModel extends RealmObject {
    private String filterId;
    private String filterName;
    private String dataTypeEnum;
    private boolean parameter;
    private boolean isRange;
    private boolean multipleChoices;
    private Integer arraySize;
    private RealmList<FilterValueModel> filterValueModels = new RealmList<>();

    //request models
    private RealmList<RealmString> filterValueIdList = new RealmList<>();
    private FilterValueRangeModel filterValueIdRange = new FilterValueRangeModel();

    public String getFilterId() {
        return filterId;
    }

    public void setFilterId(String filterId) {
        this.filterId = filterId;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public CategoryParameterType getDataTypeEnum() {
        if (dataTypeEnum == null || dataTypeEnum.isEmpty()) {
            return null;
        }
        return CategoryParameterType.valueOf(dataTypeEnum);
    }

    public void setDataTypeEnum(CategoryParameterType dataType) {
        this.dataTypeEnum = dataType.name();
    }

    public boolean isParameter() {
        return parameter;
    }

    public void setParameter(boolean parameter) {
        this.parameter = parameter;
    }

    public boolean isRange() {
        return isRange;
    }

    public void setRange(boolean range) {
        isRange = range;
    }

    public boolean isMultipleChoices() {
        return multipleChoices;
    }

    public void setMultipleChoices(boolean multipleChoices) {
        this.multipleChoices = multipleChoices;
    }

    public Integer getArraySize() {
        return arraySize;
    }

    public void setArraySize(Integer arraySize) {
        this.arraySize = arraySize;
    }

    public List<FilterValueModel> getFilterValueModels() {
        return filterValueModels;
    }

    public void addFilterValue(FilterValueModel filterValueModel) {
        this.filterValueModels.add(filterValueModel);
    }

    public List<RealmString> getFilterValueIdList() {
        return filterValueIdList;
    }

    public void addFilterValueId(RealmString filterValueId) {
        this.filterValueIdList.add(filterValueId);
    }

    public void removeAllFilterValueId() {
        this.filterValueIdList.clear();
    }

    public void removeFilterValueId(RealmString filterValueId) {
        this.filterValueIdList.remove(filterValueId);
    }

    public FilterValueRangeModel getFilterValueIdRange() {
        return filterValueIdRange;
    }
}
