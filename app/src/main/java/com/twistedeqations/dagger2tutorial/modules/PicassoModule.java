package com.twistedeqations.dagger2tutorial.modules;

import android.content.Context;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import com.twistedeqations.dagger2tutorial.annotations.ApplicationContextQualifier;
import com.twistedeqations.dagger2tutorial.annotations.GithubApplicationScope;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module(includes = {ContextModule.class, NetworkModule.class})
public class PicassoModule {

    @Provides
    @GithubApplicationScope
    // binds this @Provides method GithubApplicationComponent, to ensure that only one instance will exist (reuse)
    // like in that scope reuse instance instead of creating new one
    // короче говоря, инстанс будет реюзаться в этом скоупе. Мы также отметили этой аннотацией наш Компонент (пометели, где его скоуп)
    public Picasso picasso(
            /*@Named("application_context") */ @ApplicationContextQualifier Context context,
            OkHttpClient okHttpClient, OkHttp3Downloader okHttp3Downloader) {
        return new Picasso.Builder(context)
                .downloader(okHttp3Downloader) // from PicassoModule.okHttp3Downloader()
                .build();
    }

    @Provides
    @GithubApplicationScope
    public OkHttp3Downloader okHttp3Downloader(OkHttpClient okHttpClient) {
        return new OkHttp3Downloader(okHttpClient);
    }
}
