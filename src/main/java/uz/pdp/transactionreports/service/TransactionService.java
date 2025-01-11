package uz.pdp.transactionreports.service;

import org.springframework.stereotype.Service;
import uz.pdp.transactionreports.dto.TransactionExpenseDto;
import uz.pdp.transactionreports.dto.TransactionIncomeDto;
import uz.pdp.transactionreports.entity.Transaction;
import uz.pdp.transactionreports.utils.enums.ExpenseCategory;

import java.time.LocalDate;
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
    List<TransactionIncomeDto> getAllIncomes();
    List<TransactionExpenseDto> getAllExpenses();
    List<Transaction> getAll();
    List<Transaction> getAllByPeriod(LocalDate startDate, LocalDate endDate);
    List<TransactionExpenseDto> getAllByExpenseCategory(ExpenseCategory expenseCategory);
    List<TransactionIncomeDto> getAllByCustomerPhoneNumber(String phoneNumber);
    List<TransactionIncomeDto> getAllByAffairName(String affairName);

}
