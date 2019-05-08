package com.oauth.server.data;

import com.google.common.collect.ImmutableList;
import java.io.Serializable;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.List;

/**
 * An persistence entity class for an OAuthPartner.
 *
 * @author lucuncai
 */
@Entity
@Table(name = "partner_resources")
@Data
public class OAuthPartner implements Serializable {

    @Id
    @NotEmpty
    @Column(name = "partner_id")
    private String partnerId;

    @NotEmpty
    @Column(name = "client_id")
    private String clientId;

    @NotEmpty
    @Column(name = "client_secret")
    private String clientSecret;

    @NotEmpty
    @Column(name = "access_token_uri")
    private String accessTokenUri;

    @Column(name = "user_authorization_uri")
    private String userAuthorizationUri;

    @NotEmpty
    @Column(name = "grant_type")
    private String grantType = "authorization_code";

    @NotEmpty
    @Column(name = "scope")
    private String scope;

    public List<String> getScope() {
        if (StringUtils.isEmpty(scope)) {
            return ImmutableList.of();
        } else {
            return Arrays.asList(scope.split(","));
        }
    }
}
