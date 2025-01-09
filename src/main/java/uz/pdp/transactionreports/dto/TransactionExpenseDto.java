package uz.pdp.transactionreports.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import uz.pdp.transactionreports.entity.Attachment;
import uz.pdp.transactionreports.entity.Transaction;
import uz.pdp.transactionreports.utils.enums.Currency;
import uz.pdp.transactionreports.utils.enums.ExpenseCategory;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class TransactionExpenseDto {
    @NotNull
    private ExpenseCategory expenseCategory;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private Currency currency;
    @NotNull
    private LocalDate transactionDate;
    @NotBlank
    private String description;
    @NotNull
    private Attachment attachment;

    public TransactionExpenseDto(Transaction transaction) {
        this.expenseCategory = transaction.getExpenseCategory();
        this.amount = transaction.getAmount();
        this.currency = transaction.getCurrency();
        this.transactionDate = transaction.getTransactionDate();
        this.description = transaction.getDescription();
        this.attachment = transaction.getAttachment();
    }
}
