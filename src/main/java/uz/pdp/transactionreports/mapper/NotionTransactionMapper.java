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

        BigDecimal transactionAmount = properties.has("Transaction Amount") &&
                properties.get("Transaction Amount").has("number") &&
                properties.get("Transaction Amount").get("number").has("format")
                ? new BigDecimal(properties.get("Transaction Amount").get("number").get("format").asText())
                : BigDecimal.ZERO;

        String transactionCategory = getStringProperty(properties, "Transaction Category", "select", "name");
        String expenseCategory = getRichTextContent(properties, "Expense Category");
        String currency = getRichTextContent(properties, "currency");
        String customerPhoneNumber = getStringProperty(properties, "Customer Phone Number", "phone_number", null);
        String description = getRichTextContent(properties, "Transaction Description");
        String attachmentId = getRichTextContent(properties, "Attachment Id");
        String transactionStatus = getRichTextContent(properties, "Transaction Status");

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

    private static String getRichTextContent(JsonNode properties, String key) {
        if (properties.has(key) &&
                properties.get(key).has("rich_text") &&
                properties.get(key).get("rich_text").isArray() &&
                !properties.get(key).get("rich_text").isEmpty() &&
                properties.get(key).get("rich_text").get(0).has("text") &&
                properties.get(key).get("rich_text").get(0).get("text").has("content")) {
            return properties.get(key).get("rich_text").get(0).get("text").get("content").asText();
        }
        return ""; // Default value if the property is missing or malformed
    }

    private static String getStringProperty(JsonNode properties, String key, String nestedKey, String nestedSubKey) {
        if (properties.has(key) && properties.get(key).has(nestedKey)) {
            JsonNode nestedNode = properties.get(key).get(nestedKey);
            if (nestedSubKey != null && nestedNode.has(nestedSubKey)) {
                return nestedNode.get(nestedSubKey).asText();
            }
            return nestedNode.asText();
        }
        return ""; // Default value
    }

}
