package uz.pdp.transactionreports.service.impl;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.transactionreports.entity.Expense;
import uz.pdp.transactionreports.exception.NotFoundException;
import uz.pdp.transactionreports.repository.ExpenseRepository;
import uz.pdp.transactionreports.service.ExpenseService;
import uz.pdp.transactionreports.utils.Validations;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseRepository expenseRepository;

    @Override
    public Expense create(@NotBlank String name) {
        return expenseRepository.save(Expense.builder()
                .name(name)
                .build());
    }

    @Override
    public Expense update(@NotNull Expense expense) {
        Expense oldExpense = expenseRepository.findById(expense.getId()).orElseThrow(
                () -> new NotFoundException("Expense"));
        return expenseRepository.save(Expense.builder()
                .name(Validations.requireNonNullElse(expense.getName(), oldExpense.getName()))
                .build());
    }

    @Override
    public Expense getById(UUID id) {
        return expenseRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Expense"));
    }

    @Override
    public List<Expense> getAll() {
        return expenseRepository.findAll();
    }
}
