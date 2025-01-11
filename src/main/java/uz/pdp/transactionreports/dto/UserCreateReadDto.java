package uz.pdp.transactionreports.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.transactionreports.entity.User;
import uz.pdp.transactionreports.utils.enums.Role;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateReadDto {
    @NotBlank
    private String name;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotNull
    private Role role;

    public UserCreateReadDto(User user) {
        this.name = user.getName();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.role = user.getRole();
    }
}
