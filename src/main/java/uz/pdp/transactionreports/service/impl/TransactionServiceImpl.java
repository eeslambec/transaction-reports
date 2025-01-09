package uz.pdp.transactionreports.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.transactionreports.dto.TransactionExpenseDto;
import uz.pdp.transactionreports.dto.TransactionIncomeDto;
import uz.pdp.transactionreports.entity.Transaction;
import uz.pdp.transactionreports.repository.TransactionRepository;
import uz.pdp.transactionreports.service.TransactionService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    @Override
    public TransactionIncomeDto createIncome(TransactionIncomeDto transactionIncomeDto) {
        if (transactionIncomeDto.getTransactionDate() == null)
            transactionIncomeDto.setTransactionDate(LocalDate.now());
        return new TransactionIncomeDto(transactionRepository.save(Transaction.builder()
                .amount(transactionIncomeDto.getAmount())
                .currency(transactionIncomeDto.getCurrency())
                .description(transactionIncomeDto.getDescription())
                .transactionDate(transactionIncomeDto.getTransactionDate())
                .transactionCategory(transactionIncomeDto.getTransactionCategory())
                .currency(transactionIncomeDto.getCurrency())
                .attachment(transactionIncomeDto.getAttachment())
                .build()));
    }

    @Override
    public TransactionExpenseDto createExpense(TransactionExpenseDto transactionExpenseDto) {
        if (transactionExpenseDto.getTransactionDate() == null)
            transactionExpenseDto.setTransactionDate(LocalDate.now());
        return new transactionExpenseDto(transactionRepository.save(Transaction.builder()
                .amount(transactionExpenseDto.getAmount())
                .currency(transactionExpenseDto.getCurrency())
                .description(transactionExpenseDto.getDescription())
                .transactionDate(transactionExpenseDto.getTransactionDate())
                .transactionCategory(transactionExpenseDto.getExpenseCategory())
                .currency(transactionExpenseDto.getCurrency())
                .attachment(transactionExpenseDto.getAttachment())
                .build()));
    }

    @Override
    public void deleteById(UUID id) {

    }

    @Override
    public Transaction getById(UUID id) {
        return null;
    }

    @Override
    public List<Transaction> getAll() {
        return List.of();
    }

    @Override
    public List<Transaction> getAllIncomes() {
        return List.of();
    }

    @Override
    public List<Transaction> getAllExpenses() {
        return List.of();
    }
}
