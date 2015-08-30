package com.mooveit.android;


import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mooveit.android.networking.RestTemplateBuilder;
import com.mooveit.android.networking.helpers.ObjectMapperUtils;
import com.mooveit.android.networking.helpers.ObjectParamsCreator;
import com.mooveit.android.networking.helpers.SpiceManagerFactory;
import com.mooveit.android.networking.helpers.SpiceManagerInstanceProvider;
import com.mooveit.android.networking.helpers.TasksManagerFactory;
import com.mooveit.android.services.NetworkingService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.CacheManager;

import org.springframework.web.client.RestTemplate;

public class Application extends android.app.Application implements
        SpiceManagerInstanceProvider {

    private CacheManager mCacheManager;
    private ObjectMapper mObjectMapper;
    private RestTemplate mRestTemplate;
    private SpiceManagerFactory mSpiceManagerFactory = new SpiceManagerFactory(this);
    private TasksManagerFactory mTasksManagerFactory = new TasksManagerFactory();

    public static Application get(Context context) {
        return (Application) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mObjectMapper = ObjectMapperUtils.createObjectMapper(this);

        mRestTemplate = RestTemplateBuilder.build(this);

        ObjectParamsCreator.init(mObjectMapper);
    }

    public CacheManager getCacheManager() {
        return mCacheManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.mCacheManager = cacheManager;
    }

    public ObjectMapper getObjectMapper() {
        return mObjectMapper;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.mObjectMapper = objectMapper;
    }

    public RestTemplate getRestTemplate() {
        return mRestTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        mRestTemplate = restTemplate;
    }

    public SpiceManagerFactory getSpiceManagerFactory() {
        return mSpiceManagerFactory;
    }

    @Override
    public SpiceManager provideSpiceManager() {
        return new SpiceManager(NetworkingService.class);
    }

    public SpiceManager getTasksManager() {
        return mTasksManagerFactory.getTasksManagerInstance();
    }

    public void setTasksManagerFactory(TasksManagerFactory tasksManagerFactory) {
        mTasksManagerFactory = tasksManagerFactory;
    }
}
