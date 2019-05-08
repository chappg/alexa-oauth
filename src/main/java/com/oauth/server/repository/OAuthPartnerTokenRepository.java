package com.oauth.server.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.token.JdbcClientTokenServices;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * A Repository to manage partner OAuth tokens.
 *
 * @author lucuncai
 */
@Component
public class OAuthPartnerTokenRepository extends JdbcClientTokenServices {
    public OAuthPartnerTokenRepository(@Autowired DataSource dataSource) {
        super(dataSource);
    }

}
