package com.sebastian.sokolowski.auctionhunter.soap.envelopes;

import com.alexgilleran.icesoap.envelope.SOAPEnvelope;
import com.alexgilleran.icesoap.xml.XMLParentNode;
import com.sebastian.sokolowski.auctionhunter.soap.request.FilterOptionsType;
import com.sebastian.sokolowski.auctionhunter.soap.request.RangeValueType;
import com.sebastian.sokolowski.auctionhunter.soap.request.SortOptionsType;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Sebastian Sokołowski on 19.02.17.
 */

public class DoGetItemsListEnvelope extends BaseEnvelope {
    private List<FilterOptionsType> filterOptionsTypeList = new ArrayList<>();
    private SortOptionsType sortOptionsType;
    private Integer resultsSize;
    private Integer resultOffset;
    private Integer resultScope;

    public DoGetItemsListEnvelope() {
    }

    public SOAPEnvelope create() {
        XMLParentNode mainNode = getBody().addNode(
                NAMESPACE, "DoGetItemsListRequest");
        mainNode.addTextNode(NAMESPACE, "webapiKey", webApiKey);
        mainNode.addTextNode(NAMESPACE, "countryId", countryId.toString());

        if (filterOptionsTypeList.size() > 0) {
            XMLParentNode filterParent = mainNode.addNode(
                    NAMESPACE, "filterOptions");
            for (FilterOptionsType optionsType : filterOptionsTypeList
                    ) {
                XMLParentNode filterItem = filterParent.addNode(
                        NAMESPACE, "item");
                filterItem.addTextNode(NAMESPACE, "filterId", optionsType.getFilterId());


                RangeValueType rangeValueType = optionsType.getFilterValueRange();
                if (rangeValueType != null) {
                    XMLParentNode filterValueItem = filterItem.addNode(
                            NAMESPACE, "filterValueRange");
                    if (rangeValueType.getRangeValueMin() != null) {
                        filterValueItem.addTextNode(NAMESPACE, "rangeValueMin", rangeValueType.getRangeValueMin());
                    }
                    if (rangeValueType.getRangeValueMax() != null) {
                        filterValueItem.addTextNode(NAMESPACE, "rangeValueMax", rangeValueType.getRangeValueMax());
                    }
                } else {
                    XMLParentNode filterValueItem = filterItem.addNode(
                            NAMESPACE, "filterValueId");
                    for (String filterValue : optionsType.getFilterValueId()
                            ) {
                        filterValueItem.addTextNode(NAMESPACE, "item", filterValue);
                    }
                }
            }
        }

        if (sortOptionsType != null) {
            XMLParentNode sortOptions = mainNode.addNode(
                    NAMESPACE, "sortOptions");
            sortOptions.addTextNode(NAMESPACE, "sortType", sortOptionsType.getSortType().toString());
            sortOptions.addTextNode(NAMESPACE, "sortOrder", sortOptionsType.getSortOrder().toString());
        }

        if (resultsSize != null) {
            mainNode.addTextNode(NAMESPACE, "resultsSize", resultsSize.toString());
        }

        if (resultOffset != null) {
            mainNode.addTextNode(NAMESPACE, "resultOffset", resultOffset.toString());
        }

        if (resultScope != null) {
            mainNode.addTextNode(NAMESPACE, "resultScope", resultScope.toString());
        }

        return this;
    }

    public List<FilterOptionsType> getFilterOptionsType() {
        return filterOptionsTypeList;
    }

    public void addFilterOptionsType(FilterOptionsType filterOptionsType) {
        this.filterOptionsTypeList.add(filterOptionsType);
    }

    public SortOptionsType getSortOptionsType() {
        return sortOptionsType;
    }

    public void setSortOptionsType(SortOptionsType sortOptionsType) {
        this.sortOptionsType = sortOptionsType;
    }

    public Integer getResultsSize() {
        return resultsSize;
    }

    public void setResultsSize(Integer resultsSize) {
        this.resultsSize = resultsSize;
    }

    public Integer getResultOffset() {
        return resultOffset;
    }

    public void setResultOffset(Integer resultOffset) {
        this.resultOffset = resultOffset;
    }

    public Integer getResultScope() {
        return resultScope;
    }

    public void setResultScope(Integer resultScope) {
        this.resultScope = resultScope;
    }
}
