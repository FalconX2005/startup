package uz.pdp.startup.tgbot;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.pdp.startup.entity.Employee;
import uz.pdp.startup.entity.User;
import uz.pdp.startup.repository.EmployeeRepository;
import uz.pdp.startup.repository.UserRepository;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class TelegramBotService extends TelegramLongPollingBot {

    private static final Logger log = LoggerFactory.getLogger(TelegramBotService.class);

    private final PasswordResetService passwordResetService;
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.bot.username}")
    private String botUsername;

    private final Map<Long, ResetSession> userState = new ConcurrentHashMap<>();

    private enum State {WAITING_FOR_USERNAME, WAITING_FOR_PHONE, DONE}

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            String data = update.getCallbackQuery().getData();
            Long chatId = update.getCallbackQuery().getMessage().getChatId();

            if ("RESET_PASSWORD".equals(data)) {
                sendMessage(chatId, "🔑 Parolni tiklash uchun username kiriting:");
                userState.put(chatId, new ResetSession(State.WAITING_FOR_USERNAME));
            }
            return;
        }

        if (update.hasMessage() && update.getMessage().hasText()) {
            Long chatId = update.getMessage().getChatId();
            String text = update.getMessage().getText();

            if ("/start".equals(text)) {
                InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rows = new ArrayList<>();

                InlineKeyboardButton resetBtn = new InlineKeyboardButton();
                resetBtn.setText("🔑 Parolni tiklash");
                resetBtn.setCallbackData("RESET_PASSWORD");

                InlineKeyboardButton webBtn = new InlineKeyboardButton();
                webBtn.setText("🌐 PayPeak WebApp");
                webBtn.setWebApp(new WebAppInfo("https://debt-crm-kappa.vercel.app/user"));

                rows.add(Collections.singletonList(resetBtn));
                rows.add(Collections.singletonList(webBtn));

                markup.setKeyboard(rows);

                SendMessage msg = new SendMessage(String.valueOf(chatId), "Kerakli bo‘limni tanlang ⬇️");
                msg.setReplyMarkup(markup);
                try {
                    execute(msg);
                } catch (TelegramApiException e) {
                    log.error("Xabar yuborishda xatolik: ", e);
                }
                return;
            }

            ResetSession session = userState.get(chatId);

            if (session != null && session.state == State.WAITING_FOR_USERNAME) {
                Optional<User> userOpt = userRepository.findByUsername(text);
                if (userOpt.isEmpty()) {
                    sendMessage(chatId, "❌ Foydalanuvchi topilmadi.");
                    return;
                }

                session.username = text;
                session.state = State.WAITING_FOR_PHONE;
                sendMessage(chatId, "📱 Endi ro‘yxatdan o‘tgan telefon raqamingizni kiriting (+998 formatda):");
                return;
            }

            if (session != null && session.state == State.WAITING_FOR_PHONE) {
                Optional<User> userOpt = userRepository.findByUsername(session.username);
                if (userOpt.isPresent()) {
                    User user = userOpt.get();
                    Optional<Employee> empOpt = employeeRepository.findByUser(user);
                    if (empOpt.isPresent()) {
                        Employee emp = empOpt.get();
                        if (emp.getPhoneNumber().equals(text)) {
                            String code = passwordResetService.startReset(session.username, chatId);
                            session.state = State.DONE;
                            sendMessage(chatId, "✅ Parolni tiklash kodingiz: *" + code + "*\nIltimos, uni web-saytda kiriting.");
                        } else {
                            sendMessage(chatId, "❌ Telefon raqam mos kelmadi.");
                        }
                    } else {
                        sendMessage(chatId, "❌ Ushbu foydalanuvchiga bog‘langan hodim topilmadi.");
                    }
                }
            }


        }
    }


    public void sendPasswordChangedMessage(Long chatId) {
        sendMessage(chatId, "✅ Parolingiz muvaffaqiyatli o‘zgartirildi.");
    }

    private void sendMessage(Long chatId, String text) {
        SendMessage msg = new SendMessage(String.valueOf(chatId), text);
        msg.setParseMode("Markdown");
        try {
            execute(msg);
        } catch (TelegramApiException e) {
            log.error("Xabar yuborishda xatolik: ", e);
        }
    }


    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    static class ResetSession {
        State state;
        String username;

        ResetSession(State state) {
            this.state = state;
        }
    }
}
