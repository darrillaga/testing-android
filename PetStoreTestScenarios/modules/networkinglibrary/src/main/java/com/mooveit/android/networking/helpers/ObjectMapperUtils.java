package com.mooveit.android.networking.helpers;

import android.content.Context;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.math.BigDecimal;

public class ObjectMapperUtils {

    public static ObjectMapper createObjectMapper(Context context) {
        ObjectMapper mapper = new ObjectMapper();

        mapper.setPropertyNamingStrategy(
                PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES
        );

        mapper.configure(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false
        );

//        mapper.setDateFormat(DateUtils.getIsoDateFormat(context));
//        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);

//        SimpleModule module = new SimpleModule();

//        module.addSerializer(BigDecimal.class, new BigDecimalSerializer());
//        mapper.registerModule(module);

        return mapper;
    }
}
