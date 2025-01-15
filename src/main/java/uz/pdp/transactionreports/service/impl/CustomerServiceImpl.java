package uz.pdp.transactionreports.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.transactionreports.dto.CustomerCRUDDto;
import uz.pdp.transactionreports.dto.CustomerUpdateDto;
import uz.pdp.transactionreports.entity.Affair;
import uz.pdp.transactionreports.entity.Customer;
import uz.pdp.transactionreports.exception.NotFoundException;
import uz.pdp.transactionreports.repository.CustomerRepository;
import uz.pdp.transactionreports.service.AffairService;
import uz.pdp.transactionreports.service.CustomerService;
import uz.pdp.transactionreports.utils.Validations;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final AffairService affairService;

    @Override
    public Customer create(CustomerCRUDDto customerCRUDDto) {
        Affair byId = affairService.getById(customerCRUDDto.getAffairId());
        return customerRepository.save(Customer.builder()
                .firstName(customerCRUDDto.getFirstName())
                .lastName(customerCRUDDto.getLastName())
                .middleName(customerCRUDDto.getMiddleName())
                .affair(byId)
                .phoneNumber(customerCRUDDto.getPhoneNumber())
                .build());
    }

    @Override
    public Customer update(CustomerUpdateDto customerUpdateDto) {
        Customer customer = customerRepository.findById(customerUpdateDto.getId()).orElseThrow(
                () -> new NotFoundException("Customer"));
        return customerRepository.save(Customer.builder()
                .id(customer.getId())
                .firstName(Validations.requireNonNullElse(customerUpdateDto.getFirstName(), customer.getFirstName()))
                .lastName(Validations.requireNonNullElse(customerUpdateDto.getLastName(), customer.getLastName()))
                .middleName(Validations.requireNonNullElse(customer.getMiddleName(), customer.getMiddleName()))
                .phoneNumber(Validations.requireNonNullElse(customer.getPhoneNumber(), customer.getPhoneNumber()))
                .build());
    }

    @Override
    public Customer getById(UUID id) {
        return customerRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Customer"));
    }

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumber(phoneNumber).orElseThrow(
                () -> new NotFoundException("Customer"));
    }
}
