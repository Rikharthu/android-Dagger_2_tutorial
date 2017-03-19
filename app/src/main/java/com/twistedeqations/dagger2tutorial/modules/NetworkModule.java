package com.twistedeqations.dagger2tutorial.modules;

import android.content.Context;

import com.twistedeqations.dagger2tutorial.annotations.ApplicationContextQualifier;
import com.twistedeqations.dagger2tutorial.annotations.GithubApplicationScope;

import java.io.File;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

@Module(includes = ContextModule.class)
public class NetworkModule {

    @Provides
    @GithubApplicationScope
    public HttpLoggingInterceptor loggingInterceptor() {
        HttpLoggingInterceptor interceptor;
        interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Timber.i(message);
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return interceptor;
    }

    @Provides
    @GithubApplicationScope
    public Cache cache(File cachefile) {
        return new Cache(cachefile, 10 * 1024 * 1024); // 10MB
    }

    @Provides
    @GithubApplicationScope
    public File cacheFile(@ApplicationContextQualifier Context context) {
        File cachefile = new File(context.getCacheDir(), "okhttp_cache");
        cachefile.mkdirs();
        return cachefile;
    }

    @Provides
    @GithubApplicationScope
    public OkHttpClient okHttpClient(HttpLoggingInterceptor loggingInterceptor, Cache cache) {
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .cache(cache)
                .build();
    }
}
