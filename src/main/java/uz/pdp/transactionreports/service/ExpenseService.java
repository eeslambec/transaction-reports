package uz.pdp.transactionreports.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import uz.pdp.transactionreports.dto.ExpenseDto;
import uz.pdp.transactionreports.entity.Expense;

import java.util.List;
import java.util.UUID;

@Service
public interface ExpenseService {
    ExpenseDto create (@NotBlank String name);
    ExpenseDto update (@NotNull ExpenseDto expense);
    Expense getById(UUID id);
    List<Expense> getAll();
}
