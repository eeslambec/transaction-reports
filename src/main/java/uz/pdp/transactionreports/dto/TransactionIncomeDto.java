package uz.pdp.transactionreports.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import uz.pdp.transactionreports.entity.Attachment;
import uz.pdp.transactionreports.entity.Customer;
import uz.pdp.transactionreports.entity.Transaction;
import uz.pdp.transactionreports.utils.enums.Currency;
import uz.pdp.transactionreports.utils.enums.TransactionCategory;

import java.math.BigDecimal;
import java.time.LocalDate;

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
    @NotNull
    private Customer customer;
    @NotNull
    private LocalDate transactionDate;
    @NotBlank
    private String description;
    @NotNull
    private Attachment attachment;

    public TransactionIncomeDto(Transaction transaction) {
        this.transactionCategory = transaction.getTransactionCategory();
        this.amount = transaction.getAmount();
        this.currency = transaction.getCurrency();
        this.transactionDate = transaction.getTransactionDate();
        this.description = transaction.getDescription();
        this.customer = transaction.getCustomer();
        this.attachment = transaction.getAttachment();
    }
}
