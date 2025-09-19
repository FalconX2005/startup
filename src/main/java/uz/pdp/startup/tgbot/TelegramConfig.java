package uz.pdp.startup.tgbot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class TelegramConfig {

    private static final Logger log = LoggerFactory.getLogger(TelegramConfig.class);

    @Bean
    public TelegramBotsApi telegramBotsApi(TelegramBotService botService) {
        try {
            TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(botService);
            return api;
        } catch (TelegramApiException e) {
            log.error("Botni ro‘yxatdan o‘tkazishda xatolik: ", e);
            throw new RuntimeException(e);
        }
    }
}
