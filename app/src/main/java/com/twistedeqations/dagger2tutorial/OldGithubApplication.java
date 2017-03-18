package com.twistedeqations.dagger2tutorial;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import com.twistedeqations.dagger2tutorial.network.DateTimeConverter;
import com.twistedeqations.dagger2tutorial.network.GithubService;

import org.joda.time.DateTime;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/*
    DEPENDENCY TREE:

        Activity
    GitHubService Picasso
        Retrofit    OkHttp3Downloader
    Gson    OkHttp
          Logger Cache
          Timber File

 */

public class OldGithubApplication extends Application {

    public static OldGithubApplication get(Activity activity) {
        return (OldGithubApplication) activity.getApplication();
    }

    private GithubService githubService;
    private Picasso picasso;

    @Override
    public void onCreate() {
        super.onCreate();

        // --- CONTEXT ---
        Context context = this;

        // --- NETWORK ---
        // Prepare timber and OkHttp's HttpLoggingInterceptor to log urls and etc
        Timber.plant(new Timber.DebugTree());

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Timber.i(message);
            }
        });

        // Cache
        File cachefile = new File(getCacheDir(),"okhttp_cache");
        cachefile.mkdirs();
        Cache cache = new Cache(cachefile, 10*1024*1024); // 10MB

        // Prepare OkHttpClient
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .cache(cache)
                .build();

        // --- PICASSO ---
        // We want Picasso to use our OkHttp3 client instead of default
        picasso = new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(okHttpClient))
                .build();

        // --- CLIENT ---
        // Initialize Gson
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeConverter());
        Gson gson = gsonBuilder.create();

        // Build retrofit
        Retrofit gitHubRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient) // tell retrofit to use our OkHttpClient
                .baseUrl("https://api.github.com/")
                .build();

        githubService = gitHubRetrofit.create(GithubService.class);
    }

    public GithubService getGithubService() {
        return githubService;
    }

    public Picasso getPicasso() {
        return picasso;
    }
}