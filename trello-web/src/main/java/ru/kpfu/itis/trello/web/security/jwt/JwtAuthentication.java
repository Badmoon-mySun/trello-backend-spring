package ru.kpfu.itis.trello.web.security.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.kpfu.itis.trello.web.security.details.UserDetailsImpl;

import java.util.Collection;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
public class JwtAuthentication implements Authentication {

    private UserDetailsImpl userDetails;

    private boolean isAuthenticated;

    private final String jwtToken;

    public JwtAuthentication(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = (UserDetailsImpl) userDetails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userDetails.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return userDetails.getPassword();
    }

    @Override
    public Object getDetails() {
        return userDetails;
    }

    @Override
    public Object getPrincipal() {
        if (userDetails != null) {
            return userDetails.getUser();
        }

        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return jwtToken;
    }
}

