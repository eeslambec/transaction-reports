package uz.pdp.transactionreports.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import uz.pdp.transactionreports.entity.Affair;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class AffairDto {
    private UUID id;
    private String name;

    public AffairDto (Affair affair) {
        this.id = affair.getId();
        this.name = affair.getName();
    }
}
