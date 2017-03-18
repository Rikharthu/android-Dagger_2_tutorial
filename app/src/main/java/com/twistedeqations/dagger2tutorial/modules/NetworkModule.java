package com.twistedeqations.dagger2tutorial.modules;

import android.content.Context;

import java.io.File;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

@Module(includes = ContextModule.class)
public class NetworkModule {

    @Provides
    public HttpLoggingInterceptor loggingInterceptor(){
        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Timber.i(message);
            }
        });
    }

    @Provides
    public Cache cache(File cachefile){
        return new Cache(cachefile, 10*1024*1024); // 10MB
    }

    @Provides
    public File cacheFile(Context context){
        File cachefile = new File(context.getCacheDir(),"okhttp_cache");
        cachefile.mkdirs();
        return cachefile;
    }

    @Provides
    public OkHttpClient okHttpClient(HttpLoggingInterceptor loggingInterceptor, Cache cache){
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .cache(cache)
                .build();
    }
}
