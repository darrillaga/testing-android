package com.mooveit.android.networking.helpers;

import android.net.Uri;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ObjectParamsCreator {

    public static ObjectParamsCreator instance;

    private WeakReference<ObjectMapper> mWeakObjectMapper;

    private ObjectParamsCreator(ObjectMapper objectMapper){
        this.mWeakObjectMapper = new WeakReference<ObjectMapper>(objectMapper);
    }

    public static ObjectParamsCreator getInstance() {
        if (instance == null) {
            throw new RuntimeException("You must initialize it before call getInstance");
        }
        return instance;
    }

    public static void init(ObjectMapper objectMapper){
        if (instance == null){
            instance = new ObjectParamsCreator(objectMapper);
        }
    }

    public String queryParamsFromObject(String root, Object object) {
        return queryParamsFromObject(root, object, false);
    }

    public String queryParamsFromObject(String root, Object object, boolean encode) {
        if (object instanceof Iterable) {
            return queryParamsFromIterable(root, (Iterable) object, encode);
        } else if (object instanceof Map) {
            return queryParamsFromMap(root, (Map) object, encode);
        } else {
            return queryParamsFromOtherObject(root, object, encode);
        }
    }

    public String queryParamsFromMap(String root, Map<Object, Object> map, boolean encode) {
        StringBuilder builder = new StringBuilder();
        String newRoot;
        String param;
        String key;

        for(Map.Entry<Object, Object> entry : map.entrySet()) {

            key = String.valueOf(entry.getKey());

            if (encode) {
                key = Uri.encode(key);
            }

            if (root.equals("")) {
                newRoot = key;
            } else {
                newRoot = root + "[" + key + "]";
            }

            param = queryParamsFromObject(
                    newRoot,
                    entry.getValue(),
                    encode
            );

            if (param != null && param.length() != 0) {
                builder.append(param).append("&");
            }
        }

        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }

        return builder.toString();
    }

    public String queryParamsFromIterable(String root, Iterable iterable, boolean encode) {
        StringBuilder builder = new StringBuilder();

        Iterator iterator = iterable.iterator();
        Object object;

        int index = 0;

        while(iterator.hasNext()) {
            object = iterator.next();
            builder.append(queryParamsFromObject(root + "[" + index + "]", object, encode));
            builder.append("&");

            index++;
        }

        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }

        return builder.toString();
    }

    public static String queryParamsFromOtherObject(String root, Object object, boolean encode) {
        String value = "";
        if (object != null) {
            value = String.valueOf(object);
        }

        if (encode) {
            value = Uri.encode(value);
        }

        return root + "=" + value;
    }

    public Map<String, String> objectToMap(Object object) {
        Map<String, String> data = new HashMap<String, String>();

        try {
            ObjectMapper mapper = getObjectMapper();

            data.putAll((HashMap<String, String>) mapper.convertValue(
                    object,
                    new TypeReference<HashMap<String, String>>() {
                    }
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    public <T> T convertWithView(Object object, Class<T> clazz, Class<?> view)
            throws IOException {

        ObjectMapper mapper = getObjectMapper();
        return mapper.readValue(
                mapper.
                        disable(MapperFeature.DEFAULT_VIEW_INCLUSION).
                        writerWithView(view).
                        writeValueAsString(object),
                clazz
        );
    }

    public ObjectMapper getObjectMapper() {
        return mWeakObjectMapper.get();
    }
}
