package com.twistedeqations.dagger2tutorial.components;

import com.twistedeqations.dagger2tutorial.annotations.HomeActivityScope;
import com.twistedeqations.dagger2tutorial.modules.HomeActivityModule;
import com.twistedeqations.dagger2tutorial.network.GithubService;
import com.twistedeqations.dagger2tutorial.screens.HomeActivity;
import com.twistedeqations.dagger2tutorial.screens.home.AdapterRepos;

import dagger.Component;

// has Activity lifecycle instead of Application
// but talks to Application scope

@Component(modules = HomeActivityModule.class,
        dependencies = GithubApplicationComponent.class ) // Tell Dagger to use GithubApplicationComponent as a source for our modules
// (Hey Dagger, if you are looking for some specific dependencies, look into this component as a source for them)
@HomeActivityScope
public interface HomeActivityComponent {

    AdapterRepos getAdapterRepos();

    GithubService getGithubService();

    // if return type is void, Dagger assumes that he has to inject something somewhere
    // so he will look into method parameters and try to inject there (here - HomeActivity) to @Inject
    void injectHomeActivity(HomeActivity homeActivity);

}
