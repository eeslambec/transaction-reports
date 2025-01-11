package uz.pdp.transactionreports.notion.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
import uz.pdp.transactionreports.exception.InvalidArgumentException;
import uz.pdp.transactionreports.exception.NotFoundException;
import uz.pdp.transactionreports.notion.config.NotionConfigProperties;
import uz.pdp.transactionreports.notion.model.Database;
import uz.pdp.transactionreports.notion.model.Page;

import java.util.List;

@Log4j2
@Service
public class DatabaseService {
    private final RestTemplate restTemplate;
    private final NotionConfigProperties notionConfigProperties;
    private final String url;

    public DatabaseService(RestTemplate restTemplate, NotionConfigProperties notionConfigProperties) {
        this.restTemplate = restTemplate;
        this.notionConfigProperties = notionConfigProperties;
        url = notionConfigProperties.apiUrl() + "/v1/databases/" + notionConfigProperties.databaseId() + "/query";

    }
    public List<Page> query(String databaseId) {
        if (url == null || url.isEmpty()) {
            throw new InvalidArgumentException("Base URL");
        }

        log.info("Querying Notion database: {}", url);

        ResponseEntity<Database> db = restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(getDefaultHeaders()),
                Database.class
        );

        if (db.getBody() == null) {
            throw new NotFoundException("Response body");
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
                transaction.getExpenseCategory() == null ? "NOT_EXPENSE" : null,
                transaction.getAmount(),
                transaction.getCurrency().name(),
                transaction.getCustomer() != null ? transaction.getCustomer().getPhoneNumber() : null,
                transaction.getDescription(),
                transaction.getAttachment() != null ? transaction.getAttachment().getId().toString() : null,
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
