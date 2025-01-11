package uz.pdp.transactionreports.service.telegramService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import uz.pdp.transactionreports.dto.UserCreateReadDto;
import uz.pdp.transactionreports.entity.TelegramUser;
import uz.pdp.transactionreports.service.TelegramUserService;
import uz.pdp.transactionreports.service.UserService;
import uz.pdp.transactionreports.service.impl.ExportXLSXFile;
import uz.pdp.transactionreports.utils.enums.Callback;
import uz.pdp.transactionreports.utils.enums.UserState;

@Service
@RequiredArgsConstructor
public class TelegramService {
    private final BotService botService;
    private final UserService userService;
    private final ExportXLSXFile exportXLSXFile;
    private final TelegramUserService telegramUserService;
    private final SendMessageService sendMessageService;
    private final PasswordEncoder passwordEncoder;

    private static UserCreateReadDto userDto = new UserCreateReadDto();

    public void handleMessage(Message message) {
        TelegramUser telegramUser = telegramUserService.getByChatId(message.getChatId());

        if (telegramUser == null) {
            telegramUser = telegramUserService.save(TelegramUser.builder().chatId(message.getChatId()).state(UserState.DEFAULT).build());
        }

        String text = message.getText();

        if (text.equals("/start")) {
            telegramUser.setChatId(message.getChatId());
            telegramUser.setState(UserState.ENTER_USERNAME);
            telegramUserService.save(telegramUser);
            botService.send(sendMessageService.login(telegramUser));
        }
    }

    public void handleCallbackQuery(CallbackQuery callbackQuery) {
        String data = callbackQuery.getData();
        TelegramUser byChatId = telegramUserService.getByChatId(callbackQuery.getFrom().getId());
        switch (Callback.of(data)){
            case REPORTS -> {
                botService.send(sendMessageService.reports(byChatId));
            }
            case SETTINGS_AND_LOGIN_RIGHTS ->{
                botService.send(sendMessageService.settings(byChatId));
            }
            case MONTHLY_EXPENSE -> {
            }
            case MONTHLY_INCOME -> {
            }
            case MORE_REPORTS -> {

            }
            case BY_PERIOD ->{

            }
            case BY_EXPENSE_CATEGORY -> {

            }
            case BY_CUSTOMER_PHONE_NUMBER -> {

            }
            case BY_SERVICE_NAME -> {

            }
        }
    }

    public void handleInput(Message message) {
        TelegramUser telegramUser = telegramUserService.getByChatId(message.getChatId());
        if (telegramUser == null) {
            telegramUser = telegramUserService.save(TelegramUser.builder().chatId(message.getChatId()).state(UserState.DEFAULT).build());
        }
        switch (telegramUser.getState()) {
            case ENTER_USERNAME -> {
                 userDto = userService.findByUsername(message.getText());
                if (userDto.getUsername() != null) {
                    telegramUser.setState(UserState.ENTER_PASSWORD);
                    telegramUserService.save(telegramUser);
                    botService.send(sendMessageService.enterPassword(telegramUser));
                } else botService.send(sendMessageService.usernameNotFound(telegramUser));
            }
            case ENTER_PASSWORD -> {
                if (userDto.getUsername() != null) {
                    String password = message.getText();
                    if (passwordEncoder.matches(password, userDto.getPassword())) {
                        botService.send(sendMessageService.welcome(telegramUser));
                        telegramUser.setState(UserState.CHOOSE_OPTION);

                    }
                    else {
                        telegramUser.setState(UserState.ENTER_PASSWORD);
                        botService.send(sendMessageService.deleteMessage(message.getChatId(), message.getMessageId()));
                        botService.send(sendMessageService.wrongPassword(telegramUser));
                    }
                }
            }
        }
    }


    public boolean isOverrideCommand(Message message) {
        return message.getText().equals("/start");
    }
}
