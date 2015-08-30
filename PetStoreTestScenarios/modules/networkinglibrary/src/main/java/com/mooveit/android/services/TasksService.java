package com.mooveit.android.services;

import android.content.Context;

import com.mooveit.android.dependencies.Injector;
import com.octo.android.robospice.SpiceService;
import com.octo.android.robospice.networkstate.NetworkStateChecker;
import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.exception.CacheCreationException;
import com.octo.android.robospice.request.CachedSpiceRequest;
import com.octo.android.robospice.request.SpiceRequest;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.Set;

public class TasksService extends SpiceService {

    private final NetworkStateChecker mNetworkStateChecker = new NetworkStateChecker() {
        @Override
        public boolean isNetworkAvailable(Context context) {
            return true;
        }

        @Override
        public void checkPermissions(Context context) {
        }
    };

    @Override
    public void addRequest(CachedSpiceRequest<?> request, Set<RequestListener<?>> listRequestListener) {
        SpiceRequest<?> spiceRequest = request.getSpiceRequest();

        Injector.inject(spiceRequest, this);

        if (listRequestListener != null) {
            Injector.injectOverObjects(listRequestListener, this);
        }

        super.addRequest(request, listRequestListener);
    }

    @Override
    public CacheManager createCacheManager(android.app.Application application) throws CacheCreationException {
        return new CacheManager();
    }

    @Override
    protected NetworkStateChecker getNetworkStateChecker() {
        return mNetworkStateChecker;
    }
}
