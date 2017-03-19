package com.twistedeqations.dagger2tutorial.modules;

import com.squareup.picasso.Picasso;
import com.twistedeqations.dagger2tutorial.annotations.HomeActivityScope;
import com.twistedeqations.dagger2tutorial.screens.HomeActivity;
import com.twistedeqations.dagger2tutorial.screens.home.AdapterRepos;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeActivityModule {

    private final HomeActivity homeActivity;

    public HomeActivityModule(HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
    }

    @Provides
    @HomeActivityScope
    public HomeActivity provideHomeActivity(){
        return homeActivity;
    }

    // Replaced by @Inject in the AdapterRepos's constructor
    // and probideHomeActivity();
    /*
    @Provides
    @HomeActivityScope
    public AdapterRepos provideAdapterRepos(Picasso picasso){
        return new AdapterRepos(homeActivity, picasso);
    }
    */
}
