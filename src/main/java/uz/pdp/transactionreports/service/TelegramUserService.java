package uz.pdp.transactionreports.service;

import org.springframework.stereotype.Service;
import uz.pdp.transactionreports.entity.TelegramUser;
import uz.pdp.transactionreports.utils.enums.UserState;


@Service
public interface TelegramUserService {

    TelegramUser save(TelegramUser telegramUser);

    TelegramUser getByChatId(Long chatId);

    UserState getState(Long chatId);

    void setState(Long chatId,UserState userState);
}
