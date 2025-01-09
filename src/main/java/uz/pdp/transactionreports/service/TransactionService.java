package uz.pdp.transactionreports.service;

import org.springframework.stereotype.Service;
import uz.pdp.transactionreports.dto.TransactionExpenseDto;
import uz.pdp.transactionreports.dto.TransactionIncomeDto;
import uz.pdp.transactionreports.entity.Transaction;

import java.util.List;
import java.util.UUID;

@Service
public interface TransactionService {
    TransactionIncomeDto createIncome(TransactionIncomeDto transactionIncomeDto);
    TransactionExpenseDto createExpense(TransactionExpenseDto transactionExpenseDto);
    void deleteById(UUID id);
    Transaction getById(UUID id);
    List<Transaction> getAllCompleted();
    List<Transaction> getAllDeleted();
    List<Transaction> getAllIncomes();
    List<Transaction> getAllExpenses();
}
