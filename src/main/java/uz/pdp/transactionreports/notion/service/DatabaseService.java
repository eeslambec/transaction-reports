package uz.pdp.transactionreports.notion.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.pdp.transactionreports.entity.NotionTransaction;
import uz.pdp.transactionreports.entity.Transaction;
import uz.pdp.transactionreports.notion.config.NotionConfigProperties;
import uz.pdp.transactionreports.notion.model.Database;
import uz.pdp.transactionreports.notion.model.Page;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseService {
    private final RestTemplate restTemplate;
    private final NotionConfigProperties notionConfigProperties;
    private final Logger log = LoggerFactory.getLogger(DatabaseService.class);

    private String url = notionConfigProperties.apiUrl() + "v1/databases/" + notionConfigProperties.databaseId() + "/query";
    public List<Page> query(String databaseId) {

        if (url == null || url.isEmpty()) {
            throw new IllegalStateException("Base URL for Notion API is not configured.");
        }

        if (!url.endsWith("/")) {
            url += "/";
        }

        log.info("Querying Notion database: {}", url);

        ResponseEntity<Database> db = restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(getDefaultHeaders()),
                Database.class
        );

        if (db.getBody() == null) {
            throw new IllegalStateException("No response body found for database query.");
        }

        return db.getBody().getPages();
    }
    public void createTransaction(Transaction transaction) {
        NotionTransaction request = mapTransactionToNotionPage(transaction);
        restTemplate.postForEntity(url, request, Void.class, new HttpEntity<>(getDefaultHeaders()));
    }

    private NotionTransaction mapTransactionToNotionPage(Transaction transaction) {
        return new NotionTransaction(
                transaction.getId().toString(),
                transaction.getTransactionCategory().name(),
                transaction.getExpenseCategory() == null ? "NOT_EXPENSE" : null, // Enum to String
                transaction.getAmount(),
                transaction.getCurrency().name(),
                transaction.getCustomer() != null ? transaction.getCustomer().getPhoneNumber() : null, // Handle potential null
                transaction.getDescription(),
                transaction.getAttachment() != null ? transaction.getAttachment().getId().toString() : null, // Handle potential null
                transaction.getTransactionStatus().name()
        );
    }

    private HttpHeaders getDefaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Notion-Version", notionConfigProperties.apiVersion());
        headers.set("Authorization", notionConfigProperties.authToken());
        return headers;
    }
}
