package uz.pdp.transactionreports.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;
import uz.pdp.transactionreports.entity.NotionTransaction;
import uz.pdp.transactionreports.notion.model.Page;

import java.math.BigDecimal;

@Component
public class NotionTransactionMapper {
    public static NotionTransaction toNotionTransaction(Page page) {
        JsonNode properties = page.getProperties();

        BigDecimal transactionAmount = new BigDecimal(
                properties.get("Transaction Amount")
                        .get("number")
                        .get("format")
                        .asText()
        );

        String transactionCategory = properties.get("Transaction Category")
                .get("select")
                .get("name")
                .asText();

        String expenseCategory = properties.get("Expense Category")
                .get("rich_text")
                .get(0)
                .get("text")
                .get("content")
                .asText();

        String currency = properties.get("currency")
                .get("rich_text")
                .get(0)
                .get("text")
                .get("content")
                .asText();

        String customerPhoneNumber = properties.get("Customer Phone Number")
                .get("phone_number")
                .asText();

        String description = properties.get("Transaction Description")
                .get("rich_text")
                .get(0)
                .get("text")
                .get("content")
                .asText();

        String attachmentId = properties.get("Attachment Id")
                .get("rich_text")
                .get(0)
                .get("text")
                .get("content")
                .asText();

        String transactionStatus = properties.get("Transaction Status")
                .get("rich_text")
                .get(0)
                .get("text")
                .get("content")
                .asText();

        return new NotionTransaction(
                page.getId(),
                transactionCategory,
                expenseCategory,
                transactionAmount,
                currency,
                customerPhoneNumber,
                description,
                attachmentId,
                transactionStatus
        );
    }
}
