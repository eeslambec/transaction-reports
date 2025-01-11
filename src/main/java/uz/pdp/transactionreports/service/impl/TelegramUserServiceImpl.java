package uz.pdp.transactionreports.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.transactionreports.entity.TelegramUser;
import uz.pdp.transactionreports.repository.TelegramUserRepository;
import uz.pdp.transactionreports.service.TelegramUserService;
import uz.pdp.transactionreports.utils.enums.UserState;


@Service
@RequiredArgsConstructor
public class TelegramUserServiceImpl implements TelegramUserService {

    private final TelegramUserRepository telegramUserRepository;

    @Override
    public TelegramUser save(TelegramUser telegramUser) {
        return telegramUserRepository.save(telegramUser);
    }

    @Override
    public TelegramUser getByChatId(Long chatId) {
        return telegramUserRepository.findByChatId(chatId).isPresent() ? telegramUserRepository.findByChatId(chatId).get() : null;
    }

    @Override
    public UserState getState(Long chatId) {
        if (telegramUserRepository.findByChatId(chatId).isEmpty())
            setState(chatId,UserState.DEFAULT);
        return telegramUserRepository.findByChatId(chatId).get().getState();
    }

    @Override
    public void setState(Long chatId, UserState userState) {
        TelegramUser user = telegramUserRepository.findByChatId(chatId).orElseGet(()->
                TelegramUser.builder()
                        .chatId(chatId)
                        .build()
        );
        user.setState(userState);
        telegramUserRepository.save(user);
    }
}
