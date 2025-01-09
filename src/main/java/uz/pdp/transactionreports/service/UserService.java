package uz.pdp.transactionreports.service;

import org.springframework.stereotype.Service;
import uz.pdp.transactionreports.dto.UserCRUDDto;
import uz.pdp.transactionreports.dto.UserUpdateDto;
import uz.pdp.transactionreports.entity.User;

import java.util.List;
import java.util.UUID;

@Service
public interface UserService {
    User create(UserCRUDDto user);
    UserCRUDDto update(UserUpdateDto userUpdateDto);
    void deleteById(UUID id);
    void deleteByUsername(String username);
    List<User> findAll();
    UserCRUDDto getById(UUID id);
    UserCRUDDto getByUsername(String username);
}
