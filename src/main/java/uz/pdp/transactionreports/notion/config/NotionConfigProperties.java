package uz.pdp.transactionreports.notion.config;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties("notion")
public record NotionConfigProperties(String apiUrl, String apiVersion, String authToken, String databaseId) {
}
