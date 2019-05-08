package com.oauth.server.repository;

import com.oauth.server.data.OAuthPartner;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

/**
 * A repository to access OAuth partners.
 */
@Component
public interface OAuthPartnerRepository extends JpaRepository<OAuthPartner, String> {
    /**
     * Search {@code OAuthPartner} by partner Id.
     * @param partnerId partner id.
     * @return
     */
    OAuthPartner findByPartnerId(final String partnerId);

    /**
     * Search {@code OAuthPartner} by client id.
     * @param clientId client id.
     * @return
     */
    OAuthPartner findByClientId(final String clientId);

    /**
     * Get all {@link OAuthPartner} stored.
     * @return
     */
    List<OAuthPartner> findAll();
}

