package uz.pdp.transactionreports.entity;

import java.math.BigDecimal;

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
