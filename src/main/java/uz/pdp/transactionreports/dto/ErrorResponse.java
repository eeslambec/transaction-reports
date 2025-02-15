package uz.pdp.transactionreports.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private final LocalDateTime timestamp = LocalDateTime.now();
}
