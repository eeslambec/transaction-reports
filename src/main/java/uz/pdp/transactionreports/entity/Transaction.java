package uz.pdp.transactionreports.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.transactionreports.utils.enums.Currency;
import uz.pdp.transactionreports.utils.enums.ExpenseCategory;
import uz.pdp.transactionreports.utils.enums.TransactionCategory;
import uz.pdp.transactionreports.utils.enums.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Enumerated(EnumType.STRING)
    private TransactionCategory transactionCategory;
    @Enumerated(EnumType.STRING)
    private ExpenseCategory expenseCategory;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    @ManyToOne
    private Customer customer;
    private LocalDate transactionDate;
    private String description;
    @OneToOne
    private Attachment attachment;
    private TransactionStatus transactionStatus;
}
