package com.twistedeqations.dagger2tutorial.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/* ACHTUNG!!!
    @Singleton IS EXACTLY THE SAME, NOT THE REAL SINGLETON: It is a singleton only for the current
    instance of a Component. NEVER USE IT AS IT IS MISLEADING.
    Every time you call .build(), new Instances are created, whether you have Singleton or not!
    ALWAYS CREATE YOUR OWN SCOPE ANNOTATION
 */

@Scope // ctrl+q on that annotation ~~~
@Retention(RetentionPolicy.CLASS)
public @interface GithubApplicationScope {
    // Could be replaced by Singleton
}
