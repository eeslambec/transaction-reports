package uz.pdp.transactionreports.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.transactionreports.dto.CustomerCRUDDto;
import uz.pdp.transactionreports.dto.CustomerUpdateDto;
import uz.pdp.transactionreports.entity.Customer;
import uz.pdp.transactionreports.service.CustomerService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CustomerCRUDDto customer) {
        return ResponseEntity.ok(customerService.create(customer));
    }
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody CustomerUpdateDto customer) {
        return ResponseEntity.ok(customerService.update(customer));
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(customerService.getById(id));
    }
    @GetMapping("/get/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(customerService.getAll());
    }
    @GetMapping("/get/phone-number/{phone-number}")
    public ResponseEntity<?> getByPhoneNumber(@PathVariable String phoneNumber) {
        return ResponseEntity.ok(customerService.getByPhoneNumber(phoneNumber));
    }
}
