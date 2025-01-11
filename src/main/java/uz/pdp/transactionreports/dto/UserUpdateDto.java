package uz.pdp.transactionreports.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import uz.pdp.transactionreports.entity.User;
import uz.pdp.transactionreports.utils.enums.Role;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class UserUpdateDto {
    private UUID id;
    private String name;
    private String username;
    private String password;
    private Role role;

    public UserUpdateDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.role = user.getRole();
    }
}
