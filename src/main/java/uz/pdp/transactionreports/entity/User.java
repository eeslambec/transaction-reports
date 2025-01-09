package uz.pdp.transactionreports.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.transactionreports.utils.enums.Role;
import uz.pdp.transactionreports.utils.enums.UserStatus;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private UserStatus status;
}
