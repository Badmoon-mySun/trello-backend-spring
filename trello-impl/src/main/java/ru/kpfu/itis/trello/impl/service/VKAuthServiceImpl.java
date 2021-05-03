package ru.kpfu.itis.trello.impl.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.trello.api.dto.UserDto;
import ru.kpfu.itis.trello.api.dto.VKAuthToken;
import ru.kpfu.itis.trello.api.dto.VKUserDto;
import ru.kpfu.itis.trello.api.exception.AuthorizationException;
import ru.kpfu.itis.trello.api.service.VKAuthService;
import ru.kpfu.itis.trello.impl.entity.User;
import ru.kpfu.itis.trello.impl.jpa.repository.UserRepository;
import ru.kpfu.itis.trello.impl.utils.VKAuthUtils;

import java.util.Optional;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
@Service
public class VKAuthServiceImpl implements VKAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private VKAuthUtils vkUtils;

    @Override
    public UserDto auth(String code) {
        VKAuthToken authToken = vkUtils.getAuthToken(code);

        if (authToken.getEmail() != null) {
            Optional<User> optionalUser = userRepository.findByEmail(authToken.getEmail());

            if (optionalUser.isPresent()) {
                return modelMapper.map(optionalUser.get(), UserDto.class);
            }

            VKUserDto vkUserDto = vkUtils.getUser(authToken);
            User user = User.builder()
                    .fullName(vkUserDto.getFirstName() + " " + vkUserDto.getLastName())
                    .username(authToken.getUserId().toString())
                    .email(authToken.getEmail())
                    .role(User.Role.USER)
                    .build();

            userRepository.save(user);

            return modelMapper.map(user, UserDto.class);
        }

        throw new AuthorizationException("Vk authorization failed, user is not found");
    }

}
