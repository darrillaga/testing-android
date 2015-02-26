package com.mooveit.twittertopics.networking.requests;

import android.content.Context;
import android.util.Base64;

import com.mooveit.twittertopics.R;
import com.mooveit.twittertopics.entities.Token;
import com.mooveit.twittertopics.networking.utils.UriParamsCreator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;

public class TokenRequest extends BaseRequest<Token> {

    private static final String AUTHORIZATION_KEY = "Authorization";
    private static final String AUTHORIZATION_PLACEHOLDER = "Basic ";

    private static final String GRANT_TYPE_PARAM_KEY = "grant_type";
    private static final String GRANT_TYPE_PARAM_VALUE = "client_credentials";

    private Context mContext;

    public TokenRequest(Context context) {
        super(Token.class);
        mContext = context;
    }

    @Override
    public String getBaseRoute() {
        return mContext.getString(R.string.oauthUrl);
    }

    @Override
    public String getParams(boolean encoded) {
        String params = super.getParams(encoded);

        Map<String, String> paramsMap = new HashMap<>();

        paramsMap.put(GRANT_TYPE_PARAM_KEY, GRANT_TYPE_PARAM_VALUE);

        return UriParamsCreator.create(params, paramsMap, encoded);
    }

    @Override
    public HttpMethod getMethod() {
        return HttpMethod.POST;
    }

    @Override
    public String getPath() {
        return null;
    }

    @Override
    public HttpHeaders getAuthenticationHeaders() {
        HttpHeaders authenticationHeaders = super.getAuthenticationHeaders();

        String apiString = mContext.getString(R.string.twitter_key) + ":" +
                mContext.getString(R.string.twitter_secret);

        authenticationHeaders.add(
                AUTHORIZATION_KEY,
                AUTHORIZATION_PLACEHOLDER +
                        Base64.encodeToString(apiString.getBytes(), Base64.NO_WRAP)
        );

        return authenticationHeaders;
    }



    public void setContext(Context context) {
        mContext = context;
    }
}
