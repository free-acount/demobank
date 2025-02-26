package com.example.demobank.service.impl;

import com.example.demobank.entity.Customer;
import com.example.demobank.exception.CustomerNotFoundException;
import com.example.demobank.repository.CustomerRepository;
import com.example.demobank.service.CustomerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CustomerServiceImpl implements CustomerService {

    CustomerRepository customerRepository;

    @Override
    public Customer getCustomerById(Long id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);

        if (optionalCustomer.isPresent()) {
            return optionalCustomer.get();
        } else {
            throw new CustomerNotFoundException(String.format("Customer with id %s not found!!!", id));
        }
    }

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isPresent()) {
            Customer newCustomer = optionalCustomer.get();

            newCustomer.setName(customer.getName());
            newCustomer.setSurname(customer.getSurname());
            newCustomer.setCif(customer.getCif());
            newCustomer.setIdNumber(customer.getIdNumber());

            return customerRepository.save(newCustomer);
        } else {
            throw new CustomerNotFoundException(String.format("Customer with id %s not found!!!", id));
        }
    }

    @Override
    public boolean deleteCustomerById(Long id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);

        if (optionalCustomer.isPresent()) {
            customerRepository.deleteById(id);
            return true;
        } else {
            throw new CustomerNotFoundException(String.format("Customer with id %s not found!!!", id));
        }
    }

    @Override
    public Customer findCustomerByNameAndSurname(String name, String surname) {
        return customerRepository.findCustomerByNameAndSurname(name, surname);
    }
}
