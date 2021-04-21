package ru.kpfu.itis.trello.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.kpfu.itis.trello.impl.config.TrelloImplConfiguration;

/**
 * @author Anvar Khasanov
 * student of ITIS KFU
 * group 11-905
 */
@Configuration
@Import(TrelloImplConfiguration.class)
public class TrelloWebConfiguration {
}
