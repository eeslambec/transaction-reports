package uz.pdp.transactionreports.entity;

import uz.pdp.transactionreports.notion.model.Page;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record NotionTransaction(String id,
                                String transactionCategory,
                                String expenseCategory,
                                BigDecimal amount,
                                String currency,
                                String customerPhoneNumber,
                                String description,
                                String attachmentId,
                                String transactionStatus
) {
}
