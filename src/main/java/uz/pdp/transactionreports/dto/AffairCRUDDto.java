package uz.pdp.transactionreports.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AffairCRUDDto {
    @NotBlank
    private String name;
}
