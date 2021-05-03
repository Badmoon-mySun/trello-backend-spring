package ru.kpfu.itis.trello.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.trello.api.dto.EmailAndPasswordDto;
import ru.kpfu.itis.trello.api.dto.TokenDto;
import ru.kpfu.itis.trello.api.dto.UserDto;
import ru.kpfu.itis.trello.api.dto.UserRegistrationDto;
import ru.kpfu.itis.trello.api.service.AuthService;
import ru.kpfu.itis.trello.api.service.VKAuthService;
import ru.kpfu.itis.trello.impl.service.TokenUserService;
import ru.kpfu.itis.trello.web.security.jwt.JwtUtils;

import javax.validation.Valid;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private TokenUserService tokenUserService;

    @Autowired
    private AuthService authService;

    @Autowired
    private VKAuthService vkAuthService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/signin")
    public TokenDto signIn(@Valid @RequestBody EmailAndPasswordDto emailAndPasswordDto) {
        UserDto userDto = authService.signIn(emailAndPasswordDto);
        return getTokenDto(userDto);
    }

    @PostMapping("/signup")
    public UserDto signup(@Valid @RequestBody UserRegistrationDto userRegistrationDto) {
        return authService.signUp(userRegistrationDto);
    }

    @PostMapping("/refresh")
    public TokenDto refreshToken(@RequestBody TokenDto tokenDto) {
        String oldToken = tokenDto.getRefreshToken();
        UserDto userDto = jwtUtils.getUserDtoFromRefreshToken(oldToken);

        String newToken = jwtUtils.generateRefreshToken(userDto);
        tokenUserService.updateUserToken(oldToken, newToken);

        return TokenDto.builder()
                .accessToken(jwtUtils.generateAccessToken(userDto))
                .refreshToken(newToken)
                .build();
    }

    @GetMapping("/logout")
    public void logout(@RequestBody TokenDto tokenDto) {
        UserDto userDto = jwtUtils.getUserDtoFromRefreshToken(tokenDto.getRefreshToken());

        tokenUserService.deleteUserToken(userDto);
    }

    @GetMapping("/vk")
    public TokenDto socialVk(@RequestParam String code) {
        return getTokenDto(vkAuthService.auth(code));
    }

    private TokenDto getTokenDto(UserDto userDto) {
        TokenDto tokenDto = jwtUtils.generateTokens(userDto);
        tokenUserService.updateUserToken(tokenDto.getRefreshToken(), userDto);

        return tokenDto;
    }

    @GetMapping("/me")
    public UserDto getUserInfo(@RequestHeader("Authorization") String bearerToken) {
        String jwtToken = jwtUtils.getClearToken(bearerToken);

        return jwtUtils.getUserDtoFromToken(jwtToken);
    }
}
