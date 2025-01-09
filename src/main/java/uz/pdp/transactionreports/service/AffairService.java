package uz.pdp.transactionreports.service;

import org.springframework.stereotype.Service;
import uz.pdp.transactionreports.dto.AffairCRUDDto;
import uz.pdp.transactionreports.entity.Affair;

import java.util.List;
import java.util.UUID;

@Service
public interface AffairService {
    Affair create (AffairCRUDDto affairCRUDDto);
    Affair update (AffairCRUDDto affairCRUDDto);
    void delete (UUID id);
    Affair getById(UUID id);
    List<Affair> getAll();
}
