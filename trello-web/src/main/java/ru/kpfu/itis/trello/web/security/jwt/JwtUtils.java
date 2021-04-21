package ru.kpfu.itis.trello.web.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.trello.api.dto.UserDto;

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

    private final JWTVerifier verification = JWT.require(Algorithm.HMAC256(secret)).build();

    public String generateRefreshToken(UserDto userDto) {

        return JWT.create()
                .withSubject(userDto.getId().toString())
                .withClaim("email", userDto.getEmail())
                .withClaim("nickname", userDto.getNickname())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + refreshExpirationInMs))
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

    public boolean validateToken(String token) {
        verification.verify(token);
        return true;
    }

    public UserDto getUserDtoFromToken(String token) {
        DecodedJWT decodedJWT = verification.verify(token);
        UserDto userDto = modelMapper.map(decodedJWT.getClaims(), UserDto.class);
        userDto.setId(Long.valueOf(decodedJWT.getSubject()));

        return userDto;
    }

    public Long getUserIdByToken(String token) {
        return Long.valueOf(verification.verify(token).getSubject());

    }

    public Collection<? extends GrantedAuthority> getRolesFromToken(String token) {
        DecodedJWT decodedJWT = verification.verify(token);
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(decodedJWT.getClaim("role").toString());

        return Collections.singleton(authority);
    }

}