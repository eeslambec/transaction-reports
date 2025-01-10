package uz.pdp.transactionreports.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.transactionreports.dto.JWTDto;
import uz.pdp.transactionreports.dto.UserCreateReadDto;
import uz.pdp.transactionreports.dto.UserLoginDto;
import uz.pdp.transactionreports.dto.UserUpdateDto;
import uz.pdp.transactionreports.entity.User;
import uz.pdp.transactionreports.exception.AlreadyExistsException;
import uz.pdp.transactionreports.exception.InvalidArgumentException;
import uz.pdp.transactionreports.exception.NotFoundException;
import uz.pdp.transactionreports.mapper.UserMapper;
import uz.pdp.transactionreports.repository.UserRepository;
import uz.pdp.transactionreports.security.JwtProvider;
import uz.pdp.transactionreports.service.UserService;
import uz.pdp.transactionreports.utils.Validations;
import uz.pdp.transactionreports.utils.enums.UserStatus;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final UserMapper userMapper;

    @Override
    public JWTDto login(UserLoginDto loginDto) {
        User user = userRepository.findByUsername(loginDto.getUsername()).orElseThrow(
                () -> new NotFoundException("User"));
        if (passwordEncoder.matches(loginDto.getPassword(),user.getPassword())){
            return new JWTDto(jwtProvider.generateToken(user.getUsername()));
        }
        throw new InvalidArgumentException("Password");
    }

    @Override
    public JWTDto register(UserCreateReadDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new AlreadyExistsException("User");
        }
        /// simple regex,if I write really project I can write better
        if (userDto.getUsername().matches("^[a-zA-Z0-9]{3,20}$") &&
                userDto.getPassword().matches("^[a-zA-Z0-9_@]{8,}")
        ) {
            User entity = userMapper.toEntity(userDto);
            entity.setPassword(passwordEncoder.encode(userDto.getPassword()));
            User user = userRepository.save(entity);
            return new JWTDto(jwtProvider.generateToken(user.getUsername()));
        }
        throw new InvalidArgumentException("Username or Password");
    }

    @Override
    public UserCreateReadDto update(UserUpdateDto userUpdateDto) {
        User user = userRepository.findById(userUpdateDto.getId())
                .orElseThrow(() -> new NotFoundException("User"));
        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new NotFoundException("User");
        }
        if (!userUpdateDto.getPassword().isBlank()){
            user.setPassword(passwordEncoder.encode(userUpdateDto.getPassword()));
        }
        return new UserCreateReadDto(userRepository.save(User.builder()
                .name(Validations.requireNonNullElse(userUpdateDto.getName(), user.getName()))
                .username(Validations.requireNonNullElse(userUpdateDto.getUsername(), user.getUsername()))
                .password(user.getPassword())
                .role(Validations.requireNonNullElse(userUpdateDto.getRole(), user.getRole()))
                .build()));
    }

    @Override
    public void deleteById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User"));
        if (user.getStatus() == UserStatus.DELETED)
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
    public UserCreateReadDto getById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User"));
        if (user.getStatus() != UserStatus.ACTIVE)
            throw new NotFoundException("User");
        return new UserCreateReadDto(user);
    }

    @Override
    public UserCreateReadDto getByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User"));
        if (user.getStatus() != UserStatus.ACTIVE)
            throw new NotFoundException("User");
        return new UserCreateReadDto(user);
    }
}
