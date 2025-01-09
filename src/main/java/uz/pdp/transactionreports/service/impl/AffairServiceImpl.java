package uz.pdp.transactionreports.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.transactionreports.entity.Affair;
import uz.pdp.transactionreports.exception.NotFoundException;
import uz.pdp.transactionreports.repository.AffairRepository;
import uz.pdp.transactionreports.service.AffairService;
import uz.pdp.transactionreports.utils.Validations;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AffairServiceImpl implements AffairService {
    private final AffairRepository affairRepository;

    @Override
    public Affair create(String name) {
        return affairRepository.save(Affair.builder()
                .name(name)
                .build());
    }

    @Override
    public Affair update(Affair affair) {
        Affair oldAffair = affairRepository.findById(affair.getId()).orElseThrow(
                () -> new NotFoundException("Affair"));
        return affairRepository.save(Affair.builder()
                .name(Validations.requireNonNullElse(affair.getName(), oldAffair.getName()))
                .build());
    }

    @Override
    public Affair getById(UUID id) {
        return affairRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Affair")
        );
    }

    @Override
    public List<Affair> getAll() {
        return affairRepository.findAll();
    }
}
