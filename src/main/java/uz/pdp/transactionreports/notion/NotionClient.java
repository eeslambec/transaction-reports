package uz.pdp.transactionreports.notion;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.pdp.transactionreports.notion.service.DatabaseService;

@Component
@RequiredArgsConstructor
public class NotionClient {
    public final DatabaseService databases;
}
