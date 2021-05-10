package ru.kpfu.itis.trello.web.security.jwt;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthentication tokenAuthentication = (JwtAuthentication) authentication;

        try {
            jwtUtils.validateToken(tokenAuthentication.getName());
            UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());
            tokenAuthentication.setAuthenticated(true);
            tokenAuthentication.setUserDetails(userDetails);
        } catch (JWTDecodeException | TokenExpiredException e) {
            throw new AuthenticationException("Token authentication failed \n" + e.getMessage()) {};
        }

        return tokenAuthentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthentication.class.equals(authentication);
    }
}

