package uz.pdp.transactionreports.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;
import uz.pdp.transactionreports.service.TelegramUserService;
import uz.pdp.transactionreports.service.telegramService.TelegramService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/telegram")
public class TelegramController {

    private final TelegramService telegramService;
    private final TelegramUserService telegramUserService;

    @PostMapping
    public void getUpdates(@RequestBody Update update) {

        if (update.hasMessage()) {

            if (update.getMessage().getText() != null
                    && telegramService.isOverrideCommand(update.getMessage()))
                telegramService.handleMessage(update.getMessage());
            else
                telegramService.handleInput(update.getMessage());
        } else if (update.hasCallbackQuery()) {
            telegramService.handleCallbackQuery(update.getCallbackQuery());
        }
    }
}
