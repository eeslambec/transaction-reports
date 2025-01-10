package uz.pdp.transactionreports.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class JWTDto implements Serializable {
    private String token;
    private final LocalDateTime timestamp = LocalDateTime.now();
}
