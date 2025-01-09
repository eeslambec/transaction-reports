package uz.pdp.transactionreports.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.transactionreports.dto.AffairDto;
import uz.pdp.transactionreports.entity.Affair;
import uz.pdp.transactionreports.service.AffairService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/affair")
public class AffairController {
    private final AffairService affairService;

    @PostMapping("/create/name/{name}")
    public ResponseEntity<?> create(@PathVariable String name) {
        return ResponseEntity.ok(affairService.create(name));
    }
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody AffairDto affairDto) {
        return ResponseEntity.ok(affairService.update(affairDto));
    }
    @GetMapping("/get/id/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(affairService.getById(id));
    }
    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(affairService.getAll());
    }
}
