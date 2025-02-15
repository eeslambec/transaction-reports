package uz.pdp.transactionreports.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import uz.pdp.transactionreports.entity.Transaction;
import uz.pdp.transactionreports.utils.enums.Currency;
import uz.pdp.transactionreports.utils.enums.TransactionCategory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class TransactionIncomeDto {
    @NotNull
    private TransactionCategory transactionCategory;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private Currency currency;
    @NotBlank
    private String customerPhoneNumber;
    @NotNull
    private LocalDate transactionDate;
    @NotBlank
    private String description;
    @NotNull
    private UUID attachmentId;

    public TransactionIncomeDto(Transaction transaction) {
        this.transactionCategory = transaction.getTransactionCategory();
        this.amount = transaction.getAmount();
        this.currency = transaction.getCurrency();
        this.transactionDate = transaction.getTransactionDate();
        this.description = transaction.getDescription();
        this.customerPhoneNumber = transaction.getCustomer().getPhoneNumber();
        this.attachmentId = transaction.getAttachment().getId();
    }
}
