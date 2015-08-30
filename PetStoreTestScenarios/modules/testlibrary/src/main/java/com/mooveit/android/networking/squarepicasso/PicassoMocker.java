package com.mooveit.android.networking.squarepicasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.squareup.picasso.Cache;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;
import com.squareup.picasso.RequestHandler;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class PicassoMocker {

    private static PicassoMocker sPicassoMocker;

    public static PicassoMocker getInstance(Context context) {
        if (sPicassoMocker == null) {
            sPicassoMocker = new PicassoMocker(context);
            sPicassoMocker.mock();
        }

        return sPicassoMocker;
    }

    private Context mContext;
    private Cache mCache;
    private RequestHandlerCanHandleRequest mRequestHandlerCanHandleRequest;
    private RequestHandlerLoad mRequestHandlerLoad;
    private DownloaderLoad mDownloaderLoad;
    private DownloaderShutdown mDownloaderShutdown;
    private ExecutorService mExecutor;
    private Picasso.Listener mListener;
    private Picasso.RequestTransformer mRequestTransformer;

    private RequestHandler mRequestHandler = new RequestHandler() {
        @Override
        public boolean canHandleRequest(Request data) {
            return mRequestHandlerCanHandleRequest.canHandleRequest(data);
        }

        @Override
        public Result load(Request request, int networkPolicy) throws IOException {
            return mRequestHandlerLoad.load(request);
        }

    };

    private Downloader mDownloader = new Downloader() {

        @Override
        public Response load(Uri uri, int networkPolicy) throws IOException {
            return mDownloaderLoad.load(uri, networkPolicy);
        }

        @Override
        public void shutdown() {
            mDownloaderShutdown.shutdown();
        }
    };

    public PicassoMocker(Context context) {
        mContext = context;
        setInitState();
    }

    private void setInitState() {
        mCache = new LruCache(mContext);
        mRequestHandlerCanHandleRequest = (data) -> false;
        mRequestHandlerLoad = (data) -> null;
        mDownloaderLoad = (uri, localCacheOnly) -> null;
        mDownloaderShutdown = () -> {};
        mExecutor = Executors.newFixedThreadPool(1);
        mListener = (picasso, uri, exception) -> {};
        mRequestTransformer = (request) -> request;
    }

    public Picasso build() {
        Picasso.Builder picassoBuilder = new Picasso.Builder(mContext);
        picassoBuilder.addRequestHandler(mRequestHandler);
        picassoBuilder.downloader(mDownloader);
        picassoBuilder.executor(mProxyExecutorService);
        picassoBuilder.memoryCache(mProxyCache);
        picassoBuilder.listener(mProxyListener);
        picassoBuilder.requestTransformer(mProxyRequestTransformer);

        return picassoBuilder.build();
    }

    public void reset() {
        setInitState();
    }

    public Picasso mock() {
        Picasso picasso = build();
        Picasso.setSingletonInstance(picasso);
        return picasso;
    }

    public Cache getCache() {
        return mCache;
    }

    public void setCache(Cache cache) {
        mCache = cache;
    }

    public RequestHandlerCanHandleRequest getRequestHandlerCanHandleRequest() {
        return mRequestHandlerCanHandleRequest;
    }

    public RequestHandlerCanHandleRequest setRequestHandlerCanHandleRequest(
            RequestHandlerCanHandleRequest requestHandlerCanHandleRequest) {

        RequestHandlerCanHandleRequest prev = mRequestHandlerCanHandleRequest;
        mRequestHandlerCanHandleRequest = requestHandlerCanHandleRequest;
        return prev;
    }

    public RequestHandlerLoad getRequestHandlerLoad() {
        return mRequestHandlerLoad;
    }

    public RequestHandlerLoad setRequestHandlerLoad(RequestHandlerLoad requestHandlerLoad) {
        RequestHandlerLoad prev = mRequestHandlerLoad;
        mRequestHandlerLoad = requestHandlerLoad;
        return prev;
    }

    public DownloaderLoad getDownloaderLoad() {
        return mDownloaderLoad;
    }

    public DownloaderLoad setDownloaderLoad(DownloaderLoad downloaderLoad) {
        DownloaderLoad prev = mDownloaderLoad;
        mDownloaderLoad = downloaderLoad;
        return prev;
    }

    public DownloaderShutdown getDownloaderShutdown() {
        return mDownloaderShutdown;
    }

    public DownloaderShutdown setDownloaderShutdown(DownloaderShutdown downloaderShutdown) {
        DownloaderShutdown prev = mDownloaderShutdown;
        mDownloaderShutdown = downloaderShutdown;
        return prev;
    }

    public ExecutorService getExecutor() {
        return mExecutor;
    }

    public ExecutorService setExecutor(ExecutorService executor) {
        ExecutorService prev = mExecutor;
        mExecutor = executor;
        return prev;
    }

    public Picasso.Listener getListener() {
        return mListener;
    }

    public Picasso.Listener setListener(Picasso.Listener listener) {
        Picasso.Listener prev = mListener;
        mListener = listener;
        return prev;
    }

    public Picasso.RequestTransformer getRequestTransformer() {
        return mRequestTransformer;
    }

    public Picasso.RequestTransformer setRequestTransformer(Picasso.RequestTransformer requestTransformer) {
        Picasso.RequestTransformer prev = requestTransformer;
        mRequestTransformer = requestTransformer;
        return prev;
    }

    public interface RequestHandlerCanHandleRequest {
        boolean canHandleRequest(Request data);
    }

    public interface RequestHandlerLoad {
        RequestHandler.Result load(Request data) throws IOException;
    }

    public interface DownloaderLoad {
        Downloader.Response load(Uri uri, int networkPolicy) throws IOException;
    }

    public interface DownloaderShutdown {
        void shutdown();
    }

    private ExecutorService mProxyExecutorService = new ExecutorService() {
        @Override
        public void shutdown() {
            mExecutor.shutdown();
        }

        @NonNull
        @Override
        public List<Runnable> shutdownNow() {
            return mExecutor.shutdownNow();
        }

        @Override
        public boolean isShutdown() {
            return mExecutor.isShutdown();
        }

        @Override
        public boolean isTerminated() {
            return mExecutor.isTerminated();
        }

        @Override
        public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
            return mExecutor.awaitTermination(timeout, unit);
        }

        @NonNull
        @Override
        public <T> Future<T> submit(Callable<T> task) {
            return mExecutor.submit(task);
        }

        @NonNull
        @Override
        public <T> Future<T> submit(Runnable task, T result) {
            return mExecutor.submit(task, result);
        }

        @NonNull
        @Override
        public Future<?> submit(Runnable task) {
            return mExecutor.submit(task);
        }

        @Override
        public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
            return mExecutor.invokeAll(tasks);
        }

        @Override
        public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
            return mExecutor.invokeAll(tasks, timeout, unit);
        }

        @Override
        public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
            return mExecutor.invokeAny(tasks);
        }

        @Override
        public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return mExecutor.invokeAny(tasks, timeout, unit);
        }

        @Override
        public void execute(Runnable command) {
            mExecutor.execute(command);
        }
    };

    private Cache mProxyCache = new Cache() {
        @Override
        public Bitmap get(String key) {
            return mCache.get(key);
        }

        @Override
        public void set(String key, Bitmap bitmap) {
            mCache.set(key, bitmap);
        }

        @Override
        public int size() {
            return mCache.size();
        }

        @Override
        public int maxSize() {
            return mCache.maxSize();
        }

        @Override
        public void clear() {
            mCache.clear();
        }

        @Override
        public void clearKeyUri(String keyPrefix) {
            mCache.clearKeyUri(keyPrefix);
        }
    };

    private Picasso.Listener mProxyListener = (picasso, uri, exception) ->
            mListener.onImageLoadFailed(picasso, uri, exception);

    private Picasso.RequestTransformer mProxyRequestTransformer = request ->
            mRequestTransformer.transformRequest(request);
}
