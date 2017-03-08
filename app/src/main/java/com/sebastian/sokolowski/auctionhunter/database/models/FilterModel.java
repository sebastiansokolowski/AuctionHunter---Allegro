package com.sebastian.sokolowski.auctionhunter.database.models;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Sebastian Sokołowski on 06.03.17.
 */

public class FilterModel extends RealmObject {
    public enum ControlTypeEnum {
        CONTROL_TYPE_COMBOBOX("combobox"), CONTROL_TYPE_CHECKBOX("checkbox"), CONTROL_TYPE_TEXTBOX("textbox");

        String value;

        ControlTypeEnum(String value) {
            this.value = value;
        }
    }

    public enum DataTypeEnum {
        DATA_TYPE_STRING("string"), DATA_TYPE_INT("int"), DATA_TYPE_LONG("long"), DATA_TYPE_FLOAT("float"), DATA_TYPE_DATETIME("datetime");

        String value;

        DataTypeEnum(String value) {
            this.value = value;
        }
    }

    @PrimaryKey
    private String filterId;
    private String filterName;
    private String controlTypeEnum;
    private String dataTypeEnum;
    private Boolean isRange;
    private Integer arraySize;
    private RealmList<FilterValueModel> filterValueModels = new RealmList<>();

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

    public ControlTypeEnum getControlTypeEnum() {
        if (dataTypeEnum == null || dataTypeEnum.isEmpty()) {
            return null;
        }
        return ControlTypeEnum.valueOf(controlTypeEnum);
    }

    public void setControlTypeEnum(String controlType) {
        for (ControlTypeEnum controlTypeEnum : ControlTypeEnum.values()
                ) {
            if(controlTypeEnum.value.equals(controlType)){
                this.controlTypeEnum = controlTypeEnum.toString();
            }
        }
    }

    public DataTypeEnum getDataTypeEnum() {
        if (dataTypeEnum == null || dataTypeEnum.isEmpty()) {
            return null;
        }
        return DataTypeEnum.valueOf(dataTypeEnum);
    }

    public void setDataTypeEnum(String dataType) {
        for (DataTypeEnum dataTypeEnum : DataTypeEnum.values()
                ) {
            if(dataTypeEnum.value.equals(dataType)){
                this.dataTypeEnum = dataTypeEnum.toString();
            }
        }
    }

    public Boolean getRange() {
        return isRange;
    }

    public void setRange(Boolean range) {
        isRange = range;
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
}
