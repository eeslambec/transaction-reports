package uz.pdp.transactionreports.service;

import org.springframework.stereotype.Service;
import uz.pdp.transactionreports.dto.CustomerCRUDDto;
import uz.pdp.transactionreports.dto.CustomerUpdateDto;
import uz.pdp.transactionreports.entity.Customer;

import java.util.List;
import java.util.UUID;

@Service
public interface CustomerService {
    Customer create(CustomerCRUDDto customerCRUDDto);
    Customer update(CustomerUpdateDto customerUpdateDto);
    Customer getById(UUID id);
    List<Customer> getAll();
    Customer getByPhoneNumber(String phoneNumber);
}
