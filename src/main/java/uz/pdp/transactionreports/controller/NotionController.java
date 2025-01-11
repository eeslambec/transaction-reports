package uz.pdp.transactionreports.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.transactionreports.entity.NotionTransaction;
import uz.pdp.transactionreports.mapper.NotionTransactionMapper;
import uz.pdp.transactionreports.notion.NotionClient;
import uz.pdp.transactionreports.notion.config.NotionConfigProperties;
import uz.pdp.transactionreports.notion.model.Page;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notion")
public class NotionController {
    private final NotionClient client;
    private final NotionConfigProperties notionConfigProperties;

    @GetMapping("/all")
    public List<NotionTransaction> getAll() {
        List<Page> pages = client.databases.query(notionConfigProperties.databaseId());
        return pages.stream().map(NotionTransactionMapper::toNotionTransaction).toList();
    }
}
