package com.mooveit.twittertopics.networking.requests;

import android.content.Context;

import com.mooveit.twittertopics.R;
import com.mooveit.twittertopics.entities.TweetList;
import com.mooveit.twittertopics.networking.utils.UriParamsCreator;

import java.util.HashMap;
import java.util.Map;

public class TweetsRequest extends TwitterRequest<TweetList> {

    private static final String PATH = "/search/tweets.json";
    private static final String Q_PARAM_KEY = "q";
    private static final String COUNT_PARAM_KEY = "count";
    private static final String RESULT_TYPE_PARAM_KEY = "result_type";

    private Context mContext;
    private String mQuery;
    private boolean mHasNextResults;

    public TweetsRequest(Context context, String query, boolean hasNextResults) {
        super(TweetList.class);
        mContext = context;
        mQuery = query;
        mHasNextResults = hasNextResults;
    }

    @Override
    public String getParams(boolean encoded) {
        String params = super.getParams(encoded);

        if (mHasNextResults) {
            params += mQuery;
        } else {
            Map<String, String> paramsMap = new HashMap<>();

            paramsMap.put(Q_PARAM_KEY, mQuery);
            paramsMap.put(COUNT_PARAM_KEY, mContext.getString(R.string.count));
            paramsMap.put(RESULT_TYPE_PARAM_KEY, mContext.getString(R.string.result_type));

            params = UriParamsCreator.create(params, paramsMap, encoded);
        }

        return params;
    }

    @Override
    public String getPath() {
        return PATH;
    }
}
