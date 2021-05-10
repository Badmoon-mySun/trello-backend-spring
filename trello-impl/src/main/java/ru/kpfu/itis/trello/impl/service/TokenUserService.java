package ru.kpfu.itis.trello.impl.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.kpfu.itis.trello.api.dto.UserDto;
import ru.kpfu.itis.trello.api.exception.AuthorizationException;
import ru.kpfu.itis.trello.api.service.TokenService;
import ru.kpfu.itis.trello.impl.entity.TokenUser;
import ru.kpfu.itis.trello.impl.entity.User;
import ru.kpfu.itis.trello.impl.jpa.repository.TokenUserRepository;

import java.util.Optional;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
@Service
public class TokenUserService implements TokenService {

    @Autowired
    private TokenUserRepository tokenUserRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void updateUserToken(String refreshToken, String newRefresh) {
        if (StringUtils.hasLength(refreshToken)) {
            Optional<TokenUser> optionalTokenUser = tokenUserRepository.findByToken(refreshToken);
            System.out.println(1);

            if (optionalTokenUser.isPresent()) {
                TokenUser tokenUser = optionalTokenUser.get();
                System.out.println(2);

                if (tokenUser.getToken().equals(refreshToken)) {
                    tokenUser.setToken(newRefresh);
                    tokenUserRepository.save(tokenUser);

                    System.out.println(3);
                    return;
                }
            }
        }

        throw new AuthorizationException("Invalid refresh token");
    }

    @Override
    public void updateUserToken(String refreshToken, UserDto userDto) {
        Optional<TokenUser> optional = tokenUserRepository.findByUserId(userDto.getId());

        TokenUser tokenUser = optional.orElseGet(() -> TokenUser.builder()
                .user(modelMapper.map(userDto, User.class))
                .build());

        tokenUser.setToken(refreshToken);

        tokenUserRepository.save(tokenUser);
    }

    @Override
    @Transactional
    public void deleteUserToken(UserDto userDto) {
        tokenUserRepository.removeAllByUserId(userDto.getId());
    }
}
