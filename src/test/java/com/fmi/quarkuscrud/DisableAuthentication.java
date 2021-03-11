package com.fmi.quarkuscrud;

import io.quarkus.security.spi.runtime.AuthorizationController;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.interceptor.Interceptor;

@Alternative
@Priority(Interceptor.Priority.LIBRARY_AFTER)
@ApplicationScoped
public class DisableAuthentication extends AuthorizationController {

    @Override
    public boolean isAuthorizationEnabled() {
        return false;
    }
}