package uz.pdp.transactionreports.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.transactionreports.entity.Transaction;
import uz.pdp.transactionreports.utils.enums.ExpenseCategory;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    List<Transaction> findAllByTransactionDateBetween(LocalDate startDate, LocalDate endDate);

    List<Transaction> findAllByExpenseCategory(ExpenseCategory expenseCategory);

    @Query("SELECT T FROM Transaction T WHERE T.customer.phoneNumber = ?1")
    List<Transaction> findAllByCustomerPhoneNumber(String phoneNumber);

    @Query("SELECT T FROM Transaction T WHERE T.affair.name = ?1")
    List<Transaction> findAllByAffairName(String affairName);
}
