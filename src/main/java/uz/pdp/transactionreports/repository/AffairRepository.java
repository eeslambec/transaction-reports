package uz.pdp.transactionreports.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.transactionreports.entity.Affair;

import java.util.UUID;

@Repository
public interface AffairRepository extends JpaRepository<Affair, UUID> {
}
