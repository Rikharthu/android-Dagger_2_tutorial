package com.twistedeqations.dagger2tutorial.modules;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.twistedeqations.dagger2tutorial.network.DateTimeConverter;
import com.twistedeqations.dagger2tutorial.network.GithubService;

import org.joda.time.DateTime;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Configures how Dagger should generate an instance of a Component (GithubApplicationComponent)
 */
// also specify which Modules it depends on (if any)
@Module(includes = NetworkModule.class)
public class GithubServiceModule {

    // Tell Dagger that this method provides dependencies
    @Provides
    public GithubService getGithubService(Retrofit gitHubRetrofit){
        return gitHubRetrofit.create(GithubService.class);
    }

    @Provides
    public Gson gson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeConverter());
        return gsonBuilder.create();
    }

    @Provides
    public Retrofit retrofit(OkHttpClient okHttpClient, Gson gson){
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient) // tell retrofit to use our OkHttpClient
                .baseUrl("https://api.github.com/")
                .build();
    }
}
