package uz.pdp.transactionreports.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import uz.pdp.transactionreports.entity.Expense;

import java.util.List;
import java.util.UUID;

@Service
public interface ExpenseService {
    Expense create (@NotBlank String name);
    Expense update (@NotNull Expense expense);
    Expense getById(UUID id);
    List<Expense> getAll();
}
