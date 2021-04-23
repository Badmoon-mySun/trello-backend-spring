package ru.kpfu.itis.trello.web.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.kpfu.itis.trello.api.dto.TokenDto;
import ru.kpfu.itis.trello.api.dto.UserDto;
import ru.kpfu.itis.trello.api.exception.AuthorizationException;

import java.util.*;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.accessExpirationInMs}")
    private int accessExpirationInMs;

    @Value("${jwt.refreshExpirationInMs}")
    private int refreshExpirationInMs;

    @Autowired
    private ModelMapper modelMapper;

    public String generateRefreshToken(UserDto userDto) {

        return JWT.create()
                .withSubject(userDto.getId().toString())
                .withClaim("email", userDto.getEmail())
                .withClaim("nickname", userDto.getNickname())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + refreshExpirationInMs))
                .withClaim("isRefresh", "true")
                .sign(Algorithm.HMAC256(secret));
    }

    public String generateAccessToken(UserDto userDto) {

        return JWT.create()
                .withSubject(userDto.getId().toString())
                .withClaim("email", userDto.getEmail())
                .withClaim("nickname", userDto.getNickname())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + accessExpirationInMs))
                .sign(Algorithm.HMAC256(secret));
    }

    public boolean validateToken(String token) throws JWTDecodeException {
        getVerification().verify(token);
        return true;
    }

    public UserDto getUserDtoFromToken(String token) throws AuthorizationException {
        if (!StringUtils.hasLength(token)) {
            throw new AuthorizationException("Invalid token");
        }

        DecodedJWT decodedJWT = getVerification().verify(token);
        UserDto userDto = modelMapper.map(decodedJWT.getClaims(), UserDto.class);
        userDto.setId(Long.valueOf(decodedJWT.getSubject()));

        return userDto;
    }

    public UserDto getUserDtoFromRefreshToken(String token) {
        UserDto userDto = getUserDtoFromToken(token);

        DecodedJWT decoded = getVerification().verify(token);
        Claim isRefresh = decoded.getClaim("isRefresh");

        if (isRefresh.isNull() && !isRefresh.asBoolean()) {
            throw new AuthorizationException("It is not refresh token!");
        }

        return userDto;
    }

    public Long getUserIdByToken(String token) {
        return Long.valueOf(getVerification().verify(token).getSubject());

    }

    public Collection<? extends GrantedAuthority> getRolesFromToken(String token) {
        DecodedJWT decodedJWT = getVerification().verify(token);
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(decodedJWT.getClaim("role").toString());

        return Collections.singleton(authority);
    }

    public TokenDto generateTokens(UserDto userDto) {
        return TokenDto.builder()
                .accessToken(generateAccessToken(userDto))
                .refreshToken(generateRefreshToken(userDto))
                .build();
    }

    private JWTVerifier getVerification() {
        return JWT.require(Algorithm.HMAC256(secret)).build();
    }
}