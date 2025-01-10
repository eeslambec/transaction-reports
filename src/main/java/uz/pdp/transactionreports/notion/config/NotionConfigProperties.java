package uz.pdp.transactionreports.notion.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("notion")
public record NotionConfigProperties(@Value("${notion.api-url}") String apiUrl,@Value("${notion.api-version}") String apiVersion, @Value("${notion.auth-token}") String authToken,@Value("${notion.database-id}") String databaseId) {
}
