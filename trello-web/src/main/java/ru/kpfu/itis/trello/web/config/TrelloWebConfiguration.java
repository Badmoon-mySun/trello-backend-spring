package ru.kpfu.itis.trello.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.kpfu.itis.trello.impl.config.TrelloImplConfiguration;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
@Configuration
@Import(TrelloImplConfiguration.class)
public class TrelloWebConfiguration implements WebMvcConfigurer {

//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new CorsWebMvcConfigurer();
//    }
}
