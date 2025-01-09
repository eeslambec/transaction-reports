package uz.pdp.transactionreports.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.transactionreports.dto.TransactionExpenseDto;
import uz.pdp.transactionreports.dto.TransactionIncomeDto;
import uz.pdp.transactionreports.service.TransactionService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/create/income")
    public ResponseEntity<?> createIncome(@RequestBody TransactionIncomeDto transaction) {
        return ResponseEntity.ok(transactionService.createIncome(transaction));
    }
    @PostMapping("/create/expense")
    public ResponseEntity<?> createExpense(@RequestBody TransactionExpenseDto transaction) {
        return ResponseEntity.ok(transactionService.createExpense(transaction));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable UUID id) {
        transactionService.deleteById(id);
        return ResponseEntity.ok("Transaction deleted");
    }
    @GetMapping("/get/completed")
    public ResponseEntity<?> getCompletedTransactions() {
        return ResponseEntity.ok(transactionService.getAllCompleted());
    }
    @GetMapping("/get/deleted")
    public ResponseEntity<?> getDeletedTransactions() {
        return ResponseEntity.ok(transactionService.getAllDeleted());
    }
    @GetMapping("/get/income")
    public ResponseEntity<?> getIncomeTransactions() {
        return ResponseEntity.ok(transactionService.getAllIncomes());
    }
    @GetMapping("/get/expense")
    public ResponseEntity<?> getExpenseTransactions() {
        return ResponseEntity.ok(transactionService.getAllExpenses());
    }

}
