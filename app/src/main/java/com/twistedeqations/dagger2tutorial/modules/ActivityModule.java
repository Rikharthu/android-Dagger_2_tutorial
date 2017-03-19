package com.twistedeqations.dagger2tutorial.modules;

import android.app.Activity;
import android.content.Context;

import com.twistedeqations.dagger2tutorial.annotations.GithubApplicationScope;
import com.twistedeqations.dagger2tutorial.annotations.ApplicationContextQualifier;
import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 Module made for qualifier usage demonstration
 <p><i>(how to distinct ActivityModule and ContextModule in the dependencies)</i>
 @see ApplicationContextQualifier
 @see NetworkModule
 @see PicassoModule
 */

@Module
public class ActivityModule {

    private final Activity activity;

    public ActivityModule(Activity activity){
        this.activity=activity;
    }

    @Provides
    @GithubApplicationScope
    @Named("activity_context")
    public Context context(){
        return activity;
    }
}
