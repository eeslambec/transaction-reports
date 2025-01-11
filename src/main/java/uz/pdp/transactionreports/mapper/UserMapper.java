package uz.pdp.transactionreports.mapper;

import org.springframework.stereotype.Component;
import uz.pdp.transactionreports.dto.UserCreateReadDto;
import uz.pdp.transactionreports.entity.User;
import uz.pdp.transactionreports.utils.enums.UserStatus;

@Component
public class UserMapper {
    public User toEntity(UserCreateReadDto dto){
        return User.builder()
                .name(dto.getName())
                .password(dto.getPassword())
                .username(dto.getUsername())
                .status(UserStatus.ACTIVE)
                .role(dto.getRole())
                .build();
    }
}
