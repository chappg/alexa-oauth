package com.oauth.server.authentication;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import org.apache.http.auth.BasicUserPrincipal;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.security.Principal;

/**
 * A simple User-ID based authentication token.
 *
 * @author lucuncai
 */
public class UserIDAuthenticationToken extends AbstractAuthenticationToken {

    @NonNull
    private String userID;

    public UserIDAuthenticationToken(final String userID) {
        super(ImmutableList.of());
        this.userID = userID;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Principal getPrincipal() {
        return new BasicUserPrincipal(userID);
    }
}
