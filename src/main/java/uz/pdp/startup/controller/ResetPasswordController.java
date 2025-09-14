package uz.pdp.startup.controller;//package uz.pdp.startupproject.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import uz.pdp.startupproject.payload.ResetPasswordDTO;
//import uz.pdp.startupproject.tgbot.PasswordResetService;
//import uz.pdp.startupproject.tgbot.TelegramBotService;
//
//@RestController
//@RequestMapping("/auth/")
//@RequiredArgsConstructor
//public class ResetPasswordController {
//
//    private final PasswordResetService passwordResetService;
//    private final TelegramBotService telegramBotService;
//
//
//    @PostMapping("/reset-password")
//    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordDTO request) {
//        Long chatId = passwordResetService.updatePassword(
//                request.getUsername(),
//                request.getCode(),
//                request.getNewPassword()
//        );
//        telegramBotService.sendPasswordChangedMessage(chatId);
//        return ResponseEntity.ok("Parol muvaffaqiyatli o‘zgartirildi");
//    }
//
//}
