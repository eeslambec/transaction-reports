package uz.pdp.transactionreports.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import uz.pdp.transactionreports.entity.Affair;
import uz.pdp.transactionreports.entity.Expense;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ExpenseDto {
    private UUID id;
    private String name;

    public ExpenseDto (Expense expense) {
        this.id = expense.getId();
        this.name = expense.getName();
    }
}
