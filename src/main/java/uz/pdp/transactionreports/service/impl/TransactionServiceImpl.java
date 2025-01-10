package uz.pdp.transactionreports.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uz.pdp.transactionreports.dto.AttachmentDto;
import uz.pdp.transactionreports.dto.TransactionExpenseDto;
import uz.pdp.transactionreports.dto.TransactionIncomeDto;
import uz.pdp.transactionreports.entity.Attachment;
import uz.pdp.transactionreports.entity.Customer;
import uz.pdp.transactionreports.entity.Transaction;
import uz.pdp.transactionreports.exception.InvalidArgumentException;
import uz.pdp.transactionreports.exception.NotFoundException;
import uz.pdp.transactionreports.mapper.AttachmentMapper;
import uz.pdp.transactionreports.notion.service.DatabaseService;
import uz.pdp.transactionreports.repository.TransactionRepository;
import uz.pdp.transactionreports.service.AttachmentService;
import uz.pdp.transactionreports.service.CustomerService;
import uz.pdp.transactionreports.service.TransactionService;
import uz.pdp.transactionreports.utils.enums.TransactionStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final TransactionRepository transactionRepository;
    private final DatabaseService notionService;
    private final AttachmentService attachmentService;
    private final AttachmentMapper attachmentMapper;
    private final CustomerService customerService;

    @Override
    public TransactionIncomeDto createIncome(TransactionIncomeDto transactionIncomeDto) {
        if (transactionIncomeDto.getTransactionDate() == null)
            transactionIncomeDto.setTransactionDate(LocalDate.now());

        Attachment byId = attachmentMapper.toEntity(attachmentService.getById(transactionIncomeDto.getAttachmentId()));
        Customer byPhoneNumber = customerService.getByPhoneNumber(transactionIncomeDto.getCustomerPhoneNumber());

        Transaction saved = transactionRepository.save(Transaction.builder()
                .amount(transactionIncomeDto.getAmount())
                .currency(transactionIncomeDto.getCurrency())
                .description(transactionIncomeDto.getDescription())
                .transactionDate(transactionIncomeDto.getTransactionDate())
                .transactionCategory(transactionIncomeDto.getTransactionCategory())
                .currency(transactionIncomeDto.getCurrency())
                .attachment(byId)
                .customer(byPhoneNumber)
                .expenseCategory(null)
                .transactionStatus(TransactionStatus.COMPLETED)
                .build());
        try {
            notionService.createTransaction(saved);
        } catch (Exception e) {
            log.error("Failed to sync transaction to Notion: {}", saved.getId(), e);
            throw new InvalidArgumentException("Transaction");
        }
        return new TransactionIncomeDto(saved);
    }

    @Override
    public TransactionExpenseDto createExpense(TransactionExpenseDto transactionExpenseDto) {
        if (transactionExpenseDto.getTransactionDate() == null)
            transactionExpenseDto.setTransactionDate(LocalDate.now());
        return new TransactionExpenseDto(transactionRepository.save(Transaction.builder()
                .amount(transactionExpenseDto.getAmount())
                .currency(transactionExpenseDto.getCurrency())
                .description(transactionExpenseDto.getDescription())
                .transactionDate(transactionExpenseDto.getTransactionDate())
                .expenseCategory(transactionExpenseDto.getExpenseCategory())
                .attachment(transactionExpenseDto.getAttachment())
                .transactionStatus(TransactionStatus.COMPLETED)
                .build()));
    }

    @Override
    public void deleteById(UUID id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Transaction"));
        if (transaction.getTransactionStatus() != TransactionStatus.COMPLETED)
            transaction.setTransactionStatus(TransactionStatus.DELETED);
    }

    @Override
    public Transaction getById(UUID id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Transaction"));
        if (transaction.getTransactionStatus() != TransactionStatus.COMPLETED)
            throw new NotFoundException("Transaction");
        return transaction;
    }

    @Override
    public List<Transaction> getAllCompleted() {
        List<Transaction> all = transactionRepository.findAll();
        List<Transaction> allCompleted = new ArrayList<>();
        for (Transaction transaction : all) {
            if (transaction.getTransactionStatus() != TransactionStatus.DELETED)
                allCompleted.add(transaction);
        }
        return allCompleted;
    }

    @Override
    public List<Transaction> getAllDeleted() {
        List<Transaction> all = transactionRepository.findAll();
        List<Transaction> allDeleted = new ArrayList<>();
        for (Transaction transaction : all) {
            if (transaction.getTransactionStatus() == TransactionStatus.DELETED)
                allDeleted.add(transaction);
        }
        return allDeleted;
    }

    @Override
    public List<Transaction> getAllIncomes() {
        List<Transaction> all = transactionRepository.findAll();
        List<Transaction> allIncomes = new ArrayList<>();
        for (Transaction transaction : all) {
            if (transaction.getCustomer() != null
                    && transaction.getTransactionStatus() != TransactionStatus.DELETED)
                allIncomes.add(transaction);
        }
        return allIncomes;
    }

    @Override
    public List<Transaction> getAllExpenses() {
        List<Transaction> all = transactionRepository.findAll();
        List<Transaction> allExpenses = new ArrayList<>();
        for (Transaction transaction : all) {
            if (transaction.getCustomer() == null
                    && transaction.getTransactionStatus() != TransactionStatus.DELETED)
                allExpenses.add(transaction);
        }
        return allExpenses;
    }
}
