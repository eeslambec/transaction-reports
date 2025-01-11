package uz.pdp.transactionreports.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import uz.pdp.transactionreports.entity.Affair;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class CustomerUpdateDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String phoneNumber;
    private Affair affair;
}
