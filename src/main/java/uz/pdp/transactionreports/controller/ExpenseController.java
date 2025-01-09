package uz.pdp.transactionreports.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.transactionreports.dto.ExpenseDto;
import uz.pdp.transactionreports.entity.Expense;
import uz.pdp.transactionreports.service.ExpenseService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/expense")
public class ExpenseController {
    private final ExpenseService expenseService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody String name) {
        return ResponseEntity.ok(expenseService.create(name));
    }
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody ExpenseDto expense) {
        return ResponseEntity.ok(expenseService.update(expense));
    }
    @GetMapping("/get/id/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(expenseService.getById(id));
    }
    @GetMapping("/get/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(expenseService.getAll());
    }
}
