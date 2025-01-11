package uz.pdp.transactionreports.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.transactionreports.entity.TelegramUser;

import java.util.Optional;


@Repository
public interface TelegramUserRepository extends JpaRepository<TelegramUser,Long> {

    Optional<TelegramUser> findByChatId(Long chatId);

}
