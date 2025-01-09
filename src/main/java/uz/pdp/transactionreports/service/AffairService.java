package uz.pdp.transactionreports.service;

import org.springframework.stereotype.Service;
import uz.pdp.transactionreports.dto.AffairDto;
import uz.pdp.transactionreports.entity.Affair;

import java.util.List;
import java.util.UUID;

@Service
public interface AffairService {
    AffairDto create (String name);
    AffairDto update (AffairDto affair);
    Affair getById(UUID id);
    List<Affair> getAll();
}
