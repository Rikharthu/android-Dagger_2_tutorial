package com.twistedeqations.dagger2tutorial.components;

import com.squareup.picasso.Picasso;
import com.twistedeqations.dagger2tutorial.modules.ActivityModule;
import com.twistedeqations.dagger2tutorial.modules.GithubServiceModule;
import com.twistedeqations.dagger2tutorial.modules.PicassoModule;
import com.twistedeqations.dagger2tutorial.network.GithubService;
import com.twistedeqations.dagger2tutorial.annotations.GithubApplicationScope;

import dagger.Component;

/**
 * Public interface to our dependency graph
 * Dagger will aggro at @Component annotation and generate an instance(subclass) of this interface
 * and will provide required instances for the annotated methods from our Modules (@Module)
 * (Dagger looks for what he needs to provide, searches in provided Modules on how to do it)
 */
@GithubApplicationScope
@Component(modules = {GithubServiceModule.class, PicassoModule.class, ActivityModule.class}) // tell which modules to use in order to generate this instance
public interface GithubApplicationComponent {

    Picasso getPicasso();

    GithubService getGithubService();
}
