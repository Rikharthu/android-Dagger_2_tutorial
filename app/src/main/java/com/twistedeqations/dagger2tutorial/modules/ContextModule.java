package com.twistedeqations.dagger2tutorial.modules;

import android.content.Context;

import com.twistedeqations.dagger2tutorial.annotations.ApplicationContextQualifier;
import com.twistedeqations.dagger2tutorial.annotations.GithubApplicationScope;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {

    private final Context context;

    public ContextModule(Context context){
        this.context=context;
    }

    @Provides
    @GithubApplicationScope
    /*@Named("application_context") */// add a qualifier
    @ApplicationContextQualifier
    public Context context(){
        return context;
    }
}
