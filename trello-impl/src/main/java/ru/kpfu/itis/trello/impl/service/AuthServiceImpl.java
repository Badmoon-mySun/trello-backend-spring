package ru.kpfu.itis.trello.impl.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.trello.api.dto.EmailAndPasswordDto;
import ru.kpfu.itis.trello.api.dto.UserDto;
import ru.kpfu.itis.trello.api.dto.UserRegistrationDto;
import ru.kpfu.itis.trello.api.exception.AuthorizationException;
import ru.kpfu.itis.trello.api.service.AuthService;
import ru.kpfu.itis.trello.impl.entity.User;
import ru.kpfu.itis.trello.impl.jpa.repository.UserRepository;

import java.util.Optional;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto signIn(EmailAndPasswordDto emailAndPasswordDto) {
        Optional<User> optionalUser = userRepository.findByEmail(emailAndPasswordDto.getEmail());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (passwordEncoder.matches(emailAndPasswordDto.getPassword(), user.getHashPassword())) {
                return modelMapper.map(user, UserDto.class);
            }
        }

        throw new AuthorizationException("Email or password do not right");
    }

    public UserDto signUp(UserRegistrationDto userRegistrationDto) {
        Optional<User> optionalUser = userRepository.findByEmail(userRegistrationDto.getEmail());

        if (optionalUser.isPresent()) {
            throw new AuthorizationException("A user with this email already exists");
        }

        User user = modelMapper.map(userRegistrationDto, User.class);
        user.setHashPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        user = userRepository.save(user);

        return modelMapper.map(user, UserDto.class);
    }
}
