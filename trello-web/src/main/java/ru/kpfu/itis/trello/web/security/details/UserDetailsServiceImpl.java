package ru.kpfu.itis.trello.web.security.details;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.trello.impl.entity.User;
import ru.kpfu.itis.trello.impl.jpa.repository.UserRepository;
import ru.kpfu.itis.trello.web.security.jwt.JwtUtils;

import java.util.Optional;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
@Component("jwtUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public UserDetails loadUserByUsername(String jwtToken) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findById(jwtUtils.getUserIdByToken(jwtToken));

        if (userOptional.isPresent()) {
            return new UserDetailsImpl(userOptional.get());
        }

        throw new UsernameNotFoundException("User not found");
    }
}

