package ru.kpfu.itis.trello.web.config;

import com.github.lambdaexpression.annotation.EnableRequestBodyParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
@Configuration
@EnableWebMvc
@EnableRequestBodyParam
public class TrelloWebMvcConfigurer implements WebMvcConfigurer {

    @Value("${file.save.path}")
    private String path;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowedOrigins("*");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/public/**").addResourceLocations("file:" + path);
    }
}
