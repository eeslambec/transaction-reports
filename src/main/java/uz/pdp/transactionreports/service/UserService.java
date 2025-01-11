package uz.pdp.transactionreports.service;

import org.springframework.stereotype.Service;
import uz.pdp.transactionreports.dto.JWTDto;
import uz.pdp.transactionreports.dto.UserCreateReadDto;
import uz.pdp.transactionreports.dto.UserLoginDto;
import uz.pdp.transactionreports.dto.UserUpdateDto;
import uz.pdp.transactionreports.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface UserService {
    JWTDto login(UserLoginDto loginDto);
    JWTDto register(UserCreateReadDto user);
    UserCreateReadDto update(UserUpdateDto userUpdateDto);
    void deleteById(UUID id);
    void deleteByUsername(String username);
    List<User> findAll();
    UserCreateReadDto getById(UUID id);
    UserCreateReadDto getByUsername(String username);
    UserCreateReadDto findByUsername(String username);
}
