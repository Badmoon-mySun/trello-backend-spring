package ru.kpfu.itis.trello.impl.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.trello.api.dto.UserDto;
import ru.kpfu.itis.trello.api.service.UserService;
import ru.kpfu.itis.trello.impl.entity.User;
import ru.kpfu.itis.trello.impl.jpa.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }

    @Override
    public Optional<UserDto> findById(Long id) {
        return userRepository.findById(id).map(user -> modelMapper.map(user, UserDto.class));
    }

    @Override
    public UserDto save(UserDto userDto) {
        // TODO why user id set null?
        User user = userRepository.save(modelMapper.map(userDto, User.class));
        user.setRole(User.Role.USER);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public Boolean delete(UserDto userDto) {
        userRepository.delete(modelMapper.map(userDto, User.class));
        return true;
    }

    @Override
    public Boolean deleteById(Long id) {
        userRepository.deleteById(id);
        return true;
    }
}
