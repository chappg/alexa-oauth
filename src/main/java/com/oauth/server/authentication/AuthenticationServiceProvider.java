package com.oauth.server.authentication;

import com.google.common.collect.ImmutableList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * An customized AuthenticationProvider.
 *
 * @author lucuncai
 */
@Component
public class AuthenticationServiceProvider implements AuthenticationProvider, AuthenticationManager,
    UserDetailsService {

    private static final List<User> mockUsers = ImmutableList.of(
        new User("user", "$2a$10$tNrknh3ZtTQ4IWq.P1KSaOwIar7ToOM1TjQTmuxGIIjYCJvy.55uS",
            ImmutableList.of(new SimpleGrantedAuthority(RoleEnum.ROLE_USER_CUSTOMER.name()))),
        new User("admin", "$2a$10$tNrknh3ZtTQ4IWq.P1KSaOwIar7ToOM1TjQTmuxGIIjYCJvy.55uS",
            ImmutableList.of(new SimpleGrantedAuthority(RoleEnum.ROLE_USER_ADMIN.name()))));

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails user = loadUserByUsername (username);
        if (passwordEncoder.matches(password, user.getPassword())) {
            return new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
        } else {
            throw new BadCredentialsException("Invalid credential for user " + username);
        }
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

        //TODO: Integrate with your authentication system in replace the mock users.
        return mockUsers.stream()
            .filter(u -> u.getUsername().equals(username))
            .findAny()
            .orElseThrow(() -> new UsernameNotFoundException("User " + username + " cannot be found"));
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return authentication.equals(
            UsernamePasswordAuthenticationToken.class);
    }

}
