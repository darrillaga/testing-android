package com.mooveit.twittertopics.networking.services;

import android.app.Application;

import com.octo.android.robospice.Jackson2SpringAndroidSpiceService;
import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.exception.CacheCreationException;
import com.octo.android.robospice.persistence.springandroid.json.jackson2.Jackson2ObjectPersisterFactory;

import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class NetworkingService extends Jackson2SpringAndroidSpiceService {

    private static final int WEBSERVICES_TIMEOUT = 10000;

    @Override
    public CacheManager createCacheManager(Application application) throws CacheCreationException {
        CacheManager cacheManager = new CacheManager();
        cacheManager.addPersister(new Jackson2ObjectPersisterFactory(application));
        com.mooveit.twittertopics.Application.get(application).setCacheManager(cacheManager);

        return cacheManager;
    }

    @Override
    public RestTemplate createRestTemplate() {

        RestTemplate restTemplate = new RestTemplate();

        HttpComponentsClientHttpRequestFactory httpRequestFactory =
                new HttpComponentsClientHttpRequestFactory();

        httpRequestFactory.setReadTimeout(WEBSERVICES_TIMEOUT);
        httpRequestFactory.setConnectTimeout(WEBSERVICES_TIMEOUT);
        restTemplate.setRequestFactory(httpRequestFactory);

        MappingJackson2HttpMessageConverter jsonConverter = createJsonConverter();

        final List<HttpMessageConverter<?>> listHttpMessageConverters =
                restTemplate.getMessageConverters();

        listHttpMessageConverters.add(jsonConverter);
        listHttpMessageConverters.add(new StringHttpMessageConverter());

        restTemplate.setMessageConverters(listHttpMessageConverters);

        return restTemplate;
    }

    private MappingJackson2HttpMessageConverter createJsonConverter(){
        MappingJackson2HttpMessageConverter jsonConverter =
                new MappingJackson2HttpMessageConverter();

        List supportedTypes = new ArrayList();
        supportedTypes.add(MediaType.APPLICATION_JSON);
        jsonConverter.setSupportedMediaTypes(supportedTypes);

        jsonConverter.setObjectMapper(
                com.mooveit.twittertopics.Application.get(this).getObjectMapper()
        );

        return jsonConverter;
    }
}
