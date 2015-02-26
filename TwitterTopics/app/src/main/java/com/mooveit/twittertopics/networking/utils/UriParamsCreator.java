package com.mooveit.twittertopics.networking.utils;

import android.net.Uri;

import java.util.Iterator;
import java.util.Map;

public class UriParamsCreator {

    public static String BEGINNING_TOKEN = "?";
    public static String ASSIGNMENT_TOKEN = "=";
    public static String CONCAT_TOKEN = "&";

    public static String create(String previous, Map<String, String> params) {
        return  create(previous, params, false);
    }

    public static String create(String previous, Map<String, String> params, boolean encode) {
        return previous + create(params, previous.length() == 0, encode);
    }

    public static String create(Map<String, String> params) {
        return create(params, true, false);
    }

    public static String create(Map<String, String> params, boolean encode) {
        return create(params, true, encode);
    }

    public static String create(Map<String, String> params,
                                boolean fromTheBeginning, boolean encode) {
        StringBuilder uriParams = new StringBuilder();

        Iterator<String> it = params.keySet().iterator();

        if (fromTheBeginning) {
            uriParams.append(BEGINNING_TOKEN);
        } else {
            uriParams.append(CONCAT_TOKEN);
        }

        while (it.hasNext()) {
            String key = it.next();
            String value = params.get(key);

            if (encode) {
                key = Uri.encode(key);
                value = Uri.encode(value);
            }

            uriParams.append(key).append(ASSIGNMENT_TOKEN)
                    .append(value).append(CONCAT_TOKEN);
        }
        uriParams.deleteCharAt(uriParams.length() - 1);

        return uriParams.toString();
    }
}
