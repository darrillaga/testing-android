package com.mooveit.android.services;

import com.mooveit.android.Application;
import com.mooveit.android.dependencies.Injector;
import com.mooveit.android.networking.helpers.RequestHelper;
import com.octo.android.robospice.Jackson2SpringAndroidSpiceService;
import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.exception.CacheCreationException;
import com.octo.android.robospice.persistence.springandroid.json.jackson2.Jackson2ObjectPersisterFactory;
import com.octo.android.robospice.request.CachedSpiceRequest;
import com.octo.android.robospice.request.listener.RequestListener;

import org.springframework.web.client.RestTemplate;

import java.util.Set;

public class NetworkingService extends Jackson2SpringAndroidSpiceService {

    @Override
    public void onCreate() {
        super.onCreate();

        RequestHelper.trustEveryone();
    }

    @Override
    public CacheManager createCacheManager(android.app.Application application) throws CacheCreationException {
        CacheManager cacheManager = new CacheManager();
        cacheManager.addPersister(new Jackson2ObjectPersisterFactory(application));
        ((Application) application).setCacheManager(cacheManager);

        return cacheManager;
    }

    @Override
    public RestTemplate createRestTemplate() {
        return Application.get(this).getRestTemplate();
    }

    @Override
    public void addRequest(CachedSpiceRequest<?> request, Set<RequestListener<?>> listRequestListener) {
        if (listRequestListener != null) {
            Injector.injectOverObjects(listRequestListener, this);
        }

        Injector.inject(request.getSpiceRequest(), this);

        super.addRequest(request, listRequestListener);
    }

}
