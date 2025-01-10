package uz.pdp.transactionreports;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import uz.pdp.transactionreports.notion.config.NotionConfigProperties;

@SpringBootApplication
@EnableConfigurationProperties(NotionConfigProperties.class)
public class TransactionReportsApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionReportsApplication.class, args);
    }

}
