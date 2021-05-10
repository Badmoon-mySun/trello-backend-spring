package ru.kpfu.itis.trello.impl.config;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
@Configuration
@ComponentScan(basePackages = {
        "ru.kpfu.itis.trello.impl.service",
        "ru.kpfu.itis.trello.impl.utils",
        "ru.kpfu.itis.trello.impl.aspect"
})
@EntityScan(basePackages = "ru.kpfu.itis.trello.impl.entity")
@EnableJpaRepositories(basePackages = "ru.kpfu.itis.trello.impl.jpa.repository")
@EnableAspectJAutoProxy
public class TrelloImplConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
