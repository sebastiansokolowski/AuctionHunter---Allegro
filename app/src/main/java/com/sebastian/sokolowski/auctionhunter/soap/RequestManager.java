package com.sebastian.sokolowski.auctionhunter.soap;

import com.alexgilleran.icesoap.observer.SOAPObserver;
import com.alexgilleran.icesoap.request.Request;
import com.alexgilleran.icesoap.request.RequestFactory;
import com.alexgilleran.icesoap.request.impl.RequestFactoryImpl;
import com.sebastian.sokolowski.auctionhunter.database.models.FilterModel;
import com.sebastian.sokolowski.auctionhunter.database.models.FilterValueRangeModel;
import com.sebastian.sokolowski.auctionhunter.database.models.RealmString;
import com.sebastian.sokolowski.auctionhunter.database.models.Target;
import com.sebastian.sokolowski.auctionhunter.soap.envelopes.DoGetCatsDataCountEnvelope;
import com.sebastian.sokolowski.auctionhunter.soap.envelopes.DoGetCatsDataLimitEnvelope;
import com.sebastian.sokolowski.auctionhunter.soap.envelopes.DoGetItemsListEnvelope;
import com.sebastian.sokolowski.auctionhunter.soap.fault.AllegroSOAPFault;
import com.sebastian.sokolowski.auctionhunter.soap.request.FilterOptionsType;
import com.sebastian.sokolowski.auctionhunter.soap.request.RangeValueType;
import com.sebastian.sokolowski.auctionhunter.soap.request.SortOptionsType;
import com.sebastian.sokolowski.auctionhunter.soap.request.SortOrderEnum;
import com.sebastian.sokolowski.auctionhunter.soap.request.SortTypeEnum;
import com.sebastian.sokolowski.auctionhunter.soap.response.doGetCatsDataCountResponse.DoGetCatsDataCountResponse;
import com.sebastian.sokolowski.auctionhunter.soap.response.doGetCatsDataLimitResponse.DoGetCatsDataLimitResponse;
import com.sebastian.sokolowski.auctionhunter.soap.response.doGetItemsListResponse.DoGetItemsListResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sebastain Sokołowski on 19.02.17.
 */

public class RequestManager {
    private final static String TAG = RequestManager.class.getSimpleName();
    private final String URL = "https://webapi.allegro.pl/service.php";
    private final RequestFactory requestFactory = new RequestFactoryImpl();

    public void doGetItemsList(Target target, SOAPObserver<DoGetItemsListResponse, AllegroSOAPFault> doGetItemsListResponseAllegroSOAPFaultSOAPObserver) {
        DoGetItemsListEnvelope doGetItemsListEnvelope = new DoGetItemsListEnvelope();
        doGetItemsListEnvelope.setResultScope(3);

        SortOptionsType sortOptionsType = new SortOptionsType();
        sortOptionsType.setSortOrder(SortOrderEnum.desc);
        sortOptionsType.setSortType(SortTypeEnum.startingTime);
        doGetItemsListEnvelope.setSortOptionsType(sortOptionsType);

        FilterOptionsType filterCategory = new FilterOptionsType();
        filterCategory.setFilterId("category");
        filterCategory.addFilterValueId(target.getCategoryId().toString());

        FilterOptionsType filterSearch = new FilterOptionsType();
        filterSearch.setFilterId("search");
        filterSearch.addFilterValueId(target.getSearchingName());

        doGetItemsListEnvelope.addFilterOptionsType(filterCategory);
        doGetItemsListEnvelope.addFilterOptionsType(filterSearch);

        if (target.getFilterModels() != null && target.getFilterModels().size() != 0) {
            for (FilterModel filterModel : target.getFilterModels()
                    ) {
                FilterOptionsType filterOptionsType = new FilterOptionsType();
                filterOptionsType.setFilterId(filterModel.getFilterId());

                boolean isRange = filterModel.isRange();
                if (isRange) {
                    FilterValueRangeModel filterValueRangeModel = filterModel.getFilterValueIdRange();

                    String minValue = filterValueRangeModel.getRangeValueMin();
                    String maxValue = filterValueRangeModel.getRangeValueMax();
                    if (minValue != null && !minValue.equals("") ||
                            maxValue != null && !maxValue.equals("")) {
                        RangeValueType rangeValueType = new RangeValueType();
                        rangeValueType.setRangeValueMin(minValue);
                        rangeValueType.setRangeValueMax(maxValue);

                        filterOptionsType.setFilterValueRange(rangeValueType);
                    } else {
                        continue;
                    }
                } else {
                    List<RealmString> realmStringList = filterModel.getFilterValueIdList();

                    if (realmStringList != null && realmStringList.size() > 0) {
                        List<String> stringList = new ArrayList<>();
                        for (RealmString realmString : realmStringList
                                ) {
                            stringList.add(realmString.getString());
                        }

                        filterOptionsType.addFilterValuesId(stringList);
                    } else {
                        continue;
                    }
                }
                doGetItemsListEnvelope.addFilterOptionsType(filterOptionsType);
            }
        }

        deGetItemsList(doGetItemsListEnvelope, doGetItemsListResponseAllegroSOAPFaultSOAPObserver);
    }

    public void deGetItemsList(DoGetItemsListEnvelope doGetItemsListEnvelope, SOAPObserver<DoGetItemsListResponse, AllegroSOAPFault> doGetItemsListResponseAllegroSOAPFaultSOAPObserver) {
        Request<DoGetItemsListResponse, AllegroSOAPFault> definitionRequest = requestFactory.buildRequest(
                URL,
                doGetItemsListEnvelope.create(),
                "#doGetItemsList",
                DoGetItemsListResponse.class, AllegroSOAPFault.class);
        definitionRequest.execute(doGetItemsListResponseAllegroSOAPFaultSOAPObserver);
    }

    public void doGetCatsDataCount(SOAPObserver<DoGetCatsDataCountResponse, AllegroSOAPFault> doGetCatsDataCountResponseAllegroSOAPFaultSOAPObserver) {
        Request<DoGetCatsDataCountResponse, AllegroSOAPFault> definitionRequest = requestFactory.buildRequest(
                URL,
                new DoGetCatsDataCountEnvelope(),
                "#doGetCatsDataCount",
                DoGetCatsDataCountResponse.class, AllegroSOAPFault.class);
        definitionRequest.execute(doGetCatsDataCountResponseAllegroSOAPFaultSOAPObserver);
    }

    public void doGetCatsDataLimit(DoGetCatsDataLimitEnvelope doGetCatsDataLimitEnvelope, SOAPObserver<DoGetCatsDataLimitResponse, AllegroSOAPFault> doGetCatsDataLimitResponseAllegroSOAPFaultSOAPObserver) {
        Request<DoGetCatsDataLimitResponse, AllegroSOAPFault> definitionRequest = requestFactory.buildRequest(
                URL,
                doGetCatsDataLimitEnvelope.create(),
                "#doGetCatsDataLimit",
                DoGetCatsDataLimitResponse.class, AllegroSOAPFault.class);
        definitionRequest.execute(doGetCatsDataLimitResponseAllegroSOAPFaultSOAPObserver);
    }
}
