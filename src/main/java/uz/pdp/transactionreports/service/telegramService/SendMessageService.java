package uz.pdp.transactionreports.service.telegramService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
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

    public SendMessage enterPassword(TelegramUser telegramUser) {
        Long chatId = telegramUser.getChatId();
        return SendMessage.builder()
                .chatId(chatId)
                .text("Enter password")
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

    public EditMessageText reports(TelegramUser telegramUser, Integer messageId) {
        Long chatId = telegramUser.getChatId();
        return EditMessageText.builder()
                .chatId(chatId)
                .messageId(messageId)
                .text("Choose option")
                .replyMarkup(KeyboardUtils.inlineMarkup(
                        KeyboardUtils.inlineButton("Monthly income", Callback.MONTHLY_INCOME.getCallback()),
                        KeyboardUtils.inlineButton("Monthly expense", Callback.MONTHLY_EXPENSE.getCallback()),
                        KeyboardUtils.inlineButton("More reports", Callback.MORE_REPORTS.getCallback())
                ))
                .build();
    }

    public EditMessageText chooseReportsOption(TelegramUser telegramUser, Integer messageId) {
        Long chatId = telegramUser.getChatId();
        return EditMessageText.builder()
                .chatId(chatId)
                .messageId(messageId)
                .text("Choose option")
                .replyMarkup(KeyboardUtils.inlineMarkup(
                        KeyboardUtils.inlineButton("Period", Callback.BY_PERIOD.getCallback()),
                        KeyboardUtils.inlineButton("Expense category", Callback.BY_EXPENSE_CATEGORY.getCallback()),
                        KeyboardUtils.inlineButton("Customer phone number", Callback.BY_CUSTOMER_PHONE_NUMBER.getCallback()),
                        KeyboardUtils.inlineButton("Service name", Callback.BY_SERVICE_NAME.getCallback())
                ))
                .build();
    }
    public EditMessageText settings(TelegramUser telegramUser, Integer messageId) {
        Long chatId = telegramUser.getChatId();
        return EditMessageText.builder()
                .chatId(chatId)
                .messageId(messageId)
                .text("Choose option")
                .replyMarkup(KeyboardUtils.inlineMarkup(
                        KeyboardUtils.inlineButton("Change profile details", Callback.CHANGE_PROFILE_DETAILS.getCallback()),
                        KeyboardUtils.inlineButton("Permissions", Callback.PERMISSIONS.getCallback())
                ))
                .build();
    }
    public SendMessage error(TelegramUser telegramUser) {
        Long chatId = telegramUser.getChatId();
        return SendMessage.builder()
                .chatId(chatId)
                .text("Something went wrong")
                .build();
    }

    public DeleteMessage deleteMessage(Long chatId, Integer messageId) {
        return DeleteMessage.builder()
                .chatId(chatId)
                .messageId(messageId)
                .build();
    }
    public EditMessageText enterPeriod(TelegramUser telegramUser, Integer messageId) {
        Long chatId = telegramUser.getChatId();
        return EditMessageText.builder()
                .chatId(chatId)
                .messageId(messageId)
                .text("Enter period")
                .build();
    }
    public EditMessageText enterExpenseCategory(TelegramUser telegramUser, Integer messageId) {
        Long chatId = telegramUser.getChatId();
        return EditMessageText.builder()
                .chatId(chatId)
                .messageId(messageId)
                .text("Enter expense category. It should be the same as one of them: \n```" +
                        "SALARY,\n" +
                        "    DIVIDEND,\n" +
                        "    ADVERTISEMENT,\n" +
                        "    CUSTOMER_ADVERTISEMENT,\n" +
                        "    DEVELOPMENT_FUND,\n" +
                        "    FISCAL_EXPENDITURES,\n" +
                        "    HOUSEHOLD_EXPENSES,\n" +
                        "    SERVICES,\n" +
                        "    TRADE_UNION,\n" +
                        "    COMMISSIONS,\n" +
                        "    OTHER" +
                        "```")
                .build();
    }
    public SendMessage wrongDataEntered(TelegramUser telegramUser) {
        Long chatId = telegramUser.getChatId();
        return SendMessage.builder()
                .chatId(chatId)
                .text("Wrong data entered \nTry again")
                .build();
    }
    public EditMessageText enterCustomerPhoneNumber(TelegramUser telegramUser, Integer messageId) {
        Long chatId = telegramUser.getChatId();
        return EditMessageText.builder()
                .chatId(chatId)
                .messageId(messageId)
                .text("Enter customer phone number. It should be at the same format as: +998*******")
                .build();
    }
    public EditMessageText enterServiceName(TelegramUser telegramUser, Integer messageId) {
        Long chatId = telegramUser.getChatId();
        return EditMessageText.builder()
                .chatId(chatId)
                .messageId(messageId)
                .text("Enter service name")
                .build();
    }
    public SendMessage dataNotFound(TelegramUser telegramUser) {
        Long chatId = telegramUser.getChatId();
        return SendMessage.builder()
                .chatId(chatId)
                .text("Data not found")
                .build();
    }
    public EditMessageText chooseChanging(TelegramUser telegramUser, Integer messageId) {
        Long chatId = telegramUser.getChatId();
        return EditMessageText.builder()
                .chatId(chatId)
                .messageId(messageId)
                .text("Choose changing")
                .replyMarkup(KeyboardUtils.inlineMarkup(
                        KeyboardUtils.inlineButton("Name", Callback.CHANGE_NAME.getCallback()),
                        KeyboardUtils.inlineButton("Username", Callback.CHANGE_USERNAME.getCallback()),
                        KeyboardUtils.inlineButton("Password", Callback.CHANGE_PASSWORD.getCallback())
                ))
                .build();
    }
    public EditMessageText enterNewName(TelegramUser telegramUser, Integer messageId) {
        Long chatId = telegramUser.getChatId();
        return EditMessageText.builder()
                .chatId(chatId)
                .messageId(messageId)
                .text("Enter name")
                .build();
    }
    public EditMessageText enterNewUsername(TelegramUser telegramUser, Integer messageId) {
        Long chatId = telegramUser.getChatId();
        return EditMessageText.builder()
                .chatId(chatId)
                .messageId(messageId)
                .text("Enter username")
                .build();
    }
    public EditMessageText enterNewPassword(TelegramUser telegramUser, Integer messageId) {
        Long chatId = telegramUser.getChatId();
        return EditMessageText.builder()
                .chatId(chatId)
                .messageId(messageId)
                .text("Enter new password")
                .build();
    }
    public EditMessageText reEnterNewPassword(TelegramUser telegramUser, Integer messageId) {
        Long chatId = telegramUser.getChatId();
        return EditMessageText.builder()
                .chatId(chatId)
                .messageId(messageId)
                .text("Re enter new password")
                .build();
    }
    public SendMessage usernameAlreadyExists(TelegramUser telegramUser) {
        Long chatId = telegramUser.getChatId();
        return SendMessage.builder()
                .chatId(chatId)
                .text("Username already exists")
                .build();
    }
    public SendMessage profileSuccessfullyUpdated(TelegramUser telegramUser) {
        Long chatId = telegramUser.getChatId();
        return SendMessage.builder()
                .chatId(chatId)
                .text("Profile successfully updated")
                .build();
    }
}
