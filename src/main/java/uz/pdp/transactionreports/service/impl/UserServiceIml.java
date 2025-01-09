package uz.pdp.transactionreports.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.transactionreports.dto.UserCRUDDto;
import uz.pdp.transactionreports.dto.UserUpdateDto;
import uz.pdp.transactionreports.entity.User;
import uz.pdp.transactionreports.exception.NotFoundException;
import uz.pdp.transactionreports.repository.UserRepository;
import uz.pdp.transactionreports.service.UserService;
import uz.pdp.transactionreports.utils.Validations;
import uz.pdp.transactionreports.utils.enums.UserStatus;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceIml implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public User create(UserCRUDDto user) {
        return userRepository.save(User.builder()
                        .name(user.getName())
                        .username(user.getUsername())
                        .password(passwordEncoder.encode(user.getPassword()))
                        .role(user.getRole())
                .build());
    }

    @Override
    public UserCRUDDto update(UserUpdateDto userUpdateDto) {
        User user = userRepository.findById(userUpdateDto.getId())
                .orElseThrow(() -> new NotFoundException("User"));
        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new NotFoundException("User");
        }
        if (!userUpdateDto.getPassword().isBlank()){
            user.setPassword(passwordEncoder.encode(userUpdateDto.getPassword()));
        }
        return new UserCRUDDto(userRepository.save(User.builder()
                .name(Validations.requireNonNullElse(userUpdateDto.getName(), user.getName()))
                .username(Validations.requireNonNullElse(userUpdateDto.getUsername(), user.getUsername()))
                .password(user.getPassword())
                .role(Validations.requireNonNullElse(userUpdateDto.getRole(), user.getRole()))
                .build()));
    }

    @Override
    public void deleteById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User"));
        if (user.getStatus() != UserStatus.DELETED)
            throw new NotFoundException("User");
        user.setStatus(UserStatus.DELETED);
        userRepository.save(user);
    }

    @Override
    public void deleteByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User"));
        if (user.getStatus() != UserStatus.DELETED)
            throw new NotFoundException("User");
        user.setStatus(UserStatus.DELETED);
        userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserCRUDDto getById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User"));
        if (user.getStatus() != UserStatus.ACTIVE)
            throw new NotFoundException("User");
        return new UserCRUDDto(user);
    }

    @Override
    public UserCRUDDto getByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User"));
        if (user.getStatus() != UserStatus.ACTIVE)
            throw new NotFoundException("User");
        return new UserCRUDDto(user);
    }
}
