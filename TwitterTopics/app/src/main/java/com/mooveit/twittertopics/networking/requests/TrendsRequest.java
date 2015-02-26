package com.mooveit.twittertopics.networking.requests;

import com.mooveit.twittertopics.entities.TrendList;
import com.mooveit.twittertopics.networking.utils.UriParamsCreator;
import java.util.HashMap;
import java.util.Map;

public class TrendsRequest extends TwitterRequest<TrendList[]> {

    private static final String PATH = "/trends/place.json";
    private static final String ID_PARAM_KEY = "id";
    private static final String ID_PARAM_VALUE = "1";

    public TrendsRequest() {
        super(TrendList[].class);
    }

    @Override
    public String getPath() {
        return PATH;
    }

    @Override
    public String getParams(boolean encoded) {
        String params = super.getParams(encoded);

        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put(ID_PARAM_KEY, ID_PARAM_VALUE);

        return UriParamsCreator.create(params, paramsMap, encoded);
    }
}
