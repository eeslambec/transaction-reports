package uz.pdp.transactionreports.service.telegramService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import uz.pdp.transactionreports.entity.TelegramUser;
import uz.pdp.transactionreports.utils.KeyboardUtils;
import uz.pdp.transactionreports.utils.enums.Callback;

@Service
@RequiredArgsConstructor
public class SendMessageService {

    public SendMessage login(TelegramUser telegramUser) {
        Long chatId = telegramUser.getChatId();
        return SendMessage.builder()
                .chatId(chatId)
                .text("Enter username")
                .build();
    }

    public SendMessage usernameNotFound(TelegramUser telegramUser) {
        Long chatId = telegramUser.getChatId();
        return SendMessage.builder()
                .chatId(chatId)
                .text("Username incorrect! \n Enter username")
                .build();
    }

    public SendMessage enterPassword(TelegramUser telegramUser) {
        Long chatId = telegramUser.getChatId();
        return SendMessage.builder()
                .chatId(chatId)
                .text("Enter password")
                .build();
    }
    public SendMessage wrongPassword(TelegramUser telegramUser) {
        Long chatId = telegramUser.getChatId();
        return SendMessage.builder()
                .chatId(chatId)
                .text("Wrong password \nEnter password")
                .build();
    }

    public SendMessage welcome(TelegramUser telegramUser) {
        Long chatId = telegramUser.getChatId();
        return SendMessage.builder()
                .chatId(chatId)
                .replyMarkup(KeyboardUtils.inlineMarkup(
                        KeyboardUtils.inlineButton("Reports", Callback.REPORTS.getCallback()),
                        KeyboardUtils.inlineButton("Settings and login rights", Callback.SETTINGS_AND_LOGIN_RIGHTS.getCallback())
                ))
                .text("Welcome")
                .build();
    }

    public SendMessage reports(TelegramUser telegramUser) {
        Long chatId = telegramUser.getChatId();
        return SendMessage.builder()
                .chatId(chatId)
                .text("Choose option")
                .replyMarkup(KeyboardUtils.inlineMarkup(
                        KeyboardUtils.inlineButton("Monthly income", Callback.MONTHLY_INCOME.getCallback()),
                        KeyboardUtils.inlineButton("Monthly expense", Callback.MONTHLY_EXPENSE.getCallback()),
                        KeyboardUtils.inlineButton("More reports", Callback.MORE_REPORTS.getCallback())
                ))
                .build();
    }

    public SendMessage chooseReportsOption(TelegramUser telegramUser) {
        Long chatId = telegramUser.getChatId();
        return SendMessage.builder()
                .chatId(chatId)
                .text("Choose option")
                .replyMarkup(KeyboardUtils.inlineMarkup(
                        KeyboardUtils.inlineButton("Period", Callback.BY_PERIOD.getCallback()),
                        KeyboardUtils.inlineButton("Expense category", Callback.BY_EXPENSE_CATEGORY.getCallback()),
                        KeyboardUtils.inlineButton("Customer phone number", Callback.BY_CUSTOMER_PHONE_NUMBER.getCallback()),
                        KeyboardUtils.inlineButton("Service name", Callback.BY_SERVICE_NAME.getCallback())
                ))
                .build();
    }
    public SendMessage settings(TelegramUser telegramUser) {
        Long chatId = telegramUser.getChatId();
        return SendMessage.builder()
                .chatId(chatId)
                .text("Choose option")
                .replyMarkup(KeyboardUtils.inlineMarkup(
                        KeyboardUtils.inlineButton("Change profile details", Callback.CHANGE_PROFILE_DETAILS.getCallback()),
                        KeyboardUtils.inlineButton("Permissions", Callback.PERMISSIONS.getCallback()),
                        KeyboardUtils.inlineButton("Change password", Callback.CHANGE_PASSWORD.getCallback())
                ))
                .build();
    }

    public DeleteMessage deleteMessage(Long chatId, Integer messageId) {
        return DeleteMessage.builder()
                .chatId(chatId)
                .messageId(messageId)
                .build();
    }

}
