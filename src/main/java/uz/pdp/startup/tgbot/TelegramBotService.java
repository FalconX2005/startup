package uz.pdp.startup.tgbot;//package uz.pdp.startupproject.tgbot;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.bots.TelegramLongPollingBot;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.objects.Update;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Component
//@RequiredArgsConstructor
//public class TelegramBotService extends TelegramLongPollingBot {
//
//    private final PasswordResetService passwordResetService;
//
//    @Value("${telegram.bot.token}")
//    private String botToken;
//
//    @Value("${telegram.bot.username}")
//    private String botUsername;
//
//    private final Map<Long, ResetSession> userState = new HashMap<>();
//
//    @Override
//    public void onUpdateReceived(Update update) {
//        if (!update.hasMessage() || !update.getMessage().hasText()) return;
//
//        Long chatId = update.getMessage().getChatId();
//        String text = update.getMessage().getText();
//
//        if ("/start".equals(text)) {
//            sendMessage(chatId, "Parolni tiklash uchun username kiriting:");
//            userState.put(chatId, new ResetSession("WAITING_FOR_USERNAME"));
//            return;
//        }
//
//        ResetSession session = userState.get(chatId);
//        if (session != null && "WAITING_FOR_USERNAME".equals(session.state)) {
//            try {
//                String code = passwordResetService.startReset(text, chatId);
//                session.state = "DONE";
//                sendMessage(chatId, "Parolni tiklash kodingiz: *" + code + "*\nIltimos, uni web-saytda kiriting.");
//            } catch (UsernameNotFoundException e) {
//                sendMessage(chatId, "Foydalanuvchi topilmadi.");
//            }
//        }
//    }
//
//    public void sendPasswordChangedMessage(Long chatId) {
//        sendMessage(chatId, "✅ Parolingiz muvaffaqiyatli o‘zgartirildi.");
//    }
//
//    private void sendMessage(Long chatId, String text) {
//        SendMessage msg = new SendMessage(String.valueOf(chatId), text);
//        msg.setParseMode("Markdown");
//        try {
//            execute(msg);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public String getBotUsername() {
//        return botUsername;
//    }
//
//    @Override
//    public String getBotToken() {
//        return botToken;
//    }
//
//    static class ResetSession {
//        String state;
//        ResetSession(String state) {
//            this.state = state;
//        }
//    }
//}
