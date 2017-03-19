package com.twistedeqations.dagger2tutorial.annotations;

import javax.inject.Qualifier;

// Our custom qualifier annotation
// Replacement for @Named("application_context")
@Qualifier
public @interface ApplicationContextQualifier {
}
