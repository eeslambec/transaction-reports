package uz.pdp.transactionreports.service.telegramService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import uz.pdp.transactionreports.dto.TransactionExpenseDto;
import uz.pdp.transactionreports.dto.TransactionIncomeDto;
import uz.pdp.transactionreports.dto.UserCreateReadDto;
import uz.pdp.transactionreports.dto.UserUpdateDto;
import uz.pdp.transactionreports.entity.TelegramUser;
import uz.pdp.transactionreports.entity.User;
import uz.pdp.transactionreports.service.TelegramUserService;
import uz.pdp.transactionreports.service.TransactionService;
import uz.pdp.transactionreports.service.UserService;
import uz.pdp.transactionreports.service.impl.ExportXLSXFile;
import uz.pdp.transactionreports.utils.enums.Callback;
import uz.pdp.transactionreports.utils.enums.ExpenseCategory;
import uz.pdp.transactionreports.utils.enums.UserState;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class TelegramService {
    private final BotService botService;
    private final UserService userService;
    private final ExportXLSXFile exportXLSXFile;
    private final TelegramUserService telegramUserService;
    private final SendMessageService sendMessageService;
    private final PasswordEncoder passwordEncoder;

    private static User authenticatedUser = new User();
    private final TransactionService transactionService;

    Path UPLOAD_DIR = Path.of(System.getProperty("user.home"), "transactions/reports.xlsx");



    public void handleMessage(Message message) {
        TelegramUser telegramUser = telegramUserService.getByChatId(message.getChatId());

        if (telegramUser == null) {
            telegramUser = telegramUserService.save(TelegramUser.builder().chatId(message.getChatId()).state(UserState.DEFAULT).build());
        }

        String text = message.getText();

        if (text.equals("/start")) {
            if (telegramUser.getState() == UserState.AUTHENTICATED) {
                botService.send(sendMessageService.welcome(telegramUser));
            }
            else {
                telegramUser.setChatId(message.getChatId());
                telegramUser.setState(UserState.ENTER_USERNAME);
                telegramUserService.save(telegramUser);
                botService.send(sendMessageService.login(telegramUser));
            }
        }
    }

    public void handleCallbackQuery(CallbackQuery callbackQuery) {
        String data = callbackQuery.getData();
        TelegramUser telegramUser = telegramUserService.getByChatId(callbackQuery.getFrom().getId());
        switch (Callback.of(data)){
            case REPORTS -> {
                botService.send(sendMessageService.reports(telegramUser, callbackQuery.getMessage().getMessageId()));
            }
            case SETTINGS_AND_LOGIN_RIGHTS ->{
                botService.send(sendMessageService.settings(telegramUser, callbackQuery.getMessage().getMessageId() ));
            }
            case CHANGE_PROFILE_DETAILS -> {
                botService.send(sendMessageService.chooseChanging(telegramUser, callbackQuery.getMessage().getMessageId()));
            }
            case CHANGE_PASSWORD -> {
                botService.send(sendMessageService.enterNewPassword(telegramUser, callbackQuery.getMessage().getMessageId()));
                telegramUser.setState(UserState.ENTER_NEW_USERNAME);
                telegramUserService.save(telegramUser);
            }
            case CHANGE_NAME -> {
                botService.send(sendMessageService.enterNewName(telegramUser, callbackQuery.getMessage().getMessageId()));
                telegramUser.setState(UserState.ENTER_NEW_NAME);
                telegramUserService.save(telegramUser);
            }
            case CHANGE_USERNAME -> {
                botService.send(sendMessageService.enterNewUsername(telegramUser, callbackQuery.getMessage().getMessageId()));
                telegramUser.setState(UserState.ENTER_NEW_USERNAME);
                telegramUserService.save(telegramUser);

            }
            case MONTHLY_EXPENSE -> {
                try {
                    List<TransactionExpenseDto> allExpenses = transactionService.getAllExpenses();
                    exportXLSXFile.exportAllExpensesToXLSXFile(allExpenses);
                    botService.sendFile(callbackQuery.getFrom().getId(), UPLOAD_DIR.toString());
                } catch (IOException e) {
                    botService.send(sendMessageService.error(telegramUser));
                }
            }
            case MONTHLY_INCOME -> {
                try {
                    List<TransactionIncomeDto> allIncomes = transactionService.getAllIncomes();
                    exportXLSXFile.exportAllIncomesToXLSXFile(allIncomes);
                    botService.sendFile(callbackQuery.getFrom().getId(), UPLOAD_DIR.toString());
                } catch (IOException e) {
                    botService.send(sendMessageService.error(telegramUser));
                }
            }
            case MORE_REPORTS -> {
                botService.send(sendMessageService.chooseReportsOption(telegramUser, callbackQuery.getMessage().getMessageId()));
            }
            case BY_PERIOD ->{
                botService.send(sendMessageService.enterPeriod(telegramUser, callbackQuery.getMessage().getMessageId()));
                telegramUser.setState(UserState.ENTER_PERIOD);
                telegramUserService.save(telegramUser);
            }
            case BY_EXPENSE_CATEGORY -> {
                botService.send(sendMessageService.enterExpenseCategory(telegramUser, callbackQuery.getMessage().getMessageId()));
                telegramUser.setState(UserState.ENTER_EXPENSE_CATEGORY);
                telegramUserService.save(telegramUser);
            }
            case BY_CUSTOMER_PHONE_NUMBER -> {
                botService.send(sendMessageService.enterCustomerPhoneNumber(telegramUser, callbackQuery.getMessage().getMessageId()));
                telegramUser.setState(UserState.ENTER_CUSTOMER_PHONE_NUMBER);
                telegramUserService.save(telegramUser);
            }
            case BY_SERVICE_NAME -> {
                botService.send(sendMessageService.enterServiceName(telegramUser, callbackQuery.getMessage().getMessageId()));
                telegramUser.setState(UserState.ENTER_SERVICE_NAME);
                telegramUserService.save(telegramUser);
            }
        }
    }

    public void handleInput(Message message) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        TelegramUser telegramUser = telegramUserService.getByChatId(message.getChatId());
        if (telegramUser == null) {
            telegramUser = telegramUserService.save(TelegramUser.builder().chatId(message.getChatId()).state(UserState.DEFAULT).build());
        }
        switch (telegramUser.getState()) {
            case ENTER_USERNAME -> {
                 authenticatedUser = userService.getByUsernameWithId(message.getText());
                if (authenticatedUser != null) {
                    telegramUser.setState(UserState.ENTER_PASSWORD);
                    telegramUserService.save(telegramUser);
                    botService.send(sendMessageService.enterPassword(telegramUser));
                } else botService.send(sendMessageService.wrongDataEntered(telegramUser));
            }
            case ENTER_PASSWORD -> {
                if (authenticatedUser.getUsername() != null) {
                    String password = message.getText();
                    if (passwordEncoder.matches(password, authenticatedUser.getPassword())) {
                        botService.send(sendMessageService.welcome(telegramUser));
                        botService.send(sendMessageService.deleteMessage(message.getChatId(),message.getMessageId()));
                        telegramUser.setState(UserState.AUTHENTICATED);
                    }
                    else {
                        telegramUser.setState(UserState.ENTER_PASSWORD);
                        botService.send(sendMessageService.deleteMessage(message.getChatId(), message.getMessageId()));
                        botService.send(sendMessageService.wrongDataEntered(telegramUser));
                    }
                }
            }
            case ENTER_PERIOD -> {
                try {
                    String periods = message.getText();
                    String from = periods.substring(0, periods.indexOf(" "));
                    String to = periods.substring(periods.indexOf(" ") + 1);
                    LocalDate fromDateParse = LocalDate.parse(from, formatter);
                    LocalDate toDateParse = LocalDate.parse(to, formatter);
                    botService.sendFile(telegramUser.getChatId(), UPLOAD_DIR.toString());
                    exportXLSXFile.exportAllCompletedToXLSXFile(transactionService.getAllByPeriod(fromDateParse, toDateParse));
                }catch (Exception e){
                    botService.send(sendMessageService.wrongDataEntered(telegramUser));
                }
            }
            case ENTER_EXPENSE_CATEGORY -> {
                try{
                    ExpenseCategory expenseCategory = ExpenseCategory.valueOf(message.getText().toUpperCase());
                    List<TransactionExpenseDto> allByExpenseCategory
                            = transactionService.getAllByExpenseCategory(expenseCategory);
                    exportXLSXFile.exportAllExpensesToXLSXFile(allByExpenseCategory);
                    botService.sendFile(telegramUser.getChatId(), UPLOAD_DIR.toString());
                }catch (Exception e){
                    botService.send(sendMessageService.wrongDataEntered(telegramUser));
                }
            }
            case ENTER_SERVICE_NAME -> {
                List<TransactionIncomeDto> allByAffairName
                        = transactionService.getAllByAffairName(message.getText());
                if (allByAffairName.isEmpty())
                    botService.send(sendMessageService.dataNotFound(telegramUser));
                try {
                    exportXLSXFile.exportAllIncomesToXLSXFile(allByAffairName);
                    botService.sendFile(telegramUser.getChatId(), UPLOAD_DIR.toString());
                } catch (IOException e) {
                    botService.send(sendMessageService.error(telegramUser));
                }
            }
            case ENTER_CUSTOMER_PHONE_NUMBER -> {
                if (message.getText().matches("\\+998\\d{9}\n"))
                    botService.send(sendMessageService.wrongDataEntered(telegramUser));
                List<TransactionIncomeDto> allByCustomerPhoneNumber
                        = transactionService.getAllByCustomerPhoneNumber(message.getText());
                if (allByCustomerPhoneNumber.isEmpty())
                    botService.send(sendMessageService.dataNotFound(telegramUser));
                try {
                    exportXLSXFile.exportAllIncomesToXLSXFile(allByCustomerPhoneNumber);
                    botService.sendFile(telegramUser.getChatId(), UPLOAD_DIR.toString());
                } catch (IOException e) {
                    botService.send(sendMessageService.error(telegramUser));
                }
            }
            case ENTER_NEW_USERNAME -> {
                UserCreateReadDto byUsername = userService.findByUsername(message.getText());
                if (byUsername.getUsername() != null)
                    botService.send(sendMessageService.usernameAlreadyExists(telegramUser));
                if (message.getText().matches("^[a-zA-Z0-9_]{3,20}$")) {
                    userService.update(new UserUpdateDto(User.builder()
                            .id(authenticatedUser.getId())
                            .username(message.getText())
                            .build()));
                    botService.send(sendMessageService.profileSuccessfullyUpdated(telegramUser));
                    telegramUser.setState(UserState.AUTHENTICATED);
                    telegramUserService.save(telegramUser);
                }else botService.send(sendMessageService.wrongDataEntered(telegramUser));
            }
            case ENTER_NEW_NAME -> {
                userService.update(new UserUpdateDto(User.builder()
                        .id(authenticatedUser.getId())
                        .name(message.getText())
                        .build()));
                botService.send(sendMessageService.profileSuccessfullyUpdated(telegramUser));
                telegramUser.setState(UserState.AUTHENTICATED);
                telegramUserService.save(telegramUser);
            }
            case ENTER_NEW_PASSWORD -> {
                if (message.getText().matches("^[a-zA-Z0-9_@]{8,}")) {
                    userService.update(new UserUpdateDto(User.builder()
                            .id(authenticatedUser.getId())
                            .password(message.getText())
                            .build()));
                }
                botService.send(sendMessageService.wrongDataEntered(telegramUser));
            }
        }
    }


    public boolean isOverrideCommand(Message message) {
        return message.getText().equals("/start");
    }
}
