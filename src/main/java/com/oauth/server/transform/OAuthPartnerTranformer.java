package com.oauth.server.transform;

import com.oauth.server.data.OAuthPartner;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

public class OAuthPartnerTranformer implements DataTransformer <OAuth2ProtectedResourceDetails, OAuthPartner> {

    @Override
    public OAuth2ProtectedResourceDetails transform(OAuthPartner oauthPartner) {
        AuthorizationCodeResourceDetails resourceDetails = new AuthorizationCodeResourceDetails();

        resourceDetails.setId(oauthPartner.getPartnerId());
        resourceDetails.setClientId(oauthPartner.getClientId());
        resourceDetails.setClientSecret(oauthPartner.getClientSecret());

        resourceDetails.setAccessTokenUri(oauthPartner.getAccessTokenUri());
        resourceDetails.setUserAuthorizationUri(oauthPartner.getUserAuthorizationUri());
        resourceDetails.setScope(oauthPartner.getScope());

        resourceDetails.setUseCurrentUri(true);
        resourceDetails.setClientAuthenticationScheme(AuthenticationScheme.header);
        resourceDetails.setAuthenticationScheme(AuthenticationScheme.header);
        resourceDetails.setTokenName(OAuth2AccessToken.ACCESS_TOKEN);

        return resourceDetails;
    }
}
