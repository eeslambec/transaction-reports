package uz.pdp.transactionreports.service;

import org.springframework.stereotype.Service;
import uz.pdp.transactionreports.dto.ExpenseCRUDDto;
import uz.pdp.transactionreports.entity.Expense;

import java.util.List;
import java.util.UUID;

@Service
public interface ExpenseService {
    Expense create (ExpenseCRUDDto affairCRUDDto);
    Expense update (ExpenseCRUDDto affairCRUDDto);
    void delete (UUID id);
    Expense getById(UUID id);
    List<Expense> getAll();
}
