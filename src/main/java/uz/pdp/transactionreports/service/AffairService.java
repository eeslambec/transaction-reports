package uz.pdp.transactionreports.service;

import org.springframework.stereotype.Service;
import uz.pdp.transactionreports.entity.Affair;

import java.util.List;
import java.util.UUID;

@Service
public interface AffairService {
    Affair create (String name);
    Affair update (Affair affair);
    Affair getById(UUID id);
    List<Affair> getAll();
}
