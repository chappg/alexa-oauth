package com.oauth.server.controller;

import com.oauth.server.authentication.UserIDAuthenticationToken;
import com.oauth.server.data.OAuthPartner;
import com.oauth.server.repository.OAuthPartnerRepository;
import com.oauth.server.repository.OAuthPartnerTokenRepository;
import com.oauth.server.transform.OAuthPartnerTranformer;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Rest Controller for partner token endpoint.
 *
 * <p>
 *    This endpoint is called by internal service to retrieve access tokens received from partner OAuth providers (LWA).
 * <p>
 *
 * @author lucuncai
 */
@RestController
public class PartnerTokenEndpoint {

    @Autowired
    private OAuthPartnerTokenRepository partnerTokenRepository;

    @Autowired
    private OAuthPartnerRepository partnerRepository;

    /**
     * Endpoint to retrieve a client token from ClientTokenService.
     * @param parameters
     * @return
     */
    @RequestMapping(value = "/api/partner/token")
    public OAuth2AccessToken getPartnerToken(final @RequestParam Map<String, String> parameters) {
        final String userID = parameters.get("user_id");
        final String partnerId = parameters.get("partner_id");

        OAuthPartner partner = partnerRepository.findByPartnerId(partnerId);

        if (partner == null) {
            throw new InvalidClientException("Invalid partner id: " + partnerId);
        }

        OAuth2ProtectedResourceDetails resourceDetails = new OAuthPartnerTranformer().transform(partner);

        OAuth2AccessToken accessToken = partnerTokenRepository.getAccessToken(resourceDetails,
            new UserIDAuthenticationToken(userID));

        if (accessToken == null) {
            throw new OAuth2Exception("No token found for user: " + userID);
        } else if (accessToken.getExpiresIn() <= NumberUtils.INTEGER_ZERO) {
            //Token expired, refresh the token.
            accessToken = refreshClientToken(accessToken, resourceDetails);
        }

        partnerTokenRepository.saveAccessToken(resourceDetails, new UserIDAuthenticationToken(userID), accessToken);

        return accessToken;
    }

    /**
     * Refresh a client access token.
     *
     * @param accessToken
     * @param resourceDetails
     * @return
     */
    private OAuth2AccessToken refreshClientToken(final OAuth2AccessToken accessToken,
                                                 final OAuth2ProtectedResourceDetails resourceDetails) {
        final AccessTokenRequest AccessTokenRequest = new DefaultAccessTokenRequest();

        final AuthorizationCodeAccessTokenProvider tokenProvider = new AuthorizationCodeAccessTokenProvider();
        tokenProvider.setStateMandatory(false);

        return tokenProvider.refreshAccessToken(resourceDetails,
            accessToken.getRefreshToken(), AccessTokenRequest);
    }

}